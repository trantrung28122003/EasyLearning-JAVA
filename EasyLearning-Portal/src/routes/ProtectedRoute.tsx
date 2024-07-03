import { Route } from "react-router-dom";
import { ApplicationRoute } from "../model/Index";
import { IsLogin } from "../hooks/useLogin";
const ProtectedRoute: React.FC<ApplicationRoute> = ({ path, component }) => {
  const isLogin: boolean = IsLogin();
  return <Route path={path} element={component} />;
};

export default ProtectedRoute;
