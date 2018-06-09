<#import "/macro/common.ftl" as common />
<#import "/macro/poll.ftl" as poll />

<html>
<head>
    <title>campaignCandidate</title>
    <style>
        .preview {
            overflow: hidden;
            max-height: 150px;
        }
    </style>
    <@poll.defaultCSS />
</head>
<body>
    <@common.importJS />
    <@poll.defaultScript "CANDIDATE" />

    <#include "/fanClub/layout/head.ftl">
    <div style="margin-top:20px">
        <span>NEXT WEEK CAMPAIGN</span>
        <a href="/fanClub/${star.id}/campaign-candidate/write">등록하기</a>
    </div>

    <#if campaignCandidateFeed?has_content>
    <div id="listSection">
        <#list campaignCandidateFeed.list as campaignCandidate>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <div class="preview">
                    <div>
                        <label>${campaignCandidate_index+1} ${campaignCandidate.title!}</label>
                        <span>
                            <#if campaignCandidateFeed.pollList?seq_contains(campaignCandidate.id)>
                                <#assign pollClass="polled">
                            <#else>
                                <#assign pollClass="">
                            </#if>
                            <a href="javascript:;" class="pollArea ${pollClass!}" onclick="poll(${campaignCandidate.id!}, this);">
                                투표
                                <span class="pollCount">${campaignCandidate.pollCount!}</span>
                            </a>
                        </span>
                    </div>
                    <p>${campaignCandidate.body!}</p>
                    <p>캠페인 노출 기간 : ${adExecutionRule.startDttm!} ~ ${adExecutionRule.endDttm!}</p>
                    <p>랜딩페이지 url : ${campaignCandidate.randingUrl!}</p>
                    <p>광고소재 : (TODO 현재 파일명 저장안하고 있음)</p>
                    <#if campaignCandidate.bannerImg?has_content>
                        <img src="${campaignCandidate.bannerImg!}" style="max-width: 400px;">
                    </#if>
                </div>

                <div class="moreView" style="text-align: center">
                    <button>더보기</button>
                </div>
            </div>
        </#list>
    </div>

    <div id="endOfListSection"></div>
    </#if>

    <script type="text/template" id="campaign-candidate-detail-template">
        <div style="border: 1px solid; padding: 10px; width:400px">
            <div class="preview">
                <div>
                    <label><%= rank %> <%= title %></label>
                    <span>
                        <%
                            if (pollList.includes(id)) {
                                pollClass = "polled";
                            } else {
                                pollClass = "";
                            }
                        %>
                        <a href="javascript:;" class="pollArea <%= pollClass %>" onclick="poll(<%= id %>, this);">
                            투표
                            <span class="pollCount"><%= pollCount %></span>
                        </a>
                    </span>
                </div>
                <p><%= body %></p>
                <p>캠페인 노출 기간 : ${adExecutionRule.startDttm!} ~ ${adExecutionRule.endDttm!}</p>
                <p>랜딩페이지 url : <%= randingUrl %></p>
                <p>광고소재 : (TODO 현재 파일명 저장안하고 있음)</p>
                <% if (bannerImg) { %>
                    <img src="<%= bannerImg %>" style="max-width: 400px;">
                <% } %>
            </div>

            <div class="moreView" style="text-align: center">
                <button>더보기</button>
            </div>
        </div>
    </script>

    <script src="/js/jquery.visible.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $(window).scroll(function(e) {
                if ($("#endOfListSection").visible(true) && campaignCandidateList.hasMore && !campaignCandidateList.isLoading) {
                    campaignCandidateList.appendItem();
                }
            });
        });

        var campaignCandidateList = {
            page: ${campaignCandidateFeed.page!},
            hasMore: ${campaignCandidateFeed.hasMore!?string},
            isLoading: false,
            lastRank: ${campaignCandidateFeed.list!?size},

            appendItem: function() {
                campaignCandidateList.isLoading = true;
                campaignCandidateList.getData().then(function(data){
                    if(data.list == null || data.list.length === 0) {
                        return;
                    }

                    var template = _.template($("#campaign-candidate-detail-template").html());
                    data.list.forEach(function(e, i){
                        e.pollList = data.pollList;
                        e.rank = ++campaignCandidateList.lastRank;
                        $('#listSection').append(template(e));
                    });

                    campaignCandidateList.page = data.page;
                    campaignCandidateList.hasMore = data.hasMore;
                    campaignCandidateList.isLoading = false;
                });
            },

            getData: function() {
                var data = {};
                if (campaignCandidateList.page != null) {
                    data.page = campaignCandidateList.page + 1;
                }

                return $.ajax({
                    url : '/api/campaign-candidate/' + ${star.id!},
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

        $('#listSection').delegate('.moreView', 'click', function () {
            $(this).siblings('.preview').removeClass('preview');
            $(this).remove();
        });
    </script>
</body>
</html>
