<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <html>

  <head>
    <title>Search Results</title>
  </head>

  <body>
    <form>
      <span>Enter a search query:</span><br/>
      <input type="text" name="query" value=${query}>
      <br>
      <input id="submit" type="submit" value="Search!">
      <button onclick="skipResults()">More</button>
      <h2>Searching for: ${query}</h2>

      <table>
        <c:forEach var="result" items="${results}">
          <tr>
            <td>${result.getItemId()}</td>
            <td>${result.getName()}</td>
          </tr>
        </c:forEach>

      </table>
      <br> Print: ${print} <br>
      <input name="numResultsToSkip" type="hidden" value="0">
      <input name="numResultsToReturn" type="hidden" value="10">

      <!--save data: http://stackoverflow.com/questions/1055308/retaining-the-submitted-jsp-form-data-->

    </form>

    <script type="text/javascript">
      function skipResults() {
        var indexVal = document.getElementById("index").value;
        var oldIndex = parseInt(indexVal);
        var newIndex = oldIndex + 20;
        var newIndexString = newIndex.toString();
        document.getElementById("index").value = newIndexString;
        console.log(document.getElementById("index").value);
      }
    </script>
  </body>


  </html>