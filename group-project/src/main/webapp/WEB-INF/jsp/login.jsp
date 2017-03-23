<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SeqCircos</title>
    <script type="text/javascript" src="vendor/react.js"></script>
    <script type="text/javascript" src="vendor/showdown.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
</head>
<body>
<div id="main">${main}</div>
<script type="text/javascript" src="login.js"></script>
<script type="text/javascript">
    $(function () {
        renderMainComponent();
    });
</script>
</body>
</html>