import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Theme} from "../interfaces/theme.interface";

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private apiUrl = `${environment.baseUrl}/api/themes`; // ou `environment.apiUrl + /themes`

  constructor(private http: HttpClient) {
  }

  public subscribe(themeId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/subscription/${themeId}`, {});
  }

  public unsubscribe(themeId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/subscription/${themeId}`);
  }

  public allTheme(): Observable<Theme[]> {
    return this.http.get<Theme[]>(`${this.apiUrl}`);
  }
}
