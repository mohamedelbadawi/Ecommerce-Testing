# E-Commerce Website Automation Project

This is a personal automation project to test the functionality of an e-commerce website using **Selenium WebDriver** and **TestNG**. The goal is to practice automated testing with various web elements and ensure the site's key features are working as expected.

## Overview

This project automates tests for an e-commerce website's core functionalities:

- **Login and Registration**
- **Product Search and Checkout**
- **Cart Management**
- **Review and Rating System**
- **Category and Brand Management**

I’ve used the **Page Object Model (POM)** design pattern combined with **Factory Pattern** to allow tests to be run across different browsers. It’s easy to switch between **Chrome**, **Firefox**, and **Edge** with only change the browser name in "propreties.config" file.

## Key Features

- **Multi-Browser Support**: Supports running tests on Chrome, Firefox, and Edge using the Factory Pattern.
- **Page Object Model (POM)**: Keeps the test scripts clean and maintainable.
- **TestNG**: Used for executing tests and generating reports.
- **Easy Configuration**: The test configuration is simple to modify for browser choice, headless mode, and wait times.

## Upcoming Features

- **CI/CD Integration**: I'll integrate Continuous Integration and Continuous Delivery pipelines in the future to run tests on every commit.
- **Allure Reports**: I plan to add **Allure** for more detailed and visually appealing test reports.
- **Parallel Test Execution**: Will be implemented to speed up test execution.

## Technologies Used

- **Selenium WebDriver**: Browser automation.
- **TestNG**: Test execution and reporting.
- **Maven**: Dependency management and build tool.
- **Java**: Programming language for test scripts.
- **Page Object Model (POM)**: To improve test maintainability.
- **Factory Pattern**: To manage browser instances dynamically.

## How to Run the Project
- Clone the project
- open it in Intellij IDE or any IDE
- Run any of test suites such as "sanity-suite.xml" file

