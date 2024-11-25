import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./(context)/AuthContext";
import ProtectedRoute from "./components/features/Auth/ProtectedRoute";
import Login from "./components/features/Auth/LoginForm";
import OauthCallback from "./components/features/Auth/OauthCallback";
import Dashboard from "./pages/Dashboard";
import Home from "./components/features/Home";

const App = () => {
  return (
  
    <BrowserRouter>
      <AuthProvider>
      
        <Routes >
          <Route path="/login" element={<Login />} />
          <Route path="/oauth2/redirect" element={<OauthCallback />} />
          <Route
            path="/dashboard"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />
          <Route path="/" element={<Home />} />
        </Routes>
       
      </AuthProvider>
    </BrowserRouter>
  );
};

export default App;
