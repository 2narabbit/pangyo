<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Post/Edit</title>
</head>
<body>
    <table style="border:0px; text-align:center">
        <tr>
            <td><a id="backButton" href="#">뒤로</a></td>
            <td style="width:200px">글쓰기<br/>${star.name!} 팬클럽</td> <!-- TODO : 기획서에 직업 노출 있음. 현재DB에 직업없음 -->
            <td><a id="submitButton" href="#">완료</a></td>
        </tr>
    </table>

    <div>
        <textarea id="body" style="width:400px" rows="10" placeholder="${star.name!}님과 관련한 소식을 알려주세요."></textarea>
    </div>

    <div>
        TODO : 이미지 업로드
    </div>

    <@common.importJS />

<script type="text/javascript">
        $(document).ready(function() {
            $('#backButton').click(goToBack);
            $('#submitButton').click(submit);
        });

        function goToBack() {
            if(confirm("이 화면을 나가시면 내용이 저장되지 않습니다.")) {
                window.history.back();
            }
        }

        function submit() {
            var type = 'POST';
            var data = {
                starId: ${star.id!},
                user: {id:2},  // TODO : 실 USER 주입
                body: $('#body').val()
            };

            $.ajax({
                url : '/api/post',
                type : type,
                data : JSON.stringify(data),
                contentType : "application/json",
                success: function(result) {
                    // TODO : api 성공여부 확인
                    location.replace('/fanClub/${star.id!}');
                },
                error: function(res) {
                    console.log(res);
                    alert('글쓰기에 실패했습니다.');
                }
            });
        }

        <!-- TODO : 수정 -->
    </script>
</body>
</html>