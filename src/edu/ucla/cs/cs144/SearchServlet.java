package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

public class SearchServlet extends HttpServlet implements Servlet {

    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      //set title
      String pageTitle = "Search";
      request.setAttribute("title", pageTitle);

      //our test printing...
      String query = request.getParameter("query");
      request.setAttribute("title", query);

      if(query != null) {
        SearchResult[] searchResult = AuctionSearch.basicSearch(query, 0, 20);
        request.setAttribute("print", searchResult);
      }


      request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
