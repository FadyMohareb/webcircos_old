<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.springframework.org/schema/mvc">
<head>
    <title>Hello React</title>
       <script src="${contextPath}/resources/js/vendor/react.js"></script>
       <script src="${contextPath}/resources/js/vendor/showdown.min.js"></script>
       <script src="${contextPath}/resources/js/commentBox.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
</head>
<body>
<div id="content">${content}</div>
<script type="text/javascript">
    $(function () {
        renderClient([]);
    });
</script>
</body>
</html>