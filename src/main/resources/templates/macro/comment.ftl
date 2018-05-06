<#macro defaultUI commentFeed, contentType, contentId>
    <div style="margin-top:20px">
        <div>
            <input id="commentBody" type="text" style="width:350px">
            <button id="addCommentButton" type="button">댓글쓰기</button>
        </div>

        <div id="commentListSection">
        <#list commentFeed.list as comment>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <div>
                    <strong>${comment.user.name!}</strong>
                    <span>${comment.dateTime.reg!}</span>
                    <#if comment.user.id == 2> <!-- TODO: 실 user 주입 -->
                        <button id="removeCommentButton" onclick="removeComment(${comment.id!})">삭제</button>
                    </#if>
                </div>

                <p>${comment.body!}</p>
            </div>
        </#list>
        </div>

        <#if commentFeed.hasMore>
            <button id="moreCommentButton" style="width:400px" type="button">더보기</button>
        </#if>
    </div>

    <script type="text/template" id="comment-detail-template">
        <div style="border: 1px solid; padding: 10px; width:400px">
            <div>
                <strong><%= user.name %></strong>
                <span><%= dateTime.reg %></span>
                <% if (user.id == 2) { %>  <!-- TODO: 실 user 주입 -->
                    <button id="removeCommentButton" onclick="removeComment(<%= id %>)">삭제</button>
                <% } %>
            </div>

            <p><%= body %></p>
        </div>
    </script>

    <script type="text/javascript">
        $('#addCommentButton').click(function(){
            var data = {
                contentType: "${contentType!}",
                contentId: ${contentId!},
                user: {id:2}, // TODO: 실 user 주입
                body: $('#commentBody').val()
            };

            $.ajax({
                url : '/api/comment',
                data : JSON.stringify(data),
                type : 'POST',
                contentType : "application/json",
                success: function(result) {
                    location.reload();
                },
                error: function(res) {
                    console.log(res);
                }
            });
        });

        function removeComment(commentId){
            if (!confirm('댓글을 정말 삭제하시겠습니까?')) {
                return false;
            }

            $.ajax({
                url : '/api/comment/' + commentId,
                type : 'DELETE',
                contentType : "application/json",
                success: function() {
                    location.reload();
                },
                error: function(res) {
                    console.log(res);
                    alert('삭제에 실패했습니다.');
                }
            });
        };

        $('#moreCommentButton').click(function(){
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
                        $('#moreCommentButton').remove();
                    }
                });
            },

            getData: function() {
                var data = {};
                if (commentList.lastId != null) {
                    data.lastId = commentList.lastId;
                }

                return $.ajax({
                    url : '/api/comment/${contentType!}/${contentId!}',
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
</#macro>
