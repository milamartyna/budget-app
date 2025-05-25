import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/login";
import Dashboard from "./pages/dashboard";
import NewTransaction from "./pages/new_transaction";
import NewCategory from "./pages/new_category";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/transactions/add" element={<NewTransaction />} />
                <Route path="/categories/add" element={<NewCategory />} />
                <Route path="/" element={<Navigate to="/login" replace />} />
            </Routes>
        </BrowserRouter>
    );
}
