package com.example.workflow;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.inject.Named;

@Named
public class CheckMail implements JavaDelegate {

    CheckCvr checkCvr = new CheckCvr();

    private final Logger logger = LoggerFactory.getLogger(CheckMail.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        String input = (String) delegateExecution.getVariable(delegateExecution.getVariableNames().toString().replaceAll("[\\[\\]]", ""));
        String mail = input.split(" : ")[0].trim();
        String cvr = input.split(" : ")[1].trim();
        logger.info("Mail= " + mail);
        logger.info("cvr= " + cvr);

        int mailTrue = 0;
        for (String s : checkCvr.checkingForMail(cvr)) {
            if (mail.equals(s)) {
                mailTrue++;
            }

        }
        System.out.println(mailTrue);
        if (mailTrue > 0) {
            delegateExecution.setVariable("MailExists", true);
            //:todo send to kafka mail service
        } else {
            delegateExecution.setVariable("MailExists", false);
            delegateExecution.setVariable("Mail", mail);
        }
    }
}
