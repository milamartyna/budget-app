import { useState } from "react";
import { addCategory as addCategoryRequest, getCategories } from "../api/CategoryService";

export function useCategory() {
    const [error, setError] = useState<string | null>(null);

    const addCategory = async (userName: string, name: string): Promise<boolean> => {
        try {
            await addCategoryRequest(userName, name);
            setError(null);
            return true;
        } catch (err: any) {
            if (err.response && err.response.data) {
                setError(err.response.data);
            } else {
                setError("An unexpected error occurred.");
            }
            return false;
        }
    };

    const fetchCategories = async (userName: string) => {
        try {
            const categories = await getCategories(userName);
            setError(null);
            return categories;
        } catch (err: any) {
            setError("Failed to load categories");
            return [];
        }
    };

    return { addCategory, fetchCategories, error };
}
