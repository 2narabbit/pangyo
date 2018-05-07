<#import "/macro/common.ftl" as common />
<#import "/macro/comment.ftl" as comment />
<#import "/macro/like.ftl" as like />

<!DOCTYPE html>
<html>
<head>
    <title>Post</title>
</head>
<body>
<div>
    <@common.importJS />

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

        <div style="padding: 10px">
            ${post.body!?replace('\n', '<br>')}
        </div>

        <#if post.img?has_content>
            <img src="${post.img!}" style="max-width: 400px;">
        </#if>

        <div>
            <span>조회 ${post.viewCount!}</span>
            <span>
                <@like.defaultUI isLiked, "POST", post.id />
            </span>
            <span>댓글 ${post.commentCount!}</span>
        </div>
    </div>

    <@comment.defaultUI commentFeed, "POST", post.id />

    <script type="text/javascript">
        $(document).ready(function() {
            $('#modifyButton').click(goToModify);
            $('#removeButton').click(remove);
        });

        function goToModify() {
            location.replace('/fanClub/${starId!}/post/write?postId='+${post.id!});
        }

        function remove() {
            if (!confirm('글을 정말 삭제하시겠습니까?')) {
                return false;
            }

            $.ajax({
                url : '/api/fanClub/${starId!}/post/' + ${post.id!},
                type : 'DELETE',
                contentType : "application/json",
                success: function() {
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