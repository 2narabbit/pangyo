<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Withdrawal Pangyo</title>
</head>
<body>
    <button id="withdrawalBtn" onclick="logout()">
        진짜, 진짜로 탈퇴할껀가요? 다시한번만 생각해줘요ㅠㅠ
    </button>

    <@common.importJS />
    <script src="/js/jquery.visible.js"></script>

    <script type="text/javascript">
        function logout() {
            $.ajax({
                url : '/api/member/me',
                type : 'DELETE',
                contentType : "application/json",
                success: function() {
                    alert('힝ㅠ 친구 손잡고 다시 와요!?');
                },
                error: function(res) {
                    console.log(res);
                    alert('일부러 그런건 아닙니다! 탈퇴 실패한 김에 한번 더 고민해 봐요!');
                }
            });
        }
    </script>
</body>
</html>