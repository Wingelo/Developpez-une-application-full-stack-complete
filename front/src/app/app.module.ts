import {LOCALE_ID, NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {ButtonModule} from "primeng/button";
import {CardModule} from "primeng/card";
import {InputTextModule} from "primeng/inputtext";
import {PasswordModule} from "primeng/password";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MeComponent} from "./component/me/me.component";
import {NotFoundComponent} from "./component/not-found/not-found.component";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {MatToolbarModule} from "@angular/material/toolbar";
import {ListThemesComponent} from "./features/themes/list/listThemes.component";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ConfirmationService, MessageService} from "primeng/api";
import {MenubarModule} from "primeng/menubar";
import {SidebarModule} from "primeng/sidebar";

@NgModule({
  declarations: [AppComponent, HomeComponent,
    MeComponent,
    NotFoundComponent,
    ListThemesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    ButtonModule,
    HttpClientModule,
    ButtonModule,
    CardModule,
    InputTextModule,
    PasswordModule,
    MatCardModule,
    MatIconModule,
    MatToolbarModule,
    ConfirmDialogModule,
    MenubarModule,
    CardModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    ReactiveFormsModule,
    FormsModule,
    ReactiveFormsModule,
    SidebarModule
  ],
  providers: [
    ConfirmationService,
    MessageService,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: LOCALE_ID, useValue: 'fr-FR'},
  ],
  bootstrap: [AppComponent],
})
export class AppModule {
}
