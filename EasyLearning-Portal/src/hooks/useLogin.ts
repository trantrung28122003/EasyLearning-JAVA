import { LoginResponse } from "../model/Authentication";
import { ApplicationResponse } from "../model/BaseResponse";
import { User } from "../model/User";

const authenticationInfo: LoginResponse = JSON.parse(
  localStorage.getItem("authentication") || ""
);

const isUserLogin = (): boolean => {
  return authenticationInfo.authenticated || false;
};

const getCredentials = (): string => {
  return authenticationInfo.token || "";
};

const getUserInfo = () => {
  const userInfo: ApplicationResponse<User> = JSON.parse(
    localStorage.getItem("user_info") || ""
  );
  return userInfo.result;
};

export { isUserLogin, getUserInfo, getCredentials };
