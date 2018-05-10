<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Login Pangyo</title>
</head>
<body>
    <#--<#if auth??>-->
        <#--<button id="homeBtn" onclick="redirectHomePage()">-->
            <#--<img src="/img/booo_star.png" width="300"/>-->
        <#--</button>-->
    <#--<#else>-->
        <#if kOauthInfo??>
            <span>accessToken : ${kOauthInfo.accessToken}</span>
            <span>refreshToken : ${kOauthInfo.refreshToken}</span>
        </#if>
        <button id="loginBtn" onclick="redirectLoginPage()">
            <img src="/img/login_btn.png" width="300"/>
        </button>
    <#--</#if>-->

    <script type="text/javascript">
        function redirectHomePage() {
            location.href = "/star";
        }

        function redirectLoginPage() {
            var masterUrl = 'https://kauth.kakao.com/oauth/authorize?';
            var clientId = '06f5127652e08d50c9e8dce58624e96b';
            var redirectUri = 'http://localhost:8080/member/oauth';
            var responseType = 'code';

            location.href = masterUrl + 'client_id=' + clientId + '&redirect_uri=' + encodeURIComponent(redirectUri) + '&response_type='+responseType;
        }
    </script>
</body>
</html>