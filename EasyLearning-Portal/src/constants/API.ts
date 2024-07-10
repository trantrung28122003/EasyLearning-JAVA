const BASE_URL = "http://localhost:8080";
/* 
  authentication
*/
const BASE_URL_AUTHEN = BASE_URL + "/auth";
const LOGIN_URL = BASE_URL_AUTHEN + "/login";
const REGISTER_URL = BASE_URL_AUTHEN + "/register";

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

/**
 * home
 */
const GET_COURSES_MOST_REGISTERED = BASE_URL + "/topThreeMostRegisteredCourses";
/**
 * home
 */

/**
 * Shopping cart
 */
const BASE_URL_SHOPPING_CART = BASE_URL + "/shoppingCart";
/**
 * Shopping cart
 */

export {
  LOGIN_URL,
  GET_USER_INFO_URL,
  GET_COURSES_MOST_REGISTERED,
  BASE_URL_SHOPPING_CART,
  REGISTER_URL,
};
