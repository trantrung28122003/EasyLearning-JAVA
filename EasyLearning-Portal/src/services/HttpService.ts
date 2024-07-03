import axios from "axios";

export const DoCallAPI = async <T>(
  token: string | null,
  url: string,
  requestBody: T,
  method: string
) => {
  return axios({
    method: method,
    url: url,
    data: requestBody,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
};
