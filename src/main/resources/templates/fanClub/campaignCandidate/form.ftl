<#import "/macro/common.ftl" as common />
<#import "/macro/imageUploader.ftl" as imageUploader />

<!DOCTYPE html>
<html>
<head>
    <title>Post/Edit</title>
</head>
<body>
    <@common.importJS />

    <table style="border:0px; text-align:center">
        <tr>
            <td><a id="backButton" href="#">뒤로</a></td>
            <td style="width:200px">캠페인 후보 등록</td>
        </tr>
    </table>

    <form>
        <div style="margin-top:10px">* 캠페인 제목</div>
        <div>
            <input type="text" name="title" placeholder="캠페인 제목을 22자 이내로 입력해주세요." maxlength="22" style="width:400px">
        </div>

        <div style="margin-top:10px">* 캠페인 세부내용</div>
        <div>
            <textarea name="body" style="width:400px" rows="10" placeholder="캠페인 상세 내용을 입력해주세요."></textarea>
        </div>

        <div style="margin-top:10px">* 랜딩페이지 url</div>
        <div>
            <input type="text" name="randingUrl" placeholder="배너 클릭 시 이동할 랜딩페이지 url을 입력해주세요." style="width:400px">
            <br><input name="useCampaignRandingUrl" type="checkbox"> boostar 캠페인을 랜딩페이지로
        </div>

        <div style="margin-top:10px">* 광고 소재 등록</div>
        <div>
            <input type="radio" name="bannerImgRegister" value="custom" checked> 직접 업로드
            <input type="radio" name="bannerImgRegister" value="admin"> boostar에 소재제작 요청
        </div>

        <@imageUploader.defaultUI '' />
    </form>

    <div style="margin-top:10px">
        <a id="submitButton" href="#">등록 완료</a>
    </div>

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
            var data = getFormData($('form'));
            data.star = {id: ${starId!}};
            data.user = {id: 1};  // TODO : 실 USER 주입

            if (data.useCampaignRandingUrl) {
                data.randingUrl = '/fanClub/${starId!}/campaign-candidate';
            }

            if (data.bannerImgRegister == 'custom') {
                data.bannerImg = getImageUrl();
            }

            $.ajax({
                url : '/api/campaign-candidate',
                type : type,
                data : JSON.stringify(data),
                contentType : "application/json",
                success: function() {
                    location.replace('/fanClub/${starId!}/campaign-candidate');
                },
                error: function(res) {
                    console.log(res);
                    res = res.responseJSON;
                    if (res.message) {
                        alert(res.message);
                    } else {
                        alert('글쓰기에 실패했습니다.');
                    }
                }
            });
        }
    </script>
</body>
</html>
