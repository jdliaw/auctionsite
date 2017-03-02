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
    String id = request.getParameter("id");
    Element root = null;

    String data = AuctionSearch.getXMLDataForItemId(id);
    // request.setAttribute("data", data);

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(data)));
      // request.setAttribute("doc", doc);
      root = doc.getDocumentElement();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    request.setAttribute("itemId", root.getAttribute("ItemID"));

    String name = MyParser.getElementTextByTagNameNR(root, "Name");
    request.setAttribute("name", name);

    String description = MyParser.getElementTextByTagNameNR(root, "Description");
    request.setAttribute("description", description);

    ArrayList<String> categories = new ArrayList<String>();
    Element[] catElem = MyParser.getElementsByTagNameNR(root, "Category");
    for (int i = 0; i < catElem.length; i++) {
      categories.add(MyParser.getElementText(catElem[i]));
    }
    request.setAttribute("categories", categories);

    request.getRequestDispatcher("/item.jsp").forward(request, response);
  }
}
