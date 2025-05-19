import {
    AppBar,
    Box,
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
    Toolbar,
    Typography,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import PaidIcon from "@mui/icons-material/Paid";
import MoneyOffCsredIcon from "@mui/icons-material/MoneyOffCsred";
import AccountBalanceWalletIcon from "@mui/icons-material/AccountBalanceWallet";
import AddIcon from "@mui/icons-material/Add";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const drawerWidth = 240;

/* FAKE DATA – zamienisz na fetch z backendu */
const rows = [
    { id: 1, date: "2025-05-01", description: "Salary", category: "Income", type: "INCOME", amount: 3200 },
    { id: 2, date: "2025-05-03", description: "Groceries", category: "Food",   type: "EXPENSE", amount: -150 },
    { id: 3, date: "2025-05-05", description: "Gas",       category: "Transport", type: "EXPENSE", amount: -60 },
    { id: 4, date: "2025-05-10", description: "Freelance", category: "Income", type: "INCOME", amount: 650 },
];

export default function Dashboard() {
    const [mobileOpen, setMobileOpen] = useState(false);
    const navigate = useNavigate();

    const totalIncome  = rows.filter(r => r.type === "INCOME" ).reduce((s, r) => s +  r.amount, 0);
    const totalExpense = rows.filter(r => r.type === "EXPENSE").reduce((s, r) => s + -r.amount, 0);
    const balance      = totalIncome - totalExpense;

    const columns: GridColDef[] = [
        { field: "date",        headerName: "Date",        width: 110 },
        { field: "description", headerName: "Description", flex: 1, minWidth: 160 },
        { field: "category",    headerName: "Category",    width: 120 },
        { field: "type",        headerName: "Type",        width: 100,
            renderCell: p => <Typography color={p.value === "INCOME" ? "success.main" : "error.main"}>{p.value}</Typography> },
        { field: "amount",      headerName: "Amount",      width: 130, type: "number",
            renderCell: p => <Typography color={p.value >= 0 ? "success.main" : "error.main"}>
                {p.value.toFixed(2)}
            </Typography> },
    ];

    const drawer = (
        <Box textAlign="center">
            <Typography variant="h6" sx={{ my: 2 }}>Budget Buddy</Typography>
            <Divider />
            <List>
                <ListItem button onClick={() => navigate("/dashboard")}>
                    <ListItemText primary="Dashboard" />
                </ListItem>
                <ListItem button onClick={() => navigate("/transactions/new")}>
                    <ListItemIcon><AddIcon /></ListItemIcon>
                    <ListItemText primary="Add transaction" />
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
                    <Typography variant="h6" noWrap>Dashboard</Typography>
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

            {/* Main */}
            <Box component="main" sx={{ flexGrow: 1, p: 3, width: { sm: `calc(100% - ${drawerWidth}px)` } }}>
                <Toolbar />

                {/* KARTY PODSUMOWANIA */}
                <Grid container spacing={3} mb={4}>
                    <SummaryCard icon={<PaidIcon color="success" />}  title="Income"   value={totalIncome}  color="success.main" />
                    <SummaryCard icon={<MoneyOffCsredIcon color="error" />} title="Expenses" value={totalExpense} color="error.main" />
                    <SummaryCard icon={<AccountBalanceWalletIcon color={balance>=0?"success":"error"} />}
                                 title="Balance"  value={balance} color={balance>=0 ? "success.main" : "error.main"} />
                </Grid>

                {/* TABELA */}
                <Typography variant="h6" gutterBottom>Recent transactions</Typography>
                <Box sx={{ height: 400 }}>
                    <DataGrid
                        rows={rows}
                        columns={columns}
                        pageSize={5}
                        rowsPerPageOptions={[5, 10]}
                    />                </Box>
            </Box>
        </Box>
    );
}

/* Mała pomocnicza karta */
function SummaryCard({ icon, title, value, color }:
                         { icon: React.ReactNode; title: string; value: number; color: string }) {
    return (
        <Grid item xs={12} md={4}>
            <Card sx={{ borderLeft: "6px solid", borderColor: color }}>
                <CardContent>
                    <Grid container alignItems="center" spacing={1}>
                        <Grid item>{icon}</Grid>
                        <Grid item>
                            <Typography variant="h6">{title}</Typography>
                            <Typography variant="h5" color={color}>{value.toFixed(2)} zł</Typography>
                        </Grid>
                    </Grid>
                </CardContent>
            </Card>
        </Grid>
    );
}
