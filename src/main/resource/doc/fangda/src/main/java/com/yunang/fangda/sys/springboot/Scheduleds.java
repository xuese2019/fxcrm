package com.yunang.fangda.sys.springboot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
@Component
public class Scheduleds {


    @Scheduled(cron = "0 0 22 * * ?")
    public void statusCheck() {

    }
}
