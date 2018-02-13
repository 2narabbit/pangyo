<html>
<head>
    <title>Post/Template List</title>
</head>
<body>
<div class="button">
    <h2><a href="/post/edit">등록하러 가기</a></h2>

    <#list list as post>
        <div style="border: 1px solid; padding: 10px;">
            <label for="id">id:</label>
            <input type="text" name="id" value="<#if post??>${post.id}</#if>" />
            <label for="starId">starId:</label>
            <input type="text" name="starId" value="<#if post??>${post.starId}</#if>"/>
            <label for="userId">userId:</label>
            <input type="text" name="userId" value="<#if post??>${post.userId}</#if>"/>
            <label for="body">body:</label>
            <input type="text" name="body" value="<#if post??>${post.body!}</#if>" />
            <label for="img">img:</label>
            <input type="img" name="img" value="<#if post??>${post.img!}</#if>" />
            <label for="viewCount">viewCount:</label>
            <input type="text" name="viewCount" value="<#if post??>${post.viewCount}</#if>"/>
            <label for="likeCount">likeCount:</label>
            <input type="text" name="likeCount" value="<#if post??>${post.likeCount}</#if>" />
            <label for="commentCount">commentCount:</label>
            <input type="text" name="commentCount" value="<#if post??>${post.commentCount}</#if>"/>
            <label for="status">status:</label>
            <input type="text" name="status" value="<#if post??>${post.status}</#if>"/>
            <label for="regDttm">regDttm:</label>
            <input type="text" name="regDttm" value="<#if post?? && post.dateTime??>${post.dateTime.reg}</#if>" />
            <label for="upDttm">upDttm:</label>
            <input type="text" name="upDttm" value="<#if post?? && post.dateTime ??>${post.dateTime.up}</#if>" />
        </div>
     </#list>
</body>
</html>