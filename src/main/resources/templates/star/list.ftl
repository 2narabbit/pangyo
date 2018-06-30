<head>
    <style>
        table {
            width: 400px;
            border: 1px solid;
            border-collapse: collapse;
        }
        th, td {
            border-bottom: 1px solid;
            padding: 10px;
        }
    </style>
</head>

<body>
    <div style="margin-top:20px; border: 1px;">
        <h2>MY STAR</h2>
        <#if myStarFeed.hasMore>
            <a href="/star/my" style="padding-left:357px">더보기</a>
        </#if>

        <table>
            <#list myStarFeed.list as myStar>
                <#if myStar??>
                <tr>
                    <td>
                        <h2>${myStar.ranking!}</h2>
                    </td>
                    <td>
                        <h3><a href="/fanClub/${myStar.content.id!}">${myStar.content.name!}</a></h3>
                        <span>${myStar.content.fanCount!}fans</span>
                    </td>
                    <td style="text-align: center">
                        <img src="${myStar.content.profileImg!}" style="max-height:100px;">
                    </td>
                </tr>
                </#if>
            </#list>
        </table>
    </div>

    <div id="listSection" style="margin-top:50px; border: 1px;">
        <h2>Everyone`s STAR</h2>

        <table>
        <#list starFeed.list as star>
            <#if star??>
                <tr>
                    <td>
                        <h2>${star.ranking!}</h2>
                    </td>
                    <td>
                        <h3>${star.content.name!}</h3>
                        <span>${star.content.fanCount!}fans</span>
                        <button id="joinBotton${star.content.id!}" onclick="joinStar(${star.content.id!})">+ Join</button>
                    </td>
                    <td style="text-align: center">
                        <img src="${star.content.profileImg!}" style="max-height: 100px;">
                    </td>
                </tr>
            </#if>
        </#list>
        </table>
    </div>

    <div id="endOfListSection"></div>

    <script type="text/template" id="star-join-template">
        <tr>
            <td>
                <h2><%= ranking %></h2>
            </td>
            <td>
                <h3><%= content.name %></h3>
                <span><%= content.fanCount %>fans</span>
                <button id="joinBotton<%= content.id %>" onclick="joinStar(<%= content.id %>)">+ Join</button>
            </td>
            <td style="text-align: center">
                <img src="<%= content.profileImg %>" style="max-height: 100px">
            </td>
        </tr>
    </script>

    <script src="/js/jquery.visible.js"></script>

    <script type="text/javascript">
        function joinStar(starId){
            <#if viewer??>
              var loginUserId = ${viewer.getId()};
            <#else >
              var loginUserId = null;
            </#if>
            if (loginUserId === null) {
                confirm('로그인하셔야 이용할 수 있습니다!');
                return;
            }

            $.ajax({
                url : '/api/star/join/' + starId,
                type : 'POST',
                contentType : "application/json",
                success: function() {
                    $("#joinBotton"+starId).text("*Joined*");
                    confirm('스타 팬클럽에 참여하였습니다b');
                },
                error: function(res) {
                    console.log(res);
                    alert('스타 팬클럽에 참여하지 못했습니다');
                }
            });
        };

        var starList = {
            lastId: ${starFeed.lastId!},
            hasMore: ${starFeed.hasMore!?string},
            isLoading: false,

            appendItem: function() {
                starList.isLoading = true;
                starList.getData().then(function(data){
                    if(data.list == null || data.list.length == 0) {
                        return;
                    }

                    var template = _.template($("#star-join-template").html());
                    data.list.forEach(function(e, i){
                        $('#listSection > table').append(template(e));
                    });

                    starList.lastId = data.lastId;
                    starList.hasMore = data.hasMore;
                    starList.isLoading = false;
                });
            },

            getData: function() {
                var data = {};
                if (starList.lastId != null) {
                    data.rankId = starList.lastId;
                    data.joined = 'false';
                }

                return $.ajax({
                    url : '/api/star',
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

        $(document).ready(function() {
            $(window).scroll(function(e) {
                if ($("#endOfListSection").visible(true) && starList.hasMore && !starList.isLoading) {
                    starList.appendItem();
                }
            });
        });
    </script>
</body>
