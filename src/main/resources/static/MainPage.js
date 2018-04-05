$(document).ready(function() {


    /* Use JQuery to get the page elements.  */
        var subs = $("#sub_perk").get(0);//This should be changed to an ajax call to the db.
        var cntxPath = window.location.protocol+ "//" + window.location.host;
        var userName = $("#userNameLabel").text().replace("username: ","");
        var searchName = $( "#search_perk" );

        var dialog_sub,dialog_perk, form,

            name_sub = $( "#name_sub" ),
            code_perk = $( "#code_perk" ),
            sub_perk = $( "#sub_perk" ),
            desc_perk = $( "#desc_perk" ),
            exp_perk = $( "#exp_perk" );

        $.ajax({
            url: cntxPath+"/GetSubs?userName="+userName
        }).then(function(data) {
            for(var i=0;i < data.length;i++)
            {
                var option = document.createElement("option");
                option.text = data[i];
                subs.add(option);
                $('#ActiveSubscriptions').append('<li class=\"list-group-item d-flex justify-content-between align-items-center\"><span id="subscription_name">'+data[i]+'</span> <span class=\"btn btn-danger delete-btn\">Delete</span></li>');
                AddDeleteSubscriptionListener();
            }
        });

        //Add event listener for the search button
        document.getElementById("search").addEventListener("click", searchPerkListener);
        $('#formA input[type=radio]').click(function(){
           GetTable();
        });

    /* Make sure they aren't pushing at least 3 chars. */
        function checkLength( o, min ) {
            if ( o.val().length < min ) {
                showWarnings();
                return false;
            } else {
                return true;
            }
        }

        /* Get the subscription table in the mainpage. */
        function GetTable() {
            if(!$("#ViewAllSubscriptions")[0].checked) {
                $("#InfoTable").find("tr:gt(0)").remove();
                $.ajax({
                    async: false,
                    url: cntxPath + "/GetTable?userName=" + userName,
                }).then(function (data) {
                    $('#InfoTable').append(""+data);
                });
            }else
            {
                $("#InfoTable").find("tr:gt(0)").remove();
                $.ajax({
                    async: false,
                    url: cntxPath + "/GetCompleteTable",
                }).then(function (data) {
                    $('#InfoTable').append(""+data);
                });
            }
            addUpvoteListener();
            addDownvoteListener();
            if (document.getElementById('r1').checked){
                sortTableUpvotes();
            }else if (document.getElementById('r2').checked){
                sortTableDownvotes();
            }else if(document.getElementById('r3').checked){
                sortTableDates();
            }
        }

        /* Add a subscription to the signed in user. */
        function addSubscription() {
            var valid = true;

            valid = valid && checkLength( name_sub,  3 );

            if ( valid ) {
                var option = document.createElement("option");
                option.text = name_sub.val();
                subs.add(option);

                /* Construct JSON and send. */
                var subscriptionJson = {"name":name_sub.val(),"perks":null,"fee":0};
                $.ajax({
                    type:"POST",
                    async:false,
                    contentType: "application/json; charset=utf-8",
                    url: cntxPath+"/AddSubscription?userName="+userName,
                    dataType:"json",
                    data: JSON.stringify(subscriptionJson)
                });
                /* Update table. */
                GetTable();
                if($('#ActiveSubscriptions span:contains('+name_sub.val()+')').length == 0)
                {
                    $('#ActiveSubscriptions').append('<li class=\"list-group-item d-flex justify-content-between align-items-center\"><span id="subscription_name">'+name_sub.val()+'</span> <span class=\"btn btn-danger delete-btn\">Delete</span></li>');
                    AddDeleteSubscriptionListener();
                }
                dialog_sub.dialog( "close" );
            }
            return valid;
        }

        /* Add perk for the subscription. */
    function addPerk() {
        var valid = true;
        valid = valid && checkLength( code_perk, 3 );
        valid = valid && checkLength( sub_perk, 3 );
        valid = valid && checkLength( desc_perk, 3 );
        valid = valid && checkLength( exp_perk, 10 );

        if ( valid ) {
            /* Construct JSON and send. This call is not async because the calls to GetTable finishes before this one.  */
            var perkJson = {"code":code_perk.val(),"description":desc_perk.val(),"expiryDate":exp_perk.val()};
            $.ajax({
                type:"POST",
                async:false,
                contentType: "application/json; charset=utf-8",
                url: cntxPath+"/AddPerk?userName="+userName+"&subName="+sub_perk.val(),
                dataType:"json",
                data: JSON.stringify(perkJson)
            });
            /* Update table. */
            GetTable();
            dialog_perk.dialog( "close" );
        }
        return valid;
    }

    function addUpvoteListener(){
        if ($('.upvotebutton').length == 0){
            return;
        }
        $('.upvotebutton').click(function(event){

            var subname = $(event.target).parent().parent().find("#subscription_name").text();
            var perkname = $(event.target).parent().parent().find("#perk_name").text();

            /* Construct JSON and send. This call is not async because the calls to GetTable finishes before this one.  */
            var perkJson = {"code":perkname,"description":desc_perk.val(),"expiryDate":null,"subscription":null};
            $.ajax({
                type:"POST",
                async:false,
                contentType: "application/json; charset=utf-8",
                url: cntxPath+"/upvote?userName="+userName+"&subName="+subname,
                dataType:"json",
                data: JSON.stringify(perkJson)
            });
            /* Update table. */
            GetTable();
            });
    }

    function addDownvoteListener(){
        if ($('.downvotebutton').length == 0){
            return;
        }
        $('.downvotebutton').click(function(event){

            var subname = $(event.target).parent().parent().find("#subscription_name").text();
            var perkname = $(event.target).parent().parent().find("#perk_name").text();

            /* Construct JSON and send. This call is not async because the calls to GetTable finishes before this one.  */
            var perkJson = {"code":perkname,"description":desc_perk.val(),"expiryDate":null,"subscription":null};
            $.ajax({
                type:"POST",
                async:false,
                contentType: "application/json; charset=utf-8",
                url: cntxPath+"/downvote?userName="+userName+"&subName="+subname,
                dataType:"json",
                data: JSON.stringify(perkJson)
            });
            /* Update table. */
            GetTable();
        });
    }

    function AddDeleteSubscriptionListener(){
        if ($('.delete-btn').length == 0){
            return;
        }
        $('.delete-btn').click(function(){

            var subname = $(this).parent().find("#subscription_name").text();
            /* Construct JSON and send. This call is not async because the calls to GetTable finishes before this one.  */
            $.ajax({
                type:"DELETE",
                async:false,
                contentType: "application/json; charset=utf-8",
                url: cntxPath+"/DeleteSub?userName="+userName+"&subName="+subname,
                dataType:"json"
            });
            $(this).parent().remove();
            GetTable();
        });
    }

    function searchPerkListener(){
        $('#InfoTable').empty();
        $.ajax({
            type:"GET",
            async:false,
            url: cntxPath+"/SearchPerk?userName="+userName+"&searchName="+searchName.val(),
        }).then(function(data) {
             $('#InfoTable').append("<tr><th>Subscription</th><th>Perk Code</th><th>Perk Description</th><th>Expiry Date</th><th></th><th></th><th></th></tr>"+data);
        });
        addUpvoteListener();
        addDownvoteListener();
    }

    function sortTableDates() {
        var table, rows, switching, i, x, y, shouldSwitch;
        table = document.getElementById("InfoTable");
        switching = true;
        /*Make a loop that will continue until
        no switching has been done:*/
        while (switching) {
            //start by saying: no switching is done:
            switching = false;
            rows = table.getElementsByTagName("TR");
            /*Loop through all table rows (except the
            first, which contains table headers):*/
            for (i = 1; i < (rows.length - 1); i++) {
                //start by saying there should be no switching:
                shouldSwitch = false;
                /*Get the two elements you want to compare,
                one from current row and one from the next:*/
                x = rows[i].getElementsByTagName("TD")[3];
                y = rows[i + 1].getElementsByTagName("TD")[3];
                var d1 = Date.parse(x.innerHTML);
                var d2 = Date.parse(y.innerHTML);
                //check if the two rows should switch place:
                if (d1 > d2) {
                    //if so, mark as a switch and break the loop:
                    shouldSwitch= true;
                    break;
                }
            }
            if (shouldSwitch) {
                /*If a switch has been marked, make the switch
                and mark that a switch has been done:*/
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }
    }

    function sortTableUpvotes() {
        var table, rows, switching, i, x, y, shouldSwitch;
        table = document.getElementById("InfoTable");
        switching = true;
        /*Make a loop that will continue until
        no switching has been done:*/
        while (switching) {
            //start by saying: no switching is done:
            switching = false;
            rows = table.getElementsByTagName("TR");
            /*Loop through all table rows (except the
            first, which contains table headers):*/
            for (i = 1; i < (rows.length - 1); i++) {
                //start by saying there should be no switching:
                shouldSwitch = false;
                /*Get the two elements you want to compare,
                one from current row and one from the next:*/
                x = rows[i].getElementsByTagName("TD")[6];
                y = rows[i + 1].getElementsByTagName("TD")[6];
                //check if the two rows should switch place:
                if (parseFloat(x.innerHTML) < parseFloat(y.innerHTML)) {
                    //if so, mark as a switch and break the loop:
                    shouldSwitch= true;
                    break;
                }
            }
            if (shouldSwitch) {
                /*If a switch has been marked, make the switch
                and mark that a switch has been done:*/
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }
    }

    function sortTableDownvotes() {
        var table, rows, switching, i, x, y, shouldSwitch;
        table = document.getElementById("InfoTable");
        switching = true;
        /*Make a loop that will continue until
        no switching has been done:*/
        while (switching) {
            //start by saying: no switching is done:
            switching = false;
            rows = table.getElementsByTagName("TR");
            /*Loop through all table rows (except the
            first, which contains table headers):*/
            for (i = 1; i < (rows.length - 1); i++) {
                //start by saying there should be no switching:
                shouldSwitch = false;
                /*Get the two elements you want to compare,
                one from current row and one from the next:*/
                x = rows[i].getElementsByTagName("TD")[6];
                y = rows[i + 1].getElementsByTagName("TD")[6];
                //check if the two rows should switch place:
                if (parseFloat(x.innerHTML) > parseFloat(y.innerHTML)) {
                    //if so, mark as a switch and break the loop:
                    shouldSwitch= true;
                    break;
                }
            }
            if (shouldSwitch) {
                /*If a switch has been marked, make the switch
                and mark that a switch has been done:*/
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }
    }

    /* Set up subscription popup. */
    dialog_sub = $( "#subscription-form" ).dialog({
            autoOpen: false,
            height: 400,
            width: 350,
            modal: true,
            buttons: {
                "Add  Subscription": addSubscription,
                Cancel: function() {
                    dialog_sub.dialog( "close" );
                }
            },
            close: function() {
                form[ 0 ].reset();
            }
        });
        /* On form submit make ajax call. */
        form = dialog_sub.find( "form" ).submit( function(){
            event.preventDefault();
            addSubscription();
        });
        /* open popup. */
        $( "#AddSubscription" ).click( function() {
            hideWarnings();
            dialog_sub.dialog( "open" );
        });

    /* Set up perk popup. */
    dialog_perk = $( "#perk-form" ).dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        buttons: {
            "Add Perk": addPerk,
            Cancel: function() {
                dialog_perk.dialog( "close" );
            }
        },
        close: function() {
            form[ 0 ].reset();
        }
    });
    /* On form submit make ajax call. */
    form = dialog_perk.find( "form" ).submit( function(){
        event.preventDefault();
        addSubscription();
    });
    /* open popup. */
    $( "#AddPerk" ).click( function() {
        hideWarnings();
        dialog_perk.dialog( "open" );
    });

    var warningsVisible = true;
    function showWarnings()
    {
        if(!warningsVisible)
        {
            $(".alert").each(function() {
                $(this).toggle();
            });
            warningsVisible = true;
        }
    }

    function hideWarnings()
    {
        if(warningsVisible)
        {
            $(".alert").each(function() {
                $(this).toggle();
            });
            warningsVisible = false;
        }
    }

    $("#ViewAllSubscriptions").change(function(){
        GetTable();
    });

    /* Populate table initially. */
    GetTable();

    }
);
