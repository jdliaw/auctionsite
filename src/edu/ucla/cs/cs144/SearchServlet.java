package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

public class SearchServlet extends HttpServlet implements Servlet {

    public static final int NUM_RESULTS_TO_DISPLAY = 20;
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // get query
        String query = request.getParameter("query");
        String numResultsToSkipString = request.getParameter("numResultsToSkip");
        String numResultsToReturnString = request.getParameter("numResultsToReturn");
        int numResultsToSkip;
        int numResultsToReturn;
        if(numResultsToSkipString == null) {
            numResultsToSkip = 0;
        }
        else {
            numResultsToSkip = Integer.parseInt(request.getParameter("numResultsToSkip"));
        }

        if(numResultsToReturnString == null) {
            numResultsToReturn = 0;
        }
        else {
            numResultsToReturn = Integer.parseInt(request.getParameter("numResultsToReturn"));
        }

        if(query == null) {
            query = "";
        }




        SearchResult[] results = AuctionSearch.basicSearch(query, numResultsToSkip, numResultsToReturn);
        if(results == null) {
            request.setAttribute("query", "It is a null value...");
        }
        else {
            request.setAttribute("results", results);
            request.setAttribute("query", query);
            request.setAttribute("print", AuctionSearch.getXMLDataForItemId("1045272701"));
        }

        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
