package com.highway65.demo.batch.processor;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;

@Log4j2
public abstract class AbstractItemProcessor<T,P> implements ItemProcessor<T,P> {
    private JobParameters jobParameters;
    private long jobId;
    private  String jobName;
    private String stepName;

    @AfterStep
    public ExitStatus afterStep (StepExecution se){
        try {

        }catch (Exception e){

            ExitStatus es = new ExitStatus(ExitStatus.FAILED.getExitCode(),e.getMessage());
            se.setExitStatus(es);
            return  es;
        }
        log.info("[{} - {}] { } - { } PEND [{}] | Parameter [{}]",
                se.getJobExecution().getJobInstance().getJobName(),
                se.getJobExecution().getId(),
                se.getStepName(),
                this.getClass().getName(),
                se.getExitStatus(),
                se.getJobParameters());
        return se.getExitStatus();
    }

    public long getJobId() {
        return jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public String getStepName() {
        return stepName;
    }
    protected abstract void beforeAll(JobParameters jobParameters);
    protected  abstract void  afterAll(JobParameters jobParameters);
    protected  abstract P processing(JobParameters jobParameters,T obj) throws Exception;

    public P process(T obj)throws  Exception{
        return processing(this.jobParameters,obj);
    }
}
