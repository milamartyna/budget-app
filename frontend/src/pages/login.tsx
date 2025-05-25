import { useState } from "react";
import { TextField, Button, Container, Typography, Card, CardContent, Grid, Box } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/auth";

export default function Login() {
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    const { login, error } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const success = await login(name, password);
        if (success) navigate("/dashboard");
    };

    return (
        <Container maxWidth="sm" sx={{ mt: 8 }}>
            <Card sx={{ p: 3 }}>
                <CardContent>
                    <Typography variant="h5" gutterBottom align="center">
                        Login
                    </Typography>
                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    label="Username"
                                    fullWidth
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    label="Password"
                                    type="password"
                                    fullWidth
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                />
                            </Grid>
                            {error && (
                                <Grid item xs={12}>
                                    <Typography color="error" align="center">
                                        {error}
                                    </Typography>
                                </Grid>
                            )}
                            <Grid item xs={12}>
                                <Box textAlign="right">
                                    <Button type="submit" variant="contained" color="primary">
                                        Login
                                    </Button>
                                </Box>
                            </Grid>
                        </Grid>
                    </form>
                </CardContent>
            </Card>
        </Container>
    );
}
