<head>
</head>

<body>
    <script type="text/javascript">
        $(document).ready(function() {
            var message = '${errorMessage!}';
            alert(message ? message : '사용자 폭주로 접근이 어렵습니다.');
            history.back();
        });
    </script>
</body>
