package listeners;

import com.epam.reportportal.listeners.ItemStatus;
import com.epam.reportportal.service.Launch;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ReportPortalCucumberReporter
        implements ConcurrentEventListener {

    private final Map<UUID, ScenarioContext> scenarios =
            new ConcurrentHashMap<>();

    private static class ScenarioContext {

        private final String scenarioName;

        private int stepNumber = 0;

        ScenarioContext(String scenarioName) {
            this.scenarioName = scenarioName;
        }
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {

        publisher.registerHandlerFor(
                TestCaseStarted.class,
                this::handleTestCaseStarted
        );

        publisher.registerHandlerFor(
                TestStepStarted.class,
                this::handleTestStepStarted
        );

        publisher.registerHandlerFor(
                TestStepFinished.class,
                this::handleTestStepFinished
        );

        publisher.registerHandlerFor(
                TestCaseFinished.class,
                this::handleTestCaseFinished
        );
    }

    private void handleTestCaseStarted(TestCaseStarted event) {

        UUID scenarioId =
                event.getTestCase().getId();

        String scenarioName =
                event.getTestCase().getName();

        scenarios.put(
                scenarioId,
                new ScenarioContext(scenarioName)
        );

        System.out.println();
        System.out.println(
                "🧪 RP Scenario: " + scenarioName
        );

        Launch launch = getLaunch();

        if (launch == null) {
            System.out.println(
                    "⚠ ReportPortal launch unavailable for scenario."
            );
            return;
        }

        launch.getStepReporter().sendStep(
                ItemStatus.INFO,
                "Scenario: " + scenarioName
        );
    }

    private void handleTestStepStarted(TestStepStarted event) {

        TestStep testStep =
                event.getTestStep();

        if (!(testStep instanceof PickleStepTestStep)) {
            return;
        }

        UUID scenarioId =
                event.getTestCase().getId();

        ScenarioContext context =
                scenarios.get(scenarioId);

        if (context == null) {
            return;
        }

        PickleStepTestStep pickleStep =
                (PickleStepTestStep) testStep;

        String stepText =
                pickleStep.getStep().getText();

        context.stepNumber++;

        String reportStep =
                context.stepNumber
                        + ". "
                        + stepText;

        System.out.println(
                "   ▶ RP Step: " + reportStep
        );

        Launch launch = getLaunch();

        if (launch == null) {
            return;
        }

        launch.getStepReporter().sendStep(
                ItemStatus.INFO,
                reportStep
        );
    }

    private void handleTestStepFinished(TestStepFinished event) {

        TestStep testStep =
                event.getTestStep();

        if (!(testStep instanceof PickleStepTestStep)) {
            return;
        }

        Launch launch = getLaunch();

        if (launch == null) {
            return;
        }

        switch (event.getResult().getStatus()) {

            case PASSED:

                launch.getStepReporter()
                        .finishPreviousStep(
                                ItemStatus.PASSED
                        );

                break;

            case FAILED:

                launch.getStepReporter()
                        .finishPreviousStep(
                                ItemStatus.FAILED
                        );

                break;

            case SKIPPED:

                launch.getStepReporter()
                        .finishPreviousStep(
                                ItemStatus.SKIPPED
                        );

                break;

            case UNDEFINED:
            case AMBIGUOUS:

                launch.getStepReporter()
                        .finishPreviousStep(
                                ItemStatus.FAILED
                        );

                break;

            default:

                launch.getStepReporter()
                        .finishPreviousStep();

                break;
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {

        UUID scenarioId =
                event.getTestCase().getId();

        ScenarioContext context =
                scenarios.remove(scenarioId);

        if (context == null) {
            return;
        }

        System.out.println(
                "   ✓ RP Scenario finished: "
                        + context.scenarioName
        );
    }

    private Launch getLaunch() {

        Launch launch =
                Launch.currentLaunch();

        if (launch == null ||
                launch == Launch.NOOP_LAUNCH) {

            return null;
        }

        return launch;
    }
}