import { http } from "./RestClient";

export type Category = { name: string };

export async function getCategories(userName: string): Promise<Category[]> {
    return await http.get(`/categories/${userName}`);
}

export async function addCategory(userName: string, name: string): Promise<Category> {
    return await http.post(`/categories/${userName}`, { name });
}
