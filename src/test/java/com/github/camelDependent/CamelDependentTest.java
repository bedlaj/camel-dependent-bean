package com.github.camelDependent;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;

import javax.naming.Context;

public class CamelDependentTest extends CamelTestSupport {

    private Context context;
    private JndiRegistry registry;

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:in")
                        .to("bean:something?cache=false");
            }
        };
    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry registry = super.createRegistry();
        registry.bind("something", new SomeDependentBean());
        this.context = registry.getContext();
        this.registry = registry;
        return registry;
    }

    @Test
    public void testFreshBeanInContext() throws Exception{
        SomeDependentBean originalInstance = registry.lookup("something", SomeDependentBean.class);
        template.sendBody("direct:in",null);
        context.unbind("something");
        context.bind("something", new SomeDependentBean()); //Bind new instance to Context
        Assert.assertNotSame(registry.lookup("something"), originalInstance); //Passes, the issue is not in JndiRegistry.

        template.sendBody("direct:in",null); //fails, uses cached instance of SameDependentBean
    }
}
