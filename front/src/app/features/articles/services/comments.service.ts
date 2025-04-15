import {Injectable} from "@angular/core";
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private apiUrl = `${environment.baseUrl}/api/articles`;

  constructor(private http: HttpClient) {
  }

  public addComment(articleId: number, content: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/comment`, null, {
      params: {
        articleId: articleId,
        content: content
      }
    });
  }
}
