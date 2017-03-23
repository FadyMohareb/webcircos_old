<html>
<head>
    <title>SeqCircos</title>
    <script src="${contextPath}/resources/js/vendor/react.js"></script>
    <script src="${contextPath}/resources/js/vendor/showdown.min.js"></script>
    <script src="${contextPath}/resources/js/login.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
</head>
<body>
<div id="signIn">${signIn}</div>

<script type="text/javascript">
    $(function () {
        renderSignin();
    });
</script>
<div id="signUp">${signUp}</div>
<script type="text/javascript">
    $(function () {
        renderSignup();
    });
</script>
</body>
</html>