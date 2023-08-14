import { OpenVidu } from "openvidu-browser";

import axios from "axios";
import React, { Component, useState, useEffect } from "react";
import classes from "./ChattingSection.module.css";
import UserVideoComponent from "./UserVideoComponent";
import { json } from "react-router-dom";

import { useRecoilState, useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import { bubbleNumberState } from "../../store/bubbleState";

import https from "../../utils/https"

export default function ChattingSection() {
    // TODO : atom에서 방넘버 받기
    // TODO : atom에서 멤버넘거 가져오기
    const memberNumber = useRecoilValue(memberNumberState);
    const bubbleNum = useRecoilValue(bubbleNumberState)
    // const [bubbleNum, setBubbleNum]=useState()

    // SSE 연결하기
    const eventSource = new EventSource(
        // `https://i9b108.p.ssafy.io:8080/api/v1/bubble/chat/${bubbleNum}`
        `https://i9b108.p.ssafy.io:8080/api/v1/bubble/chat/1`
    );

    eventSource.onmessage = (event) => {

        const data = JSON.parse(event.data);
        // console.log(data);

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
        let md = data.createdAt.substring(5, 10);
        let tm = data.createdAt.substring(11, 16);
        let convertTime = tm + " | " + md;

        let sendBox = `
            <div className=${classes.outgoingMsg}>
                <div className=${classes.sendMsg}>
                    <p className=${classes.sendMsgData}>${data.msg}</p>
                    <span className=${classes.timeDate}> ${convertTime} / <b>${data.sender}</b> </span>
                </div>
            </div>`;

        // chatBox.append(sendBox);
        chatBox.innerHTML += sendBox;


        document.documentElement.scrollTop = document.body.scrollHeight;
    }

    // 회색 박스 초기화/동기화
    function initYourMessage(data) {
        let chatBox = document.querySelector("#chat-box");
        let md = data.createdAt.substring(5, 10);
        let tm = data.createdAt.substring(11, 16);
        let convertTime = tm + " | " + md;

        let receivedBox = `
            <div className=${classes.receivedMsg}>
                <div className=${classes.receivedWithdMsg}>
                    <p className=${classes.receivedWithdMsgData}>${data.msg}</p>
                    <span className=${classes.timeDate}> ${convertTime} / <b>${data.sender}</b> </span>
                </div>
            </div>`;

        // console.log(receivedBox);
        chatBox.innerHTML += receivedBox;

        document.documentElement.scrollTop = document.body.scrollHeight;
    }

    // DB에 새 채팅 보내기 : AJAX 채팅 메시지 전송
    async function addMessage() {
        let msgInput = document.querySelector("#chat-outgoing-msg");

        let chat = {
            bubbleNumber: 1,
            msg: msgInput.value,
        };
        console.log("전송직전", chat);

        // let data = {
        //     method: "post",
        //     body: JSON.stringify(chat),
        //     headers: {
        //         "Content-Type": "application/json; charset=utf-8",
        //     },
        // }

        https
        .post("/api/v1/bubble/chat", chat)
        .then((result) => console.log(result));
        console.log("전송끝", chat);

        msgInput.value = "";
    }

    // 버튼 클릭시 메시지 전송
    // document.querySelector("#chat-send").addEventListener("click", () => {
    //     addMessage();
    // });
    const enterMsg = () => {
        addMessage();
    }

    // 엔터를 치면 메시지 전송
    const sendMsg = (e) => {
        if (e.keyCode === 13) {
            console.log("잘되나", e);
            addMessage();
        }
    }
    // document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e) => {
    //     if (e.keyCode === 13) {classes.
    //         addMessage();
    //     }
    // });

    return (
        <div className={classes.containerFluid}>

            <div className={classes.containerFluidrow}>

                <div>

                    <div id="user_chat_data" className={classes.userChatData}>

                        <div className={classes.containerFluidChatSection} id="chat-box"></div>

                        <div className={classes.typeMsg}>
                            <div className={classes.inputMsgWrite}>
                                <input onKeyDown={(e) => sendMsg(e)} id="chat-outgoing-msg" type="text" className={classes.writeMsg} placeholder="Type a message" />
                                <button id="chat-send" onClick={enterMsg} className={classes.msgSendBtn} type="button"><i className={classes.FaFaPaperPlane} aria-hidden="true"></i></button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    )

}