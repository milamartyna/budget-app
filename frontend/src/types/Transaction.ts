export type TransactionType = 'INCOME' | 'EXPENSE';

export type Transaction = {
    id?: number;
    amount: number;
    transactionType: TransactionType;
    accountName: string;
    categoryName: any;
    description: string;
    date: string;
}
