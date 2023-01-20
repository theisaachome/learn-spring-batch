package com.highway65.demo.batch.listener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class AbstractProcessListener {

    private static final long GIGABYTE=1024 * 1024 * 1024;
    protected   void  displayMemory(){
        Runtime rt=Runtime.getRuntime();
        log.debug("Max Memory [{}], Total Memory [{}], Free Memory [{}] ",
                (rt.maxMemory()/GIGABYTE),
                (rt.totalMemory()/GIGABYTE),
                (rt.freeMemory()/GIGABYTE));
    }
}
