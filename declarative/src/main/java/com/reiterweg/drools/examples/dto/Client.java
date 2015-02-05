package com.reiterweg.drools.examples.dto;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private String name;
    private Long scoreCard = 0L;
    private List<Purchase> purchases = new ArrayList<Purchase>();

    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }

    public Client(String name, Long scoreCard) {
        this.name = name;
        this.scoreCard = scoreCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getScoreCard() {
        return scoreCard;
    }

    public void setScoreCard(Long scoreCard) {
        this.scoreCard = scoreCard;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

}
