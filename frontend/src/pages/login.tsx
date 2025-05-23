import { useState } from "react";
import { TextField, Button, Container, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import {useAuth} from "../hooks/auth";

export default function Login() {
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    const { login, error } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const success = await login(name, password);
        console.log(success);
        if (success) navigate("/dashboard");
    };

    return (
        <Container maxWidth="sm">
            <Typography variant="h4" gutterBottom>Login</Typography>
            <form onSubmit={handleSubmit}>
                <TextField label="Username" fullWidth value={name} onChange={(e) => setName(e.target.value)} />
                <TextField label="Password" fullWidth type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                {error && <Typography color="error">{error}</Typography>}
                <Button type="submit" variant="contained" color="primary">Login</Button>
            </form>
        </Container>
    );
}
