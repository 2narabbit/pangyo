<#import "/macro/common.ftl" as common />
<#import "/macro/like.ftl" as like />

<!DOCTYPE html>
<html>
<head>
    <title>FanClub</title>
    <@like.defaultCSS />
</head>
<body>
    <#include "/fanClub/layout/head.ftl">

    <h3 style="margin-top:50px; margin-bottom:0px">NEXT WEEK CAMPAIGN</h3>

    <div style="border: 2px solid; border-color: red; padding: 10px; width:400px;">
        <#if campaignCandidateList?has_content>
            <div id="campaignCandidate">
                <#list campaignCandidateList as campaignCandidate>
                    <div>
                        <strong>${campaignCandidate_index+1} ${campaignCandidate.title}</strong>
                        <span>투표수 : ${campaignCandidate.pollCount!}</span>
                    </div>
                </#list>
                <a href="/fanClub/${star.id!}/campaign-candidate">[후보 더 보기]</a>
            </div>
        <#else>
            <div style="padding: 10px">
                <a href="/fanClub/${star.id!}/campaign-candidate/write">[후보 등록하러 가기]</a>
            </div>
        </#if>
    </div>

    <div style="margin-top:50px">
        <h3 style="margin:0px">STAR FEED</h3>
        <a href="/fanClub/${star.id!}/post/write" style="margin-left:377px">글쓰기</a>
    </div>

    <div id="listSection">
        <#list postFeed.list as post>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <div>
                    <img src="${post.user.profileImg!}"  style="width: 50px; height: 50px">
                    <strong>${post.user.name!}</strong>
                    <span>${post.dateTime.reg!}</span>
                </div>

                <div style="overflow: hidden; max-height: 300px;">
                    <p>${post.body!}</p>
                    <#if post.img?has_content>
                        <img src="${post.img!}" style="max-width: 400px;">
                    </#if>
                </div>

                <div style="margin-top: 10px;">
                    <span>조회 ${post.viewCount!}</span>
                    <#if likedList?seq_contains(post.id)>
                        <#assign likeClass="liked">
                    <#else>
                        <#assign likeClass="">
                    </#if>
                    <span class="likeArea ${likeClass!}">
                        좋아요 ${post.likeCount!}
                    </span>
                    <span>댓글 ${post.commentCount!}</span>
                    <a href="/fanClub/${star.id!}/post/${post.id!}">[더 보기]</a>
                </div>
            </div>
        </#list>
    </div>

    <div id="endOfListSection"></div>

    <script type="text/template" id="post-detail-template">
        <div style="border: 1px solid; padding: 10px; width:400px">
            <div>
                <img src="<%= user.profileImg %>"  style="width: 50px; height: 50px">
                <strong><%= user.name %></strong>
                <span><%= dateTime.reg %></span>
            </div>

            <div style="overflow: hidden; max-height: 300px;">
                <p><%= body %></p>
                <% if (img) { %>
                    <img src="<%= img %>" style="max-width: 400px">
                <% } %>
            </div>

            <div style="margin-top: 10px;">
                <span>조회 <%= viewCount %></span>
                <span>좋아요 <%= likeCount %></span>
                <span>댓글 <%= commentCount %></span>
                <a href="/fanClub/${star.id!}/post/<%= id %>">[더 보기]</a>
            </div>
        </div>
    </script>

    <@common.importJS />
    <script src="/js/jquery.visible.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
            $(window).scroll(function(e) {
                if ($("#endOfListSection").visible(true) && postList.hasMore && !postList.isLoading) {
                    postList.appendItem();
                }
            });
        });

        var postList = {
            lastId: ${postFeed.lastId!},
            hasMore: ${postFeed.hasMore!?string},
            isLoading: false,

            appendItem: function() {
                postList.isLoading = true;
                postList.getData().then(function(data){
                    if(data.list == null || data.list.length == 0) {
                        return;
                    }

                    var template = _.template($("#post-detail-template").html());
                    data.list.forEach(function(e, i){
                        $('#listSection').append(template(e));
                    });

                    postList.lastId = data.lastId;
                    postList.hasMore = data.hasMore;
                    postList.isLoading = false;
                });
            },

            getData: function() {
                var data = {};
                if (postList.lastId != null) {
                    data.lastId = postList.lastId;
                }

                return $.ajax({
                    url : '/api/fanClub/${star.id!}/post',
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
