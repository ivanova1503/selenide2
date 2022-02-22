package selenide;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.openqa.selenium.*;

import java.io.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class News {

    static boolean serverGotCorrectRequest = false;

    @Test
    void isElementInViewport() {
        //  boolean isInViewPort = executeJavaScript("function isInView(); var element=document.getElementById('arguments[0]');return isInView(element); ","title12");
    }

    @Test
    void compareScreenshots() throws IOException {
        var screenshot = $("div").screenshot();
        var expectedScreenshot = new FileInputStream(new File("abc")).readAllBytes();
    }

    @Test
    void testDropdown() {
        open("https://accenture.com");
        $(".country-icon").click();
        $("#location-recommendation").$(Selectors.byText("USA (English)")).click();
        sleep(5000);
    }

    @Test
    void interceptRequest() {
        Configuration.proxyEnabled = true;
        open("");

        /*var proxy = WebDriverRunner.getSelenideProxy();
        proxy.addResponseFilter("catch response", (response, contents, messageInfo) -> {
            System.out.println(messageInfo.getUrl());
            if (messageInfo.getUrl().contains("data.json")) {
                System.out.println("Responded!");
                serverGotCorrectRequest = true;
            }
        });
        proxy.addRequestFilter("catch response", (request, contents, messageInfo) -> {
            System.out.println("test");
            request.method();
            return null;
        });

         */
        $("button").click();
        sleep(500);
        Assertions.assertTrue(serverGotCorrectRequest);

    }

    @Test
    void setCookie() {
        // all tests
        Cookie cookie = new Cookie("consent_cookies", "true");
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);

        // cookie test
        open();
        // check popup
        // click yes
        Cookie c = WebDriverRunner.getWebDriver().manage().getCookieNamed("consent_cookies");
        Assertions.assertEquals(c.getValue(), "true");
    }

    @Test
    @DisabledIfSystemProperty(named = "selenide.remote", matches = "http.*", disabledReason = "Clipboard not supported on Selenium Grid yet")
    @EnabledIfSystemProperty(named = "environment", matches = "staging", disabledReason = "....")
    void clipboards() {
        open("https://moskva.mts.ru/personal");
        Selenide.clipboard().setText("1234324234");
        $("[name=number]").sendKeys(Keys.COMMAND + "v");
        $("[name=number]").shouldHave(value("(123) 432-42-34"));

        // open page with repository
        // find element with copy url - function
        // click copy-button
        Assertions.assertEquals(Selenide.clipboard().getText(), "https://github.com/selenide/selenide.git");


    }

    @Test
    void ownTextTest() {
        open("https://github.com");
        $("#home-code h3").shouldHave(exactText("Record or rewind any change to your code to keep you and your team in sync."));

    }

    @Test
    void ownText2() {
        open("https://www.tutorialrepublic.com/snippets/preview.php?topic=bootstrap&file=elegant-contact-form");
        $(".alert-info").shouldHave(exactOwnText("on to learn how to customize this layout further. Bootstrap 3 version of this snippet is ."));
    }


    void snapshotsTest() {

        ElementsCollection checkboxes = $$("checkbox").filter(visible);   // Page has 100+  elements
        for (int i = 0; i < 10; i++) {
            checkboxes.get(i).shouldBe(enabled);
        }

        ElementsCollection checkboxesFast = $$("checkbox").filter(visible).snapshot();
        for (int i = 0; i < 10; i++) {
            checkboxesFast.get(i).shouldBe(enabled);
        }

        ElementsCollection collection = $$("").snapshot();
        collection.get(4).shouldHave(text("abc"));
        $("button").click();
        collection.get(5).shouldHave(text("abc"));
    }


}
