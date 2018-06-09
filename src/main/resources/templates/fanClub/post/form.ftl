<#import "/macro/common.ftl" as common />
<#import "/macro/imageUploader.ftl" as imageUploader />

<!DOCTYPE html>
<html>
<head>
    <title>Post/Edit</title>
</head>
<body>
    <table style="border:0px; text-align:center; width: 400px">
        <tr>
            <td><a id="backButton" href="#">뒤로</a></td>
            <td style="width:200px">글쓰기<br/>${star.name!} 팬클럽</td>
            <td><a id="submitButton" href="#">완료</a></td>
        </tr>
    </table>

    <div>
        <textarea id="body" style="width:400px" rows="10" placeholder="${star.name!}님과 관련한 소식을 알려주세요.">${(post.body)!}</textarea>
    </div>

    <@common.importJS />

    <@imageUploader.defaultUI (post.img)! />

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
                body: $('#body').val(),
                img: getImageUrl()
            };

            $.ajax({
                url : '/api/post',
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

    </script>
</body>
</html>
