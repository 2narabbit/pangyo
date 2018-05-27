package com.adinstar.pangyo.admin.schedule;

import com.adinstar.pangyo.admin.service.RankMaker;
import com.adinstar.pangyo.admin.service.RuleMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
 * 스케줄된 tasks는 실제 수행할 task들을 기록하는 곳!
 * 향후 해당 task를 api로 수행하고 싶을 때도 해당 메소드를 호출하면 됩니다.
 */

@Component
public class ScheduledTasks {

    @Autowired
    private RuleMaker ruleMaker;

    @Autowired
    private RankMaker rankMaker;

    @Async
    public void addExecutionRules() {
        if (!ruleMaker.haveNextTurnRules()) {
            ruleMaker.addExecutionRule();
        }
    }

    @Async
    @Transactional
    public void proceedExecutionRules() {   // 이것도 3개를 병렬로 수행하도록 하는게 좋을 지 이야기하자!
        ruleMaker.proceedFromDoneToEnd();
        ruleMaker.proceedFromRunningToDone();
        ruleMaker.proceedFromReadyToRunning();
    }

    @Async
    public void snapshotForCampaign() {
        rankMaker.snapshotForCampaign();
    }

    @Async
    public void snapshotForStar() {
        rankMaker.snapshotForStar();
    }
}
