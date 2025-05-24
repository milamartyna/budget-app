import {http} from "./RestClient";
import {Transaction} from "../types/Transaction";

export const getTransactions = async(userName: string)=> {
    const endpoint = `/transactions/user/${userName}`;
    const response = await http.get(endpoint);

    return response as Array<Transaction>;
}

export async function create_new_transaction(transaction: Transaction){
    return await http.post("/transactions", transaction);
}
