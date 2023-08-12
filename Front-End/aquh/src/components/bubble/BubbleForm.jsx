import { useState } from 'react'

export default function BubbleForm(){
  const [bubbleForm, setBubbleForm] = useState({ title : '', imgUrl : '', description : '', headcount: 0})

  const handleSubmit = (e) => {
    e.preventDefault();
  }
  
  const handleChange = (e) => {
    const { title, imgUrl, description, headcount } = e.target;
    setBubbleForm({ ...bubbleForm, [title]: title, [imgUrl]: imgUrl, [description]: description, [headcount]: headcount })
  }

  return(
  <form onSubmit={handleSubmit}>
    <label htmlFor="title">Title</label>
    <input type="text" name="title" value={bubbleForm.title} onChange={handleChange} />
    <label htmlFor="imgUrl">Image URL</label>
    <input type="text" name="imgUrl" value={bubbleForm.imgUrl} onChange={handleChange} />
    <label htmlFor="description">Description</label>
    <input type="text" name="description" value={bubbleForm.description} onChange={handleChange} />
    <label htmlFor="headcount">Headcount</label>
    <select size="8" name="headcount" value={bubbleForm.headcount} onChange={handleChange}>
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
      <option value="4">4</option>
      <option value="5">5</option>
      <option value="6">6</option>
      <option value="7">7</option>
      <option value="8">8</option>
    </select>
  </form>
  )
}