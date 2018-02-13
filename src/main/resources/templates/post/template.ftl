<html>
<head>
    <title>Post/Edit</title>
</head>
<body>
<#if post??>
<form action="/post/change" method="PUT">
 <div>
     <label for="id">id:</label>
     <input type="text" name="id" value="<#if post??>${post.id}</#if>" />
 </div>
<div>
    <label for="status">status:</label>
    <input type="text" name="status" value="<#if post??>${post.status}</#if>"/>
</div>
    <div>
        <label for="regDttm">regDttm:</label>
        <input type="text" name="reg" value="<#if post?? && post.dateTime ??>${post.dateTime.reg}</#if>" />
    </div>
    <div>
        <label for="upDttm">upDttm:</label>
        <input type="text" name="up" value="<#if post?? && post.dateTime ??>${post.dateTime.up}</#if>" />
    </div>
<#else>
<form action="/post/add" method="POST">

</#if>
    <div>
        <label for="starId">starId:</label>
        <input type="text" name="starId" value="<#if post??>${post.starId}</#if>"/>
    </div>
    <div>
        <label for="userId">userId:</label>
        <input type="text" name="userId" value="<#if post??>${post.userId}</#if>"/>
    </div>
    <div>
        <label for="body">body:</label>
        <input type="text" name="body" value="<#if post??>${post.body!}</#if>" />
    </div>
    <div>
        <label for="img">img:</label>
        <input type="text" name="img" value="<#if post??>${post.img!}</#if>" />
    </div>
    <div>
        <label for="viewCount">viewCount:</label>
        <input type="text" name="viewCount" value="<#if post??>${post.viewCount}</#if>"/>
    </div>
    <div>
        <label for="likeCount">likeCount:</label>
        <input type="text" name="likeCount" value="<#if post??>${post.likeCount}</#if>" />
    </div>
    <div>
        <label for="commentCount">commentCount:</label>
        <input type="text" name="commentCount" value="<#if post??>${post.commentCount}</#if>"/>
    </div>

    <div class="button">
        <button><#if post??>수정<#else>등록</#if></button>
    </div>

</form>
</body>
</html>