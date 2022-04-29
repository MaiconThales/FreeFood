import { UserAuth } from "./userAuth";

export interface AuthToken {
    token: string;
    user: UserAuth;
}