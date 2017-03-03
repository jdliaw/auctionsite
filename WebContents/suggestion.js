
/**
 * Provides suggestions for state names (USA).
 * @class
 * @scope public
 */
function GoogleSuggestions() {
  // this.states = [
  //     "Alabama", "Alaska", "Arizona", "Arkansas",
  //     "California", "Colorado", "Connecticut",
  //     "Delaware", "Florida", "Georgia", "Hawaii",
  //     "Idaho", "Illinois", "Indiana", "Iowa",
  //     "Kansas", "Kentucky", "Louisiana",
  //     "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", 
  //     "Mississippi", "Missouri", "Montana",
  //     "Nebraska", "Nevada", "New Hampshire", "New Mexico", "New York",
  //     "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", 
  //     "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
  //     "Tennessee", "Texas", "Utah", "Vermont", "Virginia", 
  //     "Washington", "West Virginia", "Wisconsin", "Wyoming"  
  // ];
}

/**
 * Request suggestions for the given autosuggest control. 
 * @scope protected
 * @param oAutoSuggestControl The autosuggest control to provide suggestions for.
 */


GoogleSuggestions.prototype.requestSuggestions = function (oAutoSuggestControl /*:AutoSuggestControl*/,
  bTypeAhead /*:boolean*/) {
  var aSuggestions = [];
  var sTextboxValue = oAutoSuggestControl.textbox.value;

  if (sTextboxValue.length > 0) {
    //Get the query URL and open up http request
    var queryUrl = "suggest?query=" + encodeURI(sTextboxValue);
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", queryUrl);
    //If ready, do this function (anonymous wow es6 so cool!!!)
    xmlHttp.onreadystatechange = () => {
      if (xmlHttp.readyState == 4 && xmlHttp.responseXML !== null) {
        var aSuggestions = [];      //this variable is the stuff that populates the dropdown
        var suggestions = xmlHttp.responseXML.getElementsByTagName('CompleteSuggestion');     //this gets all the XML into one variable
        for (i = 0; i < suggestions.length; i++) {
          var text = suggestions[i].childNodes[0].getAttribute("data");                       //get the data and push to our suggestions array
          aSuggestions.push(text);
        }
        console.log(aSuggestions);

        oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead);                            //was already here lol.
      }
    };
    xmlHttp.send(null);

  }

  // var aSuggestions = [];
  // var sTextboxValue = oAutoSuggestControl.textbox.value;

  // if (sTextboxValue.length > 0){

  //     //search for matching states
  //     for (var i=0; i < this.states.length; i++) { 
  //         if (this.states[i].indexOf(sTextboxValue) == 0) {
  //             aSuggestions.push(this.states[i]);
  //         } 
  //     }
  // }

  // //provide suggestions to the control
  // oAutoSuggestControl.autosuggest(aSuggestions, bTypeAhead);
};
