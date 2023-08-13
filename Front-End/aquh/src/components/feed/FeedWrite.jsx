import React, { useState } from "react";
import axios from "axios";
import https from "../../utils/https_nonHeader";
import classes from "./FeedWrite.module.css";

import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";

function FeedWrite({ setIsNewFeed }) {
  const [feedTitle, setFeedTitle] = useState("");
  const [feedContent, setFeedConTent] = useState("");
  const [file, setFile] = useState(null);

  const memberNumber = useRecoilValue(memberNumberState);

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

  const onClinkWriteBtn = () => {
    if (feedTitle && feedContent) {
      const formData = new FormData();
      const jsonData = {
        member: {
          memberNumber: memberNumber,
        },
        title: feedTitle,
        content: feedContent,
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
      //   .post("https://i9b108.p.ssafy.io/api/v1/feed", formData, {
      https
        .post("/api/v1/feed", formData, {})
        .then((response) => {
          console.log("Response:", response.data);
        })
        .then(setIsNewFeed(true))
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
  //       .post("https://localhost:8080/api/v1/feed", formData, {
  //         headers: {
  //           "AUTH-TOKEN": localStorage.getItem("access_token"),
  //         },
  //       })
  //       .then((response) => {
  //         console.log("Response:", response.data);
  //       })
  //       .then(setIsNewFeed(true))
  //       .catch((error) => {
  //         console.error(":", error);
  //       });
  //   } else if (!feedTitle) {
  //     alert("글 제목을 작성해주세요");
  //   } else if (!feedContent) {
  //     alert("글 내용을 작성해주세요");
  //   }
  // };

  return (
    <div className={classes.feedWriteCard}>
      <p className={classes.feedWriteMent}>
        <img
          src='../../droplet-white.png'
          alt='droplet'
          className={classes.droplet}
        />
        나의 이야기를 작성해주세요
      </p>
      <div className={classes.feedWriteSection}>
        <input
          type='text'
          value={feedTitle}
          onChange={onChangeFeedTitle}
          placeholder='제목을 입력하세요'
          className={classes.feedTitleInput}
        />

        <textarea
          cols=''
          rows='10'
          value={feedContent}
          className={classes.feedContent}
          onChange={onChangeFeedContent}
          placeholder='내용을 입력하세요'></textarea>

        <div className={classes.feedWriteUnder}>
          <div className={classes.feedFileInput}>
            <input
              className={classes.uploadFeedName}
              value='첨부파일'
              placeholder='첨부파일'
              disabled
            />
            <label for='file' className={classes.feedFileLabel}>
              파일찾기
            </label>
            <input
              onClick={onChangeFeedFile}
              className={classes.feedImgRealBtn}
              accept='image/*'
              id='file'
              type='file'
            />
          </div>
          <button className={classes.feedWritButton} onClick={onClinkWriteBtn}>
            글 작성하기
          </button>{" "}
        </div>
      </div>
    </div>
  );
}

export default FeedWrite;
