<#macro defaultCSS>
    <style>
        .likeArea {
            color: black;
        }
        .likeArea.liked {
            color: red;
        }
    </style>
</#macro>

<#macro defaultUI isLiked, contentType, contentId, count>
    <#if isLiked>
        <#assign likeClass="liked">
    <#else>
        <#assign likeClass="">
    </#if>
    <a href="javascript:;" class="likeArea ${likeClass!}"  onclick="like(${contentId!}, this);">
        좋아요
        <span class="likeCount">${count!}</span>
    </a>

    <script type="text/javascript">
        function doLike(contentId, $likeArea) {
            $.ajax({
                url : '/api/like/${contentType!}/' + contentId,
                type : 'POST',
                contentType : "application/json",
                success: function() {
                    var $likeCount = $likeArea.find('.likeCount');
                    $likeCount.text($likeCount.text()*1+1);
                    $likeArea.addClass('liked');
                },
                error: function(res) {
                    console.log(res);
                    alert('좋아요에 실패했습니다.');
                }
            });
        }

        function like(contentId, _this) {
            if (!$(_this).hasClass('liked')) {
                doLike(contentId, $(_this));
            }
        }
    </script>
</#macro>
