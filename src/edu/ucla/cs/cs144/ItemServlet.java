package edu.ucla.cs.cs144;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

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
    request.setAttribute("data", data);

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(data)));
      request.setAttribute("doc", doc);
      root = doc.getDocumentElement();
    }
    catch (Exception e) {
      ;
    }

    request.setAttribute("test", root);

    String name = MyParser.getElementTextByTagNameNR(root, "name");
    request.setAttribute("name", name);

    request.getRequestDispatcher("/item.jsp").forward(request, response);
  }
}
