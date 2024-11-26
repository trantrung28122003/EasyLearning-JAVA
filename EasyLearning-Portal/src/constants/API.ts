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
const GET_COURSE_BY_USER = BASE_URL_CUSTOMER + "/purchasedCourses";
const GET_EVENTS_BY_USER = BASE_URL_CUSTOMER + "/schedule";
const GET_COURSE_STATUS_BY_USER = BASE_URL + "/customer/CourseStatus"
const GET_TRAINING_PROGRESS_STATUS = BASE_URL + "/userTrainingProgress/getUserTrainingProgress"
const UPDATE_TRAINING_PROGRESS = BASE_URL + "/userTrainingProgress/updateStatusPartProgress"
const GET_QUESTION_BY_TRAINING_PART = BASE_URL + "/userTrainingProgress/getExercise"
const GET_SAVED_SCORE_BY_TRAINING_PART = BASE_URL + "/userTrainingProgress/getUserTrainingProgressByPart"
const GET_ALL_CERTIFICATE_BY_USER = BASE_URL + "/certificates/getAllCertificate"
const ADD_TO_FEEDBACK = BASE_URL+ "/customer/addToFeedback"
const CREATE_CERTIFICATE = BASE_URL + "/certificates/createCertificate"
const GET_CERTIFICATE_BY_COURSE = BASE_URL + "/certificates/getCertificateByCourseAndUser"
/*
  user
*/

/**
 * home
 */
const GET_COURSES_MOST_REGISTERED = BASE_URL + "/topThreeMostRegisteredCourses";
const GET_ALL_CATEGORY_WITH_COURSE = BASE_URL + "/getAllCategoryWithCourse";
const GET_ALL_COURSE = BASE_URL + "/getAllCourse";
const GET_COURSE_BY_CATEGORY = BASE_URL + "/getCoursesByCategory";
const GET_COURSE_DETAIL = BASE_URL + "/detailCourse";
const GET_FEEDBACKS_FOR_COURSE = BASE_URL + "/getFeedbacksByCourseWithoutUser";
const GET_TOP_FOUR_MOST_CATEGORY = BASE_URL + "/topFourMostCategory";
const SEARCH_COURSES = BASE_URL + "/search";


/**
 * home
 */

/**
 * Shopping cart
 */
const BASE_URL_SHOPPING_CART = BASE_URL + "/shoppingCart";
const ADD_TO_CART = BASE_URL_SHOPPING_CART + "/addToCart";
const REMOVE_FROM_CART = BASE_URL_SHOPPING_CART + "/remove";
const GET_COUPONS_BY_USER = BASE_URL +"/discount/getAllDisCountByUser"

/**
 * Shopping cart
 */

/**
 * payment
 */
const BASE_URL_PAYMENT = BASE_URL + "/payment";
const DO_PAYMENT_MOMO = BASE_URL_PAYMENT + "/doPaymentMomo";
const CONFIRM_PAYMENT = BASE_URL_PAYMENT + "/confirmPaymentMomoClient";
const APPLY_DISCOUNT_USER = BASE_URL + "/discount/addUserDiscount"
/**
 * payment
 */
export {
  LOGIN_URL,
  GET_USER_INFO_URL,
  GET_COURSES_MOST_REGISTERED,
  BASE_URL_SHOPPING_CART,
  REGISTER_URL,
  GET_ALL_CATEGORY_WITH_COURSE,
  ADD_TO_CART,
  REMOVE_FROM_CART,
  DO_PAYMENT_MOMO,
  CONFIRM_PAYMENT,
  GET_COURSE_DETAIL,
  GET_COURSE_BY_USER,
  GET_EVENTS_BY_USER,
  GET_FEEDBACKS_FOR_COURSE,
  GET_COURSE_BY_CATEGORY,
  GET_TOP_FOUR_MOST_CATEGORY,
  GET_ALL_COURSE,
  SEARCH_COURSES,
  GET_COURSE_STATUS_BY_USER,
  GET_TRAINING_PROGRESS_STATUS,
  UPDATE_TRAINING_PROGRESS,
  GET_QUESTION_BY_TRAINING_PART,
  GET_SAVED_SCORE_BY_TRAINING_PART,
  GET_ALL_CERTIFICATE_BY_USER,
  ADD_TO_FEEDBACK,
  APPLY_DISCOUNT_USER,
  GET_COUPONS_BY_USER,
  CREATE_CERTIFICATE,
  GET_CERTIFICATE_BY_COURSE
};
