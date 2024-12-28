import { useEffect } from "react";

import { BrowserRouter, Route, Routes, useLocation } from "react-router-dom";

import { HomePage, LoginPage, RegisterPage } from "./pages";
import { Toaster } from "react-hot-toast";
import ProtectedRoute from "./routes/protected-route";

const ScrollToTop = () => {
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  return null;
};

const App = () => {
  return (
    <BrowserRouter>
      <ScrollToTop />
      <Toaster position="bottom-center" reverseOrder={false} />
      <Routes>
        <Route index path="/login" element={<LoginPage />} />
        <Route index path="/register" element={<RegisterPage />} />
        <Route element={<ProtectedRoute />}>
          <Route index path="/" element={<HomePage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default App;
