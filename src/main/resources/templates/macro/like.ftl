<#macro defaultUI doLike, contentType, contentId>
    <style>
        #likeArea {
            color: black;
        }
        #likeArea.doLike {
            color: red;
        }
    </style>

    <#if doLike>
        <#assign likeClass="doLike">
    </#if>
    <a id="likeArea" class="${likeClass!}" href="javascript:;">
        좋아요
        <span id="likeCount">${post.likeCount!}</span>
    </a>

    <script type="text/javascript">
        function dontLike() {
            $.ajax({
                url : '/api/like/${contentType!}/${contentId!}',
                type : 'DELETE',
                contentType : "application/json",
                success: function() {
                    $('#likeCount').text($('#likeCount').text()*1-1);
                    $('#likeArea').removeClass('doLike');
                },
                error: function(res) {
                    console.log(res);
                    alert('좋아요 취소에 실패했습니다.');
                }
            });
        }

        function doLike() {
            $.ajax({
                url : '/api/like/${contentType!}/${contentId!}',
                type : 'POST',
                contentType : "application/json",
                success: function() {
                    $('#likeCount').text($('#likeCount').text()*1+1);
                    $('#likeArea').addClass('doLike');
                },
                error: function(res) {
                    console.log(res);
                    alert('좋아요에 실패했습니다.');
                }
            });
        }

        $('#likeArea').click(function () {
            if ($(this).hasClass('doLike')) {
                dontLike();
            } else {
                doLike();
            }
        });
    </script>
</#macro>
