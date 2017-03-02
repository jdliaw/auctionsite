<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title><%= request.getAttribute("name") %></title>
  </head>
  <body>
    <h1>${name}</h1>
    <h4>Description</h4>
    <p>${description}</p>
    <table>
      <tr>
        <td><b>ID</b></td>
        <td>${itemId}</td>
      </tr>
      <tr>
        <td><b>Categories</b></td>
        <c:forEach var="category" items="${categories}">
          <td>${category}</td>
        </c:forEach>
      </tr>
    </table>
  </body>
</html>