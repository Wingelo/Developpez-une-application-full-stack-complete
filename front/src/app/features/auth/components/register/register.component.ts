import {Component} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {SessionService} from 'src/app/services/session.service';
import {RegisterRequest} from '../../interfaces/registerRequest.interface';
import {AuthSuccess} from '../../interfaces/authSuccess.interface';
import {User} from 'src/app/interfaces/user.interface';
import {AuthenticationService} from "../../services/auth.service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  public onError = false;
  public hide = true;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    username: ['', [Validators.required, Validators.min(3)]],
    password: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/)]]
  });

  constructor(private authService: AuthenticationService,
              private fb: FormBuilder,
              protected router: Router,
              private sessionService: SessionService) {
  }

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;

    this.authService.register(registerRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);

        this.authService.me().subscribe({
          next: (user: User) => {
            this.sessionService.logIn(user);
            this.router.navigate(['/articles']);
          },
          error: (err) => {
            console.error("Erreur lors de la récupération de l'utilisateur", err);
            this.onError = true;
          }
        });
      },
      error: (err) => {
        console.error("Erreur d'inscription", err);
        this.onError = true;
      }
    });
  }

}
