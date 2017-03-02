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
        <td>
          <c:forEach var="category" items="${categories}">
            ${category},
          </c:forEach>
        </td>
      </tr>
      <tr>
        <td><b>Location</b></td>
        <td>${location}</td>
      </tr>
      <tr>
        <td><b>Current Bid</b></td>
        <td>${currently}</td>
      </tr>
      <tr>
        <td><b>Buy Now Price</b></td>
        <td>${buy-now}</td>
      </tr>
      <tr>
        <td><b>First Bid</b></td>
        <td>${first-bid}</td>
      </tr>
      <tr>
        <td><b>Number of Bids</b></td>
        <td>${num-bids}</td>
      </tr>
      <tr>
        <td><b>Bid Started</b></td>
        <td>${start}</td>
      </tr>
      <tr>
        <td><b>Bid Ends</b></td>
        <td>${end}</td>
      </tr>

    </table>
  </body>
</html>