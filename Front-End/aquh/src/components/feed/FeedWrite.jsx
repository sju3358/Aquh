import React, { useState } from "react";
// import "./FeedWrite.css";
import axios from "axios";
import classes from "./FeedWrite.module.css";

function FeedWrite() {
  // 기본 값 세팅
  const [feedTitle, setFeedTitle] = useState("");
  const [feedContent, setFeedContent] = useState("");
  const [fileName, setFileName] = useState("");

  const [isFeedName, setIsFeedName] = useState(false);
  const [isFeedContent, setIsFeedContent] = useState(false);

  // input값 변화-> 글 작성
  const onChangeFeedTitle = (e) => {
    const currentFeedTitle = e.target.value;
    console.log(e.target.value);
    setFeedTitle(currentFeedTitle);
    setIsFeedName(true);
  };
  const onChangeFeedContent = (e) => {
    const currentFeedContent = e.target.value;
    console.log(e.target.value);
    setFeedContent(currentFeedContent);
    setIsFeedContent(true);
  };

  const onChangeFeedFile = (event) => {
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      setFileName(selectedFile.name);
    } else {
      setFileName("");
    }
  };

  const feedWrite = () => {
    const memberNumber = localStorage.getItem("member_number");
    console.log(memberNumber);

    if (!memberNumber) {
      alert("로그인이 필요합니다."); // 예외 처리: 로그인이 되어 있지 않으면 알림 표시
      return;
    }

    if (feedTitle && feedContent) {
      const formData = new FormData(); // FormData 객체 생성
      const data = {
        member: {
          memberNumber: memberNumber,
        },
        title: feedTitle,
        content: feedContent,
      };
      formData.append(
        "",

        new Blob([JSON.stringify(data)], {
          type: "application/json",
        })
      );

      // 파일 추가 (fileInput은 파일 업로드 input 엘리먼트를 가정)
      // const fileData = new Blob(fileInput.files[0], {
      //   type: "multipart/form-data",
      // });

      // if (fileInput && fileInput.files.length > 0) {
      //   formData.append("file", fileInput.files[0]);
      // } else {

      console.log("여기");
      const fileInput = document.querySelector("#file");

      formData.append("file", fileInput.files[0]); // 파일이 없는 경우에도 null 값으로 추가

      axios
        .post("http://i9b108.p.ssafy.io:8080/api/v1/feed", {
          headers: {
            "AUTH-TOKEN": localStorage.getItem("access_token"),
            "Content-Type": "multipart/form-data", // 파일을 보낼 때 Content-Type 설정
          },
          data: formData,
        })
        .then((res) => {
          console.log(res);
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      alert("작성하신 내용을 다시 확인해 주세요!");
    }
  };

  return (
    <div className={classes.feedWriteCard}>
      <div className={classes.feedTitle}>
        {/* <label htmlFor='feedTitle'> 글 제목 : </label> */}
        <input
          className={classes.feedTitleInput}
          type='text'
          id='feedTitle'
          placeholder='제목을 입력하세요'
          defaultValue={feedTitle} // Use 'value' instead of 'defaultValue'
          onChange={onChangeFeedTitle} // Use the function directly
          // Use the function directly
        />
      </div>
      <div className={classes.feedContent}>
        <textarea
          className={classes.feedContentInput}
          name='feedContent'
          id='feedContent'
          cols='70'
          rows='10'
          placeholder='내용을 입력하세요'
          defaultValue={feedContent}
          onChange={onChangeFeedContent}></textarea>
        <div className='fileBox'>
          {/* <input
            type='text'
            className='upload-name'
            defaultValue='첨부파일'
            placeholder='첨부파일'
          />
          <label htmlFor='file'>파일찾기</label> */}
          <input
            type='file'
            id='file'
            className=''
            onChange={onChangeFeedFile}
            accept='image/*'
          />
        </div>
        <button onClick={feedWrite}>글 작성하기</button>
      </div>
    </div>
  );
}

export default FeedWrite;
