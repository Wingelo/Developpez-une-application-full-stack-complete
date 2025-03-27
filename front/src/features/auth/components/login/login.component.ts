import {Component} from "@angular/core";
import {AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {LoginRequest} from "../../interfaces/loginRequest.interface";
import {Router} from "@angular/router";
import {SessionService} from "../../../../services/session.service";
import {AuthenticationService} from "../../services/auth.service";
import {AuthSuccess} from "../../interfaces/authSuccess.interface";
import {User} from "../../../../interfaces/user.interface";

function pseudoOrEmailValidator(control: AbstractControl) {

  const value = control.value;
  if (!value) return {required: true};

  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const pseudoPattern = /^[a-zA-Z0-9_]{3,}$/;

  if (emailPattern.test(value) || pseudoPattern.test(value)) {
    return null;
  }
  return {required: true};
}


@Component({
  selector: "login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent {
  public hide = true
  public onError = false

  public form = this.fb.group({
    login: ['', [Validators.required, pseudoOrEmailValidator]],
    password: ['', [Validators.required, pseudoOrEmailValidator, Validators.minLength(8)]],
  })

  constructor(private authService: AuthenticationService,
              private fb: FormBuilder,
              private router: Router,
              private sessionService: SessionService) {
  }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;

    this.authService.login(loginRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem("token", response.token);

        this.authService.me().subscribe({
          next: (user: User) => {
            this.sessionService.logIn(user);
            this.router.navigate(['/articles']);
          },
          error: (err: any) => {
            console.error("Erreur lors de la récupération de l'utilisateur", err);
            this.onError = true;
          }
        });
      },
      error: (err) => {
        console.error("Erreur de connexion", err);
        this.onError = true;
      }
    });
  }

}
