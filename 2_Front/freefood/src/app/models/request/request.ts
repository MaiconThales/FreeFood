import { Menu } from "../menu/menu";
import { Restaurant } from "../restaurant/restaurant";
import { User } from "../user/user";

export interface Request {
    idRequest: number;
    restaurant: Restaurant;
    menu: Menu;
    user: User;
    name: string;
    amount: number;
    deliveryAddress: string;
    observation: string;
}