import { useNavigate } from "react-router-dom";
import axios from "axios";
import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";

export default function FeedModal({ modalOpen, closeModal }) {
  const modalStyle = {
    //모달창 바깥부분 관련 스타일링
    overlay: {
      position: "fixed",
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: "rgba(0,0,0,0.6)",
      zIndex: 10,
    },
    // 모달창 내부 관련 스타일링
    content: {
      display: "flex",
      flexDirection: "column", // 오타 수정
      backgroundColor: "rgba(255,255,255,0.95)",
      overflow: "auto",
      zIndex: 10,
      // top,left,right,bottom ==> 모달창의 사이즈가 아니고 여백에서부터
      // 얼마나 떨어지게 할지 입니다.
      top: "300px",
      left: "300px",
      right: "300px",
      bottom: "300px",
      border: "5px solid black",
      borderRadius: "20px",
    },
  };

  return (
    <Modal style={modalStyle} isOpen={modalOpen} onRequestClose={closeModal}>
      {/* 모달 내용 */}
    </Modal>
  );
}
