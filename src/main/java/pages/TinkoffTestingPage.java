package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;

public class TinkoffTestingPage {
    public SelenideElement footerLink(String text){
        return $$(".footer__Footer-common__link_383TZ").findBy(text(text));
    }

    public SelenideElement firstProvider(){
        return $$(".UIMenu__link_enabledFade_2a9MA").first();
    }

    public SelenideElement JKUTabs(String name){
        return $$(".Tab__tab_23tRq").findBy(text(name));
    }

    public ElementsCollection gridColumn(){
        return $$(".Grid__column_3qcJA.Grid__column_size_12_2AOcu.Grid__column_sizeMobile_12_1mA7y");
    }
}
