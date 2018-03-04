<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
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

        <div>
            <button id="modifyButton">수정</button>
            <button id="removeButton">삭제</button>
            <button>신고하기(TODO)</button>
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

    <@common.importJS />

    <script type="text/javascript">
        $(document).ready(function() {
            $('#modifyButton').click(goToModify);
            $('#removeButton').click(remove);
        });

        function goToModify() {
            location.replace('/fanClub/${starId!}/write?postId='+${post.id!});
        }

        function remove() {
            if (!confirm('글을 정말 삭제하시겠습니까?')) {
                return false;
            }

            $.ajax({
                url : '/api/post/' + ${post.id!},
                type : 'DELETE',
                contentType : "application/json",
                success: function(result) {
                    // TODO : api 성공여부 확인
                    location.replace('/fanClub/${starId!}');
                },
                error: function(res) {
                    console.log(res);
                    alert('글삭제에 실패했습니다.');
                }
            });
        }
    </script>
</body>
</html>