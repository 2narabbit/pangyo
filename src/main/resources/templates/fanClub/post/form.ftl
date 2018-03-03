<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Post/Edit</title>
</head>
<body>
    <div>
        <span>뒤로</span>
        <span>글쓰기<br/>${star.name!} 팬클럽</span> <!-- TODO : 기획서에 직업 노출 있음. 현재DB에 직업없음 -->
        <span>완료</span>
    </div>

    <div>
        <textarea placeholder="${star.name!}님과 관련한 소식을 알려주세요."></textarea>
    </div>

    <div>
        TODO : 이미지
    </div>
</body>
</html>