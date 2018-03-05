<#import "/macro/common.ftl" as common />

<html>
<head>
    <title>campaignCandidate</title>
</head>
<body>
    <h2><a href="/fanClub/${starId}/campaign-candidate">등록하러 가기</a></h2>

    <#if campaignCandidateList?has_content>
    <div id="listSection">
        <#list campaignCandidateList as campaignCandidate>
            <div style="border: 1px solid; padding: 10px; width:400px">
                <div>
                    <img src="${campaignCandidate.user.profileImg!"http://t1.daumcdn.net/profile/TfdXX_AUCLw0"}"  style="width: 50px; height: 50px">
                    <strong>${campaignCandidate.user.name!"없는유저"}</strong>
                    <span>${campaignCandidate.dateTime.reg!}</span>
                </div>

                <label>${campaignCandidate.title!}</label>
                <p>${campaignCandidate.body!}</p>
                <#if campaignCandidate.bannerImg?has_content>
                    <img src="${campaignCandidate.bannerImg!}" style="width: 200px; height: 200px">
                </#if>

                <div>
                    <span>투표 ${campaignCandidate.pollCount!}</span>
                </div>

                <a href="/fanClub/${starId}/campaign-candidate/${campaignCandidate.id!}">[더 보기]</a>
            </div>
        </#list>
    </div>
    </#if>

    <script type="text/template" id="campaign-candidate-detail-template">
        <div id="campaignCandidateDetailSection" style="border: 1px solid; padding: 10px; width:400px">
            <div>
                <img src="<%= user.profileImg %>"  style="width: 50px; height: 50px">
                <strong><%= user.name %></strong>
                <span><%= dateTime.reg %></span>
            </div>

            <label><%= title %></label>
            <p><%= body %></p>
            <% if (bannerImg) { %>
                <img src="<%= bannerImg %>" style="width: 200px; height: 200px">
            <% } %>

            <div>
                <span>투표 <%= pollCount %></span>
                <a href="/campaign-candidate/<%= id %>">[더 보기]</a>
            </div>
        </div>
    </script>

    <@common.importJS />

    <script type="text/javascript">
        $(document).ready(function() {
        });

        var postList = {
            page: 1,

            appendItem: function() {
                campaignCandidateList.getData().then(function(data){
                    if(data.length == 0) {
                        return;
                    }

                    var template = _.template($("#campaign-candidate-detail-template").html());
                    data.forEach(function(e, i){
                        if (e.user.name == null)          e.user.name = '무명';
                        if (e.user.profileImg == null)    e.user.profileImg = 'http://t1.daumcdn.net/profile/TfdXX_AUCLw0';

                        $('#listSection').append(template(e));
                    });
                });
            },

            getData: function() {
                var data = {};
                if (postList.page != null) {
                    data.starId = 1;   // starId 는 해당 페이지 읽을 때 static하게 관리하는 변수로.... or 스타 정보 가져오는 부분에;;
                    data.page = postList.page + 1;
                }

                return $.ajax({
                    url : '/api/campaign-candidate',
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

        // TODO : 더보기 구현
    </script>
</body>
</html>
