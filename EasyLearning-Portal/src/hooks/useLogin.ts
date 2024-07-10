import { LoginResponse } from "../model/Authentication";
import { User } from "../model/User";

const isUserLogin = (): boolean => {
  const authenticationInfo: LoginResponse = JSON.parse(
    localStorage.getItem("authentication") || ""
  );
  return authenticationInfo.authenticated || false;
};

const getCredentials = (): string => {
  const authenticationInfo: LoginResponse = JSON.parse(
    localStorage.getItem("authentication") || ""
  );
  return authenticationInfo.token || "";
};

const getUserInfo = () => {
  const userInfo: User = JSON.parse(localStorage.getItem("user_info") || "");
  return userInfo;
};

const hasAdminRole = () => {
  const res: User = JSON.parse(localStorage.getItem("user_info") || "");
  console.log(res.roles);
  return res.roles.some((role) => role.name === "ADMIN");
};

export { isUserLogin, getUserInfo, getCredentials, hasAdminRole };
