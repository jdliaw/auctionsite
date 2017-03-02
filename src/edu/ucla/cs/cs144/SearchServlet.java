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
    public static final String NUM_RESULTS_TO_DISPLAY_STRING = String.valueOf(NUM_RESULTS_TO_DISPLAY);
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // get query
        String query = request.getParameter("query");
        String numResultsToSkipString = request.getParameter("numResultsToSkip");
        int numResultsToSkip;
        if(numResultsToSkipString == null) {
            numResultsToSkip = 0;
        }
        else {
            numResultsToSkip = Integer.parseInt(request.getParameter("numResultsToSkip"));
        }

        if(query == null) {
            query = "";
        }

        SearchResult[] results = AuctionSearch.basicSearch(query, numResultsToSkip, NUM_RESULTS_TO_DISPLAY);
        if(results == null) {
            request.setAttribute("query", "It is a null value...");
        }
        else {
            request.setAttribute("results", results);
            request.setAttribute("query", query);
            request.setAttribute("numResultsToSkip", numResultsToSkip);

        }
        request.setAttribute("numResultsToReturn", NUM_RESULTS_TO_DISPLAY_STRING);
        request.getRequestDispatcher("/search.jsp").forward(request, response);
    }
}
