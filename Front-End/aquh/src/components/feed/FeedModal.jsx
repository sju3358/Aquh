import { useNavigate } from "react-router-dom";
import axios from "axios";
import React, { useEffect, useState } from "react";
import classes from "./FeedModal.module.css";
import Modal from "react-modal";
import { memberNumberState } from "../store/loginUserState";
import { useRecoilValue } from "recoil";

export default function FeedModal(
  setModalOpen,
  modalOpen,
  detailTitle,
  detailContent,
  detailImg,
  detailNickname,
  detailCreateDate,
  detailMemberNumber
) {
  // modal style ==> 지금 현재 RoutineSelectMain에 적용되어있는 스타일링
  // **하단에 있는 옵션 말고도 웬만하면 CSS에서 줄 수 있는 스타일링 들어가는 것 같아요!**

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
      flexDirextion: "column",
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
  const userNumber = useRecoilValue(memberNumberState);
  //   모달 오픈창이 false면 모달이 닫힘(처음엔 true로 넘어온 상태)
  function closeModal() {
    setModalOpen(false);
  }

  return (
    <Modal
      style={modalStyle}
      isOpen={modalOpen}
      // ==> 모달 오픈 옵션 true or false
      onRequestClose={() => closeModal(false)}
      // ==> esc나 모달 외부 누르면꺼지게 하는 옵션, 콜백함수로 넣을 것
    >
      <h3>제목 : {detailTitle}</h3>
      <div>닉네임 : {detailNickname}</div>
      <div>작성시간 : {detailCreateDate}</div>
      <div>{detailContent}</div>
      {detailImg && <img src={`${detailImg}`} alt='피드 이미지' />}
      {/* TODO : 작성자랑 유저랑 같을떄만 수정 가능 */}
      {
        userNumber === detailMemberNumber
          ? ((<button>수정하기</button>), (<button>삭제하기</button>))
          : null // 혹은 다른 JSX 또는 컴포넌트를 반환해서 버튼을 안 보이게 함
      }
    </Modal>
  );
}
