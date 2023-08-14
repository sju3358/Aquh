import classes from "./BubblePage.module.css";
import { bubbleList, joinedBubbleList, bubbleCategory } from "../utils/api/api.bubble_service";
import { useEffect, useState } from "react";
import ButtonSelector from "../components/ui/ButtonSelector";
import BubbleList from "../components/bubble/BubbleList";
import Modal from "../components/ui/Modal";
import BubbleForm from "../components/bubble/BubbleForm";
import https from "../utils/https";



export default function BubblePage() {

  const [bubbles, setBubbles] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedType, setSelectedType] = useState(null);
  const [joinedBubbles, setJoinedBubbles] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);


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

  // related Modal
  const showModal = () => {
    setIsModalOpen(prev => !prev)
  }



//   const handleFormSubmit = () => {
//   const createSingleBubble = async (bubbleForm) => {
//     try {
//       const response = await createBubble(JSON.stringify(bubbleForm));
//       const res = response
//       console.log("BubbleForm", res)
//     }
//     catch(error){
//       console.log(error)
//     }
//   } 
//   createSingleBubble();
// }

// post 버블 생성
const handleFormSubmit = (form) => {
  https.post('api/v1/bubble', form)
    .then(
      (response) => {
        console.log(response)
      }
    )
    .catch(
      (error) => {
        console.log(error)
      }
  )
}


  return (
    <div className={!isModalOpen ? classes.container : classes.containerInvalid }>
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
      <button onClick={showModal}>방 생성하기</button>
      <Modal isModalOpen={isModalOpen} onClose={() => setIsModalOpen(false)}> 
        <BubbleForm onSubmit={handleFormSubmit} />
      </Modal>

      <div className={classes.oldChat}>
        <BubbleList
          bubbles={bubbles}
          selectedCategory={selectedCategory}
          selectedType={selectedType} 
          />
      </div>
    </div>
  );
}
    