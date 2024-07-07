import { FullApplicationRoute } from "../model/Route";
import LazyLoadComponent from "../components/lazyLoadComponent/LazyLoadComponent";
export const RoutesConfig: FullApplicationRoute[] = [
  {
    path: "/",
    component: LazyLoadComponent(import("../pages/Client/Home/Home")),
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
    isProtected: false,
    isAdmin: false,
  },
];
