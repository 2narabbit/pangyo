<div style="margin-top:20px">
    <div>
        <span>댓글쓰기 (TODO)</span>
        <textarea></textarea>
    </div>

    <div id="commentListSection">
    <#list commentFeed.list as comment>
        <div style="border: 1px solid; padding: 10px; width:400px">
            <div>
                <strong>${comment.user.name!}</strong>
                <span>${comment.dateTime.reg!}</span>
                <!-- TODO: 내 댓글인 경우 수정/삭제-->
                <!-- TODO: 댓글없는 경우 -->
            </div>

            <p>${comment.body!}</p>
        </div>
    </#list>
    </div>

    <#if commentFeed.hasMore>
        <button id="moreButton">더보기</button>
    </#if>
</div>

<script type="text/template" id="comment-detail-template">
    <div style="border: 1px solid; padding: 10px; width:400px">
        <div>
            <strong><%= user.name %></strong>
            <span><%= dateTime.reg %></span>
        </div>

        <p><%= body %></p>
    </div>
</script>

<script type="text/javascript">
    $('#moreButton').click(function(){
        commentList.appendItem();
    });

    var commentList = {
        lastId: ${commentFeed.lastId!},
        hasMore: ${commentFeed.hasMore!?string},
        isLoading: false,

        appendItem: function() {
            commentList.isLoading = true;
            commentList.getData().then(function(data){
                if(data.list == null || data.list.length == 0) {
                    return;
                }

                var template = _.template($("#comment-detail-template").html());
                data.list.forEach(function(e, i){
                    $('#commentListSection').append(template(e));
                });

                commentList.lastId = data.list[data.list.length-1].id;
                commentList.hasMore = data.hasMore;
                commentList.isLoading = false;

                if (!commentList.hasMore) {
                    $('#moreButton').remove();
                }
            });
        },

        getData: function() {
            var data = {};
            if (commentList.lastId != null) {
                data.lastId = commentList.lastId;
            }

            return $.ajax({
                url : '/api/comment/${contentType!}/${contentId}',
                data : data,
                type : 'GET',
                success: function(result) {
                    return result;
                },
                error: function(res) {
                    console.log(res);
                    return {};
                }
            });
        }
    };
</script>
