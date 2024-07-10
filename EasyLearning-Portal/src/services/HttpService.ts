import axios from "axios";
import { getCredentials } from "../hooks/useLogin";

const DoCallAPIWithToken = async <T>(
  url: string,
  method: string,
  requestBody?: T
) => {
  const token = getCredentials();
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

const DoCallAPIWithOutToken = async <T>(
  url: string,
  method: string,
  requestBody?: T | FormData
) => {
  return axios({
    method: method,
    url: url,
    data: requestBody,
  });
};

export { DoCallAPIWithToken, DoCallAPIWithOutToken };
