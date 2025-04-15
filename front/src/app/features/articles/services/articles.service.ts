import {environment} from "../../../../environments/environment";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Article} from "../../../interfaces/article.interface";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {
  private apiUrl = `${environment.baseUrl}/api/articles`;

  constructor(private http: HttpClient) {
  }

  public all(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.apiUrl}`);
  }

  public detail(id: string): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }

  public create(form: FormData): Observable<Article> {
    return this.http.post<Article>(`${this.apiUrl}`, form);
  }
}
