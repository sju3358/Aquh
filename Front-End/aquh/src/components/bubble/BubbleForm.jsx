import { useEffect, useState } from "react";
import createBubble from "../../utils/api/api.bubble_service";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import classes from "./BubbleForm.module.css";

export default function BubbleForm({ onSubmit }) {
  const memberNumber = useRecoilValue(memberNumberState);
  const [bubbleForm, setBubbleForm] = useState({
    hostMemberNumber: "0",
    categoryNumber: "1",
    bubbleTitle: "",
    bubbleContent: "",
    bubbleThumbnail: "",
    // planOpenDate: '',
    // planCloseDate: '',
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(bubbleForm);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBubbleForm({
      ...bubbleForm,
      hostMemberNumber: memberNumber,
      [name]: value,
    });
  };

  const { bubbleTitle, bubbleContent, bubbleThumbnail } = bubbleForm;
  return (
    <form onSubmit={handleSubmit} className={classes.formContainer}>
      <label htmlFor="imgUrl" className={classes.label}>
        이미지
      </label>
      <div className={classes.inputbox}>
        <input
          type="text"
          name="bubbleThumbnail"
          value={bubbleThumbnail}
          onChange={handleChange}
          className={classes.input}
        />
      </div>

      <label htmlFor="title" className={classes.label}>
        제목
      </label>
      <div className={classes.inputbox}>
        <input
          type="text"
          name="bubbleTitle"
          value={bubbleTitle}
          onChange={handleChange}
          className={classes.input}
        />
      </div>
      <label htmlFor="description" className={classes.label}>
        내용
      </label>
      <div className={classes.room}>
        <textarea
          type="text"
          name="bubbleContent"
          value={bubbleContent}
          onChange={handleChange}
          className={`${classes.input} ${classes.textarea}`}
        />
      </div>
      {/* <label htmlFor="headcount">인원</label>
    <select size="8" name="headcount" value={} onChange={handleChange}>
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
      <option value="4">4</option>
      <option value="5">5</option>
      <option value="6">6</option>
      <option value="7">7</option>
      <option value="8">8</option>
    </select> */}
      <div className={classes.buttonContainer}>
        <button className={classes.button}>제출하기</button>
      </div>
    </form>
  );
}
