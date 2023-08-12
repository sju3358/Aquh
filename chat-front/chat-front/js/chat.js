// 로그인 대신
let username = prompt("아이디를 입력하세요");
let roomNum = prompt("채팅방 번호를 입력하세요");

// SSE 연결하기
const eventSource = new EventSource(`http://localhost:8080/chat/roomNum/${roomNum}`);
eventSource.onmessage = (event) => {
  const data = JSON.parse(event.data);

  // 로그인 유저가 보낸 메세지
  if (data.sender === username) {
    // 파란 박스(오른쪽)
    initMyMessage(data);
  } else {
    // 회색 박스 (왼쪽)
    initYourMessage(data);
  }
};

// 파란 박스 만들기
function getSendMsgBox(data) {
  let md = data.createdAt.substring(5, 10);
  let tm = data.createdAt.substring(11, 16);
  convertTime = tm + " | " + md;

  return `<div class="sent_msg">
    <p>${data.msg}</p>
    <span class="time_date"> ${convertTime} / <b>${data.sender}</b> </span>
  </div>`;
}

// 회색 박스 만들기
function getReceiveMsgBox(data) {
  let md = data.createdAt.substring(5, 10);
  let tm = data.createdAt.substring(11, 16);
  convertTime = tm + " | " + md;

  return `<div class="received_withd_msg">
    <p>${data.msg}</p>
    <span class="time_date"> ${convertTime} / <b>${data.sender}</b> </span>
  </div>`;
}

// 파란 박스 초기화/동기화
function initMyMessage(data) {
  let chatBox = document.querySelector("#chat-box");

  let sendBox = document.createElement("div");
  sendBox.className = "outgoing_msg";

  sendBox.innerHTML = getSendMsgBox(data);
  chatBox.append(sendBox);

  document.documentElement.scrollTop = document.body.scrollHeight;
}

// 회색 박스 초기화/동기화
function initYourMessage(data) {
  let chatBox = document.querySelector("#chat-box");

  let receivedBox = document.createElement("div");
  receivedBox.className = "received_msg";

  receivedBox.innerHTML = getReceiveMsgBox(data);
  chatBox.append(receivedBox);

  document.documentElement.scrollTop = document.body.scrollHeight;
}

// DB에 새 채팅 보내기 : AJAX 채팅 메시지 전송
async function addMessage() {
  let msgInput = document.querySelector("#chat-outgoing-msg");

  let chat = {
    sender: username,
    roomNum: roomNum,
    msg: msgInput.value,
  };

  fetch("http://localhost:8080/chat", {
    method: "post",
    body: JSON.stringify(chat),
    headers: {
      "Content-Type": "application/json; charset=utf-8",
    },
  });

  msgInput.value = "";
}

// 버튼 클릭시 메시지 전송
document.querySelector("#chat-send").addEventListener("click", () => {
  addMessage();
});

// 엔터를 치면 메시지 전송
document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e) => {
  if (e.keyCode === 13) {
    addMessage();
  }
});
