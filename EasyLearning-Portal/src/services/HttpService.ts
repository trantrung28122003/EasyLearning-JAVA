import axios from "axios";

const DoCallAPIWithToken = async <T>(
  token: string | null,
  url: string,
  method: string,
  requestBody?: T
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

const DoCallAPIWithOutToken = async <T>(
  url: string,
  requestBody: T,
  method: string
) => {
  return axios({
    method: method,
    url: url,
    data: requestBody,
  });
};

export { DoCallAPIWithToken, DoCallAPIWithOutToken };
