export type TransactionType = 'INCOME' | 'EXPENSE';

export type Transaction = {
    amount: number;
    transactionType: TransactionType;
    accountName: string;
    categoryName: string;
    description: string;
    date: string;
}
