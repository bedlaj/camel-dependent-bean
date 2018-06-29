package com.github.camelDependent;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
//@Dependent //Dependent is default
public class SomeDependentBean implements Processor {
    private int numOfInvocations = 0;
    private static Logger log = LoggerFactory.getLogger(SomeDependentBean.class);

    public void process(Exchange exchange) throws Exception {
        log.info("This is: "+toString());
        numOfInvocations++;
        if (numOfInvocations!=1){
            throw new IllegalStateException(numOfInvocations+"!=1");
        } else {
            log.info("OK");
        }
    }
}
