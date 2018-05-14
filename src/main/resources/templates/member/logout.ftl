<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Logout Pangyo</title>
</head>
<body>
    <button id="logoutBtn" onclick="logout()">
        <img src="/img/logout_btn.png" width="300"/>
    </button>
    </body>

    <@common.importJS />
    <script src="/js/jquery.visible.js"></script>

    <script type="text/javascript">
        function logout() {
            $.ajax({
                url : '/api/member/logout',
                type : 'GET',
                contentType : "application/json",
                success: function() {
                    alert('힝ㅠ 다시 돌아올꺼죠?');
                },
                error: function(res) {
                    console.log(res);
                    alert('쏴뤼- 로그아웃을 실패했습니다.');
                }
            });
        }
    </script>
</html>
