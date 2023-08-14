import React, { useRef, useEffect } from 'react';
import classes from './Modal.module.css';

export default function Modal({
  children,
  isModalOpen = false,
  onClose
}) {

  // 처음 렌더될땐 null 값을 가지고 있으므로 current를 붙여줘야 함
  const dialogRef = useRef(null);

  const handleModalClose = (e) => {
    const dialogDimensions = dialogRef.current.getBoundingClientRect();
    if(
      e.clientX < dialogDimensions.left ||
      e.clientX > dialogDimensions.right ||
      e.clientY < dialogDimensions.top ||
      e.clientY > dialogDimensions.bottom 
    ){
      onClose();
    }
  }

  useEffect(() => {
    const dialog = dialogRef.current;
    isModalOpen ? dialog?.showModal() : dialog?.close();
  }, [isModalOpen])

  return (
    <dialog ref={dialogRef} className={classes.dialog} onClick={handleModalClose}>
      <div class={classes.inner}>
        <button onClick={onClose} className={classes.close} title="Close">X</button>
        <div>
          {children}
        </div>
      </div>
    </dialog>
  );
}

