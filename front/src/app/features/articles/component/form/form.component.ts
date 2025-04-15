import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Theme} from 'src/app/interfaces/theme.interface';
import {ThemeService} from 'src/app/services/theme.service';
import {ArticlesService} from '../../services/articles.service';

@Component({
  selector: 'app-article-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public articleForm!: FormGroup;
  public themes: Theme[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private articleService: ArticlesService,
    private themeService: ThemeService
  ) {
  }

  ngOnInit(): void {
    this.articleForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(5)]],
      content: ['', [Validators.required, Validators.minLength(50)]],
      themeId: [null, Validators.required]
    });

    this.themeService.allTheme().subscribe({
      next: (themes) => this.themes = themes,
      error: () => this.snackBar.open('Erreur de chargement des thèmes.', 'Fermer', {duration: 3000})
    });
  }

  public submit(): void {
    if (this.articleForm.invalid) return;

    const formData = new FormData();
    formData.append('title', this.articleForm.get('title')?.value);
    formData.append('content', this.articleForm.get('content')?.value);
    formData.append('themeId', this.articleForm.get('themeId')?.value);

    this.articleService.create(formData).subscribe({
      next: () => {
        this.snackBar.open('Article créé avec succès', 'Fermer', {duration: 3000});
        this.router.navigate(['/articles']);
      },
      error: () => {
        this.snackBar.open('Erreur lors de la création de l’article', 'Fermer', {duration: 3000});
      }
    });
  }

  public back(): void {
    window.history.back();
  }
}
