<html>
<head>
    <title>CampaignCandidate</title>
</head>
<body>
<div>
    <div style="border: 1px solid; padding: 10px; width:400px">
        <div>
            <img src="${campaignCandidate.user.profileImg!"http://t1.daumcdn.net/profile/TfdXX_AUCLw0"}"  style="width: 50px; height: 50px">
            <strong>${campaignCandidate.user.name!"없는유저"}</strong>
            <span>${campaignCandidate.dateTime.reg!}</span>
        </div>

        <level>${campaignCandidate.title!}</level>
        <p>${campaignCandidate.body!}</p>
        <#if campaignCandidate.bannerImg?has_content>
            <img src="${campaignCandidate.bannerImg!}" style="width: 200px; height: 200px">
        </#if>

        <div>
            <span>투표 ${campaignCandidate.pollCount!}</span>
        </div>

        <#--
        <label for="id">id:</label>
        <input type="text" name="id" value="<#if campaignCandidate??>${campaignCandidate.id}</#if>" />
        <label for="starId">starId:</label>
        <input type="text" name="starId" value="<#if campaignCandidate??>${campaignCandidate.starId}</#if>"/>
        <label for="userId">userId:</label>
        <input type="text" name="userId" value="<#if campaignCandidate??>${campaignCandidate.userId}</#if>"/>
        <label for="body">body:</label>
        <input type="text" name="body" value="<#if campaignCandidate??>${campaignCandidate.body!}</#if>" />
        <label for="img">img:</label>
        <input type="img" name="img" value="<#if campaignCandidate??>${campaignCandidate.img!}</#if>" />
        <label for="viewCount">viewCount:</label>
        <input type="text" name="viewCount" value="<#if campaignCandidate??>${campaignCandidate.viewCount}</#if>"/>
        <label for="likeCount">likeCount:</label>
        <input type="text" name="likeCount" value="<#if campaignCandidate??>${campaignCandidate.likeCount}</#if>" />
        <label for="commentCount">commentCount:</label>
        <input type="text" name="commentCount" value="<#if campaignCandidate??>${campaignCandidate.commentCount}</#if>"/>
        <label for="status">status:</label>
        <input type="text" name="status" value="<#if campaignCandidate??>${campaignCandidate.status}</#if>"/>
        <label for="regDttm">regDttm:</label>
        <input type="text" name="regDttm" value="<#if campaignCandidate?? && campaignCandidate.dateTime??>${campaignCandidate.dateTime.reg}</#if>" />
        <label for="upDttm">upDttm:</label>
        <input type="text" name="upDttm" value="<#if campaignCandidate?? && campaignCandidate.dateTime ??>${campaignCandidate.dateTime.up}</#if>" />
        -->
    </div>

    <strong> TODO : 댓글모듈</strong>
</body>
</html>