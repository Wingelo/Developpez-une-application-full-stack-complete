import {Component} from '@angular/core';
import {AbstractControl, FormBuilder, Validators} from '@angular/forms';
import {LoginRequest} from '../../interfaces/loginRequest.interface';
import {Router} from '@angular/router';
import {SessionService} from '../../../../services/session.service';
import {AuthenticationService} from '../../services/auth.service';
import {AuthSuccess} from '../../interfaces/authSuccess.interface';
import {User} from '../../../../interfaces/user.interface';
import {MessageService} from "primeng/api";

function pseudoOrEmailValidator(control: AbstractControl) {
  const value = control.value;
  if (!value) return null;

  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$/;
  const pseudoPattern = /^[a-zA-Z0-9_]{3,}$/;

  return emailPattern.test(value) || pseudoPattern.test(value)
    ? null
    : {invalidLogin: true};
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public hide = true;
  public onError = false;

  public form = this.fb.group({
    emailPseudo: ['', [Validators.required, pseudoOrEmailValidator]],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });

  constructor(
    private authService: AuthenticationService,
    private fb: FormBuilder,
    protected router: Router,
    private sessionService: SessionService,
    private messageService: MessageService
  ) {
  }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;

    this.authService.login(loginRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);

        this.authService.me().subscribe({
          next: (user: User) => {
            this.sessionService.logIn(user);
            this.router.navigate(['/articles']);
          },
          error: (err: any) => {
            console.error('Erreur lors de la récupération de l\'utilisateur', err);
            this.onError = true;

            this.messageService.add({
              severity: 'error',
              summary: 'Erreur',
              detail: 'Impossible de récupérer le profil utilisateur.',
            });
          }
        });
      },
      error: (err) => {
        console.error('Erreur de connexion', err);
        this.onError = true;

        this.messageService.add({
          severity: 'error',
          summary: 'Échec de connexion',
          detail: 'Identifiants incorrects.',
        });
      }
    });
  }
}
