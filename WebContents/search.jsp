<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <html>

  <head>
    <title>Search</title>
    <script type="text/javascript" src="autosuggest.js"></script>
    <script type="text/javascript" src="suggestion.js"></script>
    <script type="text/javascript">
      window.onload = function () {
        var oTextbox = new AutoSuggestControl(document.getElementById("txt1"), new GoogleSuggestions());
      }
    </script>
    
    <link rel="stylesheet" type="text/css" href="search.css">

  </head>

  <body>
    <h1>Search</h1>
    <p>Enter your query:</p>
    <form autocomplete="off">
      <input type="text" id="txt1" name="query">
      <input type="submit" value="Search!">
      <h2>Your query: ${query}</h2>
      <input name="numResultsToSkip" type="hidden" value="0">
      <table>
        <tr>
          <th>Item ID</th>
            <th>Item Name</th
          </tr>
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