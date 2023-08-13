import axios from 'axios';
import https from '../https'

// const api = axios.create({
//     baseURL: 'https://i9b108.p.ssafy.io/api/v1/room',
//     headers: { 'Content-Type': 'application/json' }
// });
// export default api;

export const bubbleList = () =>
    https.get('api/v1/bubble', {
        headers: {
          "Content-Type": "application/json",
        },
      });
    // api.get(`${room_number}`, { room_type: roomType, room_title: roomTitle, room_content: roomContent, room_thumbnail: roomThumbnail });

export const bubbleCategory = () => 
      https.get('api/v1/category')

export const joinedBubbleList = () =>
      https.get('/api/v1/bubble/bubblings/my')