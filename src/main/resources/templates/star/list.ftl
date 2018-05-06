<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Star</title>
</head>
<body>
    <div style="margin-top:20px; border: 1px;">
        <span>MY STAR</span>
        <#list myStarFeed.list as myStar>
            <#if myStar??>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <img src="${myStar.data.mainImg!}" style="width: 400px; height: 200px">
                <div>
                    <h2>${myStar.data.name!}</h2>
                    <span>${myStar.ranking!} 위</span>
                    <span>${myStar.data.fanCount!}fans</span>
                </div>
            </div>
            </#if>
        </#list>
        <#if myStarFeed.hasMore>
            <a href="/star/my">더보기</a>
        </#if>
    </div>

    <div id="listSection" style="margin-top:20px; border: 1px;">
        <span>Everyone`s STAR</span>
        <#list starFeed.list as star>
            <#if star??>
                <div style="border: 1px solid; padding: 10px; width:400px">
                    <img src="${star.data.mainImg!}" style="width: 400px; height: 200px">
                    <div>
                        <h2>${star.data.name!}</h2>
                        <span>${star.ranking!} 위</span>
                        <span>${star.data.fanCount!}fans</span>
                    </div>
                    <button id="joinBotton${star.data.id!}" onclick="joinStar(${star.data.id!})">+ Join</button>
                </div>
            </#if>
        </#list>
    </div>

    <div id="endOfListSection"></div>

    <script type="text/template" id="star-join-template">
        <div style="border: 1px solid; padding: 10px; width:400px">
            <img src="<%= data.mainImg %>" style="width: 400px; height: 200px">
            <div>
                <h2><%= data.name %></h2>
                <span><%= ranking %> 위</span>
                <span><%= data.fanCount %>fans</span>
            </div>
            <button id="joinBotton<%= data.id %>" onclick="joinStar(<%= data.id %>)">+ Join</button>
        </div>
    </script>

    <@common.importJS />
    <script src="/js/jquery.visible.js"></script>

    <script type="text/javascript">
        function joinStar(starId){
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
                        $('#listSection').append(template(e));
                    });

                    starList.lastId = data.list[data.list.length-1].id;
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
</html>
