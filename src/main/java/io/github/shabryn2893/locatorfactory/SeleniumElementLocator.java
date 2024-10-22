package io.github.shabryn2893.locatorfactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import io.github.shabryn2893.utils.LoggerUtils;

/**
 * Implementation of the {@link ElementLocator} interface for locating elements using Selenium WebDriver.
 */
public class SeleniumElementLocator implements ElementLocator<WebElement> {

    private static final Logger logger = LoggerUtils.getLogger(SeleniumElementLocator.class);
    private WebDriver driver;
    
    /**
     * Constructs a SeleniumElementLocator with the specified WebDriver.
     *
     * @param driver the Selenium {@link WebDriver} instance used for locating elements.
     */
    public SeleniumElementLocator(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Locates a single element based on the specified locator type and value.
     *
     * @param locatorType the type of the locator (e.g., "ID", "XPATH", "CSS").
     * @param locatorValue the value of the locator (e.g., the actual ID, XPath expression).
     * @return the located {@link WebElement}, or null if no element is found.
     */
    @Override
    public WebElement locateElement(String locatorType, String locatorValue) {
        WebElement element = null;
        try {
            element = findElement(locatorType, locatorValue);
        } catch (NoSuchElementException e) {
            logger.info("NoSuchElementException: {}", e.getMessage());
            assert false; // Consider handling this more gracefully in production code
        } catch (WebDriverException e) {
            logger.info("WebDriverException: {}", e.getMessage());
            assert false; // Consider handling this more gracefully in production code
        }
        return element;
    }

    /**
     * Locates multiple elements based on the specified locator type and value.
     *
     * @param locatorType the type of the locator (e.g., "ID", "XPATH", "CSS").
     * @param locatorValue the value of the locator (e.g., the actual ID, XPath expression).
     * @return a list of located {@link WebElement} instances, or an empty list if no elements are found.
     */
    @Override
    public List<WebElement> locateElements(String locatorType, String locatorValue) {
        List<WebElement> elements = null;
        try {
            elements = findElements(locatorType, locatorValue);
        } catch (NoSuchElementException e) {
            logger.info("NoSuchElementException: {}", e.getMessage());
            assert false; // Consider handling this more gracefully in production code
        } catch (WebDriverException e) {
            logger.info("WebDriverException: {}", e.getMessage());
            assert false; // Consider handling this more gracefully in production code
        }
        return elements;
    }

    /**
     * Helper method to find a single element based on the locator type and value.
     * 
     * @param locatorType the type of the locator.
     * @param locatorValue the value of the locator.
     * @return the found {@link WebElement}.
     */
    private WebElement findElement(String locatorType, String locatorValue) {
        switch (locatorType.toUpperCase()) {
            case "ID":
                return driver.findElement(By.id(locatorValue));
            case "XPATH":
                return driver.findElement(By.xpath(locatorValue));
            case "LINKTEXT":
                return driver.findElement(By.linkText(locatorValue));
            case "CSS":
                return driver.findElement(By.cssSelector(locatorValue));
            case "NAME":
                return driver.findElement(By.name(locatorValue));
            case "TAG":
                return driver.findElement(By.tagName(locatorValue));
            case "PARTIALLINKTEXT":
                return driver.findElement(By.partialLinkText(locatorValue));
            default:
                return driver.findElement(By.className(locatorValue)); // Fallback to class name
        }
    }

    /**
     * Helper method to find multiple elements based on the locator type and value.
     * 
     * @param locatorType the type of the locator.
     * @param locatorValue the value of the locator.
     * @return a list of found {@link WebElement} instances.
     */
    private List<WebElement> findElements(String locatorType, String locatorValue) {
        switch (locatorType.toUpperCase()) {
            case "ID":
                return driver.findElements(By.id(locatorValue));
            case "XPATH":
                return driver.findElements(By.xpath(locatorValue));
            case "LINKTEXT":
                return driver.findElements(By.linkText(locatorValue));
            case "CSS":
                return driver.findElements(By.cssSelector(locatorValue));
            case "NAME":
                return driver.findElements(By.name(locatorValue));
            case "TAG":
                return driver.findElements(By.tagName(locatorValue));
            case "PARTIALLINKTEXT":
                return driver.findElements(By.partialLinkText(locatorValue));
            default:
                return driver.findElements(By.className(locatorValue)); // Fallback to class name
        }
    }
}
