import {Theme} from "./theme.interface";

export interface User {
  id: number;
  username: string;
  email: string;
  theme: Theme[];
}
