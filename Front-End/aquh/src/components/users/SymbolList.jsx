import { useEffect, useState } from 'react';
import SymbolPortrait from './SymbolPortrait';
import { symbolList } from '../../utils/api/api.symbol_service'
import classes from './SymbolList.module.css';

export default function SymbolList() {

const [symbols, setSymbols] = useState([]);

useEffect(() => {
  const fetchSymbolList = async () => {
    try {
      const response = await symbolList(1);
      const res = response.data.symbolList;
      setSymbols(res)
    }
    catch(error){
      console.log(`Oh no! ${error}`);
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

