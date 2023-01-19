package com.highway65.demo.batch.file;

import java.io.File;
import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AbstractFileName {

	private static final String FILE_DATE_FORMATE="yyyyMMdd";
	private static final String FILE_NAME_SEPARATER="_";
	private static final String FILE_SQUENCE_FORMAT="%05d";
	
	private File directory;
	private String jobName;
	private String stepName;
	private LocalDate jobDate;
	private long jobId;
	private int sequennceNo;
	private String version;
	private String fileFormat;
	private String dateFormat;
	private String delimiter;
	
	public AbstractFileName(String jobName,String stepName,LocalDate jobDate) {
		if(isNullOrBlank(stepName)) {
			throw new IllegalArgumentException("Step name is empty or Blank");
		}
		if(isNullOrBlank(jobName)) {
			throw new IllegalArgumentException("Job name is empty or Blank");
		}
		if(jobDate==null) {
			throw new IllegalArgumentException("Job Date is empty or null");
		}
		this.jobName=jobName;
		this.stepName=stepName;
		this.jobDate=jobDate;
		this.version="1";
		this.dateFormat=FILE_DATE_FORMATE;
		this.delimiter=FILE_NAME_SEPARATER;
		this.sequennceNo=-1;
		
	}
	
	public AbstractFileName directory(File directory) {
		if(directory==null || !directory.exists()) {
			throw new IllegalArgumentException("Directory is null or not exists");
		}
		this.directory=directory;
		return this;
	}
	private static boolean isNullOrBlank(String val) {
		return val==null || val.trim().length()==0;
	}
	private static String formatName(String value) {
		return value.trim().replaceAll("\\s+","").toLowerCase();
				
	}
	
	
}
