let stompClient = null;
$(document).ready(function () {
    $("#userListBtn").click(function () {
        $.ajax({
            url: "/api/user?id=" + login_id,
            type: "GET",
            success: function (data) {
                // console.log("userList data:", data);
                // console.log("sender id: ", login_id);
                if (data !== undefined) {
                    showList(data);
                }
            },
        })
    });

    $("#userTable").on("click", ".btn", function () {
        var userId = $(this).closest('tr').data("modal-tr");
        console.log("UserID:", userId);
        $.ajax({
            url: "/api/userProfile/" + userId,
            type: 'GET',
            success: (function (data) {
                // console.log("userProfile data:", data);
                if (data != undefined) {
                    showProfile(data);
                }
            }),
        })
    });
    $("#profileModal").on("click", ".modal-btn", function () {
        const receiverId = $(this).data("mc-btn");
        // console.log(receiverId);

        $(".modal-btn").hide();
        const data = {
            "senderId": login_id,
            "receiverId": receiverId,
        };
        $.ajax({
            url: "/api/match",
            type: "POST",
            data: data,
            cache: false,
            success: function (data) {
                console.log(data);
            },
        });
        $("#profileModal").modal("hide");

    });
    $("#alarmBtn").click(function () {
        const userId = login_id;
        $.ajax({
            url: "/api/alert/" + login_id,
            type: "GET",
            cache: false,
            success: function (data) {
                console.log(data);
                showAlarm(data);
            },
        })
    });
    $("#alarmModal").on("click", ".accept-btn", function () {
        const senderId = login_id;
        const receiverId = $(this).data("alac-sender");
        console.log(receiverId);
        const data = {
            "senderId": senderId,
            "receiverId": receiverId,
        }
        $.ajax({
            url: "/api/matchUpdate",
            type: "put",
            data: data,
            success: function (data) {

            },
        })
        $.ajax({
            url: "/chatroom/room",
            type: "POST",
            data: data,
            success: function (data) {
                console.log(data);
            },
        });
        $("#alarmModal").modal("hide");
        $("#alarmTable").html(" ");

    });
    $("#alarmModal").on("click", ".reject-btn", function () {
        const senderId = login_id;
        const receiverId = $(this).data("alrj-sender");
        console.log(receiverId);
        const data = {
            "senderId": senderId,
            "receiverId": receiverId,
        }
        $.ajax({
            url: "/api/matchDelete",
            type: "delete",
            data: data,
            success: function (data) {

            },
        })
    });

    $("#dmListBtn").click(function () {
        const data = {
            "loginId": login_id,
        }
        $.ajax({
            url: "/api/dmList",
            type: "post",
            data: data,
            success: function (data) {
                console.log(data);
                showDmList(data);
            },
        })
    });
    $("#dmModal").on("click", ".dmChat", function () {
        const userId = $(this).data("dm-btn");

        console.log("dm-btn:" + userId);
        $("#dmModal").modal("hide");
        $("#chatModal").modal("show");
        $("#chatSendBtn").attr("data-send-bt", userId);
        const data = {
            "userId": userId,
            "loginId": login_id,
        };
        $.ajax({
            url: "/chatroom/find",
            type: "POST",
            data: data,
            success: function (data) {
                console.log(data);
                connect(data);
            },
        });
        $.ajax({
            url: "/api/chatList",
            type: "POST",
            data: data,
            success: function (data) {
                console.log(data);
                loadMessage(data);
            },
        });
    });

    $("#chatClose").click(function () {
        disconnect();
        $("#dmModal").modal('show');
    });
    $("#chatSendBtn").click(function () {
        const userId = $("#chatSendBtn").attr("data-send-bt"); //data-send-bt
        console.log("receiver userId: " + userId);
        const data = {
            "userId": userId,
            "loginId": login_id
        }
        $.ajax({
            url: "/chatroom/find",
            type: "POST",
            data: data,
            success: function (data) {
                console.log(data);
                sendChat(data);
            },
        });
    });
    $("#chatModal").on('shown.bs.modal', function () {
        scroll();
    });
});

function showList(list) {
    const out = [];
    list.forEach(user => {
        let id = user.id;
        console.log("data-modal-tr:", id);

        let username = user.userId;

        const row =
            `
            <tr data-modal-tr="${id}">
                <td>img</td>
                <td>
                    <button type="button" class="btn">${username}</button>
                </td>
                <td>
                <div class="row">
                    <div class="col-12">asdf</div>
                    <div class="col-12">asdf</div>
                    <div class="col-12">asdf</div>
                </div>
                </td>
            </tr>
            `;
        out.push(row);
    });
    $("#userTable").html(out.join("\n"));
}

function showProfile(user) {

    let id = user.id;
    let username = user.userId;

    const row =
        `
            <div class="modal-header">
                <h1 class="modal-title">${username} 님의 프로필</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>땡땡</p>
            </div>
            <div class="modal-footer">
                    <button type="button" class="btn btn-outline-dark modal-btn" data-mc-btn="${id}" role="button">매칭하기</button>
            </div>
        `;

    $("#profileModalContent").html(row);
    $("#profileModal").modal("show");
}

function showAlarm(list) {

    const out = [];
    list.forEach(list => {
        const senderId = list.senderId;
        const username = list.userId;
        const receiverId = list.receiverId;
        const accept = list.accept;
        console.log(accept);
        console.log(senderId);
        console.log(username);
        if (accept == 0 && senderId != login_id) {
            const row =
                `
          <tr>
            <td>
                <div>
                    <p>${username} 님의 매칭 요청입니다.</p>
                    <button class="btn btn-outline-dark accept-btn" data-alac-sender="${senderId}" data-alac-receiver="${receiverId}">수락</button>
                    <button class="btn btn-outline-dark reject-btn" data-alrj-sender="${senderId}" data-alrj-receiver="${receiverId}">거절</button>
                </div>
            </td>
          </tr>
        `;
            out.push(row);
            $("#alarmTable").html(out.join('/n'));
        }
    });
    $("#alarmModal").modal("show");
}

function showDmList(list) {
    const out = [];
    list.forEach(list => {
        const userId = list.id;
        const username = list.userId;
        console.log(userId);
        if (userId != login_id) {

            const row =
                `
            <tr>
                <td>
                    <div>
                        <button type="button" data-dm-btn="${userId}" class="btn btn-white dmChat">${username}</button>
                    </div>
                </td>
            </tr>
            `;
            out.push(row);
        }
    });
    $("#dmTable").html(out.join('/n'));
    $("#dmModal").modal("show");
}

function connect(data) {
    const socket = SockJS("/ws");
    stompClient = Stomp.over(socket);
    const roomId = data.roomId;
    console.log(roomId);
    stompClient.connect({}, function (frame) {
        console.log("연결됨: ", frame);
        stompClient.subscribe("/topic/chat/" + roomId, function (response) {
            console.log(JSON.parse(response.body));
            showMessage(JSON.parse(response.body));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
}

function sendChat(data) {
    const chatValue = document.getElementById('chatMessage');
    const message = chatValue.value;
    const roomId = data.roomId;
    const messageData = {
        "roomId": roomId,
        "message": message,
        "senderId": login_id
    }
    stompClient.send("/home/chat/" + roomId, {}, JSON.stringify(messageData));
    chatValue.value = '';
}

function showMessage(data) {
    const chatMessage = $("#chatBox");
    const message1 = data.message;
    console.log("message:" + message1);

    let sendTime = data.sendTime;
    const senderId = data.senderId
    sendTime = sendTime.slice(11, 16);
    if (senderId == login_id) {
        const myRow =
            `
            <div class="d-flex justify-content-end mb-4">
                <div class="msg_container_send" style="max-width: 300px;">
                    ${message1}
                <span class="msg_time_send">${sendTime}</span>
                </div>
            </div>
        `;
        chatMessage.append(myRow);
        chatMessage.append("");
        scroll();
    } else {
        const row =
            `
            <div class="d-flex justify-content-start mb-4">
                <div class="msg_container" style="max-width: 300px;">
                    ${message1}
                <span class="msg_time">${sendTime}</span>
                </div>
            </div>
        `;
        chatMessage.append(row);
        chatMessage.append("");
        scroll();
    }
}

function loadMessage(data) {
    const out = [];
    data.forEach(list => {
        let senderId = list.senderId;
        const message = list.message;
        let sendTime = list.sendTime;
        const username = list.userId;
        sendTime = sendTime.slice(11, 16);
        if (senderId == login_id) {
            const myRow =
                `
                 <div class="chatMsgSend d-flex justify-content-end mb-4">
                     <div class="msg_container_send" style="max-width: 300px;">
                            ${message}
                        <span class="msg_time_send">${sendTime}</span>
                     </div>
                   </div>
                `;
            out.push(myRow);
        } else {
            const row =
                `
                   <div class="chatMsgRec d-flex justify-content-start mb-4">
                        <div class="msg_container" style="max-width: 300px;">
                            ${message}
                            <span class="msg_time">${sendTime}</span>
                        </div>
                   </div>
                `;
            out.push(row);
        }
        $("#chatBox").html(out.join('\n'));
    });
}

function scroll() {
    var chatBox = document.getElementById('chatBox');
    chatBox.scrollTop = chatBox.scrollHeight;
}