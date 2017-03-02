package edu.ucla.cs.cs144;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.util.ArrayList;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemServlet extends HttpServlet implements Servlet {

  public ItemServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    // item id passed in through URL request
    String id = request.getParameter("id");

    // get XML data using item id
    String data = AuctionSearch.getXMLDataForItemId(id);

    Element root = null;
    try {
      // parse XML string into XML document
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(data)));
      root = doc.getDocumentElement();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    // get item id
    request.setAttribute("itemId", root.getAttribute("ItemID"));

    // get name and description
    String name = MyParser.getElementTextByTagNameNR(root, "Name");
    request.setAttribute("name", name);

    String description = MyParser.getElementTextByTagNameNR(root, "Description");
    request.setAttribute("description", description);

    // get categories as an array
    ArrayList<String> categories = new ArrayList<String>();
    Element[] catElem = MyParser.getElementsByTagNameNR(root, "Category");
    for (int i = 0; i < catElem.length; i++) {
      categories.add(MyParser.getElementText(catElem[i]));
    }
    request.setAttribute("categories", categories);

    // get the rest of XML elements
    String location = MyParser.getElementTextByTagNameNR(root, "Location");
    request.setAttribute("location", location);
    String currently = MyParser.getElementTextByTagNameNR(root, "Currently");
    request.setAttribute("currently", currently);
    String buyPrice = MyParser.getElementTextByTagNameNR(root, "Buy_Price");
    request.setAttribute("buy-now", buyPrice);
    String firstBid = MyParser.getElementTextByTagNameNR(root, "First_Bid");
    request.setAttribute("first-bid", firstBid);
    String nBids = MyParser.getElementTextByTagNameNR(root, "Number_of_Bids");
    request.setAttribute("num-bids", nBids);
    String start = MyParser.getElementTextByTagNameNR(root, "Started");
    request.setAttribute("start", start);
    String end = MyParser.getElementTextByTagNameNR(root, "Ends");
    request.setAttribute("end", end);

    request.getRequestDispatcher("/item.jsp").forward(request, response);
  }
}
