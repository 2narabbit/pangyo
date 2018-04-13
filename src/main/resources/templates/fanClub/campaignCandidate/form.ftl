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
            <td style="width:200px">캠페인 후보 등록</td>
        </tr>
    </table>

    <div style="margin-top:10px">* 캠페인 제목</div>
    <div>
        <input type="text" name="title" placeholder="캠페인 제목을 22자 이내로 입력해주세요." maxlength="22">
    </div>

    <div style="margin-top:10px">* 캠페인 세부내용</div>
    <div>
        <textarea name="body" style="width:400px" rows="10" placeholder="캠페인 상세 내용을 입력해주세요."></textarea>
    </div>

    <div style="margin-top:10px">* 랜딩페이지 url</div>
    <div>
        <input type="text" name="randingUrl" placeholder="배너 클릭 시 이동할 랜딩페이지 url을 입력해주세요.">
        <br><input type="checkbox"> boostar 캠페인을 랜딩페이지로
    </div>

    <div style="margin-top:10px">* 광고 소재 등록</div>
    <div>
        <input type="radio" name="bannerImgRegister" value="custom" checked> 직접 업로드
        <input type="radio" name="bannerImgRegister" value="trust"> boostar에 소재제작 요청
    </div>

    <div style="margin-top:10px">
        TODO : 이미지 업로드
    </div>

    <div style="margin-top:10px">
        <a id="submitButton" href="#">등록 완료</a>
    </div>

    <@common.importJS />

    <script type="text/javascript">

    </script>
</body>
</html>