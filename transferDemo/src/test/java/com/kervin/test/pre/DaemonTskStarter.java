package com.kervin.test.pre;

import com.kervin.enumerate.DataSourceEnum;
import com.kervin.utils.asyntsk.AsynTskService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DaemonTskStarter {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        AsynTskService asynTskService1 = new AsynTskService(DataSourceEnum.DataSource1);
        AsynTskService asynTskService2 = new AsynTskService(DataSourceEnum.DataSource2);
        scheduledExecutorService.scheduleWithFixedDelay(asynTskService1, 1, 30, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(asynTskService2, 1, 30, TimeUnit.SECONDS);
        System.out.println("守护进程-异步任务自动任务已启动");
    }

}