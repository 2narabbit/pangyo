<html>
<head>
    <title>Post</title>
</head>
<body>
<div>
    <div style="border: 1px solid; padding: 10px; width:400px">
        <div>
            <img src="${post.user.profileImg!}"  style="width: 50px; height: 50px">
            <strong>${post.user.name!}</strong>
            <span>${post.dateTime.reg!}</span>
        </div>

        <p>${post.body!}</p>
        <#if post.img?has_content>
            <img src="${post.img!}" style="width: 200px; height: 200px">
        </#if>

        <div>
            <span>조회 ${post.viewCount!}</span>
            <span>좋아요(TODO) ${post.likeCount!}</span>
            <span>댓글 ${post.commentCount!}</span>
        </div>
    </div>

    <strong> TODO : 댓글모듈</strong>
</body>
</html>