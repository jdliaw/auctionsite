<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <html>

  <head>
    <title>Search Results</title>
  </head>

  <body>
    <form>
      <span>Search query:</span>
      <input type="text" name="query">
      <br>
      <input id="submit" type="submit" value="Search!">
      <!--<button onclick="skipResults()">More</button>-->
      <h2>Searching for: ${query}</h2>
      <input name="numResultsToSkip" type="hidden" value="0">
      <table>
        <c:forEach var="res" items="${results}">
          <tr>
            <td>${res.getItemId()}</td>
            <td>${res.getName()}</td>
          </tr>
        </c:forEach>

      </table>


      <!--save data: http://stackoverflow.com/questions/1055308/retaining-the-submitted-jsp-form-data-->

    </form>

		<button><a id="next-k" href="search?query=test&numResultsToSkip=0">Next ${numResultsToReturn} items</a></button>


		<script type="text/javascript">
      var numResultsToReturn = parseInt("${numResultsToReturn}");
			var numResultsToSkip = parseInt("${numResultsToSkip}");


			var next_input = document.getElementById("next-k");
      next_input.href = "search?query=${query}&numResultsToSkip=" + (numResultsToSkip + numResultsToReturn);
			
		</script>
  </body>


  </html>