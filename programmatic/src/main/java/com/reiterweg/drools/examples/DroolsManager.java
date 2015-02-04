package com.reiterweg.drools.examples;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.*;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.io.ResourceFactory;

import java.util.HashMap;
import java.util.Map;

public class DroolsManager {

    private static KieContainer getContainer(String... drlResourcesPaths) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        for (String drlResourcePath : drlResourcesPaths) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(drlResourcePath));
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);

        kieBuilder.buildAll();

        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new IllegalArgumentException(kieBuilder.getResults().toString());
        }

        ReleaseId releaseId = kieBuilder.getKieModule().getReleaseId();

        return kieServices.newKieContainer(releaseId);
    }

    public static StatelessKieSession createStatelessSession(String... drlResourcesPaths) {
        KieServices kieServices = KieServices.Factory.get();
        KieBaseConfiguration kieBaseConfiguration = kieServices.newKieBaseConfiguration();
        KieBase kieBase = getContainer(drlResourcesPaths).newKieBase(kieBaseConfiguration);
        KieSessionConfiguration kieSessionConfiguration = kieServices.newKieSessionConfiguration();

        return kieBase.newStatelessKieSession(kieSessionConfiguration);
    }

    public static KieSession createStatefulSession(String... drlResourcesPaths) {
        KieServices kieServices = KieServices.Factory.get();
        KieBaseConfiguration kieBaseConfiguration = kieServices.newKieBaseConfiguration();
        KieBase kieBase = getContainer(drlResourcesPaths).newKieBase(kieBaseConfiguration);
        KieSessionConfiguration kieSessionConfiguration = kieServices.newKieSessionConfiguration();

        return kieBase.newKieSession(kieSessionConfiguration, null);
    }

}
