import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ListArticlesComponent} from "./component/list/listArticles.component";
import {DetailComponent} from "./component/detail/detail.component";
import {FormComponent} from "./component/form/form.component";

const routes: Routes = [
  {title: 'Articles', path: '', component: ListArticlesComponent},
  {title: 'Articles - detail', path: 'detail/:id', component: DetailComponent},
  {title: 'Articles - create', path: 'create', component: FormComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticlesRoutingModule {
}
