import axios from 'axios';

const api = axios.create({
    baseURL: 'https://i9b108.p.ssafy.io/api/v1/room',
    headers: { 'Content-Type': 'application/json' }
});
export default api;

export const bubbleList = () =>
    api.get('list');
    // api.get(`${room_number}`, { room_type: roomType, room_title: roomTitle, room_content: roomContent, room_thumbnail: roomThumbnail });