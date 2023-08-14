import { OpenVidu } from "openvidu-browser";

import axios from "axios";
import React, { Component, useState, useEffect } from "react";
import classes from "./ChattingSection.module.css";
import UserVideoComponent from "./UserVideoComponent";
import { json } from "react-router-dom";

import { useRecoilState } from "recoil";
import { memberNicknameState } from "../store/loginUserInfoState";

export default function ChattingSection() {
    // TODO : atom에서 방넘버 받기
    // TODO : atom에서 멤버넘거 가져오기
    const memberNumber = useRecoilValue(memberNumberState);

    const bubbleNum = useRecoilValue(bubbleNumState);
    // const [bubbleNum, setBubbleNum]=useState()

    // SSE 연결하기
    const eventSource = new EventSource(
        `https://i9b108.p.ssafy.io:8080/api/v1/bubble/chat/${bubbleNum}`
    );
    eventSource.onmessage = (event) => {
        const data = JSON.parse(event.data);

        // 로그인 유저가 보낸 메세지
        if (data.sender === memberNumber) {
            // 파란 박스(오른쪽)
            initMyMessage(data);
        } else {
            // 회색 박스 (왼쪽)
            initYourMessage(data);
        }
    };

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

    // 파란 박스 만들기
    function getSendMsgBox(data) {
        let md = data.createdAt.substring(5, 10);
        let tm = data.createdAt.substring(11, 16);
        convertTime = tm + " | " + md;

        return (
            // 파란박스 하나
            <div class="sent_msg">
                <p>${data.msg}</p>
                <span class="time_date"> ${convertTime} / <b>${data.sender}</b> </span>
            </div>
        );
    }

    // 회색 박스 만들기
    function getReceiveMsgBox(data) {
        let md = data.createdAt.substring(5, 10);
        let tm = data.createdAt.substring(11, 16);
        convertTime = tm + " | " + md;

        return (
            // 회색박스 하나
            <div class="received_withd_msg">
                <p>${data.msg}</p>
                <span class="time_date"> ${convertTime} / <b>${data.sender}</b> </span>
            </div>
        );
    }

    // DB에 새 채팅 보내기 : AJAX 채팅 메시지 전송
    async function addMessage() {
        let msgInput = document.querySelector("#chat-outgoing-msg");

        let chat = {
            bubbleNum: bubbleNum,
            msg: msgInput.value,
        };

        fetch("https://i9b108.p.ssafy.io:8080/api/v1/bubble/chat", {
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

    function ChatComponent() {
        return (
            <div className={containerFluid}>

                <div className={row}>

                    <div className={col - sm - 12}>

                        <div id="user_chat_data" className={userChatData}>

                            <div className={containerFluidChatSection} id="chat-box"></div>

                            <div className={typeMsg}>
                                <div className={inputMsgWrite}>
                                    <input id="chat-outgoing-msg" type="text" className={writeMsg} placeholder="Type a message" />
                                    <button id="chat-send" className={msgSendNtn} type="button"><i className={FaFaPaperPlane} aria-hidden="true"></i></button>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            //   <script src="js/chat.js"></script>

            //   <!-- jQuery first, then Popper.js, then Bootstrap JS -->
            //   <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            //     integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            //     crossorigin="anonymous"></script>
            //   <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            //     integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            //     crossorigin="anonymous"></script>
            //   <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            //     integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            //     crossorigin="anonymous"></script>
        )
    }
}