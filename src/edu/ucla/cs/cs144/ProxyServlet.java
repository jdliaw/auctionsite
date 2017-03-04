package edu.ucla.cs.cs144;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import java.io.PrintWriter;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        /*
            Your implementation should extract the passed-in query string, issues a request to the Google suggest service for that query (at http://google.com/complete/search?output=toolbar&q=<your query>), and returns the results back to the original caller.

            Reference: http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
            and      : http://stackoverflow.com/questions/10116961/can-you-explain-the-httpurlconnection-connection-process
        */


        //Get query and encode it into a URL
        String query = request.getParameter("query");
        if(query == null) {
            PrintWriter out = response.getWriter();
            out.println("Error with query");
            out.close();
        }
        String encodedQuery = "http://google.com/complete/search?output=toolbar&q=" + URLEncoder.encode(query, "UTF-8");
        URL url = new URL(encodedQuery);//URLEncoder.encode(query, "UTF-8")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");

        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                res.append(inputLine);
            }
            in.close();

            PrintWriter out = response.getWriter();
            out.println(res.toString());
            out.close();
        }
        else {
            PrintWriter out = response.getWriter();
            out.print("Error in url thing");
            out.close();
        }

    }
}
