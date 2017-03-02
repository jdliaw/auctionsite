<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <title>Search</title>
  </head>

  <body>
    <h1>Search</h1>
    <p>Enter your query:</p>
    <form>
      <input type="text" name="query">
      <input id="submit" type="submit" value="Search!">
      <h2>Results: ${query}</h2>
      <input name="numResultsToSkip" type="hidden" value="0">
      <table>
        <c:forEach var="res" items="${results}">
          <tr>
            <td>${res.getItemId()}</td>
            <td><a href="/eBay/item?id=${res.getItemId()}">${res.getName()}</a></td>
          </tr>
        </c:forEach>

      </table>
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