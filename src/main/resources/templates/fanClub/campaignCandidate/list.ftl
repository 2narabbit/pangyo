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

    <#include "/fanClub/layout/head.ftl">
    <div style="margin-top:20px">
        <span>NEXT WEEK CAMPAIGN</span>
        <a href="/fanClub/${star.id}/campaign-candidate/write">등록하기</a>
    </div>

    <#if campaignCandidateList?has_content>
    <div id="listSection">
        <#list campaignCandidateList as campaignCandidate>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <div class="preview">
                    <div>
                        <label>${campaignCandidate_index+1} ${campaignCandidate.title!}</label>
                        <span>
                            <@poll.defaultUI polledList?seq_contains(campaignCandidate.id), "CANDIDATE", campaignCandidate.id, campaignCandidate.pollCount />
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
    </#if>

    <script type="text/javascript">
        $(".moreView").click(function () {
            $(this).siblings('.preview').removeClass('preview');
            $(this).remove();
        });

        // TODO : 리스트 더보기 구현
    </script>
</body>
</html>
