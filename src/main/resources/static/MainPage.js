$(document).ready(function() {

        var subs = $("#sub_perk").get(0);
        var userName = $("#userNameLabel").text().replace("username: ","");
        var dialog_sub,dialog_perk, form,

            name_sub = $( "#name_sub" ),
            name_perk = $( "#name_perk" ),
            sub_perk = $( "#sub_perk" ),
            desc_perk = $( "#desc_perk" )


        function checkLength( o, min ) {
            if ( o.val().length < min ) {
                return false;
            } else {
                return true;
            }
        }


        function GetTable() {
                $('#InfoTable').empty();
                $.ajax({
                    url: "http://localhost:8081/GetTable?userName="+userName
                }).then(function(data) {
                    $('#InfoTable').append("<tr><th>Subscription</th><th>Perk</th></tr>"+data);
                });
        }

        function addSubscription() {
            var valid = true;

            valid = valid && checkLength( name_sub,  3 );

            if ( valid ) {
                var option = document.createElement("option");
                option.text = name_sub.val();
                subs.add(option);

                $.ajax({
                    url: "http://localhost:8081/AddSubscription?userName="+userName+"&subName="+name_sub.val()
                });

                GetTable();
                dialog_sub.dialog( "close" );
            }
            return valid;
        }


    function addPerk() {
        var valid = true;

        valid = valid && checkLength( name_perk, 3 );
        valid = valid && checkLength( sub_perk, 3 );
        valid = valid && checkLength( desc_perk, 3 );

        if ( valid ) {
            /* Send ajax and get new data back */
            $.ajax({
                async: false,url: "http://localhost:8081/AddPerk?userName="+userName+"&perkName="+name_perk.val()+"&subName="+name_sub.val()+"&desc="+desc_perk.val()
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