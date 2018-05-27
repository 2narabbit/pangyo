package com.adinstar.pangyo.constant;

public class PangyoEnum {
    public enum UserStatus { MEMBER, DELETED }
    public enum PostStatus { SERVICE, DELETED }
    public enum CampaignStatus { SERVICE, DELETED }
    public enum CampaignCandidateStatus { SERVICE, DELETED, SELECTED }
    public enum ContentType { POST, CANDIDATE, CAMPAIGN, STAR }
    public enum CommentStatus { SERVICE, DELETED }
    public enum ExecutionRuleType { CAMPAIGN, CANDIDATE, AD, CAMPAIGN_SNAPSHOT, STAR_SNAPSHOT }
    public enum ExecutionRuleStatus { READY, RUNNING, DONE, END }
    public enum LovKey { COMMENT, POST, LIKE, SUPPORT }
    public enum PolicyKey { COMMENT, POST, LIKE,
                            CANDIDATE_RANGE, CANDIDATE_DONE_DATE, CAMPAIGN_RANGE, CAMPAIGN_DONE_DATE }
    public enum ActionType { LIKE, POLL, SUPPORT, JOIN }
    public enum AccountType { KAKAO }

    public enum CheckingType { ID, OBJECT }
}
