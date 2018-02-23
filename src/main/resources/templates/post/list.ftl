<html>
<head>
    <title>Posts</title>

    <!-- TODO: 공통JS import 방식 정리 필요 -->
    <script src="/js/jquery-3.1.1.min.js"></script>
    <script src="/js/underscore-min.js"></script>
</head>
<body>
    <h2><a href="/post/edit">등록하러 가기</a></h2>

    <div id="listSection">
    </div>

    <script type="text/template" id="post-detail-template">
        <div id="postDetailSection" style="border: 1px solid; padding: 10px; width:400px">
            <div>
                <img src="<%= user.profileImg %>"  style="width: 50px; height: 50px">
                <strong><%= user.name %></strong>
                <span><%= dateTime.reg %></span>
            </div>

            <p><%= body %></p>
            <% if (img) { %>
                <img src="<%= img %>" style="width: 200px; height: 200px">
            <% } %>

            <div>
                <span>조회 <%= viewCount %></span>
                <span>좋아요 <%= likeCount %></span>
                <span>댓글 <%= commentCount %></span>
                <a href="/post/<%= id %>">[더 보기]</a>
            </div>
        </div>
    </script>

    <script type="text/javascript">
        $(document).ready(function() {
            appendPostList();
        });

        function getPostList() {
            var data = {};
            return $.ajax({
                url : '/api/post',
                data : data,
                type : 'GET',
                success: function(result) {
                    return result;
                },
                error: function(res) {
                    console.log(res);
                    return null;
                }
            });
        }

        function appendPostList() {
            getPostList().then(function(data){
                if(data == null) {
                    return;
                }

                var template = _.template($("#post-detail-template").html());
                data.forEach(function(e, i){
                    if (e.user == null) {
                        user.name = '무명';
                        user.profileImg = 'http://t1.daumcdn.net/profile/TfdXX_AUCLw0';
                    }
                    $('#listSection').append(template(e));
                });
            });
        }


        // TODO : 더보기 구현
    </script>
</body>
</html>