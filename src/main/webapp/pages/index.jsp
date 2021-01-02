<html>
<body>
<h2>Hello World!</h2>
<th:block th:each="msg : ${messages}">
    <p th:text="${msg}"></p>
</th:block>
</body>
</html>
