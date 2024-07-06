import { Route } from "react-router-dom";
import { ApplicationRoute } from "../model/Index";
import { isUserLogin } from "../hooks/useLogin";
const ProtectedRoute: React.FC<ApplicationRoute> = ({ path, component }) => {
  const isLogin: boolean = isUserLogin();
  return <Route path={path} element={component} />;
};

export default ProtectedRoute;
