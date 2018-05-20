<#import "/macro/common.ftl" as common />

<!DOCTYPE html>
<html>
<head>
    <title>Sorry!</title>
</head>
<body>
    <@common.importJS />

    <script type="text/javascript">
        $(document).ready(function() {
            var message = '${errorMessage}';
            alert(message ? message : '사용자 폭주로 접근이 어렵습니다.');
            history.back();
        });
    </script>
</body>
</html>
