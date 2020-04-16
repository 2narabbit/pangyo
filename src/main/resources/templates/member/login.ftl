<head>
</head>

<body>
    <#if kOauthInfo??>
        <span>accessToken : ${kOauthInfo.accessToken}</span>
        <span>refreshToken : ${kOauthInfo.refreshToken}</span>
    </#if>

    <div style="text-align: center; width:400px">
        <img src="//visualpharm.com/assets/448/User-595b40b85ba036ed117dbd22.svg" style="width:80px; padding:50px;">
        <p>로그인 혹은 회원 가입을 해주세요.</p>
        <button id="loginBtn" onclick="redirectLoginPage()">
            <img src="/img/login_btn.png" style="width:300px"/>
        </button>
        <p style="padding:30px">지금 나의 스타를 광고해보세요!</p>
    </div>

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
