import {http} from "./RestClient";
import {Transaction} from "../types/Transaction";

export const getTransactions = async(userName: string)=> {
    const endpoint = `/transactions/user/${userName}`;
    const response = await http.get(endpoint);

    return response as Array<Transaction>;
}

export async function addTransaction(transaction: Transaction){
    return await http.post("/transactions", transaction);
}

export async function deleteTransaction(id: string, accountName: string) {
    return await http.delete(`/transactions/${id}?accountName=${encodeURIComponent(accountName)}`);
}
