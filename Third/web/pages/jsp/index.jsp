<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.*" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="../../resource/styles/style.css">
    <meta charset="UTF-8">
    <title>Index JSP</title>
</head>
<body>
<h1 class="center">Программа для размножения строки</h1>
<p id="task" class="center">
    Первый аргумент – <i>строка</i><br/>
    Второй аргумент – <i>число</i><br/>
    Результат – <i>строка, повторенная заданное число раз</i>
</p>
<div id="form" class="center">
    <form id="params_form" method="get" enctype="text/plain">
        <%
            String str = request.getParameter("string_value") != null ? request.getParameter("string_value") : "";
            String num = request.getParameter("number_value") != null ? request.getParameter("number_value") : "";

            String strError = "";
            String numError = "";
            String errorLog = "";

            try {
                if (str.isEmpty()) {
                    strError = " class=\"error\"";
                    errorLog += "Строка должна содержать как минимум 1 символ!<br/>";
                }
                if (Integer.parseInt(num) <= 0) {
                    numError = " class=\"error\"";
                    errorLog += "Число должно быть строго больше 0!<br/>";
                }
                if (!str.isEmpty() && Integer.parseInt(num) > 0) {
                    TaskDto task = new TaskDto(str, Integer.parseInt(num));
                    session.setAttribute("task", task);
                    response.sendRedirect("http://localhost:8080/result.html");
                    return;
                }
            } catch (NumberFormatException e) {
                numError = " class=\"error\"";
                errorLog += "Необходимо ввести число большее нуля!";
            }
        %>
        <fieldset form="params_form" name="params_fields">
            <legend align="center">Параметры задания</legend>
            <div id="string_param" class="param">
                <label for="string_param_value" id="string_param_label">Введите строку<em>*</em></label>
                <input type="text" name ="string_value" id="string_param_value" form="params_form"
                       placeholder="Пример, Мир!"
                    <%=strError%> value=<%=str%>>
            </div>
            <div id="number_param" class="param">
                <label for="number_param_value" id="number_param_label">Введите натуральное число<em>*</em></label>
                <input type="text" name="number_value" id="number_param_value" form="params_form" placeholder="5"
                    <%=numError%> value=<%=num%>>
            </div>
        </fieldset>
        <p class="error"><%=errorLog%></p>
    </form>
    <form id="reset_form" method="get"></form>
    <div id="controls">
        <button type="submit" form="params_form">Сгенерировать</button>
        <button form="reset_form">Сбросить</button>
    </div>

</div>
</body>
</html>