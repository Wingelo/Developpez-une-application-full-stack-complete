import {Component, OnInit} from "@angular/core";
import {ThemeService} from "../../../services/theme.service";
import {Observable} from "rxjs";
import {Theme} from "../../../interfaces/theme.interface";
import {SessionService} from "../../../services/session.service";

@Component({
  selector: 'app-list-themes',
  templateUrl: './listThemes.component.html',
  styleUrls: ['./listThemes.component.scss']
})
export class ListThemesComponent implements OnInit {

  public themes$: Observable<Theme[]> = this.themeService.allTheme();

  constructor(
    private themeService: ThemeService,
    public sessionService: SessionService,
  ) {
  }

  ngOnInit(): void {
  }

  isSubscribed(themeId: number): boolean {
    return this.sessionService.getUserThemeIds().includes(themeId);
  }

  subscribe(themeId: number): void {
    if (this.isSubscribed(themeId)) return;

    this.themeService.subscribe(themeId).subscribe(() => {
      this.sessionService.user?.theme.push({id: themeId} as any);
    })
  }

}
