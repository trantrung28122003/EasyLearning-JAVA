import { Route, Routes } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import { FullApplicationRoute } from "../model/Index";
import { RoutesConfig } from "../constants/Route.config";
const ApplicationRoutes = () => {
  return (
    <>
      <Routes>
        {RoutesConfig.map((route: FullApplicationRoute, index: number) => {
          return route.isProtected ? (
            <ProtectedRoute
              path={route.path}
              component={route.component}
              key={index}
            />
          ) : (
            <Route path={route.path} element={route.component} key={index} />
          );
        })}
      </Routes>
    </>
  );
};

export default ApplicationRoutes;
