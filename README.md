# Selenium E-Commerce Automation Framework

A Selenium WebDriver + TestNG test automation framework built around the **Page Object Model (POM)**, covering the core user flows of an e-commerce site: login, product browsing/sorting, cart, and checkout.

Target application: [SauceDemo](https://www.saucedemo.com) — a public demo e-commerce site widely used for QA automation practice.

## Why this project

I work as a Manual QA Engineer testing retail, e-commerce, and POS platforms day to day. This framework is my automation practice project, applying the same test scenarios I write manually (login validation, cart behavior, checkout edge cases) as a structured, maintainable Selenium suite — the same POM + Hybrid Framework approach used in production automation frameworks.

## Tech stack

| Tool | Purpose |
|---|---|
| Java 17 | Language |
| Selenium WebDriver 4 | Browser automation |
| TestNG | Test runner & assertions |
| Maven | Build & dependency management |
| WebDriverManager | Auto-resolves the correct ChromeDriver binary |
| GitHub Actions | CI — runs the suite on every push |

## Framework design

```
src/main/java/com/adarshvk/framework/
├── base/BaseTest.java        # WebDriver lifecycle (setup/teardown), shared by every test class
├── pages/                    # Page Object Model - one class per page, no assertions here
│   ├── LoginPage.java
│   ├── ProductsPage.java
│   ├── CartPage.java
│   └── CheckoutPage.java
└── utils/ConfigReader.java   # Reads config.properties (base URL, browser, credentials)

src/test/java/com/adarshvk/tests/
├── LoginTests.java           # Valid login, locked-out user, empty credentials
├── ProductsTests.java        # Inventory listing, add-to-cart, cart badge, price sorting
└── CheckoutTests.java        # End-to-end checkout (happy path + validation error)
```

**Design principles followed:**
- **Page objects hold locators and actions only** — no assertions. Assertions live in test classes, keeping pages reusable across multiple test scenarios.
- **Config is externalized** (`config.properties`), not hardcoded — base URL, browser choice, headless mode, and test credentials can change without touching test code.
- **`BaseTest` centralizes driver setup/teardown** with `@BeforeMethod`/`@AfterMethod`, so every test starts from a clean browser session — no shared/leaked state between tests.

## Test coverage

- **Login**: valid login reaches the Products page; locked-out user is blocked with the correct error; empty credentials are rejected
- **Products**: inventory count is correct; adding items updates the cart badge; cart page reflects items added; price sort (low→high) returns a correctly ordered list
- **Checkout**: valid shipping info completes the order with a confirmation message; missing shipping info is rejected with a validation error

## Running locally

Prerequisites: JDK 17+, Maven, Google Chrome.

```bash
git clone https://github.com/adarshvk1997/selenium-ecommerce-automation-framework.git
cd selenium-ecommerce-automation-framework
mvn test
```

Tests run headless by default (`headless=true` in `src/test/resources/config.properties`). Set it to `false` to watch the browser while tests run locally.

## CI

Every push to `main` runs the full suite via GitHub Actions (`.github/workflows/ci.yml`) — Chrome and JDK are provisioned fresh on Ubuntu runners, and TestNG/Surefire reports are uploaded as build artifacts.

## Possible next steps

- Parallel execution across test classes (TestNG `parallel="classes"`)
- Extend to cross-browser runs (Firefox, Edge) via WebDriverManager
- Add Allure or ExtentReports for richer HTML test reports
