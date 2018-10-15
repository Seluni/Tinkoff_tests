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
import pages.TinkoffTestingPage;

public class TinkoffTests extends BaseTest {

    @Tag("Regression")
    @Description("Simple check payments")
    @Feature("Tinkoff main test")
    @Test
    public void when_DoingTestTask_Expect_ToDoItPerfect() {

        //1.
        open(Configuration.baseUrl);

        //2.
        SelenideElement payment = new TinkoffTestingPage().footerLink("Платежи");
        payment.click();

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
        SelenideElement JKUMoscow = new TinkoffTestingPage().firstProvider();
        String provider = JKUMoscow.getText();
            JKUMoscow
            .shouldHave(text(provider))
            .click();

        //6.
        SelenideElement payTab = new TinkoffTestingPage().JKUTabs("оплатить");
        payTab.click();
        String jkhTitle = $(".Text__text_alignDesktop_left_1xu1i").getText();

        //7.
        //Код плательщика
        SelenideElement errorField = $(by("data-qa-file", "UIFormRowError"));
        $(byName("provider-payerCode")).setValue("1");
        payTab.click();
        $(by("data-qa-file", "UIFormRowError")).shouldHave(text("Поле неправильно заполнено"));
        refresh();
        $(byName("provider-payerCode")).setValue("1").setValue(null);
        payTab.click();
        errorField.shouldHave(text("Поле обязательное"));
        refresh();

        //За какой период оплачиваете коммунальные услуги
        $(byName("provider-period")).setValue("1");
        payTab.click();
        errorField.shouldHave(text("Поле заполнено некорректно"));
        refresh();

        $(byName("provider-period")).setValue("33.3333");
        payTab.click();
        errorField.shouldHave(text("Поле заполнено некорректно"));
        refresh();

        $(byName("provider-period")).setValue("1").setValue(null);
        payTab.click();
        errorField.shouldHave(text("Поле обязательное"));
        refresh();

        ElementsCollection otherInputs = $$(".Input__valueContent_primary_3sxF0");
        //Сумма добровольного страхования
        otherInputs.first().setValue("-2");
        payTab.click();
        errorField.shouldHave(text("Поле заполнено неверно"));
        refresh();

        otherInputs.first().setValue("999999999999999999999999999999");
        payTab.click();
        errorField.shouldHave(text("Поле заполнено неверно"));
        refresh();

        //Сумма платежа
        $(".ui-form__fieldset.ui-form__fieldset_inline.ui-form__row_amount").click();
        otherInputs.get(1).setValue("-2");
        payTab.click();
        errorField.shouldHave(text("Поле заполнено неверно"));
        refresh();

        $(".ui-form__fieldset.ui-form__fieldset_inline.ui-form__row_amount").click();
        otherInputs.get(1).setValue("5");
        payTab.click();
        errorField.shouldHave(text("Минимум — 10\u20BD"));
        refresh();

        $(".ui-form__fieldset.ui-form__fieldset_inline.ui-form__row_amount").click();
        otherInputs.get(1).setValue("16000");
        payTab.click();
        errorField.shouldHave(text("Максимум — 15 000 \u20BD"));
        refresh();

        $(".ui-form__fieldset.ui-form__fieldset_inline.ui-form__row_amount").click();
        otherInputs.get(1).setValue("500");
        payTab.click();
        otherInputs.first().setValue("600");
        payTab.click();
        errorField.shouldHave(text("Сумма добровольного страхования не может быть больше итоговой суммы."));


        //8.
        payment.click();

        //9.
        $(".Input__valueContent_1Os4v.Input__valueContent_alone_2RBHi.Input__valueContent_primary_3sxF0").setValue(provider);

        //10.
        SelenideElement foundProvider = new TinkoffTestingPage().gridColumn().first();
        foundProvider.shouldHave(text(provider));
        foundProvider.click();

        //11.
        payTab.click();
        $(".Text__text_alignDesktop_left_1xu1i").shouldHave(text(jkhTitle));

        //12.
        payment.click();
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
        SelenideElement spbProvider = new TinkoffTestingPage().firstProvider();
        Assertions.assertNotEquals(spbProvider.getText(), provider);

    }
}
