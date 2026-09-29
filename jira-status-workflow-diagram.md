# Simple Jira Status Workflow

## 🎯 Main Workflow (Simple)

```
TO DO → IN PROGRESS → IN REVIEW → READY FOR QA → TESTING → DONE
```

## � Detailed Simple Workflow

```
📝 TO DO
  ↓
🔨 IN PROGRESS
  ↓
� IN REVIEW
  ↓
� READY FOR QA
  ↓
🧪 TESTING
  ↓
✅ DONE
```

## � Alternative Paths

### **Bug Found:**
```
� TESTING → 🐛 BUG → 🔨 IN PROGRESS → 🧪 TESTING
```

### **Automation:**
```
 TESTING → 🤖 TO BE AUTOMATED → ⚙️ AUTOMATING → ✅ DONE
```

### **Blocked:**
```
ANY STATUS → 🚫 BLOCKED → (Fixed) → Previous Status
```

## 🎨 Status Categories

**Planning:** TO DO
**Development:** IN PROGRESS, IN REVIEW
**Testing:** READY FOR QA, TESTING
**Done:** DONE
**Issues:** BUG, BLOCKED