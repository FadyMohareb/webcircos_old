<!DOCTYPE html>
<html>
<head>
    <title>SeqCircos</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <script src="${contextPath}/resources/js/vendor/react.js"></script>
    <script src="${contextPath}/resources/js/vendor/showdown.min.js"></script>
    <script src="${contextPath}/resources/js/loginReact.js"></script>
    <script src="${contextPath}/resources/js/registractionReact.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>
<div id="signIn">${signIn}</div>

<script type="text/javascript">
    $(function () {
        renderSignin();
    });
</script>
</body>
</html>