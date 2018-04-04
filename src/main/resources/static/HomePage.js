/**
 * Created by dolu on 2018-04-03.
 */
$(document).ready(function() {

    /* Use JQuery to get the page elements.  */
    //var subs = $("#sub_perk").get(0);//This should be changed to an ajax call to the db.
    var cntxPath = window.location.protocol + "//" + window.location.host;

    var form, subscription;


    /* Get the subscription table in the mainpage. */
    function GetTable() {

        $('#PerksTable').empty();
        $.ajax({
            async:false,
            url: cntxPath+"/GetTable"
        }).then(function(data) {
            $('#PerksTable').append("<tr><th>Perk Code</th><th>Perk Description</th><th>Expiry Date</th><th></th><th></th><th></th></tr>"+data);
        });

    }

    function validate() {
        
    }

    function Search(input) {
        /* We want to use the subscription name to findAll available perks and populate the table */

        var perks = [];

        //Initialize subscription service
        var subService = new Packages.com.SubscriptionService();

        //Check if subscription name exists
        if(subService.existsByName(input)){
            //Find all associated perks
            perks = subService.getPerks();
            for(var i=0;i < perks.length;i++)
            {
                var perkJson = {"code":perks[i].getCode(),"description":perks[i].getDescription(),"expiryDate":perks[i].getDescription()};
                $.ajax({
                    type:"POST",
                    async:false,
                    contentType: "application/json; charset=utf-8",
                    url: cntxPath+"/Search",
                    dataType:"json",
                    data: JSON.stringify(perkJson)
                });
                //Update table
                GetTable();
            }
        }



    }

    $("#Search").click(function(){
        subscription = $("#sub_name").text();
        Search(subscription);
    });

});
