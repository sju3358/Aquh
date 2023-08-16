import { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import axios from "axios";
import { memberNumberState } from "../../store/loginUserState";
import classes from "./BubbleForm.module.css";
import BubbleImage from "./BubbleImage";


export default function BubbleForm({ onSubmit }) {
  const memberNumber = useRecoilValue(memberNumberState);
  const [bubbleForm, setBubbleForm] = useState({
    hostMemberNumber: "0",
    bubbleTitle: "",
    bubbleContent: "",
    bubbleThumbnail: "",
    categoryNumber : "",
    // planOpenDate: '',
    // planCloseDate: '',
  });
  
  const [selectedValue, setSelectedValue] = useState("6");

  const handleSelectedOption = (e) => {
    const newValue = e.target.value;
    setSelectedValue(newValue);
    setBubbleForm({
      ...bubbleForm,
      categoryNumber: newValue,
    });
  }

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

  const handleThumbnailSubmit = async (form) => {
    await axios.post('https://api.kakaobrain.com/v2/inference/karlo/t2i',
      {
        "prompt" : `Sticker, ${form.bubbleImagePrompt}, line art drawing, ultra-detailed, clean Line art, full body, fine thin Lines, white background,`,
        "negative_prompt" : "out of frame, people, person, nsfw, close up, cropped, bad proportions, ugly, disfigured, blurry, pixelated, hideous, indistinct, out of frame, text, letters, watermark",
        "num_inference_steps" : 17,
        "guidance_scale" : 20,
        "nsfw_checker" : true,
      },
      {
        headers: {
          "Authorization" : "KakaoAK 5e3cba8892bdddabf18a9f685ea9fead",
          "Content-Type" : "application/json",
        },
      }
    ).then(
      (response) => {
        setBubbleForm({
          ...bubbleForm,
          bubbleThumbnail: response.data.images[0].image,
        });
        console.log("썸네일 생성 성공", response.data)
      }
    ).catch(
      (error) => {
        console.log("썸네일 생성 실패", error)
      }
    )
    
  }

  console.log("selected", selectedValue)

  const { bubbleTitle, bubbleContent, bubbleThumbnail } = bubbleForm;
  return (
    <div>
      <BubbleImage onSubmit={handleThumbnailSubmit} />
      <form onSubmit={handleSubmit} className={classes.formContainer}>
        <label htmlFor="imgUrl" className={classes.label}>
          이미지
        </label>
        <div className={classes.inputbox}>
          <img 
            src={bubbleThumbnail} 
            value={bubbleThumbnail}
            onChange={handleChange}
          ></img>
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
            <option  value="2">수공예-드로잉</option>
            <option  value="3">요리-베이킹</option>
            <option  value="4">문화-예술</option>
            <option  value="5">미용-뷰티</option>
            <option  value="6">홈-리빙</option>
            <option  value="7">자기개발</option>
            <option  value="8">기타</option>
          </select> 
        <div className={classes.buttonContainer}>
          <button className={classes.button}>제출하기</button>
        </div>
      </form>
    </div>
  );
}
