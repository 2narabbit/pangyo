<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Star</title>
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

    <!--  나래님과 상의하여 로그인 모듈 스크립트로 정리해야한다. UI와 연결될듯! -->
    <button id="logoutBtn" onclick="memberLogout()">로그아웃</button>
    <a href="/member/login?continue=/star">로그인 하러 가기</a>

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
                        <h3><a href="/fanClub/${myStar.data.id!}">${myStar.data.name!}</a></h3>
                        <span>${myStar.data.fanCount!}fans</span>
                    </td>
                    <td style="text-align: center">
                        <img src="${myStar.data.profileImg!}" style="max-height:100px;">
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
                        <h3>${star.data.name!}</h3>
                        <span>${star.data.fanCount!}fans</span>
                        <button id="joinBotton${star.data.id!}" onclick="joinStar(${star.data.id!})">+ Join</button>
                    </td>
                    <td style="text-align: center">
                        <img src="${star.data.profileImg!}" style="max-height: 100px;">
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
                <h3><%= data.name %></h3>
                <span><%= data.fanCount %>fans</span>
                <button id="joinBotton<%= data.id %>" onclick="joinStar(<%= data.id %>)">+ Join</button>
            </td>
            <td style="text-align: center">
                <img src="<%= data.profileImg %>" style="max-height: 100px">
            </td>
        </tr>
    </script>

    <@common.importJS />
    <script src="/js/jquery.visible.js"></script>

    <script type="text/javascript">
        function joinStar(starId){
            var loginUser = ${viewer};
            if (loginUser === null) {
                confirm('로그인하셔야 이용할 수 있습니다!');
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

        function memberLogout() {
            $.ajax({
                url : '/api/member/logout',
                type : 'GET',
                success: function() {
                    alert("+_+bb 로그아웃 성공");
                    location.replace('/member/login');
                },
                error: function(res) {
                    console.log(res);
                    alert("로그아웃 실패..ㅠㅠ 미안")
                }
            });
        }

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
