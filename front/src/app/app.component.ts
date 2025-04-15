import {Component, HostListener, OnInit} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {SessionService} from './services/session.service';
import {AuthenticationService} from './features/auth/services/auth.service';
import {filter, Observable} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  public isLogged$: Observable<boolean>;
  public isHome: boolean = false;
  public sidebarVisible = false;
  public isLoginPage: boolean = false;
  isMobile: boolean = false;

  constructor(
    private router: Router,
    private sessionService: SessionService,
    private authService: AuthenticationService
  ) {
    this.isLogged$ = this.sessionService.$isLogged();
  }

  ngOnInit(): void {
    this.autoLog();
    this.checkIfMobile();

    // détecte si on est sur la page d’accueil
    this.router.events
      .pipe(filter((event): event is NavigationEnd => event instanceof NavigationEnd))
      .subscribe(event => {
        this.isHome = event.urlAfterRedirects === '/';
        this.isLoginPage = ['/auth/login', '/auth/register'].some(path =>
          event.urlAfterRedirects.includes(path)
        );
      });

    this.sidebarVisible = false;
  }

  public $isLogged() {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

  toggleSidebar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }

  checkIfMobile(): void {
    this.isMobile = window.matchMedia('(max-width: 640px)').matches;
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): void {
    this.checkIfMobile();
  }

  private autoLog(): void {
    const token = localStorage.getItem('token');
    if (!token) {
      this.sessionService.logOut();
      return;
    }
    this.authService.me().subscribe({
      next: (user) => this.sessionService.logIn(user),
      error: () => {
        localStorage.removeItem('token');
        this.sessionService.logOut();
      }
    });
  }

}
