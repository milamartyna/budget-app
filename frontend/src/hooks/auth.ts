import { useState } from "react";
import { login as loginRequest } from "../api/AuthService";

export function useAuth() {
    const [error, setError] = useState<string | null>(null);

    const login = async (name: string, password: string): Promise<boolean> => {
        try {
            const token = await loginRequest(name, password);
            localStorage.setItem("token", token);
            localStorage.setItem("user", name);
            setError(null);
            return true;
        } catch (err) {
            setError("Login failed");
            return false;
        }
    };

    const logout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
    };

    const loggedInUser = () => localStorage.getItem("user");

    const isAuthenticated = !!localStorage.getItem("token");

    return { login, logout, loggedInUser, isAuthenticated, error };
}
