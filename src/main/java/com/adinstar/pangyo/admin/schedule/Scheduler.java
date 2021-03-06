package com.adinstar.pangyo.admin.schedule;

import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/*
 * 스케줄러는 해당 task에 대한 수행 시간을 관리하는 역할
 * 굳이 룰은 없지만 덜 혼란스럽게 스케줄 메소드명과 tasks의 메소드명은 동일하게 부탁드립니다+_+b
 */

@Component
public class Scheduler {

    @Value("${runnableHostname}")
    private String runnableHostname;

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Scheduled(cron = "0 */10 23 * * SUN")
    public void addExecutionRules() {
        exec(() -> scheduledTasks.addExecutionRules());
    }

    @Scheduled(cron = "0 0,1 0 * * ?")
    public void proceedExecutionRules() {
        exec(() -> scheduledTasks.proceedExecutionRules());
    }

    @Scheduled(cron = "0 0,30 * * * ?")
    public void snapshotForCampaign() {
        exec(() -> scheduledTasks.snapshotForCampaign());
    }

    @Scheduled(cron = "0 5,35 * * * ?")
    public void snapshotForStar() {
        exec(() -> scheduledTasks.snapshotForStar());
    }

    private boolean isRunnableHost(String runnableHostname) {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ignore) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(hostname) || !hostname.contains(runnableHostname)) {
            return false;
        }
        return true;
    }

    private void exec(Runnable runnable) {
        try {
            if (isRunnableHost(runnableHostname)) {
                runnable.run();
            }
        } catch (Exception e) {
        }
    }
}
