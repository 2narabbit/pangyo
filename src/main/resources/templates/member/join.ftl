<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Join Pangyo</title>
</head>
<body>
    <form>
        <div style="margin-top:10px">* 닉네임</div>
        <div>
            <input type="text" id="nickname" value="${nickname!}" maxlength="22" style="width:400px">
        </div>

        <div style="margin-top:10px">* 프로필 이미지</div>
        <div>
            <input type="text" id="profileImg" value="${profileImg!}" style="width:400px">
        </div>

        <div style="margin-top:10px">* 추천인 코드</div>
        <div>
            <input type="text" id="recommandCode" value="" style="width:400px">
            <button id="checkBtn" onclick="checkValidCode()">검증하기</button>
        </div>
    </form>

    <div style="margin-top:10px">
        <a id="submitButton" href="#">등록 완료</a>
    </div>

     <@common.importJS />
    <script src="/js/jquery.visible.js"></script>
    <script type='text/javascript'>

        function checkValidCode() {
            var recommandCode = $('#recommandCode').val();

            $.ajax({
                url : '/api/member/recommandCode/'+recommandCode,
                type : 'GET',
                contentType : "application/json",
                success: function() {
                    alert('유효한 추천인 코드입니다.');
                },
                error: function(res) {
                    console.log(res);
                    alert('유효하지 않은 추천인 코드입니다.');
                }
            });
        }

        function submit() {
            var data = {  // 나중에 정상적으로 처리되면 data 부분에 필요없는 내용 버려야함!!!(service, serviceUserId)
                service: '${service!}',
                serviceUserId: 776431321,
                name: $('#nickname').val(),
                profileImg: $('#profileImg').val(),
                recommandCode: $('#recommandCode').val(),
            };

            $.ajax({
                url : '/api/member/join',
                type : 'POST',
                data : JSON.stringify(data),
                contentType : "application/json",
                success: function() {
                    location.replace('${continueUrl!}');
                },
                error: function(res) {
                    console.log(res);
                    alert('회원가입에 실패했습니다.');
                }
            });
        }

        $(document).ready(function() {
            $('#submitButton').click(submit);
        });
    </script>
</body>
</html>