package com.highway65.demo.batch.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

import com.highway65.demo.batch.param.JobParam;
import com.highway65.demo.batch.param.RequestParam;
import org.springframework.batch.core.*;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JobProcessListener extends  AbstractProcessListener implements JobExecutionListener {

	@Override
	public void afterJob(JobExecution jobExecution) {
		displayMemory();
		String ipAddr = "UNKNOWN";
		try {
			InetAddress iaddr = InetAddress.getLocalHost();
			ipAddr=iaddr.getHostAddress();
			
		} catch (UnknownHostException uhe) {
			log.error("Unknown IP Address : {}",uhe.getMessage());
		}
		StringBuffer str=new StringBuffer(ipAddr);
		str.append("[")
				.append(jobExecution.getJobParameters().getLong(JobParam.DATE_REQUESTL.name()))
				.append(" | ")
				.append(jobExecution.getJobParameters().getString(RequestParam.FORCE.name()))
				.append(" ] ");
		if(!ExitStatus.COMPLETED.getExitCode().equals(jobExecution.getExitStatus().getExitCode())){
			Collection<StepExecution> steps = jobExecution.getStepExecutions();
			for (StepExecution step :steps) {
				if (!ExitStatus.COMPLETED.getExitCode().equals(step.getExitStatus().getExitCode())){
					str.append("-").append(step.getStepName())
							.append("-").append(step.isTerminateOnly()?"T":"F");
					int index = step.getExitStatus().getExitDescription().lastIndexOf(";");
					if(index==-1){
						str.append("-").append(step.getExitStatus().getExitDescription());
					}else{
						str.append("-").append(step.getExitStatus().getExitDescription().substring(index+1).trim());

					}
					if(!ExitStatus.STOPPED.getExitCode().equals(step.getExitStatus().getExitCode())){
						jobExecution.setStatus(BatchStatus.FAILED);
						jobExecution.setExitStatus(new ExitStatus(ExitStatus.FAILED.getExitCode(),str.toString()));
					}else{
						jobExecution.setExitStatus(new ExitStatus(step.getExitStatus().getExitCode(),str.toString()));
					}
					log.info("[ {} - {} ] END with STATUS [ {}:{} ] Parameter [{}]",
							jobExecution.getJobInstance().getJobName(),
							jobExecution.getId(),
							jobExecution.getExitStatus().getExitCode(),
							str.toString(),
							jobExecution.getJobParameters());
					return;
				}
			}
		}
		log.info("[ {} -{} ] END with Status [{} :{}] Parameter [{}]",
				jobExecution.getJobInstance().getJobName(),
				jobExecution.getId(),
				jobExecution.getExitStatus().getExitCode(),
				str.toString(),
				jobExecution.getJobParameters());
		jobExecution.setExitStatus(new ExitStatus(jobExecution.getExitStatus().getExitCode(),str.toString()));
		return;
	}
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		displayMemory();
		log.info("[{} {} ] START with Parameter [{}]",
				jobExecution.getJobInstance().getJobName(),
				jobExecution.getId(),
				jobExecution.getJobParameters());
	}

}
