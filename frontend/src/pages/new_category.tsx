import {
    Box,
    Button,
    Card,
    CardContent,
    Grid,
    TextField,
    Typography,
} from "@mui/material";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../hooks/auth";
import {addCategory} from "../api/CategoryService";

export default function NewCategory() {
    const navigate = useNavigate();
    const { loggedInUser } = useAuth();

    const userName = loggedInUser();

    const [name, setName] = useState("");

    const onSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!userName) return;

        await addCategory(userName, name);
        navigate("/dashboard");
    };

    return (
        <>

            {/* Submit form */}
                <form onSubmit={onSubmit}>
                    <Card sx={{maxWidth: 600, mx: "auto", p: 3}}>
                        <CardContent>
                            <Typography variant="h6" gutterBottom>
                                Add a new category
                            </Typography>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        label="Category name"
                                        name="name"
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                        required
                                    />
                                </Grid>

                                <Grid item xs={12}>
                                    <Box textAlign="right">
                                        <Button type="submit" variant="contained" color="primary">
                                            Add Category
                                        </Button>
                                    </Box>
                                </Grid>
                            </Grid>
                        </CardContent>
                    </Card>
                </form>
        </>
    );
}