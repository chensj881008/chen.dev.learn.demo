var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/logger');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/logger', function (event) {
            console.log(event);
            showGreeting(JSON.parse(event.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/app/logger", {}, JSON.stringify({}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

<!-- 日志实时推送业务处理 -->
function openSocket() {
    if (stompClient != null) {
        stompClient = null;
    }
    console.log('日志实时推送业务处理');
    if (stompClient == null) {
        if ($("#log-container").find("span").length == 0) {
            $("#log-container div").after("<span>通道连接成功,静默等待.....</span><img src='/layer/theme/default/loading-0.gif'>");
        }
        console.log($("#log-container").find("span").length);
        console.log('/logger');
        var socket = new SockJS('/logger');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/logger', function (event) {
                console.log(event);
                showGreeting(JSON.parse(event.body).content);
                // var content = JSON.parse(event.body);
                // var leverhtml = '';
                // var className = '<span class="classnametext">' + content.className + '</span>';
                // switch (content.level) {
                //     case 'INFO':
                //         leverhtml = '<span class="infotext">' + content.level + '</span>';
                //         break;
                //     case 'DEBUG':
                //         leverhtml = '<span class="debugtext">' + content.level + '</span>';
                //         break;
                //     case 'WARN':
                //         leverhtml = '<span class="warntext">' + content.level + '</span>';
                //         break;
                //     case 'ERROR':
                //         leverhtml = '<span class="errortext">' + content.level + '</span>';
                //         break;
                // }
                // $("#log-container div").append("<p class='logp'>" + content.timestamp + " " + leverhtml + " --- [" + content.threadName + "] " + className + " ：" + content.body + "</p>");
                // if (content.exception != "") {
                //     $("#log-container div").append("<p class='logp'>" + content.exception + "</p>");
                // }
                // if (content.cause != "") {
                //     $("#log-container div").append("<p class='logp'>" + content.cause + "</p>");
                // }
                // $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
            });
        });
    }
}

function closeSocket() {
    if (stompClient != null) {
        stompClient.disconnect();
        stompClient = null;
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
    // $("#log").click(function () {
    //     openSocket()
    //     //iframe层
    //     layer.open({
    //         type: 1,
    //         title: '<span class="laytit">接口实时日志</span>',
    //         shadeClose: false,
    //         shade: 0.7,
    //         maxmin: true,
    //         area: ['80%', '70%'],
    //         content: $("#logdiv").html(), //iframe的url
    //         cancel: function(index){
    //             closeSocket();
    //         }
    //     });
    // });
    // $('#logConnect').click(function () {
    //     stompClient.send("/app/logger", {}, {});
    // })


});

