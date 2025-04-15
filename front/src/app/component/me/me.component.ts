import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../features/auth/services/auth.service';
import {ThemeService} from '../../services/theme.service';
import {SessionService} from '../../services/session.service';
import {User} from 'src/app/interfaces/user.interface';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ConfirmationService} from 'primeng/api';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public user: User | undefined;
  public profileForm!: FormGroup;
  public showPassword = false;

  constructor(
    private authService: AuthenticationService,
    private themeService: ThemeService,
    private sessionService: SessionService,
    private fb: FormBuilder,
    private router: Router,
    private confirmationService: ConfirmationService
  ) {
  }

  ngOnInit(): void {
    this.initForm();

    this.authService.me().subscribe((user: User) => {
      this.user = user;

      this.profileForm.patchValue({
        username: user.username,
        email: user.email
      });
    });
  }

  initForm(): void {
    this.profileForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.email]],
      password: ['', [
        Validators.pattern(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/)
      ]]
    });
  }

  onSubmit(): void {
    if (this.profileForm.invalid) return;

    this.confirmationService.confirm({
      message: 'Tu vas être déconnecté après la mise à jour. Veux-tu continuer?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui',
      rejectLabel: 'Annuler',
      accept: () => {
        const username = this.profileForm.get('username')?.value;
        const email = this.profileForm.get('email')?.value;
        const password = this.profileForm.get('password')?.value;

        this.authService.updateUser(username, email, password).subscribe(() => {
          this.sessionService.logOut();
          this.router.navigate(['']);
        });
      }
    });
  }

  public deleteTheme(themeId: number): void {
    this.themeService.unsubscribe(themeId).subscribe({
      next: () => {
        if (this.user) {
          this.user.theme = this.user.theme.filter(t => t.id !== themeId);
          this.sessionService.removeTheme(themeId);
        }
      },
      error: err => {
        console.error('Erreur lors de la désinscription du thème', err);
      }
    });
  }

  public back() {
    window.history.back();
  }
}
