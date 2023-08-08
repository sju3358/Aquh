import React, { useState } from "react";
import axios from "axios";
import classes from "./FeedWrite.module.css";

function FeedWrite() {
  const [feedTitle, setFeedTitle] = useState("");
  const [feedContent, setFeedContent] = useState("");
  const [file, setFile] = useState(null);

  const onChangeFeedTitle = (e) => {
    const currentFeedTitle = e.target.value;
    setFeedTitle(currentFeedTitle);
  };
  const onChangeFeedContent = (e) => {
    const currentFeedContent = e.target.value;
    setFeedContent(currentFeedContent);
  };
  const onChangeFeedFile = (e) => {
    const currentFile = e.target.files[0];
    setFile(currentFile);
    console.log("여기 파일임", currentFile);
  };

  const onClinkWriteBtn = () => {
    const memberNumber = localStorage.getItem("member_number");
    console.log("멤버넘버", memberNumber);

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

      axios
        .post("http://localhost:8080/api/v1/feed", formData, {
          headers: {
            "AUTH-TOKEN": localStorage.getItem("access_token"),
          },
        })
        .then((response) => {
          console.log("Response:", response.data);
          setFeedTitle("");
          setFeedContent("");
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
      <div className={classes.feedTitle}>
        <input
          type='text'
          value={feedTitle}
          className={classes.feedTitle}
          onChange={onChangeFeedTitle}
          placeholder='제목을 입력하세요'
        />
      </div>
      <div className={classes.feedContent}>
        <textarea
          cols='30'
          rows='10'
          value={feedContent}
          className={classes.feedContent}
          onChange={onChangeFeedContent}
          placeholder='내용을 입력하세요'></textarea>
      </div>
      <div className={classes.feedFile}>
        <input onChange={onChangeFeedFile} accept='image/*' type='file' />
      </div>
      <button onClick={onClinkWriteBtn}>글 작성하기</button>
    </div>
  );
}

export default FeedWrite;
