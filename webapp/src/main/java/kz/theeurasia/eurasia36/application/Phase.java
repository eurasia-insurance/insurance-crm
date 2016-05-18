package kz.theeurasia.eurasia36.application;

public enum Phase {
    CALCULATION, // calculating policy cost
    EXPRESS_ORDER_FORM, // filling-up express order form
    ONLINE_ORDER_FORM, // filling-up online form
    PAYMENT, // choose a payment method for online order
    FINAL, // final page

    //
    ;
}
