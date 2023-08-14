import { useEffect, useState } from 'react'
import createBubble from '../../utils/api/api.bubble_service'
import { useRecoilValue } from 'recoil'
import { memberNumberState } from '../../store/loginUserState';

export default function BubbleForm({ onSubmit }){
  const memberNumber = useRecoilValue(memberNumberState);
  const [bubbleForm, setBubbleForm] = useState({
    hostMemberNumber: '0',
    categoryNumber: '1',
    bubbleTitle: '',
    bubbleContent: '',
    bubbleThumbnail : '',
    // planOpenDate: '',
    // planCloseDate: '',
  })

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(bubbleForm)
  }
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setBubbleForm({ ...bubbleForm, hostMemberNumber : memberNumber, [name]: value })
  }

  const {  bubbleTitle, bubbleContent, bubbleThumbnail } = bubbleForm;
  return(
  <form onSubmit={handleSubmit}>
    <label htmlFor="title">제목</label>
    <input type="text" name="bubbleTitle" value={bubbleTitle} onChange={handleChange} />
    <label htmlFor="imgUrl">image</label>
    <input type="text" name="bubbleThumbnail" value={bubbleThumbnail} onChange={handleChange} />
    <label htmlFor="description">내용</label>
    <textarea type="text" name="bubbleContent" value={bubbleContent} onChange={handleChange} />
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
    <button>제출하기</button>
  </form>
  )
}