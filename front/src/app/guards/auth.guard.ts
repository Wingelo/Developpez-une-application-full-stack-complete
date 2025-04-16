import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {SessionService} from '../services/session.service';
import {combineLatest, Observable} from 'rxjs';
import {filter, map, take} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private sessionService: SessionService,
  ) {
  }

  canActivate(): Observable<boolean> {
    return combineLatest([
      this.sessionService.sessionLoaded$,
      this.sessionService.$isLogged()
    ]).pipe(
      filter(([loaded]) => loaded),
      take(1),
      map(([_, isLogged]) => {
        if (!isLogged) {
          this.router.navigate(['/auth/login']);
          return false;
        }
        return true;
      })
    );
  }
}
