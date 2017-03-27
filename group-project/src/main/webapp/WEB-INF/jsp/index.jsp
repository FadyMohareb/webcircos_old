<html lang="en" xmlns:th="http://www.springframework.org/schema/mvc">
<head>
    <title>Hello React</title>
    <script type="text/javascript" th:src="@{/vendor/react.js}"/>
    <script type="text/javascript" th:src="@{/vendor/showdown.min.js}"/>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.0.min.js"></script>
</head>
<body>
<div id="content">${content}</div>
<script type="text/javascript" th:src="@{/js/commentBox.js}"/>
<script type="text/javascript">
    $(function () {
        renderClient([]);
    });
</script>
</body>
</html>