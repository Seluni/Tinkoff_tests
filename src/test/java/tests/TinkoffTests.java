package tests;

import com.codeborne.selenide.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TinkoffTests extends BaseTest {

    @Tag("Regression")
    @Description("Simple check payments")
    @Feature("Tinkoff main test")
    @Test
    public void when_DoingTestTask_Expect_ToDoItPerfect() {

        //1.
        open(Configuration.baseUrl);

        //2.
        ElementsCollection footerLinks = $$(".footer__Footer-common__link_383TZ");
        SelenideElement paymentLink = footerLinks.findBy(text("Платежи"));
        paymentLink.click();

        //4.
        $(byText("ЖКХ")).click();

        //3.
        if ($(".Link__link_3805p.Link__link_color_blue_10po6.Link__link_type_simple_l_2v_.Link__link_nodecorated_2q71R").getText() != "Москве"){
            $(".PaymentsCatalogHeader__regionSelect_1Muyd").click();
            $(".ui-input__field").setValue("Москва").pressEnter();
            $(byText("г. Москва")).click();
        }
        else {
            return;
        }
        $(".PaymentsCatalogHeader__regionSelect_1Muyd").shouldHave(attribute("aria-label", "Москве"));

        //5.
        ElementsCollection itemProvider = $$(by("data-qa-file", "UIMenuItemProvider"));
        String provider = itemProvider.first().getText();
        itemProvider.first()
            .shouldHave(text(provider))
            .click();
        String jkhTitle = $(".Text__text_alignDesktop_left_1xu1i").getText();

        //6.
        ElementsCollection tabs = $$(".Tab__tab_23tRq");
        tabs.findBy(text("оплатить")).click();

        //7.
        //Код плательщика
        $(byName("provider-payerCode")).setValue("1");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле неправильно заполнено"));
        refresh();
        $(byName("provider-payerCode")).setValue("1").setValue(null);
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле обязательное"));
        refresh();

        //За какой период оплачиваете коммунальные услуги
        $(byName("provider-period")).setValue("1");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле заполнено некорректно"));
        refresh();

        $(byName("provider-period")).setValue("33.3333");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле заполнено некорректно"));
        refresh();

        $(byName("provider-period")).setValue("1").setValue(null);
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле обязательное"));
        refresh();

        ElementsCollection otherInputs = $$(".Input__valueContent_primary_3sxF0");
        //Сумма добровольного страхования
        otherInputs.first().setValue("-2");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле заполнено неверно"));
        refresh();

        otherInputs.first().setValue("999999999999999999999999999999");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле заполнено неверно"));
        refresh();

        //Сумма платежа
        $(".ui-form__fieldset.ui-form__fieldset_inline.ui-form__row_amount").click();
        otherInputs.get(1).setValue("-2");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле заполнено неверно"));
        refresh();

        $(".ui-form__fieldset.ui-form__fieldset_inline.ui-form__row_amount").click();
        otherInputs.get(1).setValue("5");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Минимум — 10 \u20BD"));
        refresh();

        $(".ui-form__fieldset.ui-form__fieldset_inline.ui-form__row_amount").click();
        otherInputs.get(1).setValue("16000");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Максимум — 15 000 \u20BD"));
        refresh();

        $(".ui-form__fieldset.ui-form__fieldset_inline.ui-form__row_amount").click();
        otherInputs.get(1).setValue("500");
        tabs.findBy(text("оплатить")).click();
        otherInputs.first().setValue("600");
        tabs.findBy(text("оплатить")).click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Сумма добровольного страхования не может быть больше итоговой суммы."));


        //8.
        paymentLink.click();

        //9.
        $(".Input__valueContent_1Os4v.Input__valueContent_alone_2RBHi.Input__valueContent_primary_3sxF0").setValue(provider);

        //10.
        ElementsCollection gridColumn = $$(".Grid__column_3qcJA.Grid__column_size_12_2AOcu.Grid__column_sizeMobile_12_1mA7y");
        gridColumn.first().shouldHave(text(provider));
        gridColumn.first().click();

        //11.
        $(".Text__text_alignDesktop_left_1xu1i").shouldHave(text(jkhTitle));

        //12.
        paymentLink.click();
        $(byText("ЖКХ")).click();

        //13.
        if ($(".Link__link_3805p.Link__link_color_blue_10po6.Link__link_type_simple_l_2v_.Link__link_nodecorated_2q71R").getText() != "Санкт-Петербурге"){
            $(".PaymentsCatalogHeader__regionSelect_1Muyd").click();
            $(".ui-input__field").setValue("г. Санкт-Петербург").pressEnter();
            $(byText("г. Санкт-Петербург")).click();
        }
        else {
            return;
        }
        $(".PaymentsCatalogHeader__regionSelect_1Muyd").shouldHave(attribute("aria-label", "Санкт-Петербурге"));

        //14.
        ElementsCollection itemProvider2 = $$(by("data-qa-file", "UIMenuItemProvider"));

        Assertions.assertNotEquals(itemProvider2.first().getText(), provider);

    }
}
