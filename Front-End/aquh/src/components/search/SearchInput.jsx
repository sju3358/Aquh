import classes from './SearchInput.module.css';
import { useRef } from 'react';
import { FaSearch } from 'react-icons/fa';

export default function SearchInput(){
  const valueRef = useRef(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    const value = valueRef.current.value;
  }

  // const filterData = dbData.filter((el) => el.name.toLowerCase().includes(nameChecked.toLowerCase()));
  
  return (
  <form className={classes.inputContainer} onSubmit={handleSubmit}>
    <input type="text" className={classes.searchInput}/>
    <button className={classes.searchButton}><FaSearch /></button>
  </form>
  )
}