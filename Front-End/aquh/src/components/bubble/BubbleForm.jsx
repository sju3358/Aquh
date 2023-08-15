import { useEffect, useState } from "react";
import createBubble from "../../utils/api/api.bubble_service";
import { useRecoilValue } from "recoil";
import { memberNumberState } from "../../store/loginUserState";
import classes from "./BubbleForm.module.css";
import Select from 'react-select'

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
  const categoryOptions = [
    { value: "1", label : "스포츠-운동"}, 
    {value: "2", label: "수공예-드로잉"},
    {value: "3", label: "요리-베이킹"},
    {value: "4", label: "문화-예술"},
    {value: "5", label: "미용-뷰티"},
    {value: "6", label: "홈-리빙"},
    {value: "7", label: "자기개발"},
    {value: "8", label: "기타"},
  ]
  const [selected, setSelected] = useState(categoryOptions[0]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(bubbleForm);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBubbleForm({
      ...bubbleForm,
      hostMemberNumber: memberNumber,
      categoryNumber : selected.value,
      [name]: value,
    });
  };

  // const handleSelectedOption = (e) => {
  //   const { value } = e.target;
  //   setSelected(value);
  // }

  console.log("selected", selected.value)

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
      <Select options={categoryOptions}
      onChange={setSelected}
      defaultValue={categoryOptions[0]} />
    {/* <select size="1" id="category" onChange={handleSelectedOption} value={selected}>
      <option value="1">스포츠-운동</option>
      <option value="2">수공예-드로잉</option>
      <option value="3">요리-베이킹</option>
      <option value="4">문화-예술</option>
      <option value="5">미용-뷰티</option>
      <option value="6">홈-리빙</option>
      <option value="7">자기개발</option>
      <option value="8">기타</option>
    </select> */}
      <div className={classes.buttonContainer}>
        <button className={classes.button}>제출하기</button>
      </div>
    </form>
  );
}
