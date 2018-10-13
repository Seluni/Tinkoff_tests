package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class BasePage {

    private final SelenideElement spinner = $(".spinner"),
        quantityOfProductsInShoppingCart = $("div[class='r-cart-items'] div span"),
        logoutButton = $("#js-logout");
    public final ElementsCollection productsPreviewInShoppingCart = $$(".cart-preview__product");

    @Step("Navigate to \"{url}\"")
    public static void openPage(final String url) {
        Selenide.open(url);
        waitForPageToLoad();
    }

    public static void waitForPageToLoad() {
        Selenide.Wait().until(
            (ExpectedCondition<Boolean>) driver -> executeJavaScript("return document.readyState")
                .equals("complete"));
    }

}

