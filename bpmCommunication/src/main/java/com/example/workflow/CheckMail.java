package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.concurrent.TimeUnit;
import javax.json.*;

import javax.inject.Named;

@Named
public class CheckMail implements JavaDelegate {


    private final Logger logger = LoggerFactory.getLogger(CheckMail.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String mail = (String) delegateExecution.getVariable(delegateExecution.getVariableNames().toString().replaceAll("[\\[\\]]", ""));
        logger.info("Mail= " + mail);

        if (mail.equals("Hello@Meew.dk")){
            delegateExecution.setVariable("MailExists", true);
        } else {
            delegateExecution.setVariable("MailExists", false);
        }
    }
//        delegateExecution.setVariable("Mail", mail);
}
