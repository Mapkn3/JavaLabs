<%@ page import="dto.TaskDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="../../resource/styles/style.css">
    <meta charset="UTF-8">
    <title>Result JSP</title>
</head>
<body>
<a href="index.jsp" id="homepage_link">На главную страницу</a>
<p id="result">
<%
    TaskDto task = (TaskDto) session.getAttribute("task");
    if (task != null) {
%>
    <div id="string_param">
        Строка: <%=task.getStr()%>
    </div>
    <div id="number_param">
        Количество повторений: <%=task.getNum()%>
    </div>
    <br/>
    <a href="../../result.xml" id="download_link" download>Скачать result.xml</a>
</p>
<div id="task_result"><%=task.getResult()%></div>
<%} else {%>
<meta http-equiv="refresh" content="5; URL='http://localhost:8080/pages/jsp/index.jsp'"/>
<h3>You redirected to home page after 5 seconds...</h3>
<%}%>
</body>
</html>