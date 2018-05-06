<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>MY Star</title>
</head>
<body>
    <div id="listSection" style="margin-top:20px; border: 1px;">
        <span>MY STAR</span>
        <#list myStarFeed.list as myStar>
            <#if myStar??>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <img src="${myStar.data.mainImg!}" style="width: 400px; height: 200px">
                <div>
                    <h2>${myStar.data.name!}</h2>
                    <span>${myStar.data.fanCount!}fans</span>
                </div>
            </div>
            </#if>
        </#list>
    </div>

    <div id="endOfListSection"></div>

    <script type="text/template" id="star-list-template">
        <div style="border: 1px solid; padding: 10px; width:400px">
            <img src="<%= data.mainImg %>" style="width: 400px; height: 200px">
            <div>
                <h2><%= data.name %></h2>
                <span><%= data.fanCount %>fans</span>
            </div>
        </div>
    </script>

    <@common.importJS />
    <script src="/js/jquery.visible.js"></script>

    <script type="text/javascript">
        var starList = {
            rankId: ${myStarFeed.lastId!},
            hasMore: ${myStarFeed.hasMore!?string},
            isLoading: false,

            appendItem: function() {
                starList.isLoading = true;
                starList.getData().then(function(data){
                    if(data.list == null || data.list.length == 0) {
                        return;
                    }

                    var template = _.template($("#star-list-template").html());
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
                }

                return $.ajax({
                    url : '/api/star/my',
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
