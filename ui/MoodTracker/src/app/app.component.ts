import { Component } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';
import { UUID } from 'angular2-uuid';
import { isDevMode } from '@angular/core';

import { MoodResults, MoodValues } from './app.types.';
import { formatDate } from '@angular/common';
import { delay, of } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'Mood Tracker';

  readonly APP_URL = 'http://localhost:8082/';
  readonly DEBUG_APP_URL = 'http://localhost:8080/';
  readonly COOKIE_NAME: string = 'MoodTrackerPersonalToken';

  serverUrl!: string;

  moodValues!: MoodValues;
  moodResults!: MoodResults | undefined;

  moodInput: string | undefined;
  commentInput: string = "";

  personalToken!: string;

  hasAlreadySubmitted: boolean = false;
  hasSubmitted: any;

  constructor(private _http: HttpClient, private _cookieService: CookieService) {
    if (isDevMode()) {
      this.serverUrl = this.DEBUG_APP_URL;
    } else {
      this.serverUrl = this.APP_URL;
    }

    this.getMoodValues();

    this.refreshPersonalToken();

    this.getAllMoods();
  }

  onMoodSelectionChange(moodInput: string) {
    this.moodInput = moodInput;
  }

  onCommentChange(event: Event): void {
    const value = (event.target as any).value;
    this.commentInput = value.substring(0, 350);
  }

  submitMood(): void {

    let mood: any = {
      mood: this.moodInput,
      comment: this.commentInput,
    };

    this.refreshPersonalToken();

    console.log(JSON.stringify(mood));

    this._http.post(`${this.serverUrl}mood/${this.personalToken}`, mood).subscribe(
      (data: any) => {
        console.log(JSON.stringify(data));

        this.getAllMoods();

        if (data.status === 200) {
          this.commentInput = '';
          this.moodInput = undefined;

          this.hasSubmitted = true;

          of(null).pipe(delay(10000)).subscribe(() => {
            this.hasSubmitted = false;
          });
        } else {
          if (data.status === 409) {
            this.hasAlreadySubmitted = true;

            of(null).pipe(delay(10000)).subscribe(() => {
              this.hasAlreadySubmitted = false;
            });
          }
          console.log(`Error ${data.status} received: ${data.statusText}`);
        }
      },
      error => {
        console.log('Error occured', error);
      }
    );
  }

  private refreshPersonalToken(): void {

    if (this._cookieService.check(this.COOKIE_NAME)) {
      this.personalToken = this._cookieService.get(this.COOKIE_NAME);
    } else {
      this.personalToken = UUID.UUID();
      this._cookieService.set(this.COOKIE_NAME, this.personalToken);
    }
  }

  private getAllMoods(): void {
    this._http.get(`${this.serverUrl}moods`, { params: new HttpParams().set('token', this.personalToken) }).subscribe(
      (data: any) => {
        console.log(JSON.stringify(data));

        if (data.status === 200) {
          let dataSet = data.moodDataSet;

          const format = 'dd MMMM yyyy';
          const locale = 'en-NZ';

          var moodResults: MoodResults = {
            moods: new Map<string, number>(),
            verbatims: new Map<string, Array<string>>(),
            date: formatDate(dataSet.date, format, locale),
          };

          let unorderedMoods = new Map<string, number>();
          let unorderedVerbatims = new Map<string, Array<string>>();
          dataSet.moods.forEach((mood: any) => {
            if (!unorderedMoods.has(mood.mood)) {
              unorderedMoods.set(mood.mood, 1);
            } else {
              unorderedMoods.set(mood.mood, unorderedMoods.get(mood.mood)! + 1 );
            }

            if (mood.comment) {
              if (!unorderedVerbatims.has(mood.mood)) {
                unorderedVerbatims.set(mood.mood, [ mood.comment ]);
              } else {
                unorderedVerbatims.get(mood.mood)!.push(mood.comment);
              }
            }
          })

          this.moodValues.forEach((_value, key) => {
            console.log(key);
            let mood = unorderedMoods.get(key);
            if (mood) {
              moodResults.moods.set(key, mood);
            }
            let verbatim = unorderedVerbatims.get(key);
            if (verbatim) {
              moodResults.verbatims.set(key, verbatim);
            }
          })

          this.moodResults = moodResults;
        } else {
          this.moodResults = undefined;

          console.log(`Error ${data.status} received: ${data.statusText}`);
        }
      },
      error => {
        console.log('Error occured', error);
      }
    );
  }

  private getMoodValues(): void {
    this._http.get(`${this.serverUrl}moodvalues`).subscribe(
      (data: any) => {
        console.log(JSON.stringify(data));

        if (data.status === 200) {
          let moodValues: MoodValues = new Map<string, string>();

          data.moodValues.forEach((moodValue: any) => {
            moodValues.set(moodValue.name, moodValue.feeling);
          });

          this.moodValues = moodValues;
        } else {
          console.log(`Error ${data.status} received: ${data.statusText}`);
        }
      },
      error => {
        console.log('Error occured', error);
      }
    );
  }

  toArray(obj: any): Array<string> {
    return Object.keys(obj).map(key => obj[key as keyof typeof obj]);
  }

  asIsOrder(a: any, b: any) {
    return 1;
  }

  getDevMode(): boolean {
    return isDevMode();
  }

  reset(): void {
    this._cookieService.delete(this.COOKIE_NAME);

    this.moodResults = undefined;
  }

}
