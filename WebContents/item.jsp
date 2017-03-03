<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title><%= request.getAttribute("name") %></title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0px; padding: 0px }
      #map_canvas { height: 100% }
    </style>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">
      function initialize() {
        console.log("coordinates", "${latitude}");
        var latlng = new google.maps.LatLng("${latitude}","${longitude}");
        var myOptions = {
          zoom: parseInt("${zoom}", 10), // default is 8
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
            myOptions);
      }
    </script>
  </head>
  <body onload="initialize()">
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
      <tr>
        <td><b>Cooridnates</b></td>
        <td>${latitude}, ${longitude}</td>
      </tr>
    </table>

    <div id="map_canvas" style="width:100%; height:100%"></div>
  </body>
</html>