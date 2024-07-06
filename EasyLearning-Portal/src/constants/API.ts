const BASE_URL = "http://localhost:8080";
/* 
  authentication
*/
const BASE_URL_AUTHEN = BASE_URL + "/auth";
const LOGIN_URL = BASE_URL_AUTHEN + "/login";
/* 
  authentication
*/

/*
  user
*/
const BASE_URL_USER = BASE_URL + "/users";
const GET_USER_INFO_URL = BASE_URL_USER + "/myInfo";
/*
  user
*/

export { LOGIN_URL, GET_USER_INFO_URL };
