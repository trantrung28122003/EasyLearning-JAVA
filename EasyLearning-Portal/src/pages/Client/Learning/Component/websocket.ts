import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs'; // Giả sử bạn đang dùng thư viện stompjs
import { BASE_URL } from '../../../../constants/API';

const SOCKET_URL = BASE_URL + "/ws";

// Singleton client để quản lý kết nối WebSocket
let client: Client | null = null;

// Hàm để lấy client WebSocket
export const getWebSocketClient = (): Client => {
    if (!client) {
        client = new Client({
            webSocketFactory: () => {
                const token = localStorage.getItem('token'); // Lấy token từ localStorage hoặc từ context nếu cần
                const socket = new SockJS(SOCKET_URL); // Tạo kết nối SockJS

                // Thêm header Authorization với token
                socket.onopen = () => {
                    socket.send(JSON.stringify({
                        headers: {
                            'Authorization': `Bearer ${token}`, // Gửi token vào header
                        },
                    }));
                };

                return socket;
            },
            reconnectDelay: 5000, // Thử kết nối lại sau 5 giây nếu thất bại
            debug: (str) => console.log("[WebSocket Debug]:", str), // Log để debug
        });
    }
    return client;
};
