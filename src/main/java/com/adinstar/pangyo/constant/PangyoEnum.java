package com.adinstar.pangyo.constant;

public class PangyoEnum {
    public enum UserStatus { MEMBER, DELETED }
    public enum PostStatus { SERVICE, DELETED }
    public enum CampaignStatus { SERVICE, DELETED }
    public enum CampaignCandidateStatus { SERVICE, DELETED, SELECTED }
    public enum ContentType { POST, CANDIDATE, CAMPAIGN, STAR }
    public enum CommentStatus { SERVICE, DELETED }
    public enum ExecutionRuleType { CAMPAIGN, CANDIDATE, AD }
    public enum ExecutionRuleStatus { READY, RUNNING, DONE, END }
    public enum LovKey { COMMENT, POST, LIKE, SUPPORT }
    public enum PolicyKey { COMMENT, POST, LIKE,
                            CANDIDATE_TURN, CANDIDATE_DONE, CAMPAIGN_TURN, CAMPAIGN_DONE, AD_TURN, AD_DONE,
                            CAMPAIGN_SNAPSHOT_TERM, STAR_SNAPSHOT_TERM,
                            CAMPAIGN_RANK_BENEFITS }
    public enum ActionType { LIKE, POLL, SUPPORT, JOIN }
    public enum AccountType { KAKAO }

    public enum CheckingType { ID, OBJECT }
}
