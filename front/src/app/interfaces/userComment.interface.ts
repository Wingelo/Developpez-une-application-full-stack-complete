export interface UserComment {
  id: number;
  content: string;
  createdAt: Date | null
  updatedAt: Date | null
  author: string;
}
