import { useEffect, useState } from 'react';
import SymbolPortrait from './SymbolPortrait';
import { symbolList } from '../../utils/api/api.symbol_service'
import classes from './SymbolList.module.css';
import { useParams } from 'react-router-dom';

export default function SymbolList() {

const [symbols, setSymbols] = useState([]);
const id = useParams().userId;

useEffect(() => {
  const fetchSymbolList = async () => {
    try {
      const response = await symbolList(id);
      const res = response.data.symbolList;
      console.log("symbolList", res)
      setSymbols(res);
    }
    catch(error){
      console.log(`Oh nonono SymbolList! ${error}`);
    }
  };
  fetchSymbolList();
}, [id])

console.log(symbols)


const symbolcards = symbols?.map((e) => (
<SymbolPortrait 
  key={e.symbolNumber} 
  symbolImgName={e.symbolImgName} 
  symbolName={e.symbolName}
  isAcquired={e.symbolActive}
  isActive={e.acquiredActive}
/>
));
  return (
    <div className={classes.symbolContainer}>
      {symbolcards}
    </div>
  );
}

