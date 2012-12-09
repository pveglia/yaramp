// $("#cmd_play").click(function () {
//     console.log("Play button clicked");
//     $.ajax({
//         url : "/play",
//         type: "POST",
//         success: function (data) {
//             console.log("Successfully called play");
//         },
//         fail : function() {
//             console.log("Command play failed")
//         }
//     });
// });

btns = [{selector: "#cmd_play", url: "/play", desc: "play"},
        {selector: "#cmd_pause", url: "/pause", desc: "pause"},
        {selector: "#cmd_stop", url: "/stop", desc: "stop"},
        {selector: "#cmd_voldown", url: "/voldown", desc: "voldown",
         retfn: function(data) {return "Volume: " + data.volume}},
        {selector: "#cmd_mute", url: "/mute", desc: "mute",
         retfn: function(data) {return "Volume: " + data.volume}},
        {selector: "#cmd_volup", url: "/volup", desc: "volup",
         retfn: function(data) {return "Volume: " + data.volume}}]

var futureEvnt;

btns.forEach(function(val, idx, array){
    $(val.selector).click( function () {
        console.log(val.desc + ' button clicked');
        $.ajax({
            url : val.url,
            type: "POST",
            success: function (data) {
                console.log("Successfully called " + val.desc);
                console.log(data);
                msg = val.retfn ? val.retfn(data) : "Success";
                clearTimeout(futureEvnt);
                $("#msg_ok").text(msg).show();
                futureEvnt = setTimeout(function(){
                    $("#msg_ok").fadeOut(500);
                }, 1000);
            },
            error : function() {
                console.log("Command " + val.desc + " failed");
                $("#msg_ko").text("Failed!").show();
                futureEvnt = setTimeout(function(){
                    $("#msg_ko").fadeOut(500);
                }, 1000);

            }
        });
    });
});
