<#import "/macro/common.ftl" as common />

<html>
<head>
    <title>campaignCandidate</title>
    <style>
        .preview {
            overflow: hidden;
            max-height: 150px;
        }
    </style>
    <style>
        .pollArea {
            color: black;
            float: right;
        }
        .pollArea.polled {
            color: red;
        }
    </style>
</head>
<body>
    <#include "/fanClub/layout/head.ftl">
    <div style="margin-top:20px">
        <span>NEXT WEEK CAMPAIGN</span>
        <a href="/fanClub/${starId}/campaign-candidate/write">등록하기</a>
    </div>

    <#if campaignCandidateList?has_content>
    <div id="listSection">
        <#list campaignCandidateList as campaignCandidate>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <div class="preview">
                    <div>
                        <label>${campaignCandidate_index+1} ${campaignCandidate.title!}</label>
                        <span>
                            <#if polledList?seq_contains(campaignCandidate.id)>
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
                    <p>캠퍼엔 노출 기간 : (TODO)</p>
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
    </#if>

    <script type="text/template" id="campaign-candidate-detail-template">
        <div style="border: 1px solid; padding: 10px; width:400px">
            <div class="preview">
                <div>
                    <label><%= index %> <%= title %></label>
                    <span>투표 <%= pollCount %></span>
                </div>
                <p><%= body %></p>
                <p>캠퍼엔 노출 기간 : (TODO)</p>
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

    <@common.importJS />

    <script type="text/javascript">
        // TODO : 더보기 구현
        $(".moreView").click(function () {
            $(this).siblings('.preview').removeClass('preview');
            $(this).remove();
        });
    </script>

    <script type="text/javascript">
        <#assign contentType = "CANDIDATE">

        function dontPoll(contentId, $pollArea) {
            $.ajax({
                url : '/api/poll/${contentType!}/' + contentId,
                type : 'DELETE',
                contentType : "application/json",
                success: function() {
                    var $pollCount = $pollArea.find('.pollCount');
                    $pollCount.text($pollCount.text()*1-1);
                    $pollArea.removeClass('polled');
                },
                error: function(res) {
                    console.log(res);
                    alert('투표 취소에 실패했습니다.');
                }
            });
        }

        function doPoll(contentId, $pollArea) {
            $.ajax({
                url : '/api/poll/${contentType!}/' + contentId,
                type : 'POST',
                contentType : "application/json",
                success: function() {
                    var $pollCount = $pollArea.find('.pollCount');
                    $pollCount.text($pollCount.text()*1+1);
                    $pollArea.addClass('polled');
                },
                error: function(res) {
                    console.log(res);
                    alert('투표에 실패했습니다.');
                }
            });
        }

        function poll(contentId, _this) {
            console.log($(_this));
            if ($(_this).hasClass('polled')) {
                dontPoll(contentId, $(_this));
            } else {
                doPoll(contentId, $(_this));
            }
        }
    </script>
</body>
</html>
