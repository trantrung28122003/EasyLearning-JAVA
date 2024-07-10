import { ReactNode } from "react";
import { Navigate } from "react-router-dom";
interface ProtectedRouteProps {
  children: ReactNode;
}
const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
  //const auth: boolean = isUserLogin();
  const auth: boolean = true;
  return auth ? children : <Navigate to="/login" />;
};

export default ProtectedRoute;
