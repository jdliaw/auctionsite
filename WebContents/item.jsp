<html>
  <head>
    <title><%= request.getAttribute("name") %></title>
  </head>
  <body>
    <p><%= request.getAttribute("data") %></p>
    <h1>Name: <%= request.getAttribute("name") %></h1>

    <h2>${doc}</h2>
    <p>${test}</p>
  </body>
</html>