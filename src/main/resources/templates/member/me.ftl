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
            <input type="text" id="name" value="${viewer.getUser().name!}" maxlength="22" style="width:400px">
            <button id="checkBtn" onclick="checkValidName()">검증하기</button>
        </div>

        <div style="margin-top:10px">* 프로필 이미지</div>
        <div>
            <input type="text" id="profileImg" value="${viewer.getUser().profileImg!}" style="width:400px">
        </div>

        <div style="margin-top:10px">* 럽 이미지</div>
        <div>
            <input type="text" id="lov" value="100" style="width:400px">
        </div>

    </form>

    <div style="margin-top:10px">
        <a id="submitButton" href="#">수정하기</a>
    </div>

    <@common.importJS />
    <script src="/js/jquery.visible.js"></script>
    <script type='text/javascript'>
        function checkValidName() {
            var name = $('#name').val();
            alert('/api/member/isValid?name=' + encodeURI(name));
            $.ajax({
                url : '/api/member/isValid?name=' + encodeURI(name),
                type : 'GET',
                contentType: "application/json",
                success: function (res) {
                    console.log(res);
                    if (res != null) {
                        var data = JSON.parse(res);
                        console.log(data);
                        if (data.result) {
                            alert('사용 할 수 있는 닉네임 입니다.');
                        } else {
                            alert('사용 할 수 없는 닉네임 입니다.');
                        }
                    }
                },
                error: function () {
                    alert('유효하지 않은 요청입니다. 다시 시도해 주세요.');
                }
            });
        }

        function submit() {
            alert("zzzzz");
            var data = {
                id: $( ${viewer.id!}),
                name: $('#name').val(),
                profileImg: $('#profileImg').val()
            };

            $.ajax({
                url: '/api/member/me',
                type: 'PUT',
                data: JSON.stringify(data),
                contentType: "application/json",
                success: function () {
                    location.reload();
                },
                error: function (res) {
                    console.log(res);
                    alert('회원 정보 수정에 실패했습니다.');
                }
            });
        }

        $(document).ready(function() {
            alert("dddd");
            $('#submitButton').click(submit);
        });
    </script>
</body>
</html>