// import PopulatedBubbleList from "../components/bubble/PopulatedBubbleList";
//TODO : 실제 api 받아오면 bubble_mock 지우기
import classes from "./BubblePage.module.css";
import { bubbleList, joinedBubbleList, bubbleCategory } from "../utils/api/api.bubble_service";
import { useEffect, useState } from "react";
import ButtonSelector from "../components/ui/ButtonSelector";
import BubbleList from "../components/bubble/BubbleList";
// TODO: remove file
// import BubbleCategory from "../components/bubble/BubbleCategory";

export default function BubblePage() {

  const [bubbles, setBubbles] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedType, setSelectedType] = useState(null);
  const [joinedBubbles, setJoinedBubbles] = useState([]);

  // fetch CategoryList
  useEffect(() => {
    const fetchBubbleCategory = async () => {
      try {
        const response = await bubbleCategory();
        const res = response.data.category;
        setCategories(res);
      }
      catch(error){
        console.log(`Oh nonono BubblePage! ${error}`);
      } 
    }
    fetchBubbleCategory();
  }, [])

  // fetch BubbleList 
  useEffect(() => {
    const fetchBubbleList = async () => {
      try {
        const response = await bubbleList();
        const res = response.data.data;
        console.log("BubbleListtttttt", res)
        setBubbles(res)
      }
      catch(error){
        console.log(`Oh nonono BubblePage! ${error}`);
      }
    }
    fetchBubbleList();
  }, [])

  //fetch JoinedBubbleList
  useEffect(() => {
    const fetchJoinedBubbleList = async () => {
      try {
        const response = await joinedBubbleList();
        const res = response.data.data;
        setJoinedBubbles(res)
      }
      catch(error){
        console.log(error)
      }
      }
      fetchJoinedBubbleList();
    }, [])

  const categoryNames = categories
    .map(category => category.categoryName)

  return (
    <div className={classes.container}>
      <ButtonSelector
        variant="regular"
        initiallySelected="전체"
        options={["전체", ...categoryNames]}
        onSelect={category => setSelectedCategory(category)}/>

      <p className={classes.latestMent}>
        <img
          src='../../droplet-white.png'
          alt='droplet'
          className={classes.droplet}
        />
        현재 참여중인 버블이예요
      </p>
      <div className={classes.latestChat}>
        <BubbleList 
        bubbles={joinedBubbles}
        />
      </div>
      <p className={classes.latestMent}>
        <img
          src='../../droplet-white.png'
          alt='droplet'
          className={classes.droplet}
        />
        Aquh에서 새로운 버블들을 찾아보세요
      </p>

      <ButtonSelector
        variant="alternate"
        initiallySelected="전체"
        options={["전체", "버블링", "버블톡"]}
        onSelect={type => setSelectedType(type)} />

      <div className={classes.oldChat}>
        <BubbleList
          bubbles={bubbles}
          selectedCategory={selectedCategory}
          selectedType={selectedType} />
      </div>
    </div>
  );
}
    