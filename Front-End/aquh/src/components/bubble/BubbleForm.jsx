import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import classes from "./BubbleForm.module.css";


export default function BubbleForm({ onSubmit }) {
  const memberNumber = useRecoilValue(memberNumberState);
  const [bubbleForm, setBubbleForm] = useState({
    hostMemberNumber: "0",
    categoryNumber: "",
    bubbleTitle: "",
    bubbleContent: "",
    bubbleThumbnail: "",
    // planOpenDate: '',
    // planCloseDate: '',
  });
  
  const [selectedValue, setSelectedValue] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(bubbleForm);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBubbleForm({
      ...bubbleForm,
      hostMemberNumber: memberNumber,
      categoryNumber : selectedValue,
      [name]: value,
    });
  };

  const handleSelectedOption = (e) => {
    console.log("E.target.valueeee", typeof e.target.value)
    setSelectedValue(e.target.value);
  }

  console.log("selected", selectedValue)

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
      <label htmlFor="category">카테고리</label>
        <select size="1" id="category" onChange={handleSelectedOption} value={selectedValue}>
          <option value="1">스포츠-운동</option>
          <option value="2">수공예-드로잉</option>
          <option value="3">요리-베이킹</option>
          <option value="4">문화-예술</option>
          <option value="5">미용-뷰티</option>
          <option value="6">홈-리빙</option>
          <option value="7">자기개발</option>
          <option value="8">기타</option>
        </select> 
      <div className={classes.buttonContainer}>
        <button className={classes.button}>제출하기</button>
      </div>
    </form>
  );
}
