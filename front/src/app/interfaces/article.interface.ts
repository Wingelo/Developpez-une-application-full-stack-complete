import {User} from "./user.interface";
import {UserComment} from "./userComment.interface";
import {Theme} from "./theme.interface";

export interface Article {
  id: number;
  title: string;
  content: string;
  createdAt: Date | null
  updatedAt: Date | null
  user?: User;
  theme: Theme;
  comments: UserComment[];
}
