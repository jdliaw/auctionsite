Thanks for grading!

We have implemented functionality for all of the requirements listed in the spec.

If you navigate to http://localhost:1448/eBay/search you should be able to see a basic search interface. You can type any query and a suggestion dropdown will be shown. You can either use the up/down arrow keys or your mouse to select which one you want. Because it was not clarified on Piazza, I decided not to include the autocomplete because of some small AJAX bug. The spec did not state that it was necessary to include it, and TAs did not respond on Piazza for this topic as of 03/04/17 1:23PM.

When you search, you should be able to see up to 20 Item IDs and respective name. If there are fewer than 20 results left, it will only display that many. If there are no results left to be shown (either no results match the query skipped through all the queries) then we display a message that says so. If you click on the Item Name, it navigates you to the item page at http://localhost:1448/eBay/item?id=<ITEM_ID>. 

The Item Page properly displays the item information as well as the coordinates of this item. If no coordinates are given, the map is set zoomed all the way out with coordinates of (34.063509, -118.44541).

Error handling:
Our implementation handles empty queries, skipping more than the number of results to return, trying to skip when there is no query, as well as invalid item ID in the item page. In theory, users should never end up navigating to an invalid Item ID because only valid item IDs are linked through search, however we did cover this case just in case. Lastly, our implementation also properly handles URLs and special characters. In no way does our program crash.