package web.pages.LeetCode.StringsPro;

public class ExcelSheetColumnTitle {
    public static void main(String[] args) {
        System.out.println(convertToTitle(26));
    }

    public static String convertToTitle(int columnNumber) {
        String result = "";

        while (columnNumber > 0) {

            columnNumber--;

            int reminder = columnNumber % 26;

            result = (char) ('A' + reminder) + result;

            columnNumber = columnNumber / 26;
        }

        return result;
    }

}
