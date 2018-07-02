<head>
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
            <input type="text" id="recommendCode" value="" style="width:400px">
            <button id="checkBtn" onclick="checkValidCode()">검증하기</button>
        </div>
    </form>

    <div style="margin-top:10px">
        <a id="submitButton" href="#">등록 완료</a>
    </div>

    <script type='text/javascript'>
        function checkValidCode() {
            var recommendCode = $('#recommendCode').val();
            console.log(recommendCode);

            $.ajax({
                url : '/api/member?recommendCode='+recommendCode,
                type : 'GET',
                contentType : "application/json",
                success: function(res) {
                    console.log(res);
                    var isValid = false;
                    if (res != null) {
                        var user = JSON.parse(res);
                        if (user.id > 0) {
                            isValid = true;
                            alert('유효한 추천인 코드입니다.');
                        }
                    }

                    if (!isValid){
                        alert('유효하지 않은 추천인 코드입니다.');
                    }
                },
                error: function(res) {
                    console.log(res);
                    alert('유효하지 않은 추천인 코드입니다.');
                }
            });
        }

        function submit() {
            var data = {
                name: $('#nickname').val(),
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
