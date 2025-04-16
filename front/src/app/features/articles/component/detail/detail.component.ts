import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Article} from "../../../../interfaces/article.interface";
import {ArticlesService} from "../../services/articles.service";
import {CommentsService} from "../../services/comments.service";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public article: Article | undefined;
  public commentForm!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private articleService: ArticlesService,
    private commentService: CommentsService,
  ) {
  }

  get sortedComments() {
    return this.article?.comments?.slice().sort((a, b) => {
      const timeA = a.createdAt ? new Date(a.createdAt).getTime() : 0;
      const timeB = b.createdAt ? new Date(b.createdAt).getTime() : 0;
      return timeB - timeA;
    }) || [];
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.loadArticle(id);

    this.commentForm = this.fb.group({
      content: ['', Validators.required]
    });
  }

  public sendComment(): void {
    if (this.commentForm.invalid || !this.article) return;

    const content = this.commentForm.get('content')?.value;
    const articleId = this.article.id;

    this.commentService.addComment(articleId, content).subscribe({
      next: () => {
        this.commentForm.reset();
        this.loadArticle(String(articleId));
      },
      error: (err) => {
        console.error("Erreur lors de l'envoi du commentaire", err);
      }
    });
  }

  public back(): void {
    window.history.back();
  }

  private loadArticle(id: string): void {
    this.articleService.detail(id).subscribe((article: Article) => {
      article.comments.forEach(comment => {
        comment.createdAt = comment.createdAt ? new Date(comment.createdAt) : null;
      });

      this.article = article;
    });
  }
}
