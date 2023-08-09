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
      setSymbols(res)
    }
    catch(error){
      console.log(`Oh nonono SymbolList! ${error}`);
    }
  };
  fetchSymbolList();
}, [])

console.log("Symbols!!!!", symbols)

const symbolcards = symbols.map((e) => (
<SymbolPortrait 
  key={e.symbolNumber} 
  symbolImgName={e.symbolImgName} 
  symbolName={e.symbolName} 
/>
));
  return (
    <div className={classes.symbolContainer}>
      {symbolcards}
    </div>
  );
}

