$(document).ready(function() {
    /*
        $('#submit').click(function(){
            $.ajax({
                url: "http://localhost:8081/AddSubscription?name="+$("#Name_input").text
            }).then(function(data) {
                $('#result tr:last').after('<tr><td>'+data.name+'</td><td>'+data.perk+'</td>');
            });
        });
     */
        var subs = $("#sub_perk").get(0);
        var dialog_sub,dialog_perk, form,

            // From http://www.whatwg.org/specs/web-apps/current-work/multipage/states-of-the-type-attribute.html#e-mail-state-%28type=email%29
            name_sub = $( "#name_sub" ),
            name_perk = $( "#name_perk" ),
            sub_perk = $( "#sub_perk" ),
            desc_perk = $( "#desc_perk" )


        function checkLength( o, min, max ) {
            if ( o.val().length > max || o.val().length < min ) {
                return false;
            } else {
                return true;
            }
        }


        function GetTable() {
            return 0;
        }

        function addSubscription() {
            var valid = true;

            valid = valid && checkLength( name_sub,  3, 16 );

            if ( valid ) {
                var option = document.createElement("option");
                option.text = name_sub.val();
                subs.add(option);
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
            /* Send ajax and get new data back */
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
    }
);