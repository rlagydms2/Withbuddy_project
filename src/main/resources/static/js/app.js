let stompClient = null;
$(document).ready(function () {
    $("#userTable").on("click", ".btn", function () { // 유저 테이블에서 특정 유저를 선택하면 유저 프로필이 나옴
        var userId = $(this).closest('tr').data("modal-tr"); //선택한 유저의 프로필을 찾기위한 id
        console.log("UserID:", userId);
        $.ajax({
            url: "/api/userProfile/" + userId, // 선택한 유저의 아이디를 보내 그 유저의 정보를 찾기
            type: 'GET',
            success: (function (data) {
                // console.log("userProfile data:", data);
                if (data != undefined) {
                    console.log("프로필버튼 누름" + data);
                    showProfile(data); //찾아서 모달에 그 유저의 정보를 보여줌
                }
            }),
        })
    });
    $("#profileModal").on("click", ".modal-btn", function () {
        const receiverId = $(this).data("mc-btn"); // 프로필 유저의 id
        // console.log(receiverId);

        $(".modal-btn").hide();        // 매칭하기를 누르면 버튼이 사라짐
        const data = {
            "senderId": login_id,
            "receiverId": receiverId,
        };  // 컨트롤러에 보낼 data
        $.ajax({
            url: "/api/match",
            type: "POST",
            data: data,
            cache: false,
            success: function (data) {
                console.log(data);
            },  // 수락을 눌렀으니 accept를 true로
        });
        $("#profileModal").modal("hide"); // 유저 프로필에서 나감

    });
    $("#alarmBtn").click(function () {
        const userId = login_id; // 로그인한 유저의 id
        $.ajax({
            url: "/api/alert/" + login_id,  //로그인한 유저의 id로 매칭알림을 가져옴
            type: "GET",
            cache: false,
            success: function (data) {
                console.log(data);
                showAlarm(data); // 매칭 알림을 로그인한 유저에게 렌더링
            },
        })
        console.log("알람버튼클릭");
        $("#alarmModal").modal("show");
    });
    $("#alarmModal").on("click", ".accept-btn", function () { //매칭을 수락하는 버튼
        const senderId = login_id;  // 매칭을 요청한 사람의 id
        const receiverId = $(this).data("alac-sender"); // 매칭을 요청받은 사람의 id
        console.log(receiverId);
        const data = {
            "senderId": senderId,
            "receiverId": receiverId,
        }  // 매칭을 수락하기 위해 보내는 data
        $.ajax({
            url: "/api/matchUpdate",  // data를 보내서 일치하는 match의 accept를 true로 업데이트
            type: "put",
            data: data,
            success: function (data) {

            },
        })
        $.ajax({
            url: "/chatroom/room", // match가 accept인 유저들의 채팅방을 만듬
            type: "POST",
            data: data,
            success: function (data) {
                console.log(data);
            },
        });
        $("#alarmModal").modal("hide");
        $("#alarmTable").html("");

    });
    $("#alarmModal").on("click", ".reject-btn", function () { // 거절 버튼
        const senderId = login_id;
        const receiverId = $(this).data("alrj-sender");
        console.log(receiverId);
        const data = {
            "senderId": senderId,
            "receiverId": receiverId,
        }
        $.ajax({
            url: "/api/matchDelete",  // 매칭요청을 거절하면 매칭을 삭제
            type: "delete",
            data: data,
            success: function (data) {

            },
        })
        $("#alarmModal").modal("hide");
        $("#alarmTable").html("");
    });

    $("#dmListBtn").click(function () { // 매칭이 연결된 채팅방을 가진 유저들을 모아놓은 리스트를 보여지게하기 위한 버튼
        const data = {
            "loginId": login_id,
        }
        console.log("loginId dmList : " + login_id);
        $.ajax({
            url: "/api/dmList", // 누르면 채팅방을 가진 유저들을 보여줌
            type: "post",
            data: data,
            success: function (data) {
                console.log(data);
                showDmList(data); // dmList에 있는 유저들을 화면에 렌더링
            },
        })
        $("#dmModal").modal("show");
    });
    $("#dmModal").on("click", ".dmChat", function () { // 채팅방을 들어가기 위한 버튼
        const userId = $(this).data("dm-btn");

        console.log("dm-btn:" + userId);
        $("#dmModal").modal("hide");
        $("#chatModal").modal("show");
        $("#chatSendBtn").attr("data-send-bt", userId); //data-send-bt 특성을 가진 버튼에 userId를 전달
        const data = {
            "userId": userId,
            "loginId": login_id,
        };
        $.ajax({
            url: "/chatroom/find", // userId와 나와의 채팅방을 연결
            type: "POST",
            data: data,
            success: function (data) {
                console.log(data);
                connect(data); // stomp로 구독시킴
            },
        });
        $.ajax({
            url: "/api/chatList", // 채팅방의 채팅을 찾아옴
            type: "POST",
            data: data,
            success: function (data) {
                console.log(data);
                loadMessage(data); // 전에 했던 채팅을 보여줌
            },
        });
    });

    $("#chatClose").click(function () { // 채팅방을 끄면 연결이 끊어지게
        disconnect();
        $("#dmModal").modal('show'); // dmlist를 다시 보여줌
    });
    $("#chatSendBtn").click(function () {
        const userId = $("#chatSendBtn").attr("data-send-bt"); //data-send-bt
        // 전달받은 data를 꺼내서 사용
        const data = {
            "userId": userId,
            "loginId": login_id
        }
        console.log("receiver userId: " + userId);

        $.ajax({
            url: "/chatroom/find",  // userId와 loginId를 가지고 찾은 채팅방에 메시지를 전달
            type: "POST",
            data: data,
            success: function (data) {
                console.log(data);
                sendChat(data); // 채팅방 메시지를 db에 전달해서 나와 상대방에게 보여줌
            },
        });
    });
    $("#chatModal").on('shown.bs.modal', function () {
        scroll();  // 채팅방에 들어가면 스크롤이 내려가게
    });
});

function showList(list) {
    const out = [];
    list.forEach(user => {
        let id = user.id;
        console.log("data-modal-tr:", id);

        let username = user.userId;
        const buddyName = user.buddyName;

        const row =
            `
            <tr data-modal-tr="${id}">
                <td><img src="/image/dog1.jpg" style="width: 40px; height: 40px;"></td>
                <td>
                    <button type="button" class="btn">${username}</button>
                </td>
                <td>
                    <span>${buddyName}</span>
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
    console.log(username);
    const category = user.category;
    console.log(category);
    const buddyImage = user.buddyImage;
    const buddyName = user.buddyName;
    const buddyAge = user.buddyAge;
    const buddyDetail = user.buddyDetail;
    let buddySex = user.buddySex;
    if (buddySex == 1) {
        buddySex = '수';
    } else {
        buddySex = '암';
    }
    const row =
        `
            <div class="modal-header">
                <h1 class="modal-title">${username} 님의 프로필</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <img src="/image/dog1.jpg" style="justify-content: center;width: 200px;height: 200px;">
                <p>이름 : ${buddyName}</p>
                <p>견종 : ${category}</p>
                <p>나이 : ${buddyAge}</p>
                <p>성별 : ${buddySex}</p>   
                <p>상세설명 : ${buddyDetail}</p>
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
        const buddyImage=list.buddyImage;
        const username = list.userId;
        console.log("userId: " +userId);
        if (userId != login_id) {

            const row =
                `
            <tr>
                <td>
                    <div class="d-inline-flex">
                    <div class="col-4">
                        <img src="/image/${buddyImage}">
                    </div>
                    <div class="col-8 d-flex">
                        <button type="button" data-dm-btn="${userId}" class="btn btn-white dmChat">${username}</button>
                    </div>
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
    stompClient.connect({}, function (frame) { //stomp를 연결
        console.log("연결됨: ", frame);
        stompClient.subscribe("/topic/chat/" + roomId, function (response) { // url에 구독하겠다는 여부를 전달하여 구독
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
    stompClient.send("/home/chat/" + roomId, {}, JSON.stringify(messageData)); // 구독한 방의 모든이에게 메시지를 broadCast
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
        $("#chatBox").html("");
        $("#chatBox").html(out.join('\n'));
    });
}

function scroll() {
    var chatBox = document.getElementById('chatBox');
    chatBox.scrollTop = chatBox.scrollHeight;
}