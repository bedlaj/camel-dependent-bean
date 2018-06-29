package com.github.camelDependent;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;

import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Startup
@ContextName("cdi-context")
public class MainRouteBuilder extends RouteBuilder {
    public void configure() throws Exception {
        from("timer:test")
                .to("bean:someDependentBean?cache=false");
    }
}
