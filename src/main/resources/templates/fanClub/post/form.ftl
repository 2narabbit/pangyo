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
        <textarea id="body" style="width:400px" rows="10" placeholder="${star.name!}님과 관련한 소식을 알려주세요."><#if post??>${post.body!}</#if></textarea>
    </div>

    <div>
        <div>
            <input id="uploadImage" type="file">
            <a id="removeImage" href="#">삭제</a>
        </div>

        <div id="imageView">
            <#if post?? && post.img??>
                <img src="${post.img}" style="max-width:400px">
            </#if>
        </div>
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
            <#if post??>
                var type = 'PUT';
            <#else>
                var type = 'POST';
            </#if>
            var data = {
                <#if post??>
                    id: ${post.id!},
                </#if>
                star: {id: ${star.id!}},
                user: {id:2},  // TODO : 실 USER 주입
                body: $('#body').val()
            };

            var img = $('#imageView > img').attr('src');
            if (img) {
                data['img'] = img;
            } else {
                data['img'] = '';
            }

            $.ajax({
                url : '/api/fanClub/${star.id!}/post',
                type : type,
                data : JSON.stringify(data),
                contentType : "application/json",
                success: function() {
                    location.replace('/fanClub/${star.id!}');
                },
                error: function(res) {
                    console.log(res);
                    alert('글쓰기에 실패했습니다.');
                }
            });
        }

        $("#uploadImage").change(function(){
            var file = this.files[0];

            $.ajax({
                url : '/api/image/' + file.name,
                type : 'POST',
                data : file,
                cache: false,
                processData: false,
                contentType: false,
                success: function(res) {
                    var html = '<img src="/api/image/' + file.name + '" style="max-width:400px">';
                    $('#imageView').html(html);
                },
                error: function(res) {
                    console.log(res);
                    alert('이미지 업로드에 실패했습니다.');
                }
            });
        });

        $('#removeImage').click(function () {
            if(confirm("이미지를 정말 삭제하시겠습니까?")) {
                $('#imageView').html('');
            }
        });
    </script>
</body>
</html>
