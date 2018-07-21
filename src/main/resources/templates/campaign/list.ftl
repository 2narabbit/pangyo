<#import "/macro/comment.ftl" as comment />

<head>
</head>

<body>
    <div id="listSection">
        <#list campaignFeed.list as feed>
            <#assign campaign=feed.content>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <div style="color:darkblue">순위 > ${feed.ranking}</div>
                <div>
                    <img src="${campaign.campaignCandidate.user.profileImg!}"  style="width: 50px; height: 50px">
                    <strong>${campaign.campaignCandidate.user.name!}</strong>
                    <span>${campaign.campaignCandidate.dateTime.reg!}</span>
                </div>

                <div>
                    <span> 스타 > ${campaign.campaignCandidate.star.name!}</span>
                    <span> 스타 > ${campaign.campaignCandidate.star.message!}</span>
                </div>

                <div style="overflow: hidden; max-height: 300px;">
                    <p>${campaign.displayTitle!}</p>
                    <#if campaign.displayBannerImg?has_content>
                        <img src="${campaign.displayBannerImg!}" style="max-width: 200px; max-height: 100px">
                    </#if>
                </div>

                <div style="margin-top: 10px;">
                    <p>노출수 > ${campaign.supportCount!}</p>
                    <p>등록일 > ${campaign.dateTime.reg!}</p>
                    <p>조회수 > ${campaign.viewCount!}</p>
                </div>

                <a href="/campaign/${campaign.id!}">[자세히]</a>
            </div>
        </#list>
    </div>

    <div id="endOfListSection"></div>

    <#--<script type="text/template" id="campaign-detail-template">-->
        <#--<div style="border: 1px solid; padding: 10px; width:400px">-->
            <#--<div>-->
                <#--<img src="<%= user.profileImg %>"  style="width: 50px; height: 50px">-->
                <#--<strong><%= user.name %></strong>-->
                <#--<span><%= dateTime.reg %></span>-->
            <#--</div>-->

            <#--<div style="overflow: hidden; max-height: 300px;">-->
                <#--<p><%= body %></p>-->
                <#--<% if (img) { %>-->
                    <#--<img src="<%= img %>" style="max-width: 400px">-->
                <#--<% } %>-->
            <#--</div>-->

            <#--<div style="margin-top: 10px;">-->
                <#--<span>조회 <%= viewCount %></span>-->
                <#--<%-->
                    <#--if (likeList.includes(id)) {-->
                        <#--likeClass = "liked";-->
                    <#--} else {-->
                        <#--likeClass = "";-->
                    <#--}-->
                <#--%>-->
                <#--<span class="likeArea <%= likeClass %>">좋아요 <%= likeCount %></span>-->
                <#--<span>댓글 <%= commentCount %></span>-->
                <#--<a href="/fanClub/${star.id!}/post/<%= id %>">[더 보기]</a>-->
            <#--</div>-->
        <#--</div>-->
    <#--</script>-->

    <script src="/js/jquery.visible.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $(window).scroll(function(e) {
                if ($("#endOfListSection").visible(true) && campaignFeed.hasMore && !campaignFeed.isLoading) {
                    campaignFeed.appendItem();
                }
            });
        });

        var campaignFeed = {
            lastId: ${campaignFeed.lastId!},
            hasMore: ${campaignFeed.hasMore!?string},
            isLoading: false,

            appendItem: function() {
                campaignFeed.isLoading = true;
                campaignFeed.getData().then(function(data){
                    if(data.list == null || data.list.length == 0) {
                        return;
                    }

                    var template = _.template($("#campaign-detail-template").html());

                    campaignFeed.lastId = data.lastId;
                    campaignFeed.hasMore = data.hasMore;
                    campaignFeed.isLoading = false;
                });
            },

            getData: function() {
                var data = {};
                if (campaignFeed.lastId != null) {
                    data.lastId = campaignFeed.lastId;
                }

                return $.ajax({
                    url : '/api/campaign',
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
</body>
</html>
