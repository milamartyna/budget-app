import {
    Box,
    Button,
    Card,
    CardContent,
    Grid,
    MenuItem,
    TextField,
    Typography,
} from "@mui/material";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useUser} from "../hooks/users";
import {useAuth} from "../hooks/auth";
import {Transaction, TransactionType} from "../types/Transaction";
import {Category, getCategories} from "../api/CategoryService";
import {addTransaction} from "../api/TransactionService";

export default function NewTransaction() {
    const navigate = useNavigate();
    const { loggedInUser } = useAuth();

    const userName = loggedInUser();
    const { user, loading, error } = useUser(userName ?? "");

    const [amount, setAmount] = useState("");
    const [type, setType] = useState("");
    const [category, setCategory] = useState("");
    const [categories, setCategories] = useState<Category[]>([]);
    const [date, setDate] = useState("");
    const [description, setDescription] = useState("");

    useEffect(() => {
        if (userName && categories.length === 0) {
            getCategories(userName).then(setCategories);
        }
    }, [userName, categories.length]);

    const onSubmit = async (e: React.FormEvent) => {
        if (!userName) return;

        const transaction: Transaction = {
            amount: parseFloat(amount),
            transactionType: type as TransactionType,
            accountName: userName,
            description,
            date,
            categoryName: category.trim() !== "" ? category : null
        };
        e.preventDefault();
        const success = await addTransaction(transaction);
        console.log(success);
        if (success) navigate("/dashboard");
    };

    if (!userName) return <div>Not logged in</div>;
    if (loading) return <div>Loading...</div>;
    if (error || !user) return <div>Error loading user</div>;

    return (
        <>
            {/* Submit form */}
                <form onSubmit={onSubmit}>
                    <Card sx={{maxWidth: 600, mx: "auto", p: 3}}>
                        <CardContent>
                            <Typography variant="h6" gutterBottom>
                                Add a new transaction
                            </Typography>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        label="Description"
                                        name="description"
                                        value={description}
                                        onChange={(e) => setDescription(e.target.value)}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        label="Amount"
                                        name="amount"
                                        type="number"
                                        value={amount}
                                        onChange={(e) => setAmount(e.target.value)}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        select
                                        label="Category"
                                        name="category"
                                        value={category}
                                        onChange={(e) => setCategory(e.target.value)}
                                    >
                                        {categories.map((cat) => (
                                            <MenuItem key={cat.name} value={cat.name}>
                                                {cat.name}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        select
                                        label="Type"
                                        name="type"
                                        value={type}
                                        onChange={(e) => setType(e.target.value)}
                                        required
                                    >
                                        <MenuItem value="INCOME">Income</MenuItem>
                                        <MenuItem value="EXPENSE">Expense</MenuItem>
                                    </TextField>
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        label="Date"
                                        name="date"
                                        type="datetime-local"
                                        value={date}
                                        onChange={(e) => setDate(e.target.value)}
                                        InputLabelProps={{shrink: true}}
                                        required
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <Box textAlign="right">
                                        <Button type="submit" variant="contained" color="primary">
                                            Add Transaction
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