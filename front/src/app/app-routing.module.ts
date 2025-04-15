import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {AuthGuard} from "./guards/auth.guard";
import {MeComponent} from "./component/me/me.component";
import {NotFoundComponent} from "./component/not-found/not-found.component";
import {ListThemesComponent} from "./features/themes/list/listThemes.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module').then((m) => m.AuthModule)
  },
  {
    path: 'articles',
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/articles/articles.module').then(m => m.ArticlesModule)
  },
  {path: 'me', component: MeComponent, canActivate: [AuthGuard]},
  {path: 'themes', component: ListThemesComponent, canActivate: [AuthGuard]},
  {path: '404', component: NotFoundComponent},
  {path: '**', redirectTo: '404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
