import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../interfaces/user.interface";

@Injectable({
  providedIn: 'root',
})
export class SessionService {

  public isLogged = false;
  public user: User | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged)

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: User): void {
    this.user = user;
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.user = undefined;
    this.isLogged = false;
    this.next();
  }

  public getCurrentUser(): User | undefined {
    return this.user;
  }

  public getUserThemeIds(): number[] {
    return this.user?.theme.map(t => t.id) || [];
  }

  removeTheme(themeId: number): void {
    if (this.user) {
      this.user.theme = this.user.theme.filter(t => t.id !== themeId);
    }
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

}
