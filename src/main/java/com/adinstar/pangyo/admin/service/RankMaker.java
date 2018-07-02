package com.adinstar.pangyo.admin.service;

import com.adinstar.pangyo.admin.mapper.StarRankMapper;
import com.adinstar.pangyo.admin.mapper.CampaignRankMapper;
import com.adinstar.pangyo.constant.PangyoEnum;
import com.adinstar.pangyo.model.Policy;
import com.adinstar.pangyo.service.CampaignService;
import com.adinstar.pangyo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/*
 * 스냡샷은 파티션으로 구성하자! 일주일치를 하나의 파티션으로 묶을 수 있을지 고민해야겠다-
 * 해서 일주일 지나면 해당 파티션 날리고!!
 * 회차별 파티션을 만드는게 좋겠군!! --> mysql 파티션을 어떻게 관리하는지 추가로 확인해 보자! 별도 테이블로 관리하면 괜츈할 듯!!ㅎㅎ CAMPAIGN_RANK 와 같은!ㅎㅎ
 * 한달 주기로 trancate 할 수 있도록 해야하는건가? snapshot에서 파티션 관리도 하돌고 ㅎ가ㅣ엔.. 책임이 2개임!
 */

@Service
public class RankMaker {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int SIZE = 1_000;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private StarService starService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private CampaignRankMapper campaignRankMapper;

    @Autowired
    private StarRankMapper starRankMapper;


    BiFunction<Long, Integer, List<Long>> selectCampaign = (offset, size) -> campaignService.getCampaignIdListOrderBySupportCount(offset, size);
    TriConsumer<Long, String, Long> insertCampaignRank = (id, time, rankId) -> campaignRankMapper.insert(id, time, rankId);

    public void snapshotForCampaign() {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0);
        if (!isSnapshotTerm(now, campaignRankMapper.selectLastTime(), PangyoEnum.PolicyKey.CAMPAIGN_SNAPSHOT_TERM)) {
            return;
        }

        insertItemRank(selectCampaign, now.format(formatter), insertCampaignRank);
    }


    BiFunction<Long, Integer, List<Long>> selectStar = (offset, size) -> starService.getStarIdListOrderByFanCount(offset, size);
    TriConsumer<Long, String, Long> insertStarRank = (id, time, rankId) -> starRankMapper.insert(id, time, rankId);

    public void snapshotForStar() {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0);
        if (isSnapshotTerm(now, starRankMapper.selectLastTime(), PangyoEnum.PolicyKey.STAR_SNAPSHOT_TERM)) {
            insertItemRank(selectStar, now.format(formatter), insertStarRank);
        }
    }

    private boolean isSnapshotTerm(LocalDateTime now, LocalDateTime lastTime, PangyoEnum.PolicyKey key) {
        Policy policy = policyService.getPolicyValueByKey(key);

        long hours = Long.valueOf(policy.getValue());
        if (lastTime != null && now.isBefore(lastTime.plusHours(hours))) {
            return false;
        }
        return true;
    }

    private void insertItemRank(BiFunction<Long, Integer, List<Long>> selectItem, String time, TriConsumer<Long, String, Long> insertItemRank) {
        boolean hasMore = true;
        long rankId = 0L;
        while (hasMore) {
            List<Long> itemIdList = selectItem.apply(rankId, SIZE);
            for (Long itemId : itemIdList) {
                rankId = rankId + 1;
                insertItemRank.accept(itemId, time, rankId);
            }
            hasMore = (itemIdList.size() == SIZE);
        }
    }

    @FunctionalInterface
    interface TriConsumer<A, B, C> {

        void accept(A a, B b, C c);

        default TriConsumer<A, B, C> andThen(TriConsumer<? super A, ? super B, ? super C> after) {
            Objects.requireNonNull(after);

            return (x, y, z) -> {
                accept(x, y, z);
                after.accept(x, y, z);
            };
        }
    }
}
