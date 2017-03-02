<html>

<head>
  <title>
    <%= request.getAttribute("title") %>
  </title>
</head>
<body>
  <h1>Search</h1>
  <form>
    <h3>Enter your query here:</h3>
    <input type="text" name="query" />
    <input type="submit" value="Submit" />
  </form>

  <p>
    <%= request.getAttribute("print") %>
  </p>

</body>

</html>