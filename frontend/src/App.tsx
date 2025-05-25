import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/login";
import Dashboard from "./pages/dashboard";
import NewTransaction from "./pages/new_transaction";
import NewCategory from "./pages/new_category";
import MainLayout from "./components/MainLayout";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />

                {/* Wrap these inside MainLayout */}
                <Route element={<MainLayout />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/transactions/add" element={<NewTransaction />} />
                    <Route path="/categories/add" element={<NewCategory />} />
                </Route>

                <Route path="/" element={<Navigate to="/login" replace />} />
            </Routes>
        </BrowserRouter>
    );
}
