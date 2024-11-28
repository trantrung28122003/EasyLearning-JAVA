import React, { useEffect, useState } from "react";
import { getWebSocketClient } from "./websocket";
import { DoCallAPIWithToken } from "../../services/HttpService";
import { HTTP_OK } from "../../constants/HTTPCode";
import { getCredentials } from "../../hooks/useLogin";

interface Comment {
  Id?: number;
  userId: string;
  content: string;
  replyTo?: number;
}

const CommentSection: React.FC = () => {
  const [comments, setComments] = useState<Comment[]>([]);
  const [input, setInput] = useState("");
  const client = getWebSocketClient();

  useEffect(() => {
    const res = DoCallAPIWithToken(
      `http://localhost:8080/comments`,
      "GET"
    ).then((res) => {
      if (res.status === HTTP_OK) {
        setComments(res.data);
      }
    });
    if (!client.active) {
      client.onConnect = () => {
        // setConnected(true);
        client.subscribe("/topic/comments", (content) => {
          const newComment = JSON.parse(content.body);
          setComments((prev) => [...prev, newComment]);
        });
      };

      // client.onStompError = (frame) => {
      //   console.error("Broker reported error:", frame.headers["message"]);
      //   console.error("Details:", frame.body);
      // };

      // client.onDisconnect = () => {
      //   console.log("Disconnected from WebSocket");
      //   setConnected(false);
      // };

      client.activate();
    }

    return () => {
      if (client.active) client.deactivate();
    };
  }, [client]);

  const sendComment = () => {
    if (client.connected) {
      const userInfo = JSON.parse(localStorage.getItem("user_info") || "{}");
      const commentRequest = {
        trainingPartId: "40955bca-7e83-408f-90d2-bf0efd10b569",
        commentContent: input,
        userId: userInfo.id,
        replies: [],
      };
      client.publish({
        destination: "/app/comment",
        body: JSON.stringify(commentRequest),
      });
      setInput("");
    } else {
      console.error("WebSocket connection is not established.");
    }
  };

  return (
    <div>
      <h3>Comments</h3>
      <ul>
        {comments.map((comment, index) => (
          <li key={index}>
            <strong>{comment.userId}</strong>: {comment.content}
          </li>
        ))}
      </ul>
      <input
        value={input}
        onChange={(e) => setInput(e.target.value)}
        placeholder="Type your comment..."
      />
      <button onClick={sendComment}>Send</button>
    </div>
  );
};

export default CommentSection;
