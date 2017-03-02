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
        // your codes here
        String query = request.getParameter("query");
        if(query == null) {
            query = "";
        }

        SearchResult[] results = AuctionSearch.basicSearch(query, 0, 20);
        if(results == null) {
            request.setAttribute("query", "It is a null value...");
        }
        else {
            request.setAttribute("results", results);
            request.setAttribute("query", query);
        }

        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
