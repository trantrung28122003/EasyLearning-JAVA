import { ApplicationRoute } from "../model/Route";
import LazyLoadComponent from "../components/lazyLoadComponent/LazyLoadComponent";
export const RoutesConfig: ApplicationRoute[] = [
  {
    path: "/",
    component: LazyLoadComponent(import("../pages/Client/Home/Home")),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/",
    component: LazyLoadComponent(import("../pages/Client/Course/Course")),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/403",
    component: LazyLoadComponent(import("../pages/Errors/403/Code403")),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/404",
    component: LazyLoadComponent(import("../pages/Errors/404/Code404")),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/login",
    component: LazyLoadComponent(
      import("../pages/Client/Authentication/Login")
    ),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/register",
    component: LazyLoadComponent(
      import("../pages/Client/Authentication/Register")
    ),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/about",
    component: LazyLoadComponent(import("../pages/Client/About/About")),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/shoppingCart",
    component: LazyLoadComponent(
      import("../pages/Client/ShoppingCart/ShoppingCart")
    ),
    isProtected: true,
    isAdmin: false,
  },
  {
    path: "/admin/dashboard",
    component: LazyLoadComponent(import("../pages/Admin/Dashboard/Dashboard")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/course",
    component: LazyLoadComponent(import("../pages/Admin/Course/Course")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/course/create",
    component: LazyLoadComponent(import("../pages/Admin/Course/CreateCourse")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/course/details",
    component: LazyLoadComponent(import("../pages/Admin/Course/DetailCourse")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/course/update",
    component: LazyLoadComponent(import("../pages/Admin/Course/UpdateCourse")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/event",
    component: LazyLoadComponent(import("../pages/Admin/Event/Events")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/event/create",
    component: LazyLoadComponent(import("../pages/Admin/Event/CreateEvent")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/event/detail",
    component: LazyLoadComponent(import("../pages/Admin/Event/EventDetail")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/event/update",
    component: LazyLoadComponent(import("../pages/Admin/Event/UpdateEvent")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/category",
    component: LazyLoadComponent(import("../pages/Admin/Category/Categories")),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/category/create",
    component: LazyLoadComponent(
      import("../pages/Admin/Category/CreateCategory")
    ),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/category/update",
    component: LazyLoadComponent(
      import("../pages/Admin/Category/UpdateCategory")
    ),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/admin/category/detail",
    component: LazyLoadComponent(
      import("../pages/Admin/Category/CategoryDetail")
    ),
    isProtected: true,
    isAdmin: true,
  },
  {
    path: "/courses/",
    component: LazyLoadComponent(import("../pages/Client/Course/Course")),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/course/:courseId",
    component: LazyLoadComponent(import("../pages/Client/Course/CourseDetail")),
    isProtected: false,
    isAdmin: false,
  },
  {
    path: "/checkout",
    component: LazyLoadComponent(import("../pages/Client/CheckOut/CheckOut")),
    isProtected: true,
    isAdmin: false,
  },
  {
    //http://localhost:5173/checkout/confirmPaymentMomoClient?partnerCode=MOMOOJOI20210710&accessKey=iPXneGmrJH0G8FOP&requestId=1720598874729&amount=299000&orderId=1720598874729&orderInfo=Thanh%20toan%20khoa%20hoc&orderType=momo_wallet&transId=4082269315&message=Success&localMessage=Th%C3%A0nh%20c%C3%B4ng&responseTime=2024-07-10%2015:10:41&errorCode=0&payType=web&extraData=&signature=86d3927d6c28955416a0f220a71ce5de1706326dfa99eeb6a0018336820ce491
    //path: "/checkout/confirmPaymentMomoClient",
    path: "/checkout/confirmPaymentMomoClient",
    //&accessKey=:accessKey&requestId=:requestId&amount=:amount&orderId=:orderId&orderInfo=:orderInfo&orderType=:orderType&transId=:transId&message=:message&localMessage=:localMessage&responseTime=:responseTime&errorCode=:errorCode&payType=:payType&extraData=:extraData&signature=:signature",

    component: LazyLoadComponent(
      import("../pages/Client/CheckOut/CheckoutResult")
    ),
    isProtected: true,
    isAdmin: false,
  },
];
