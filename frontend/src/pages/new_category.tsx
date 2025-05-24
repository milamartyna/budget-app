import {
    AppBar,
    Box,
    Button,
    Card,
    CardContent,
    CssBaseline,
    Divider,
    Drawer,
    Grid,
    IconButton,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    TextField,
    Toolbar,
    Typography,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import AddIcon from "@mui/icons-material/Add";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {useUser} from "../hooks/users";
import {useAuth} from "../hooks/auth";
import {addCategory} from "../api/CategoryService";

const drawerWidth = 240;

export default function NewCategory() {
    const [mobileOpen, setMobileOpen] = useState(false);
    const navigate = useNavigate();
    const { logout, loggedInUser } = useAuth();

    const userName = loggedInUser();
    const { user, loading, error } = useUser(userName ?? "");

    const [name, setName] = useState("");

    const onSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!userName) return;

        await addCategory(userName, name);
        navigate("/dashboard");
    };

    if (!userName) return <div>Not logged in</div>;
    if (loading) return <div>Loading...</div>;
    if (error || !user) return <div>Error loading user</div>;

    const drawer = (
        <Box textAlign="center">
            <Typography variant="h6" sx={{ my: 2 }}>Budget Buddy</Typography>
            <Divider />
            <List>
                <ListItem button onClick={() => navigate("/dashboard")}>
                    <ListItemText primary="Dashboard" />
                </ListItem>
                <ListItem button onClick={() => navigate("/transactions/add")}>
                    <ListItemIcon><AddIcon /></ListItemIcon>
                    <ListItemText primary="Add transaction" />
                </ListItem>
                <ListItem button onClick={() => navigate("/categories/add")}>
                    <ListItemIcon><AddIcon /></ListItemIcon>
                    <ListItemText primary="Add category" />
                </ListItem>
            </List>
        </Box>
    );

    return (
        <Box sx={{ display: "flex" }}>
            <CssBaseline />

            {/* AppBar */}
            <AppBar position="fixed" sx={{ zIndex: theme => theme.zIndex.drawer + 1 }}>
                <Toolbar>
                    <IconButton color="inherit" edge="start" sx={{ mr: 2, display: { sm: "none" } }}
                                onClick={() => setMobileOpen(!mobileOpen)}>
                        <MenuIcon />
                    </IconButton>

                    <Typography variant="h6" noWrap sx={{ flexGrow: 1 }}>
                        Hello, {user.name}
                    </Typography>

                    <IconButton color="inherit" onClick={() => {
                        logout()
                        window.location.href = "/login";
                    }}>
                        <Typography variant="body2">Logout</Typography>
                    </IconButton>
                </Toolbar>

            </AppBar>

            {/* Drawer */}
            <Box component="nav" sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}>
                <Drawer variant="temporary" open={mobileOpen}
                        onClose={() => setMobileOpen(false)}
                        ModalProps={{ keepMounted: true }}
                        sx={{ display: { xs: "block", sm: "none" },
                            "& .MuiDrawer-paper": { width: drawerWidth }}}>
                    {drawer}
                </Drawer>
                <Drawer variant="permanent" sx={{
                    display: { xs: "none", sm: "block" },
                    "& .MuiDrawer-paper": { width: drawerWidth }}} open>
                    {drawer}
                </Drawer>
            </Box>

            {/* Submit form */}
            <Box component="main" sx={{flexGrow: 1, p: 3, width: {sm: `calc(100% - ${drawerWidth}px)`}}}>
                <Toolbar/>
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
            </Box>
        </Box>
    );
}