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
            <input type="text" id="name" value="${name!}" maxlength="22" style="width:400px">
            <button id="checkBtn" onclick="checkValidName()">검증하기</button>
        </div>

        <div style="margin-top:10px">* 프로필 이미지</div>
        <div>
            <input type="text" id="profileImg" value="${profileImg!}" style="width:400px">
        </div>

        <div style="margin-top:10px">* 추천인 코드</div>
        <div>
            <input type="text" id="recommendCode" value="" style="width:400px">
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
            var recommendCode = $('#recommendCode').val();
            console.log(recommendCode);

            $.ajax({
                url  : '/api/member/isValid?recommendCode=' + encodeURI(recommendCode),
                type : 'GET',
                contentType : "application/json",
                success: function(res) {
                    var isValid = false;
                    if (res != null) {
                        var data = JSON.parse(res);
                        isValid = data.result;
                    }

                    if (isValid) {
                        alert('유효한 추천인 코드입니다.');
                    } else {
                        alert('유효하지 않은 추천인 코드입니다.');
                    }
                },
                error: function(res) {
                    console.log(res);
                    alert('요청이 실패했습니다. 다시 시도해 주세요');
                }
            });
        }

        function checkValidName() {
            var name = $('#name').val();

            $.ajax({
                url  : '/api/member/isValid?name=' + encodeURI(name),
                type : 'GET',
                contentType: "application/json",
                success: function (res) {
                    console.log(res);
                    var available = false;
                    if (res != null) {
                        var data = JSON.parse(res);
                        available = data.result;
                    }

                    if (available) {
                        alert('사용 할 수 있는 닉네임 입니다.');
                    } else {
                        alert('사용 할 수 없는 닉네임 입니다.');
                    }
                },
                error: function (res) {
                    console.log(res);
                    alert('요청이 실패했습니다. 다시 시도해 주세요');
                }
            });
        }

        function submit() {
            var data = {
                name: $('#name').val(),
                profileImg: $('#profileImg').val(),
                recommandCode: $('#recommandCode').val(),
            };

            $.ajax({
                url : '/api/member',
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