package com.highway65.demo.batch.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Log4j2
public class StepProcessListener extends  AbstractProcessListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        displayMemory();
        return null;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        displayMemory();
    }

}
