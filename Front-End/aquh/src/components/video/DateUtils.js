export function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);
    
    const month = date.getMonth() + 1; 
    const day = date.getDate();
    const hours = date.getHours();
    const minutes = date.getMinutes();
  
    const formattedTime = `${hours < 10 ? "0" : ""}${hours}시 ${minutes < 10 ? "0" : ""}${minutes}분`;
  
    return `${month}월 ${day}일 ${formattedTime}`;
    }
  