<#import "/macro/comment.ftl" as comment />

<head>
</head>

<body>
<div>
    <div style="border: 1px solid; padding: 10px; width:400px">
        <p>id > ${campaign.id!}</p>
        <p>executeRuleId > ${campaign.executeRuleId!}</p>
        <div>
            <span> 스타 > ${campaign.campaignCandidate.star.name!}</span>
            <span> 스타 > ${campaign.campaignCandidate.star.message!}</span>
        </div>
        <div>
            <img src="${campaign.campaignCandidate.user.profileImg!}"  style="width: 50px; height: 50px">
            <span> 팬 > ${campaign.campaignCandidate.user.name} </span>
        </div>
        <p>제목 > ${campaign.displayTitle!}</p>
        <div style="padding: 10px">
        ${campaign.displayBody!?replace('\n', '<br>')}
        </div>
        <p>랜딩URL > ${campaign.displayRandingUrl!}</p>
        <#if campaign.displayBannerImg?has_content>
          <img src="${campaign.displayBannerImg!} style="width: 30px; height: 30px">
        </#if>
        <p>럽수 > ${campaign.supportCount!}</p>
        <p>
            노출수 > ${campaign.goalExposureCount!}
            <#if rankBenefits??>
               (+ 순위 보너스! ${rankBenefits})
            </#if>
        </p>
        <p>혜택 > </p>
        <p>등록일 > ${campaign.dateTime.reg!}</p>
        <p>조회수 > ${campaign.viewCount!}</p>

        <div>
            <button id="free">참여</button>
            <button id="prime">프리미엄</button>
        </div>
    </div>

    <@comment.defaultUI commentFeed, "CAMPAIGN", campaign.id />

    <script type="text/javascript">
        $(document).ready(function() {
            $('#free').click(freeSupport);
            $('#prime').click(primeSupport);
        });

        function freeSupport() {
            if (${viewer???c}) {
                alert("오!! 참여!");
            } else {
                alert("로그인을 해야 참여 할 수 있습니다.");
            }
        }

        function primeSupport() {
            if (${viewer???c}) {
                alert("오!! 프리미엄 참여!");
            } else {
                alert("로그인을 해야 참여 할 수 있습니다.");
            }
        }
    </script>
</body>
