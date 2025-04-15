import {Component, OnInit} from '@angular/core';
import {Article} from '../../../../interfaces/article.interface';
import {ArticlesService} from '../../services/articles.service';

@Component({
  selector: 'app-list-articles',
  templateUrl: './listArticles.component.html',
  styleUrls: ['./listArticles.component.scss']
})
export class ListArticlesComponent implements OnInit {
  public articles: Article[] = [];
  public sortedArticles: Article[] = [];
  public selectedSort: 'recent' | 'oldest' = 'recent';

  public sortOptions = [
    {label: 'Date (récent → ancien)', value: 'recent'},
    {label: 'Date (ancien → récent)', value: 'oldest'}
  ];

  constructor(private articleService: ArticlesService) {
  }

  ngOnInit(): void {
    this.articleService.all().subscribe((articles) => {
      this.articles = articles;
      this.sortArticles(); // sort dès réception
    });
  }

  onSortChange(): void {
    this.sortArticles();
  }

  private sortArticles(): void {
    this.sortedArticles = [...this.articles].sort((a, b) => {
      const dateA = a.createdAt ? new Date(a.createdAt).getTime() : 0;
      const dateB = b.createdAt ? new Date(b.createdAt).getTime() : 0;
      return this.selectedSort === 'recent' ? dateB - dateA : dateA - dateB;
    });
  }
}
