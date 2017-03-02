<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <html>

  <head>
    <title>Search Results</title>
  </head>

  <body>
    <form>
      <div>
        <div>
          <span>Enter a search query:</span><br/>
          <input type="text" name="query">
          <br>
          <input id="submit" type="submit">
          <h2>Searching for: ${query}</h2>
        </div>
      </div>

      <table>
        <c:forEach var="result" items="${results}">
          <tr>
            <td>${result.getItemId()}</td>
            <td>${result.getName()}</td>
          </tr>
        </c:forEach>

      </table>
      <br>



    </form>
  </body>

  </html>