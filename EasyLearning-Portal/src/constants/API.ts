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
const BASE_URL_CUSTOMER = BASE_URL + "/customer";
const GET_COURSE_BY_COURSE = BASE_URL_CUSTOMER + "/purchasedCourses";
/*
  user
*/

/**
 * home
 */
const GET_COURSES_MOST_REGISTERED = BASE_URL + "/topThreeMostRegisteredCourses";
const GET_ALL_CATEGORY = BASE_URL + "/getAllCategoryWithCourse";
const GET_COURSE_DETAIL = BASE_URL + "/detailCourse";
/**
 * home
 */

/**
 * Shopping cart
 */
const BASE_URL_SHOPPING_CART = BASE_URL + "/shoppingCart";
const ADD_TO_CART = BASE_URL_SHOPPING_CART + "/addToCart";
const REMOVE_FROM_CART = BASE_URL_SHOPPING_CART + "/remove";
/**
 * Shopping cart
 */

/**
 * payment
 */
const BASE_URL_PAYMENT = BASE_URL + "/payment";
const DO_PAYMENT_MOMO = BASE_URL_PAYMENT + "/doPaymentMomo";
const CONFIRM_PAYMENT = BASE_URL_PAYMENT + "/confirmPaymentMomoClient";

/**
 * payment
 */
export {
  LOGIN_URL,
  GET_USER_INFO_URL,
  GET_COURSES_MOST_REGISTERED,
  BASE_URL_SHOPPING_CART,
  REGISTER_URL,
  GET_ALL_CATEGORY,
  ADD_TO_CART,
  REMOVE_FROM_CART,
  DO_PAYMENT_MOMO,
  CONFIRM_PAYMENT,
  GET_COURSE_DETAIL,
  GET_COURSE_BY_COURSE,
};
