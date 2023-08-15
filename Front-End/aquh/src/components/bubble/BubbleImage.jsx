import { useEffect, useState } from "react";
import classes from "./BubbleForm.module.css";


export default function BubbleImage({ onSubmit }) {
  const [bubbleImage, setBubbleImage] = useState({
    bubbleImagePrompt: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(bubbleImage);
    console.log(bubbleImage, "전달 되니?")
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBubbleImage({
      ...bubbleImage,
      [name]: value,
    });
  };


  const { bubbleImagePrompt } = bubbleImage;
  return (
    <form onSubmit={handleSubmit} className={classes.formContainer}>
      <label htmlFor="title" className={classes.label}>
        프롬프트
      </label>
      <div className={classes.inputbox}>
        <input
          type="text"
          name="bubbleImagePrompt"
          value={bubbleImagePrompt}
          onChange={handleChange}
          className={classes.input}
        />
      </div>
      <div className={classes.buttonContainer}>
        <button className={classes.button}>생성하기</button>
      </div>
    </form>
  );
}
