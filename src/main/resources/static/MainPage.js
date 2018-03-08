$(document).ready(function() {

        var subs = $("#sub_perk").get(0);
        var ctxPath;
        var userName = $("#userNameLabel").text().replace("username: ","");
        var dialog_sub,dialog_perk, form,
            name_sub = $( "#name_sub" ),
            name_perk = $( "#name_perk" ),
            sub_perk = $( "#sub_perk" ),
            desc_perk = $( "#desc_perk" )

        if(location.hostname === "localhost" || location.hostname === "127.0.0.1")
        {
            cntxPath = "http://localhost:8081";
        }else
        {
            cntxPath = "https://perkmanager.herokuapp.com";
        }

        function checkLength( o, min, max ) {
            if ( o.val().length > max || o.val().length < min ) {
                return false;
            } else {
                return true;
            }
        }


        function GetTable() {
                $('#InfoTable').empty();
                $.ajax({
                    url: cntxPath+"/GetTable?userName="+userName
                }).then(function(data) {
                    $('#InfoTable').append("<tr><th>Subscription</th><th>Perk</th></tr>"+data);
                });
        }

        function addSubscription() {
            var valid = true;

            valid = valid && checkLength( name_sub,  3, 16 );

            if ( valid ) {
                var option = document.createElement("option");
                option.text = name_sub.val();
                subs.add(option);

                var subscriptionJson = {"name":name_sub.val(),"perks":null,"fee":0};
                $.ajax({
                    type:"POST",
                    contentType: "application/json; charset=utf-8",
                    url: cntxPath+"/AddSubscription?userName="+userName,
                    dataType:"json",
                    data: JSON.stringify(subscriptionJson)
                });

                GetTable();
                dialog_sub.dialog( "close" );
            }
            return valid;
        }


    function addPerk() {
        var valid = true;

        valid = valid && checkLength( name_perk, 3, 16 );
        valid = valid && checkLength( sub_perk, 3, 16 );
        valid = valid && checkLength( desc_perk, 3, 16 );

        if ( valid ) {
            var perkJson = {"name":name_perk.val(),"description":desc_perk.val(),"expiryDate":null,"subscription":null};
            $.ajax({
                type:"POST",
                async:false,
                contentType: "application/json; charset=utf-8",
                url: cntxPath+"/AddPerk?userName="+userName+"&subName="+name_sub.val(),
                dataType:"json",
                data: JSON.stringify(perkJson )
            });
            GetTable();
            dialog_perk.dialog( "close" );
        }
        return valid;
    }


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

        form = dialog_sub.find( "form" ).submit( function(){
            event.preventDefault();
            addSubscription();
        });

        $( "#AddSubscription" ).click( function() {
            dialog_sub.dialog( "open" );
        });

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

    form = dialog_perk.find( "form" ).submit( function(){
        event.preventDefault();
        addSubscription();
    });

    $( "#AddPerk" ).click( function() {
        dialog_perk.dialog( "open" );
    });

    GetTable();

    }
);