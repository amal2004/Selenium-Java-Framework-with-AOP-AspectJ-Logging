# 🚀 Selenium Parallel Test Automation Framework Using AOP AspectJ Logging

<p align="center">
⚡ Scalable • Thread-Safe • Multi-Browser • AOP Logging • Reporting Engine ⚡
</p>

<p align="center">
☕ Java | 🌐 Selenium | 🧪 TestNG | 📊 Extent Reports | ⚙️ AOP | 🧵 ThreadLocal
</p>

---

# ✨ Overview

A **robust enterprise-level Selenium automation framework** built with:

- Page Object Model (POM)
- Thread-safe execution
- Aspect-Oriented Programming (AOP)
- Advanced failure diagnostics
- Centralized logging system
- Parallel test execution support

Designed for **scalability, maintainability, and production-grade testing**.

---

# ⚡ Key Highlights

✔️ AOP-based Step & Failure Logging  
✔️ Thread-safe Driver Management  
✔️ Smart Screenshot Capture System  
✔️ Dynamic Test Execution Context  
✔️ Real-time Failure Diagnostics  
✔️ Multi-browser support  
✔️ Extent Reports integration  
✔️ Clean layered architecture  

---

# 🧠 Architecture Flow

```
TestNG
   ↓
Step Annotation (@Step)
   ↓
AOP Interceptors
   ↓
BasePage Actions
   ↓
DriverFactory (ThreadLocal)
   ↓
BrowserManager
   ↓
Selenium WebDriver
```

---

# 🏗️ Framework Modules

## 📦 1. Config Manager
- Loads `config.properties`
- Supports system property override
- Handles type-safe configs (int, boolean)

---

## 🧵 2. Driver Factory
- Thread-safe WebDriver using `ThreadLocal`
- Browser initialization logic
- Runtime browser metadata capture
- Safe teardown handling

---

## 🌐 3. Browser Manager
Supported browsers:

- 🟢 Chrome
- 🔵 Firefox
- 🟣 Edge

Includes:
- Headless mode support
- Custom browser options
- WebDriverManager setup

---

## 🧪 4. Base Page Layer
Reusable Selenium actions:

- click()
- type()
- getText()
- waitForElement()
- navigation helpers

Built with explicit waits for stability.

---

## 🧾 5. AOP Layer

### 🔹 Step Logging Aspect
Automatically logs each step:

```
STARTED → EXECUTION → COMPLETED
```

### 🔹 Failure Logging Aspect
Captures:

- ❌ Exception type
- 📍 Locator
- 🌐 Current URL
- ⏱ Execution time
- 📸 Screenshot
- 🧾 Test data
- 🧠 Expected vs Actual result

---

## 📸 Screenshot Utility

Automatically captures screenshots on failure:

```
screenshots/<test>/<timestamp>.png
```

---

## 🧩 Execution Context (ThreadSafe)

Stores runtime data:

- Step name
- Locator
- Test data
- Browser info

Ensures **parallel-safe tracking across threads**

---

## 📊 Reporting System

### Extent Reports Features

- Step-wise logging
- Pass / Fail tracking
- Screenshot attachment
- Thread-safe test execution

---

## 🧪 Sample Test Flow

Example: Registration Test

```
1. Open Registration Page
2. Select Gender
3. Fill Form
4. Submit Form
5. Validate Success Message
```

---

# 🧾 Annotations Used

## ✨ @Step Annotation

Used to automatically log test steps:

```java
@Step("Fill Registration Form")
public void fillForm() { }
```

---

# 🚀 Execution Flow

```
TestNG
  ↓
BaseTest (setup/teardown)
  ↓
DriverFactory (init browser)
  ↓
RegisterPage actions
  ↓
AOP logging (Step + Failure)
  ↓
Extent Report generation
```

---

# ⚙️ Configuration Example

```properties
browser=chrome
base.url=https://demo.nopcommerce.com
headless=false
pageLoadTimeout=60
screenshot.dir=screenshots
```

---

# 🧵 Parallel Execution Support

✔️ DataProvider parallel execution  
✔️ ThreadLocal WebDriver  
✔️ Thread-safe ExtentTest  

---

# 📊 Logging System

## Log Levels

```
INFO
ACTION
STEP
VALIDATION
CONFIG
DRIVER
PASS
FAIL
ERROR
WARNING
```

---

# 💥 Exception Handling

Custom framework exception:

```
FrameworkException
```

Used for:

- Invalid browser
- Missing config
- Driver failures
- Runtime issues

---

# 🧪 Sample Test Data Flow

```
Test Data → Context Storage → AOP Logging → Failure Report
```

---

# 🔥 Key Strengths

✔️ Enterprise-ready structure  
✔️ Highly scalable architecture  
✔️ Clean separation of concerns  
✔️ Easy debugging via AOP  
✔️ Parallel execution safe  
✔️ Minimal flaky tests  

---

# 📁 Reports

Generated report location:

```
reports/index.html
```

---

# 🧑‍💻 Author

**Amal**

---

# ⭐ If you like this project

Give it a ⭐ on GitHub and improve automation together 🚀
