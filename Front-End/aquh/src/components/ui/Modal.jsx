import React from 'react';
import classes from './Modal.module.css';

export default function Modal({children, setIsModalOpen}) {

  const closeModal = () => {  
    setIsModalOpen(false);
  }
  return (
    <div className={classes.container}>
      어쩌라구요?
      {children}
      <button onClick={closeModal} className={classes.close}>X</button>
    </div>
  );
}

