import { Route, Routes } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import { RoutesConfig } from "../constants/Route.config";
import { ApplicationRoute } from "../model/Route";
const ApplicationRoutes = () => {
  return (
    <>
      <Routes>
        {RoutesConfig.map((route: ApplicationRoute, index: number) => {
          return route.isProtected ? (
            <Route
              path={route.path}
              element={<ProtectedRoute>{route.component}</ProtectedRoute>}
              key={index}
            />
          ) : (
            <Route path={route.path} element={route.component} key={index} />
          );
          // return (
          //   <Route path={route.path} element={route.component} key={index} />
          // );
        })}
      </Routes>
    </>
  );
};

export default ApplicationRoutes;
