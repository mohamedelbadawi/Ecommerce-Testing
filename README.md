﻿# E-Commerce Website Automation Project

This is a personal automation project to test the functionality of an e-commerce website using **Selenium WebDriver** and **TestNG**. The goal is to practice automated testing with various web elements and ensure the site's key features are working as expected.

## Overview

This project automates tests for an e-commerce website's core functionalities:

- **Login and Registration**
- **Product Search and Checkout**
- **Cart Management**
- **Review and Rating System**
- **Category and Brand Management**

I’ve used the **Page Object Model (POM)** design pattern combined with **Factory Pattern** to allow tests to be run across different browsers. It’s easy to switch between **Chrome**, **Firefox**, and **Edge** by changing the browser name in the `properties.config` file.

Additionally, I am using **Data-Driven Development (DDD)** with **JSON files** to provide test data. This allows for easy modification and reusability of test data, which makes the tests more flexible and efficient.

## Key Features

- **Multi-Browser Support**: Supports running tests on Chrome, Firefox, and Edge using the Factory Pattern.
- **Page Object Model (POM)**: Keeps the test scripts clean and maintainable.
- **TestNG**: Used for executing tests and generating reports.
- **Data-Driven Development (DDD)**: Test data is stored in JSON files to enable reusability and flexibility.
- **Easy Configuration**: The test configuration is simple to modify for browser choice, headless mode, and wait times.
- **Allure Reports**: **Allure** for more detailed and visually appealing test reports.
## Upcoming Features

- **CI/CD Integration**: Continuous Integration and Continuous Delivery pipelines will be added to run tests on every commit.
- **Parallel Test Execution**: Future plans include parallel test execution to speed up the test process.

## Technologies Used

- **Selenium WebDriver**: For browser automation.
- **TestNG**: For test execution and reporting.
- **Maven**: Dependency management and build tool.
- **Java**: Programming language for test scripts.
- **Page Object Model (POM)**: To improve test maintainability.
- **Factory Pattern**: To manage browser instances dynamically.
- **JSON Files**: For storing test data to enable data-driven testing.

## How to Run the Project
- Clone the project
- open it in Intellij IDE or any IDE
- Run any of test suites such as "sanity-suite.xml" file

