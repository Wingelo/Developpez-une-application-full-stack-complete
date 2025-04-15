import {LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {ArticlesRoutingModule} from './article-routing.module';

// Composants
import {ListArticlesComponent} from './component/list/listArticles.component';
import {DetailComponent} from './component/detail/detail.component';
import {FormComponent} from './component/form/form.component';

// Angular Material modules
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatSnackBarModule} from '@angular/material/snack-bar';

// PrimeNG modules
import {CardModule} from 'primeng/card';
import {PanelModule} from 'primeng/panel';
import {DropdownModule} from 'primeng/dropdown';
import {ButtonModule} from 'primeng/button';
import {InputTextModule} from "primeng/inputtext";
import {PasswordModule} from "primeng/password";

const materialModules = [
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSnackBarModule
];

const primengModules = [
  CardModule,
  PanelModule,
  DropdownModule,
  ButtonModule
];

@NgModule({
  declarations: [
    ListArticlesComponent,
    DetailComponent,
    FormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ArticlesRoutingModule,
    ...materialModules,
    ...primengModules,
    InputTextModule,
    PasswordModule
  ],
  providers: [
    {
      provide: LOCALE_ID,
      useValue: 'fr-FR'
    }
  ]
})
export class ArticlesModule {
}
