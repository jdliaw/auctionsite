/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */


//TODO: Read through the spec again. Perhaps do something with the primary keys, foreign keys, null values, etc.

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;

class MyParser {
    
    static final String columnSeparator = "|*|";
    static final String NEW_LINE_SEPARATOR = "\n";


    static final String ITEM_CSV = "item.csv";
    static final String CATEGORY_CSV = "category.csv";
    static final String BID_CSV = "bid.csv";
    static final String BIDDER_CSV = "bidder.csv";
    static final String SELLER_CSV = "seller.csv";

    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };

    
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }


    // static void createItem(String itemId, String name, String currently, String buyPrice, String firstBid, 
    //     String numberOfBids, String location, String latitude, String longitude, String country, 
    //     String started, String ends, String userId, String description) {

    //this isn't the most efficient way, but it's definitely much cleaner and easier to read...

    static void createItem(Element item, FileWriter writer) throws IOException {
        SimpleDateFormat old = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String itemId = item.getAttribute("ItemID");
        String name = getElementTextByTagNameNR(item, "Name");
        String currently = strip(getElementTextByTagNameNR(item, "Currently"));
        String buyPrice = strip(getElementTextByTagNameNR(item, "Buy_Price"));
        if(buyPrice == "") {
            buyPrice = "\\N";
        }
        String firstBid = strip(getElementTextByTagNameNR(item, "First_Bid"));
        String numberOfBids = getElementTextByTagNameNR(item, "Number_of_Bids");
        String location = getElementTextByTagNameNR(item, "Location");
        if(location == "") {
            location = "\\N";
        }
        String latitude = getElementByTagNameNR(item, "Location").getAttribute("Latitude");
        if(latitude == "") {
            latitude = "\\N";
        }
        String longitude = getElementByTagNameNR(item, "Location").getAttribute("Longitude");
        if(longitude == "") {
            longitude = "\\N";
        }
        String country = getElementTextByTagNameNR(item, "Country");
        if(country == "") {
            country = "\\N";
        }

        String started = getElementTextByTagNameNR(item, "Started");
        try {
            Date parseDate = old.parse(started);
            started = timestamp.format(parseDate);
        }
        catch (ParseException e) {
            System.err.println("Error parsing!");
        }
        String ends = getElementTextByTagNameNR(item, "Ends");
        try {
            Date parseDate = old.parse(ends);
            ends = timestamp.format(parseDate);
        }
        catch (ParseException e) {
            System.err.println("Error parsing!");
        }        

        String userId = getElementByTagNameNR(item, "Seller").getAttribute("UserID");     //seller rating            
        String description = getElementTextByTagNameNR(item, "Description");
        if(description.length() > 4000) {
            description = description.substring(0, 4000);
        }

        writer.append(itemId + columnSeparator + name + columnSeparator + currently + columnSeparator + buyPrice + columnSeparator + firstBid + columnSeparator + numberOfBids + columnSeparator + location + columnSeparator + latitude + columnSeparator + longitude
         + columnSeparator + country + columnSeparator + started + columnSeparator + ends + columnSeparator + userId + columnSeparator + description + NEW_LINE_SEPARATOR);
    }

    static void createCategories(Element item, FileWriter writer) throws IOException {
        String itemId = item.getAttribute("ItemID");
        String category;
        Element[] categories = getElementsByTagNameNR(item, "Category");
        for(int i = 0; i < categories.length; i++) {
            category = getElementText(categories[i]);
            writer.append(itemId + columnSeparator + category + NEW_LINE_SEPARATOR);
        }
        
    }

    // static void createBid(String itemId, String userId, String time, String amount) {

    // }
    static void createBids(Element item, FileWriter writer) throws IOException {
        String itemId; 
        String userId;
        String time;
        String amount;
        Element[] bids;
        SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat old = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");

        itemId = item.getAttribute("ItemID");
        bids = getElementsByTagNameNR(getElementByTagNameNR(item, "Bids"), "Bid");        //gets array of all bids
        //bids[i] is equivalent to a single <Bid>
        for(int i = 0; i < bids.length; i++) {
            userId = getElementByTagNameNR(bids[i], "Bidder").getAttribute("UserID");
            time = getElementTextByTagNameNR(bids[i], "Time");
            if(time == "") {
                time = "\\N";
            }
            try {
                Date parseDate = old.parse(time);
                time = timestamp.format(parseDate);
            }
            catch (ParseException e) {
                System.err.println("Error parsing!");
            }
            amount = strip(getElementTextByTagNameNR(bids[i], "Amount"));  
            writer.append(itemId + columnSeparator + userId + columnSeparator + time + columnSeparator + amount + NEW_LINE_SEPARATOR);          
        }

    } 

    // static void createBidder(String userId, String rating, String location, String country) {
    // }
    static void createBidders(Element item, FileWriter writer) throws IOException {
        //vars for our bidder table
        String userId;
        String rating;
        String location;
        String country;

        //bids is a var to get bids in order process the bidders
        Element[] bids;
        bids = getElementsByTagNameNR(getElementByTagNameNR(item, "Bids"), "Bid");      //gets array of all bids

        for(int i = 0; i < bids.length; i++) {
            Element bidderTag = getElementByTagNameNR(bids[i], "Bidder");
            userId = bidderTag.getAttribute("UserID");
            rating = bidderTag.getAttribute("Rating");
            if(rating == "") {
                rating = "\\N";
            }
            location = getElementTextByTagNameNR(bidderTag, "Location");
            if(location == "") {
                location = "\\N";
            }
            country = getElementTextByTagNameNR(bidderTag, "Country");
            if(country == "") {
                country = "\\N";
            }

            writer.append(userId + columnSeparator + rating + columnSeparator + location + columnSeparator + country + NEW_LINE_SEPARATOR);      
        }
    }       

    // static void createSeller(String userId, String rating) {
    // }
    static void createSeller(Element item, FileWriter writer) throws IOException {
        String sellerRating = getElementByTagNameNR(item, "Seller").getAttribute("Rating");     //seller rating
        String userId = getElementByTagNameNR(item, "Seller").getAttribute("UserID");     //seller rating      
        writer.append(userId + columnSeparator + sellerRating + NEW_LINE_SEPARATOR);  
    }

    
    static void writeFile(Document doc) {
        //Create file writers...
        FileWriter itemWriter;
        FileWriter categoryWriter;
        FileWriter bidWriter;
        FileWriter bidderWriter;
        FileWriter sellerWriter;

        try {
            itemWriter = new FileWriter(ITEM_CSV, true);
            categoryWriter = new FileWriter(CATEGORY_CSV, true);
            bidWriter = new FileWriter(BID_CSV, true);
            bidderWriter = new FileWriter(BIDDER_CSV, true);
            sellerWriter = new FileWriter(SELLER_CSV, true);

            FileWriter testwriter = new FileWriter("test.csv", true);
            testwriter  .append("Test 1" + columnSeparator + "Test 2" + NEW_LINE_SEPARATOR);

            //Grabs the room, which is Items
            Element root = doc.getDocumentElement();
            //Grabs each Item in Items
            Element[] items = getElementsByTagNameNR(root, "Item");
            for(int i = 0; i < items.length; i++) {
                Element item = items[i];            //<Item>Stuff here</Item>
                createItem(item, itemWriter);
                createCategories(item, categoryWriter);
                createBids(item, bidWriter);
                createBidders(item, bidderWriter);
                createSeller(item, sellerWriter);
            }    

            itemWriter.flush();
            itemWriter.close();
            categoryWriter.flush();
            categoryWriter.close();
            bidWriter.flush();
            bidWriter.close();
            bidderWriter.flush();
            bidderWriter.close();
            sellerWriter.flush();
            sellerWriter.close();

            testwriter.flush();
            testwriter.close();
        } catch(IOException e) {
            System.err.println("Error writing to file");
        } 


        
    }

    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
        
        writeFile(doc);


        
        /**************************************************************/
        
    }




    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
