import React, { useState } from "react";
import axios from "axios";
import classes from "./FeedWrite.module.css";

function FeedWrite() {
  const [feedTitle, setFeedTitle] = useState("");
  const [feedContent, setFeedConTent] = useState("");
  // const [fileName, setFileName] = useState("");
  const [file, setFile] = useState(null); // 파일 객체를 저장하는 상태 추가

  // 유저가 작성하여 값 변경
  const onChangeFeedTitle = (e) => {
    const currentFeedTitle = e.target.value;
    setFeedTitle(currentFeedTitle);
  };
  const onChangeFeedContent = (e) => {
    const currentFeedContent = e.target.value;
    setFeedConTent(currentFeedContent);
  };
  const onChangeFeedFile = (e) => {
    //   const currentFeedFile = e.target.files[0];
    //   setFileName(currentFeedFile);
    //   console.log("여기 파일임", currentFeedFile);
    // };
    const currentFile = e.target.files[0];
    setFile(currentFile); // 파일 객체를 상태에 저장
    console.log("여기 파일임", currentFile);
  };

  //  버튼 클릭했을 때 axios 전송
  const onClinkWriteBtn = () => {
    const memberNumber = localStorage.getItem("member_number");
    console.log("멤버넘버", memberNumber);
    // TODO : atom에서 가져오기
    if (feedTitle && feedContent) {
      //제목이랑 내용이 다 채워져 있을 경우
      const formData = new FormData();
      const jsonData = {
        member: {
          memberNumber: memberNumber,
        },
        title: feedTitle,
        content: feedContent,
      };

      formData.append("feed", JSON.stringify(jsonData));
      // if (fileName) {
      //   formData.append("file", fileName);
      // } else {
      //   formData.append("file", new Blob());
      // }

      if (file) {
        formData.append("file", file);
      } else {
        formData.append("file", new Blob());
      }
      console.log("파일 : ", file.name);
      // for (let key of formData.keys()) {
      //   console.log("파일 : ", formData.get(key.name));
      //   console.log(key, ":", formData.get(key));
      // }

      axios
        .post("http://localhost:8080/api/v1/feed", formData, {
          headers: {
            "Content-Type": "multipart/form-data", // 중요: 'multipart/form-data'로 설정해야 합니다.
            "AUTH-TOKEN": localStorage.getItem("access_token"),
          },
        })
        .then((response) => {
          console.log("Response:", response.data);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    } else if (!feedTitle) {
      alert("글 제목을 작성해주세요");
    } else if (!feedContent) {
      alert("글 내용을 작성해주세요");
    }
  };

  return (
    <div className={classes.feedWriteCard}>
      {/* 피드 제목 */}
      <div className={classes.feedTitle}>
        <input
          type='text'
          value={feedTitle}
          onChange={onChangeFeedTitle}
          placeholder='제목을 입력하세요'
        />
      </div>
      {/* 피드 내용 */}
      <div className={classes.feedContent}>
        <textarea
          cols='30'
          rows='10'
          value={feedContent}
          onChange={onChangeFeedContent}
          placeholder='내용을 입력하세요'></textarea>
      </div>
      {/* 피드 파일 */}
      <div className={classes.feedFile}>
        <input onChange={onChangeFeedFile} accept='image/*' type='file' />
      </div>
      <button onClick={onClinkWriteBtn}>글 작성하기</button>
    </div>
  );
}
export default FeedWrite;
