import React from 'react';
import classes from './SymbolPortrait.module.css';

export default function SymbolPortrait({symbolImgName = "https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/bb5.png", symbolName="베스트 프렌드", isActive}) {

  const handleClick = () => {
    console.log("클릭됨")
  }
  return (
    <section className={classes.symbolCard}>
    <div className={classes.imgBackground}>
      {/* TODO: isActive를 획득여부로 교체해야함. */}
      <img src={symbolImgName} alt="symbolImgName" onClick={handleClick} className={`${ !isActive ? classes.symbolImage : classes.symbolImageInvalid}`}/>
    </div>
    </section>
  );
}

