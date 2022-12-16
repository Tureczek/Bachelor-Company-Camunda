package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ExtendedFlow implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String mail = (String) delegateExecution.getVariable("mail");

        delegateExecution.getProcessEngineServices().getRuntimeService()
                .createMessageCorrelation("ExtendedFlow")
                .setVariable("mail", mail)
                .correlate();

    }
}
