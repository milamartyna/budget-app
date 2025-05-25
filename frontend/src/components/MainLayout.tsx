import { Outlet, useNavigate } from "react-router-dom";
import {
    AppBar,
    Box,
    CssBaseline,
    Divider,
    Drawer,
    IconButton,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    Toolbar,
    Typography,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import AddIcon from "@mui/icons-material/Add";
import { useState } from "react";
import { useAuth } from "../hooks/auth";

const drawerWidth = 240;

export default function MainLayout() {
    const [mobileOpen, setMobileOpen] = useState(false);
    const navigate = useNavigate();
    const { logout, loggedInUser } = useAuth();

    const userName = loggedInUser();

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
            <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
                <Toolbar>
                    <IconButton color="inherit" edge="start" sx={{ mr: 2, display: { sm: "none" } }}
                                onClick={() => setMobileOpen(!mobileOpen)}>
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" noWrap sx={{ flexGrow: 1 }}>
                        Hello, {userName}
                    </Typography>
                    <IconButton color="inherit" onClick={() => {
                        logout();
                        navigate("/login");
                    }}>
                        <Typography variant="body2">Logout</Typography>
                    </IconButton>
                </Toolbar>
            </AppBar>

            <Box component="nav" sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}>
                <Drawer variant="temporary" open={mobileOpen}
                        onClose={() => setMobileOpen(false)} ModalProps={{ keepMounted: true }}
                        sx={{
                            display: { xs: "block", sm: "none" },
                            "& .MuiDrawer-paper": { width: drawerWidth }
                        }}>
                    {drawer}
                </Drawer>
                <Drawer variant="permanent" sx={{
                    display: { xs: "none", sm: "block" },
                    "& .MuiDrawer-paper": { width: drawerWidth }
                }} open>
                    {drawer}
                </Drawer>
            </Box>

            <Box component="main" sx={{ flexGrow: 1, p: 3, width: { sm: `calc(100% - ${drawerWidth}px)` } }}>
                <Toolbar />
                <Outlet />
            </Box>
        </Box>
    );
}
