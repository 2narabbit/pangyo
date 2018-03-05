<html>
<head>
    <title>CampaignCandidate/Edit</title>
</head>
<body>
<#if campaignCandidate??>
<form action="/campaign-candidate/change" method="POST">
 <div>
     <label for="id">id:</label>
     <input type="text" name="id" value="<#if campaignCandidate??>${campaignCandidate.id}</#if>" />
 </div>
<div>
    <label for="status">status:</label>
    <input type="text" name="status" value="<#if campaignCandidate??>${campaignCandidate.status}</#if>"/>
</div>
    <div>
        <label for="regDttm">regDttm:</label>
        <input type="text" name="reg" value="<#if campaignCandidate?? && campaignCandidate.dateTime ??>${campaignCandidate.dateTime.reg}</#if>" />
    </div>
    <div>
        <label for="upDttm">upDttm:</label>
        <input type="text" name="up" value="<#if campaignCandidate?? && campaignCandidate.dateTime ??>${campaignCandidate.dateTime.up}</#if>" />
    </div>
<#else>
<form action="/campaign-candidate/add" method="POST">

</#if>
    <div>
        <label for="starId">starId:</label>
        <input type="text" name="star.id" value="<#if campaignCandidate??>${campaignCandidate.star.Id!}</#if>"/>
    </div>
    <div>
        <label for="userId">userId:</label>
        <input type="text" name="user.id" value="<#if campaignCandidate??>${campaignCandidate.user.id!}</#if>"/>
    </div>
    <div>
        <label for="title">title:</label>
        <input type="text" name="title" value="<#if campaignCandidate??>${campaignCandidate.title!}</#if>" />
    </div>
    <div>
        <label for="body">body:</label>
        <input type="text" name="body" value="<#if campaignCandidate??>${campaignCandidate.body!}</#if>" />
    </div>
    <div>
        <label for="randingUrl">randingUrl:</label>
        <input type="text" name="randingUrl" value="<#if campaignCandidate??>${campaignCandidate.randingUrl!}</#if>" />
    </div>
    <div>
        <label for="bannerImg">bannerImg:</label>
        <input type="text" name="bannerImg" value="<#if campaignCandidate??>${campaignCandidate.bannerImg!}</#if>" />
    </div>
    <div>
        <label for="pollCount">pollCount:</label>
        <input type="text" name="pollCount" value="<#if campaignCandidate??>${campaignCandidate.pollCount}</#if>"/>
    </div>

    <div class="button">
        <button><#if campaignCandidate??>수정<#else>등록</#if></button>
    </div>

</form>
</body>
</html>