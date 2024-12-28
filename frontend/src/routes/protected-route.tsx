import { Navigate, Outlet } from "react-router-dom";

import Cookies from "universal-cookie";

const ProtectedRoute = () => {
  const cookies = new Cookies(null, { path: "/" });

  const X_API_TOKEN = cookies.get("X-API-TOKEN");

  return X_API_TOKEN ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;
