import { useEffect, useState } from "react";
import {Transaction} from "../types/Transaction";
import {getTransactions} from "../api/TransactionService";

export function useTransactions(userName: string) {
    const [transactions, setTransactions] = useState<Transaction[]>([]);

    useEffect(() => {
        getTransactions(userName)
            .then((data) =>
                setTransactions(data)
            )
            .catch(console.error);
    }, [userName]);

    return transactions;
}