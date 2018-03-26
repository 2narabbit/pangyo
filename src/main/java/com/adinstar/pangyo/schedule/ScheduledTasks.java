package com.adinstar.pangyo.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
 * 스케줄된 tasks는 실제 수행할 task들을 기록하는 곳!
 * 향후 해당 task를 api로 수행하고 싶을 때도 해당 메소드를 호출하면 됩니다.
 */

@Component
public class ScheduledTasks {

    @Async
    public void scheduleTaskWithCronExpression() {
        System.out.println("Cron Task :: Execution Time - " + LocalDateTime.now());
        System.out.println("Current Thread : " + Thread.currentThread().getName());
    }
}
