/**
 * Created by dolu on 2018-04-03.
 */
$(document).ready(function() {

    /* Use JQuery to get the page elements.  */
    //var subs = $("#sub_perk").get(0);//This should be changed to an ajax call to the db.
    var cntxPath = window.location.protocol + "//" + window.location.host;
    var searchName = $( "#search_perk" )

    var form, subscription = $("#sub_name");

    document.getElementById("search").addEventListener("click", searchPerkListener);

    /* Get the subscription table in the mainpage. */
    function GetTable() {

        $("#PerksTable").find("tr:gt(0)").remove();
        $.ajax({
            async:false,
            url: cntxPath+"/GetTable?"
        }).then(function(data) {
            $('#PerksTable').append(""+data);
        });

    }

    function searchPerkListener(){
        $('#PerksTable').empty();
        $.ajax({
            type:"GET",
            async:false,
            url: cntxPath+"/SearchPerkUnregistered?searchName="+searchName.val(),
        }).then(function(data) {
            $('#PerksTable').append("<tr><th>Subscription</th><th>Perk Code</th><th>Perk Description</th><th>Expiry Date</th><th></th><th></th><th></th></tr>"+data);
        });
    }

    /* Make sure they aren't pushing at least 3 chars. */
    function checkLength( o, min ) {
        if ( o.val().length < min ) {
            showWarnings();
            return false;
        } else {
            return true;
        }
    }

    $.ajax({
        type: "GET",
        async: false,
        url: cntxPath+"/GeneralPopulation",
    }).then(function(data) {
        //$('#PerksTable').append("<tr><th>Subscription</th><th>Perk Code</th><th>Perk Description</th><th>Expiry Date</th><th></th><th></th><th></th></tr>"+data);
         $('#PerksTable').append(""+data);

    });

});
