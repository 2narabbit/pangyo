<head>
</head>

<body>
    <button id="logoutBtn" onclick="memberLogout()">로그아웃</button>

    <strong>TODO: 마이인포 만들어야함</strong>

    <script type="text/javascript">
        function memberLogout() {
            $.ajax({
                url : '/api/member/logout',
                type : 'GET',
                success: function() {
                    alert("+_+bb 로그아웃 성공");
                    location.reload();
                },
                error: function(res) {
                    console.log(res);
                    alert("로그아웃 실패..ㅠㅠ 미안")
                }
            });
        }
    </script>
</body>
