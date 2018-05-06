<#import "/macro/common.ftl" as common />

<html>
<head>
    <title>campaignCandidate</title>
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
                <div>
                    <label>${campaignCandidate_index+1} ${campaignCandidate.title!}</label>
                    <span>투표 ${campaignCandidate.pollCount!}</span>
                </div>
                <p>${campaignCandidate.body!}</p>
                <p>캠퍼엔 노출 기간 : (TODO)</p>
                <p>랜딩페이지 url : ${campaignCandidate.randingUrl!}</p>
                <p>광고소재 : (TODO 현재 파일명 저장안하고 있음)</p>
                <#if campaignCandidate.bannerImg?has_content>
                    <img src="${campaignCandidate.bannerImg!}" style="max-width: 400px;">
                </#if>
            </div>
        </#list>
    </div>
    </#if>

    <script type="text/template" id="campaign-candidate-detail-template">
        <div style="border: 1px solid; padding: 10px; width:400px">
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
    </script>

    <@common.importJS />

    <script type="text/javascript">
        // TODO : 더보기 구현
    </script>
</body>
</html>
