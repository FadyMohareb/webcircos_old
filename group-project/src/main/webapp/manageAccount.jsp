<!DOCTYPE html>
<html>
<head>
    <title>SeqCircos</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/font-awesome.min.css" rel="stylesheet">
    <script src="${contextPath}/resources/js/vendor/react.js"></script>
    <script src="${contextPath}/resources/js/vendor/showdown.min.js"></script>
    <script src="${contextPath}/resources/js/userAccountBox.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
</head>
<body>
<div id="accountCard">${accountCard}</div>

<script type="text/javascript">
    $(function () {
        renderUserAccount();
    });
</script>
</body>
</html>
