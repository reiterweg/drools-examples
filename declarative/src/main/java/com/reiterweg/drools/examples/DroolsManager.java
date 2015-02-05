package com.reiterweg.drools.examples;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class DroolsManager {

    private static KieContainer getContainer() {
        KieServices kieServices = KieServices.Factory.get();

        return kieServices.getKieClasspathContainer();
    }

    public static StatelessKieSession createStatelessSession(String... drlResourcesPaths) {
        return getContainer().newStatelessKieSession("kieSessionStateless");
    }

    public static KieSession createStatefulSession(String... drlResourcesPaths) {
        return getContainer().newKieSession("kieSessionStateful");
    }

}
