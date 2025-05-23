import { useEffect, useState } from "react";
import { User } from "../types/User";
import {getUser} from "../api/UserService";

export function useUser(userName: string) {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<unknown>(null);

    useEffect(() => {
        setLoading(true);
        getUser(userName)
            .then(setUser)
            .catch(setError)
            .finally(() => setLoading(false));
    }, [userName]);

    return { user, loading, error };
}