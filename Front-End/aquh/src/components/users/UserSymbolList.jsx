import React from "react";
import { useEffect, useState } from "react";
import { mySymbolList } from "../../utils/api/api.symbol_service";
import { useParams } from "react-router-dom";
import UserSymbolPortrait from "./UserSymbolPortrait";
import classes from "./UserSymbolList.module.css";
import { memberActiveSymbolState } from "../../store/loginUserInfoState.js";
import { useRecoilState } from "recoil";
import SymbolPortrait from "./SymbolPortrait";
export default function UserSymbolList() {
  const id = useParams().userId;
  const [symbols, setSymbols] = useRecoilState(memberActiveSymbolState);

  useEffect(() => {
    const fetchSymbolList = async () => {
      try {
        const response = await mySymbolList(id);
        const res = response.data.symbolList;
        console.log("UserSymbolList", res);
        setSymbols(res);
      } catch (error) {
        console.log(`Oh nonono UserSymbolList! ${error}`);
      }
    };
    fetchSymbolList();
  }, [id]);

  const symbolcards = symbols?.map((e) => (
    <UserSymbolPortrait
      key={e.symbolNumber}
      symbolImgName={e.symbolImgName}
      symbolName={e.symbolName}
    />
  ));

  return (
    <div className={classes.symbolContainer}>
      {symbols &&
        symbols.map((e) => (
          <SymbolPortrait
            key={e.symbolNumber}
            symbolNumber={e.symbolNumber}
            symbolImgName={e.symbolImgName}
            symbolName={e.symbolName}
            isAcquired={e.symbolActive}
            isActive={e.acquiredActive}
          />
        ))}
    </div>
  );
}
