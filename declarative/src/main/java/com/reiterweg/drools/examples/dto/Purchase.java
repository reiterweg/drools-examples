package com.reiterweg.drools.examples.dto;

import java.util.HashSet;
import java.util.Set;

public class Purchase {

    private PaymentType paymentType;
    private Double subtotal = 0.0;
    private Double totalDiscount = 0.0;
    private Double total = 0.0;
    private Set<DiscountType> discounts = new HashSet<DiscountType>();

    public Purchase(PaymentType paymentType, Double subtotal) {
        this.paymentType = paymentType;
        this.subtotal = subtotal;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Set<DiscountType> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<DiscountType> discounts) {
        this.discounts = discounts;
    }
}
