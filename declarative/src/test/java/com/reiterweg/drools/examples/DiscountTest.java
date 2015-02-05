package com.reiterweg.drools.examples;

import com.reiterweg.drools.examples.dto.Client;
import com.reiterweg.drools.examples.dto.Purchase;
import com.reiterweg.drools.examples.dto.PaymentType;
import com.reiterweg.drools.examples.helper.ScoreCardHelper;
import com.reiterweg.drools.examples.service.impl.EmailServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import java.util.ArrayList;
import java.util.List;

public class DiscountTest {

    private static final String DRL_PATH = "com/reiterweg/drools/examples/rules/Discount.drl";

    @Test
    public void withStatelessSession() {
        StatelessKieSession kieSessionStateless = DroolsManager.createStatelessSession(DRL_PATH);
        kieSessionStateless.setGlobal("emailService", EmailServiceImpl.getInstance());

        List<Client> clients = new ArrayList<Client>();

        Client clientPurchaseScoreCredit = new Client("Jane Doe", ScoreCardHelper.generate());
        clientPurchaseScoreCredit.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 1000.0));
        clients.add(clientPurchaseScoreCredit);

        Client clientPurchaseScoreDebit = new Client("Jane Bloggs", ScoreCardHelper.generate());
        clientPurchaseScoreDebit.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 1000.0));
        clients.add(clientPurchaseScoreDebit);

        Client clientPurchaseScoreCash = new Client("Jane Roe", ScoreCardHelper.generate());
        clientPurchaseScoreCash.getPurchases().add(new Purchase(PaymentType.CASH, 1000.0));
        clients.add(clientPurchaseScoreCash);

        Client clientPurchasesScore = new Client("Jane Smith", ScoreCardHelper.generate());
        clientPurchasesScore.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 1000.0));
        clientPurchasesScore.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 1000.0));
        clientPurchasesScore.getPurchases().add(new Purchase(PaymentType.CASH, 1000.0));
        clients.add(clientPurchasesScore);

        Client clientPurchaseNoScoreCredit = new Client("John Doe");
        clientPurchaseNoScoreCredit.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 1000.0));
        clients.add(clientPurchaseNoScoreCredit);

        Client clientPurchaseNoScoreDebit = new Client("John Bloggs");
        clientPurchaseNoScoreDebit.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 1000.0));
        clients.add(clientPurchaseNoScoreDebit);

        Client clientPurchaseNoScoreCash = new Client("John Roe");
        clientPurchaseNoScoreCash.getPurchases().add(new Purchase(PaymentType.CASH, 1000.0));
        clients.add(clientPurchaseNoScoreCash);

        Client clientPurchasesNoScore = new Client("John Smith");
        clientPurchasesNoScore.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 1000.0));
        clientPurchasesNoScore.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 1000.0));
        clientPurchasesNoScore.getPurchases().add(new Purchase(PaymentType.CASH, 1000.0));
        clients.add(clientPurchasesNoScore);

        Client clientPurchasesNoScoreAffiliate = new Client("Louis Smith");
        clientPurchasesNoScoreAffiliate.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 2000.0));
        clientPurchasesNoScoreAffiliate.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 3000.0));
        clientPurchasesNoScoreAffiliate.getPurchases().add(new Purchase(PaymentType.CASH, 4000.0));
        clients.add(clientPurchasesNoScoreAffiliate);

        for (Client client : clients) {
            kieSessionStateless.execute(client);

            for (Purchase purchase : client.getPurchases()) {
                kieSessionStateless.execute(purchase);
            }
        }

        Assert.assertNotEquals(Long.valueOf(0L), clientPurchaseScoreCredit.getScoreCard());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchaseScoreCredit.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchaseScoreCredit.getPurchases().get(0).getTotal());

        Assert.assertNotEquals(Long.valueOf(0), clientPurchaseScoreDebit.getScoreCard());
        Assert.assertEquals(Double.valueOf(15.0), clientPurchaseScoreDebit.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(850.0), clientPurchaseScoreDebit.getPurchases().get(0).getTotal());

        Assert.assertNotEquals(Long.valueOf(0), clientPurchaseScoreCash.getScoreCard());
        Assert.assertEquals(Double.valueOf(20.0), clientPurchaseScoreCash.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(800.0), clientPurchaseScoreCash.getPurchases().get(0).getTotal());

        Assert.assertNotEquals(Long.valueOf(0), clientPurchasesScore.getScoreCard());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchasesScore.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchasesScore.getPurchases().get(0).getTotal());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchasesScore.getPurchases().get(1).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchasesScore.getPurchases().get(1).getTotal());
        Assert.assertEquals(Double.valueOf(15.0), clientPurchasesScore.getPurchases().get(2).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(850.0), clientPurchasesScore.getPurchases().get(2).getTotal());

        Assert.assertEquals(Long.valueOf(0L), clientPurchaseNoScoreCredit.getScoreCard());
        Assert.assertEquals(Double.valueOf(0.0), clientPurchaseNoScoreCredit.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(1000.0), clientPurchaseNoScoreCredit.getPurchases().get(0).getTotal());

        Assert.assertEquals(Long.valueOf(0), clientPurchaseNoScoreDebit.getScoreCard());
        Assert.assertEquals(Double.valueOf(5.0), clientPurchaseNoScoreDebit.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(950.0), clientPurchaseNoScoreDebit.getPurchases().get(0).getTotal());

        Assert.assertEquals(Long.valueOf(0), clientPurchaseNoScoreCash.getScoreCard());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchaseNoScoreCash.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchaseNoScoreCash.getPurchases().get(0).getTotal());

        Assert.assertEquals(Long.valueOf(0), clientPurchasesNoScore.getScoreCard());
        Assert.assertEquals(Double.valueOf(0.0), clientPurchasesNoScore.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(1000.0), clientPurchasesNoScore.getPurchases().get(0).getTotal());
        Assert.assertEquals(Double.valueOf(5.0), clientPurchasesNoScore.getPurchases().get(1).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(950.0), clientPurchasesNoScore.getPurchases().get(1).getTotal());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchasesNoScore.getPurchases().get(2).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchasesNoScore.getPurchases().get(2).getTotal());

        Assert.assertNotEquals(Long.valueOf(0), clientPurchasesNoScoreAffiliate.getScoreCard());
    }

    @Test
    public void withStatefulSession() {
        KieSession kieSessionStateful = DroolsManager.createStatefulSession(DRL_PATH);
        kieSessionStateful.setGlobal("emailService", EmailServiceImpl.getInstance());

        List<Client> clients = new ArrayList<Client>();

        Client clientPurchaseScoreCredit = new Client("Jane Doe", ScoreCardHelper.generate());
        clientPurchaseScoreCredit.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 1000.0));
        clients.add(clientPurchaseScoreCredit);

        Client clientPurchaseScoreDebit = new Client("Jane Bloggs", ScoreCardHelper.generate());
        clientPurchaseScoreDebit.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 1000.0));
        clients.add(clientPurchaseScoreDebit);

        Client clientPurchaseScoreCash = new Client("Jane Roe", ScoreCardHelper.generate());
        clientPurchaseScoreCash.getPurchases().add(new Purchase(PaymentType.CASH, 1000.0));
        clients.add(clientPurchaseScoreCash);

        Client clientPurchasesScore = new Client("Jane Smith", ScoreCardHelper.generate());
        clientPurchasesScore.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 1000.0));
        clientPurchasesScore.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 1000.0));
        clientPurchasesScore.getPurchases().add(new Purchase(PaymentType.CASH, 1000.0));
        clients.add(clientPurchasesScore);

        Client clientPurchaseNoScoreCredit = new Client("John Doe");
        clientPurchaseNoScoreCredit.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 1000.0));
        clients.add(clientPurchaseNoScoreCredit);

        Client clientPurchaseNoScoreDebit = new Client("John Bloggs");
        clientPurchaseNoScoreDebit.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 1000.0));
        clients.add(clientPurchaseNoScoreDebit);

        Client clientPurchaseNoScoreCash = new Client("John Roe");
        clientPurchaseNoScoreCash.getPurchases().add(new Purchase(PaymentType.CASH, 1000.0));
        clients.add(clientPurchaseNoScoreCash);

        Client clientPurchasesNoScore = new Client("John Smith");
        clientPurchasesNoScore.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 1000.0));
        clientPurchasesNoScore.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 1000.0));
        clientPurchasesNoScore.getPurchases().add(new Purchase(PaymentType.CASH, 1000.0));
        clients.add(clientPurchasesNoScore);

        Client clientPurchasesNoScoreAffiliate = new Client("Louis Smith");
        clientPurchasesNoScoreAffiliate.getPurchases().add(new Purchase(PaymentType.CREDIT_CARD, 2000.0));
        clientPurchasesNoScoreAffiliate.getPurchases().add(new Purchase(PaymentType.DEBIT_CARD, 3000.0));
        clientPurchasesNoScoreAffiliate.getPurchases().add(new Purchase(PaymentType.CASH, 4000.0));
        clients.add(clientPurchasesNoScoreAffiliate);

        for (Client client : clients) {
            kieSessionStateful.insert(client);

            for (Purchase purchase : client.getPurchases()) {
                kieSessionStateful.insert(purchase);
            }
        }

        kieSessionStateful.fireAllRules();
        kieSessionStateful.dispose();

        Assert.assertNotEquals(Long.valueOf(0L), clientPurchaseScoreCredit.getScoreCard());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchaseScoreCredit.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchaseScoreCredit.getPurchases().get(0).getTotal());

        Assert.assertNotEquals(Long.valueOf(0), clientPurchaseScoreDebit.getScoreCard());
        Assert.assertEquals(Double.valueOf(15.0), clientPurchaseScoreDebit.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(850.0), clientPurchaseScoreDebit.getPurchases().get(0).getTotal());

        Assert.assertNotEquals(Long.valueOf(0), clientPurchaseScoreCash.getScoreCard());
        Assert.assertEquals(Double.valueOf(20.0), clientPurchaseScoreCash.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(800.0), clientPurchaseScoreCash.getPurchases().get(0).getTotal());

        Assert.assertNotEquals(Long.valueOf(0), clientPurchasesScore.getScoreCard());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchasesScore.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchasesScore.getPurchases().get(0).getTotal());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchasesScore.getPurchases().get(1).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchasesScore.getPurchases().get(1).getTotal());
        Assert.assertEquals(Double.valueOf(15.0), clientPurchasesScore.getPurchases().get(2).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(850.0), clientPurchasesScore.getPurchases().get(2).getTotal());

        Assert.assertEquals(Long.valueOf(0L), clientPurchaseNoScoreCredit.getScoreCard());
        Assert.assertEquals(Double.valueOf(0.0), clientPurchaseNoScoreCredit.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(1000.0), clientPurchaseNoScoreCredit.getPurchases().get(0).getTotal());

        Assert.assertEquals(Long.valueOf(0), clientPurchaseNoScoreDebit.getScoreCard());
        Assert.assertEquals(Double.valueOf(5.0), clientPurchaseNoScoreDebit.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(950.0), clientPurchaseNoScoreDebit.getPurchases().get(0).getTotal());

        Assert.assertEquals(Long.valueOf(0), clientPurchaseNoScoreCash.getScoreCard());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchaseNoScoreCash.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchaseNoScoreCash.getPurchases().get(0).getTotal());

        Assert.assertEquals(Long.valueOf(0), clientPurchasesNoScore.getScoreCard());
        Assert.assertEquals(Double.valueOf(0.0), clientPurchasesNoScore.getPurchases().get(0).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(1000.0), clientPurchasesNoScore.getPurchases().get(0).getTotal());
        Assert.assertEquals(Double.valueOf(5.0), clientPurchasesNoScore.getPurchases().get(1).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(950.0), clientPurchasesNoScore.getPurchases().get(1).getTotal());
        Assert.assertEquals(Double.valueOf(10.0), clientPurchasesNoScore.getPurchases().get(2).getTotalDiscount());
        Assert.assertEquals(Double.valueOf(900.0), clientPurchasesNoScore.getPurchases().get(2).getTotal());

        Assert.assertNotEquals(Long.valueOf(0), clientPurchasesNoScoreAffiliate.getScoreCard());
    }

}
