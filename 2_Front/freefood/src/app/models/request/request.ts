import { Address } from "../address/Address";
import { Menu } from "../menu/menu";
import { Restaurant } from "../restaurant/restaurant";
import { User } from "../user/user";

export interface Request {
    idRequest?: number;
    amount: number;
    observation: string;
    restaurant: Restaurant;
    menu: Menu;
    user: User;
    address: Address;
}