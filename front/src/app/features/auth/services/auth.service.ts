import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {RegisterRequest} from "../interfaces/registerRequest.interface";
import {AuthSuccess} from "../interfaces/authSuccess.interface";
import {LoginRequest} from "../interfaces/loginRequest.interface";
import {User} from "../../../interfaces/user.interface";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private apiUrl = `${environment.baseUrl}/api/auth`;

  constructor(private httpClient: HttpClient) {
  }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.apiUrl}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.apiUrl}/login`, loginRequest);
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.apiUrl}/me`);
  }

  public updateUser(username?: string, email?: string, password?: string,) {
    let params = new HttpParams();
    if (username) params = params.set("username", username);
    if (email) params = params.set("email", email);
    if (password) params = params.set("password", password);

    return this.httpClient.put(`${this.apiUrl}/me/update`, null, {params});
  }


}
