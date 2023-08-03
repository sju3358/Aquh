import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/api/v1/room',
    headers: { 'Content-Type': 'application/json' }
});
export default api;

export const bubbleList = () =>
    api.get('list');
    // api.get(`${room_number}`, { room_type: roomType, room_title: roomTitle, room_content: roomContent, room_thumbnail: roomThumbnail });