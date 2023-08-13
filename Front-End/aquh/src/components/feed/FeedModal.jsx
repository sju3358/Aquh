import { useNavigate } from "react-router-dom";
import axios from "axios";
import https from "../../utils/https_nonHeader";
import React, { useEffect, useState } from "react";
import classes from "./FeedModal.module.css";
import Modal from "react-modal";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";

export default function FeedModal({ setModalOpen, modalOpen, clickFeedData }) {
  const [isModify, setIsModify] = useState();
  const [feedTitle, setFeedTitle] = useState("");
  const [feedContent, setFeedConTent] = useState("");
  const [file, setFile] = useState();

  const onChangeFeedTitle = (e) => {
    const currentFeedTitle = e.target.value;
    setFeedTitle(currentFeedTitle);
    console.log(currentFeedTitle);
  };
  const onChangeFeedContent = (e) => {
    const currentFeedContent = e.target.value;
    setFeedConTent(currentFeedContent);
    console.log(currentFeedContent);
  };
  const onChangeFeedFile = (e) => {
    const currentFile = e.target.files[0];
    setFile(currentFile);
    console.log("여기 파일임", currentFile);
  };
  // 수정이 다 끝난 후 내용 변경 axios 전송
  // TODO : 수정할 때 파일이 없어도 가능하도록 (현재 사진 안넣으면 500에러 뜸)
  const onClinkModifyBtn = () => {
    console.log(clickFeedData.feedCreatorNumber);
    if (feedTitle && feedContent) {
      const formData = new FormData();
      const jsonData = {
        member: {
          memberNumber: clickFeedData.feedCreatorNumber,
        },
        title: feedTitle,
        content: feedContent,
        feedNumber: clickFeedData.feedNumber,
      };

      // Append the JSON data under a different key
      formData.append(
        "feed",
        new Blob([JSON.stringify(jsonData)], { type: "application/json" })
      );

      if (file) {
        formData.append("file", file);
      } else {
        formData.append("file", new Blob(), "empty");
      }
      // axios
      //   .put("https://i9b108.p.ssafy.io/api/v1/feed", formData, {
      https
        .put("/api/v1/feed", formData, {})
        .then((response) => {
          console.log("Response:", response.data);
          closeModal(false);
          alert("피드가 수정되었습니다. 아직은 새로고침 해야 적용됩니다 :<");
          // TODO : 모달창 닫히고 작성완료된거 알리기 or 자동새로고침
        })

        .catch((error) => {
          console.error(":", error);
        });
    } else if (!feedTitle) {
      alert("글 제목을 작성해주세요");
    } else if (!feedContent) {
      alert("글 내용을 작성해주세요");
    }
  };

  //     axios
  //       .put("https://localhost:8080/api/v1/feed", formData, {
  //         headers: {
  //           "AUTH-TOKEN": localStorage.getItem("access_token"),
  //         },
  //       })
  //       .then((response) => {
  //         console.log("Response:", response.data);
  //         closeModal(false);
  //         alert("피드가 수정되었습니다. 아직은 새로고침 해야 적용됩니다 :<");
  //         // TODO : 모달창 닫히고 작성완료된거 알리기 or 자동새로고침
  //       })

  //       .catch((error) => {
  //         console.error(":", error);
  //       });
  //   } else if (!feedTitle) {
  //     alert("글 제목을 작성해주세요");
  //   } else if (!feedContent) {
  //     alert("글 내용을 작성해주세요");
  //   }
  // };

  // <수정하기> 버튼 누르면 수정 가능한 창으로 변경
  const onClickModify = () => {
    setFeedTitle(clickFeedData.title);
    setFeedConTent(clickFeedData.content);
    setFile(clickFeedData.img_url);
    setIsModify(true);
  };

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
      top: "250px",
      left: "250px",
      right: "250px",
      bottom: "250px",
      border: "2px solid black",
      borderRadius: "20px",
    },
  };
  const userNumber = useRecoilValue(memberNumberState);
  //   모달 오픈창이 false면 모달이 닫힘(처음엔 true로 넘어온 상태)
  const closeModal = () => {
    setModalOpen(false);
  };

  return (
    <Modal
      // className={classes.modalCard}
      style={modalStyle}
      isOpen={modalOpen}
      onRequestClose={() => closeModal()}>
      {isModify ? (
        <div className={classes.feedWriteCard}>
          <div className={classes.feedTitle}>
            <input
              type='text'
              value={feedTitle}
              onChange={onChangeFeedTitle}
              placeholder='제목을 입력하세요'
            />
          </div>
          <div className={classes.feedContent}>
            <textarea
              cols='30'
              rows='10'
              value={feedContent}
              onChange={onChangeFeedContent}
              placeholder='내용을 입력하세요'></textarea>
          </div>
          <div className={classes.feedFile}>
            <input onChange={onChangeFeedFile} accept='image/*' type='file' />
          </div>
          <button onClick={onClinkModifyBtn}>수정하기</button>
        </div>
      ) : (
        <div>
          <h3 className={classes.title}>제목 : {clickFeedData?.title}</h3>
          <div className={classes.nickname}>
            닉네임 : {clickFeedData?.memberNickName}
          </div>
          <div className={classes.createTime}>
            작성시간 : {clickFeedData?.createDate}
          </div>
          <div className={classes.content}>{clickFeedData?.content}</div>
          {clickFeedData?.img_url && (
            <img src={`${clickFeedData?.img_url}`} alt='피드 이미지' />
          )}
          {/* 작성자랑 유저가 같을때만 수정/삭제 가능 */}
          {
            userNumber === clickFeedData?.feedCreatorNumber ? (
              <button onClick={onClickModify}>수정하기</button>
            ) : null // <button>수정하기</button>
          }
          {userNumber === clickFeedData?.feedCreatorNumber ? (
            <button>삭제하기</button>
          ) : null}
        </div>
      )}
    </Modal>
  );
}
