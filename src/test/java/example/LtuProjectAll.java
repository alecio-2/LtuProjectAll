package example;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LtuProjectAll {
    private static final Logger LOGGER = LoggerFactory.getLogger(LtuProjectAll.class);

    @Test
    @Order(1)
    public void setUp() {
        // Set the download folder

        Configuration.downloadsFolder = System.getProperty("user.dir") + "\\target\\downloads";

        // Open the LTU website
        LOGGER.info("Starting program");
        try {
            Configuration.browser = "chrome";
            open("https://www.ltu.se");

            if (title().isEmpty()) {
                LOGGER.error("Failed to open the web page: empty title");
            } else {
                LOGGER.info("Successfully opened the web page: " + title());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to open the web page: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void clickCookiesButton() {
        // Click the "Accept" button on the cookies notification

        try {
            if ($(Selectors.byId("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).exists()) {
                $(Selectors.byId("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll")).click();

                LOGGER.info("The cookies notification is closed");
            } else {
                LOGGER.info("The cookies notification is not displayed");
            }
        } catch (ElementNotFound e) {
            LOGGER.error("The cookies button not found");
        }
    }

    @Test
    @Order(3)
    public void clickStudentLink() {
        //Click on the Student link
        try {
            $(Selectors.byXpath("/html/body/header/div[2]/div[1]/div[1]/div[3]/div/a[1]"))
                    .shouldBe(Condition.visible).click();
            LOGGER.debug("The STUDENT link is clicked");

        } catch (ElementNotFound e) {
            LOGGER.error("The STUDENT link is not displayed");
        }
    }

    @Test
    @Order(4)
    public void clickMittLTULink() {
        //Click on the Student link
        try {
            //Press the Mitt LTU element a[onclick*='group']
            $(byCssSelector("a[onclick*='group']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Mitt LTU link is clicked");

        } catch (ElementNotFound e) {
            LOGGER.error("The Mitt LTU link is not displayed");
        }
    }

    @Test
    @Order(5)
    public void credentials() {
        //Get credentials from Json file and enter them in the login form
        //Json file with username and password to login
        File jsonFile = new File("C:\\temp\\ltu.json");

        String username = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            username = jsonNode.get("ltuCredentials").get("email").asText();
            password = jsonNode.get("ltuCredentials").get("password").asText();

            LOGGER.info("Json file is read");
        } catch (IOException e) {
            LOGGER.error("An error occurred while reading the Json file: " + e.getMessage());
        }

        //Enter the username
        try {
            $(byId("username")).sendKeys(username);
            LOGGER.debug("The username is entered");
        } catch (Exception e) {
            LOGGER.error("The username  is not entered");
        }

        //Enter the password
        try {
            $(byId("password")).sendKeys(password);
            LOGGER.debug("The password is entered");
        } catch (Exception e) {
            LOGGER.error("The password is not entered");
        }

        //Click on submit button
        try {
            $(Selectors.byName("submit")).shouldBe(Condition.visible).click();
            LOGGER.debug("The login button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The login button is not found");
        }
    }

    @Test
    @Order(6)
    public void clickTentamen() {
        //Click on the Tentamen header from left list
        try {
            $(byText("Tentamen")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Tentamen header from left list is clicked");
            Selenide.sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The Tentamen text is not found");
        }
    }

    @Test
    @Order(7)
    public void clickTentamensanmälan() {
        //Click on the Tentamensanmälan from left list
        try {
            $(byText("Tentamensanmälan")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Tentamensanmälan from left list is clicked");
            Selenide.sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The Tentamensanmälan text is not found");
        }
    }

    @Test
    @Order(8)
    public void clickTentamensanmälanLink() {
        //Click on the Tentamensanmälan from the page
        try {
            $(Selectors.byXpath("/html/body/div/div[1]/div[4]/div[2]/div[3]/div/div/div[1]/div/div/div/div/div[1]/p[5]/a"))
                    .shouldBe(Condition.visible).click();
            LOGGER.debug("The Tentamensanmälan from the page is clicked");
            Selenide.sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The Tentamensanmälan from the page is not found");
        }
    }

    @Test
    @Order(9)
    public void pressLoginKronox() {
        //Click on the login button
        try {
            $(Selectors.byXpath("//span[contains(text(),'Logga in')]")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Logga in button from Kronox is clicked");
            Selenide.sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The Logga in button from Kronox is not found");
        }
    }

    @Test
    @Order(10)
    public void credentialsKronox() {
        //Get credentials from Json file and enter them in the login form
        //Json file with username and password to login

        File jsonFile = new File("C:\\temp\\ltu.json");

        String username = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            username = jsonNode.get("ltuCredentials").get("email").asText();
            password = jsonNode.get("ltuCredentials").get("password").asText();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Enter the username
        try {
            $(By.cssSelector("#login_username")).setValue(username);
            LOGGER.debug("The username is entered");
            Selenide.sleep(1000);
        } catch (Exception e) {
            LOGGER.error("The username  is not entered");
        }

        //Enter the password
        try {
            $(By.cssSelector("#login_password")).setValue(password);
            LOGGER.debug("The password is entered");
            Selenide.sleep(1000);
        } catch (Exception e) {
            LOGGER.error("The password is not entered");
        }

        //Simulate pressing the Enter key to perform login
        try {
            $(By.cssSelector("#login_password")).pressEnter();
            LOGGER.debug("The Enter key is pressed");
            Selenide.sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The Enter key is not pressed");
        }
    }

    @Test
    @Order(11)
    public void clickAktivitetsanmälanTab() {
        //Click on the tab with Aktivitetsanmälan
        try {
            $(By.xpath("//b[text()='Aktivitetsanmälan']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Aktivitetsanmälan tab is clicked");
            LOGGER.info("Showing the examination information");
            Selenide.sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The Aktivitetsanmälan tab is not found");
        }
    }

    @Test
    @Order(12)
    public void takeAScreenshotTenta() throws IOException {
        //Wait and then take a screenshot and save it to the target/Files folder
        try {
            //Take the screenshot
            File screenshot = Screenshots.takeScreenShotAsFile();
            //Create the directory if it does not exist
            Path directoryPath = Paths.get("target", "screenshots");
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
            //Move the screenshot to the target/Files folder
            String path = System.getProperty("user.dir") + "\\target\\screenshots\\final_examination.jpeg";
            Files.move(screenshot.toPath(), new File(path).toPath(), StandardCopyOption.REPLACE_EXISTING);
            LOGGER.debug("Tenta screenshot taken and saved to: " + path);
        } catch (IOException e) {
            LOGGER.error("An error occurred while taking tenta screenshot: " + e.getMessage());
        }
    }

    @Test
    @Order(13)
    public void logoutKronox() {
        //Logout from Kronox
        try {
            //Press the logout button with the locator element a[class='greenbutton'] span
            $(By.cssSelector("a.greenbutton > span")).click();
            Selenide.sleep(3000);
            LOGGER.debug("The Kronox logout button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Kronox logout button is not found");
        }
    }

    @Test
    @Order(14)
    public void moveBackToLtu() {
        //Open again the LTU web page
        try {
            open("https://www.ltu.se");
            if (title().isEmpty()) {
                LOGGER.error("Failed to open the web page: empty title");
            } else {
                LOGGER.info("Successfully opened the web page: " + title());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to open the web page: " + e.getMessage());
        }
    }

    @Test
    @Order(15)
    public void clickStudentLink2() {
        //Click on the Student link
        try {
            $(Selectors.byXpath("/html/body/header/div[2]/div[1]/div[1]/div[3]/div/a[1]"))
                    .shouldBe(Condition.visible).click();
            LOGGER.debug("The STUDENT link is clicked");

        } catch (ElementNotFound e) {
            LOGGER.error("The STUDENT link is not displayed");
        }
    }

    @Test
    @Order(16)
    public void clickMittLTULink2() {
        //Click on the Student link
        try {
            //Press the Mitt LTU element a[onclick*='group']
            $(byCssSelector("a[onclick*='group']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Mitt LTU link is clicked");

        } catch (ElementNotFound e) {
            LOGGER.error("The Mitt LTU link is not displayed");
        }
    }

    @Test
    @Order(17)
    public void clickIntygLink() {
        //Go to ladok
        //Press the Intyg link element with id starting with something like "yui_patched_v3_11_0_1"
        // and ending with something like "271"
        try {
            $(byCssSelector("a[id^='yui_patched_v3_11_0_1'][id$='271']")).shouldBe(Condition.visible)
                    .click();
            LOGGER.debug("The Intyg link is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Intyg link is not found");
        }
    }

    @Test
    @Order(18)
    public void changeFocus() {
        //Change focus from one windows to another
        try {
            Selenide.switchTo().window(1);
            LOGGER.debug("The focus is changed to another window");
        } catch (Exception e) {
            LOGGER.error("The focus is not changed to another window");
        }
    }

    @Test
    @Order(19)
    public void choosingLTUinLadok() {
        //Here from ladok
        //Press the button to accept cookies
        try {
            $(byCssSelector("button.btn.btn-light")).shouldBe(Condition.visible).click();
            LOGGER.debug("The cookies notification is closed");
        } catch (ElementNotFound e) {
            LOGGER.error("The cookies notification button is not found");
        }

        //Press to choose the university
        try {
            $(byCssSelector("a[aria-label='Inloggning via ditt lärosäte / Login via your university']"))
                    .shouldBe(Condition.visible).click();
            LOGGER.debug("The button to login is pressed");
        } catch (ElementNotFound e) {
            LOGGER.error("The button to login is not found");
        }

        //Press on the search field
        try {
            $(byId("searchinput")).shouldBe(Condition.visible).click();
            LOGGER.debug("The search field is pressed");
        } catch (ElementNotFound e) {
            LOGGER.error("The search field is not found");
        }

        //Input LTU in the searchbar
        try {
            $(byId("searchinput")).sendKeys("LTU");
            LOGGER.debug("LTU text is entered");
        } catch (ElementNotFound e) {
            LOGGER.error("LTU text is not entered");
        }

        //Choose LTU from the list
        try {
            $(byCssSelector("div.primary")).shouldBe(Condition.visible).click();
            LOGGER.debug("Choosing LTU");
        } catch (ElementNotFound e) {
            LOGGER.error("LTU is not found");
        }
    }

    @Test
    //@Disabled
    @Order(12)
    public void credentials2() {
        //Get credentials from Json file and enter them in the login form
        //Json file with username and password to login
        File jsonFile = new File("C:\\temp\\ltu.json");

        String username = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            username = jsonNode.get("ltuCredentials").get("email").asText();
            password = jsonNode.get("ltuCredentials").get("password").asText();

            LOGGER.info("Json file is read");
        } catch (IOException e) {
            LOGGER.error("An error occurred while reading the Json file: " + e.getMessage());
        }

        //Enter the username
        try {
            $(byId("username")).sendKeys(username);
            LOGGER.debug("The username is entered");
        } catch (ElementNotFound e) {
            LOGGER.error("The username element is not found");
        } catch (Exception e) {
            LOGGER.error("An error occurred while entering the username: " + e.getMessage());
        }

        //Enter the password
        try {
            $(byId("password")).sendKeys(password);
            LOGGER.debug("The password is entered");
        } catch (ElementNotFound e) {
            LOGGER.error("The password is not entered");
        } catch (Exception e) {
            LOGGER.error("An error occurred while entering the username: " + e.getMessage());
        }

        //Click on submit button
        try {
            $(Selectors.byName("submit")).shouldBe(Condition.visible).click();
            LOGGER.debug("The login button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The login button is not found");
        }
    }

    @Test
    @Order(21)
    public void clickMenuButton() {
        //Click on the Menu button
        try {
            $(byCssSelector("svg[data-icon='bars']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Menu button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Menu button is not found");
        }
    }

    @Test
    @Order(22)
    public void clickTranscriptButton() {
        //Click on the Transcript/Intyg button
        try {
            $(byCssSelector("a[href*='intyg']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Transcript/Intyg button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Transcript/Intyg button is not found");
        }
    }

    @Test
    @Order(23)
    public void downloadFile() {
        //Click on pdf icon to start downloading the last Transcript
        try {
            $(byCssSelector("span.me-1")).shouldBe(Condition.visible).click();
            LOGGER.debug("The pdf icon is clicked");
            LOGGER.info("The last created transcript is downloaded");
        } catch (ElementNotFound e) {
            LOGGER.error("The pdf icon is not found");
        }
    }

    @Test
    @Order(24)
    public void clickMenuButton2() {
        //Click on the Menu button
        try {
            $(byCssSelector("svg[data-icon='bars']")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Menu button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Menu button is not found");
        }
    }

    @Test
    @Order(25)
    public void logoutLadok() {
        //Logout from Ladok
        try {
            //Press this xPath /html/body/ladok-root/div/ladok-sido-meny/nav/div[2]/ul[3]/li/a
            $(byXpath("/html/body/ladok-root/div/ladok-sido-meny/nav/div[2]/ul[3]/li/a"))
                    .shouldBe(Condition.visible).click();
            Selenide.sleep(3000);
            LOGGER.debug("The Ladok logout button is clicked");
        } catch (ElementNotFound e) {
            LOGGER.error("The Ladok logout button is not found");
        }
    }

    @Test
    @Order(26)
    public void moveBackToLtu2() {
        //Open again the LTU web page
        try {
            open("https://www.ltu.se");
            if (title().isEmpty()) {
                LOGGER.error("Failed to open the web page: empty title");
            } else {
                LOGGER.info("Successfully opened the web page: " + title());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to open the web page: " + e.getMessage());
        }
    }

    @Test
    @Order(27)
    public void clickSearchButton() {
        try {
            $(Selectors.byId("ltu-menu-search")).shouldBe(Condition.visible).click();
            LOGGER.debug("The search button is clicked");
            sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The search button is not displayed");
        }
    }

    @Test
    @Order(28)
    public void clickSearchButton2() {
        // Click the "Search" button to input
        try {
            $(Selectors.byId("cludo-search-bar-input")).shouldBe(Condition.visible).click();
            LOGGER.debug("The search input is clicked");
            sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The search input is not displayed");
        }
    }

    @Test
    @Order(29)
    public void inputText() {
        // Enter the "Search" text
        try {
            $(Selectors.byId("cludo-search-bar-input")).sendKeys("I0015N");
            LOGGER.debug("The search text is entered");
            sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The search text is not displayed");
        }
    }

    @Test
    @Order(30)
    public void clickSearchButton3() {
        // Click the "Search" button
        try {
            $(byText("Sök")).shouldBe(Condition.visible).click();
            LOGGER.debug("The search input is clicked");
            sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The search input is not displayed");
        }
    }

    @Test
    @Order(31)
    public void clickKursplan() {
        //Click on the Kursplan link
        try {
            $(byText("Kursplan")).shouldBe(Condition.visible).click();
            LOGGER.debug("The Kursplan is clicked");
            sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The Kursplan is not displayed");
        }
    }

    @Test
    @Order(32)
    public void chooseV23() {
        // Click on the text V23 to choose year 2023
        try {
            $(byText("V23")).shouldBe(Condition.visible).click();
            LOGGER.debug("The V23 is clicked");
            sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The V23 is not displayed");
        }
    }

    @Test
    @Order(33)
    public void downloadPDF() {
        // Click on the PDF link to download the course syllabus
        try {
            sleep(2000);
            $(Selectors.byAttribute("alt", "PDF")).shouldBe(Condition.visible).click();
            LOGGER.debug("The PDF is clicked");
            LOGGER.info("The PDF is downloaded");
            sleep(1000);
        } catch (ElementNotFound e) {
            LOGGER.error("The PDF is not displayed");
        }
    }

    @Test
    @Order(99)
    public void result() {
        LOGGER.info("The test is finished");
        LOGGER.info("-");
        Selenide.sleep(4000);
    }
}