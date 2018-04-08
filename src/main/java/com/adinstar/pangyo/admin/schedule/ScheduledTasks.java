package com.adinstar.pangyo.admin.schedule;

import com.adinstar.pangyo.admin.service.Ranker;
import com.adinstar.pangyo.admin.service.RuleMaker;
import com.adinstar.pangyo.constant.PangyoEnum.ExecutionRuleType;
import com.adinstar.pangyo.model.ExecutionRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * 스케줄된 tasks는 실제 수행할 task들을 기록하는 곳!
 * 향후 해당 task를 api로 수행하고 싶을 때도 해당 메소드를 호출하면 됩니다.
 */

@Component
public class ScheduledTasks {

    private static final long AFTER_NEXT_TURN_NUM = 2L;

    @Autowired
    private RuleMaker ruleMaker;

    @Autowired
    private Ranker ranker;

    @Async
    public void settingOfExecutionRule() {
        long runningTurnNum = ruleMaker.getRunningTurnNum();
        List<ExecutionRule> afterNextExecutionRuleList = ruleMaker.getExecutionRuleByTurnNum(runningTurnNum + AFTER_NEXT_TURN_NUM);  // 2회차 뒤 정보를 셋팅함으로써 한주를 벌 수 있습니다.
        if (afterNextExecutionRuleList.size() == ExecutionRuleType.values().length) {  // 신규 타입이 들어오게 되면 해당 조건을 변경해야합니다!
            return;
        }

        ruleMaker.registeredExecutionRule(runningTurnNum + AFTER_NEXT_TURN_NUM);
    }

    @Async
    @Transactional
    public void processedExecutionRule() {
        ruleMaker.processedFromDoneToEnd();
        ruleMaker.processedFromRunningToDone();
        ruleMaker.processedFromReadyToRunning(ruleMaker.getRunningTurnNum());
    }

    @Async
    public void snapshotForCampaign() {
        /*
         * 스냡샷은 파티션으로 구성하자! 일주일치를 하나의 파티션으로 묶을 수 있을지 고민해야겠다-
         * 해서 일주일 지나면 해당 파티션 날리고!!
         * 회차별 파티션을 만드는게 좋겠군!! --> mysql 파티션을 어떻게 관리하는지 추가로 확인해 보자! 별도 테이블로 관리하면 괜츈할 듯!!ㅎㅎ CAMPAIGN_RANK 와 같은!ㅎㅎ
         * 한달 주기로 trancate 할 수 있도록 해야하는건가? snapshot에서 파티션 관리도 하돌고 ㅎ가ㅣ엔.. 책임이 2개임!
         */
        ranker.snapshotForCampaign();
    }
}
