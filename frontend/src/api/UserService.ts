import {http} from "./RestClient";
import {User} from "../types/User";

export const getUser = async (userName: string) => {
    const endpoint = `/users/${userName}`;
    const response = await http.get(endpoint);

    return response as User;
}