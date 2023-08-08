import React from 'react';
import classes from './SymbolPortrait.module.css';

export default function SymbolPortrait({symbolImgName = "https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/bb5.png", symbolName="베스트 프렌드"}) {
  return (
    <section className={classes.symbolCard}>
    <div className={classes.imgBackground}>
      <img src="https://aquh.s3.ap-northeast-2.amazonaws.com/symbol/bb5.png" alt="symbolImgName" className={classes.symbolImage}/>
    </div>
    </section>
  );
}

