import { Component } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { MoodResults } from './MoodResults';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'Mood Tracker';

  moodResults!: MoodResults;

  readonly APP_URL = 'http://localhost:8082/';
  //readonly APP_URL = 'http://localhost:8080/moodtracker/';

  personalToken: string = "foobar4";

  constructor(private _http: HttpClient) {
    this.refreshPersonalToken();

    this.getAllMoods();
  }

  submitMood(): void {

    let mood: any = {
      mood: 'MEH',
      comment: 'Boring',
    };

    this.refreshPersonalToken();

    this._http.post(`${this.APP_URL}mood/${this.personalToken}`, mood).subscribe(
      (data: any) => {
        console.log(JSON.stringify(data), null, 2);

        if (data.status !== 200) {
          console.log(`Error ${data.status} received: ${data.statusText}`);
        }
      },
      error => {
        console.log('Error occured', error);
      }
    );

    this.getAllMoods();
  }

  private refreshPersonalToken(): void {
    const format = 'yyyymmddHHMM';
    const locale = 'en-NZ';
    this.personalToken = formatDate(new Date(), format, locale);
  }

  private getAllMoods(): void {
    this._http.get(`${this.APP_URL}moods`, { params: new HttpParams().set('token', this.personalToken) }).subscribe(
      (data: any) => {
        console.log(JSON.stringify(data), null, 2);

        if (data.status === 200) {
          var moodResults: MoodResults = {
            moods: new Map<string, number>(),
            verbatims: new Map<string, Array<string>>(),
            date: '22 Feb 2023',
          };

          data.moods.forEach((mood: any) => {
            if (!moodResults.moods.has(mood.mood)) {
              moodResults.moods.set(mood.mood, 1);
            } else {
              moodResults.moods.set(mood.mood, moodResults.moods.get(mood.mood)! + 1 );
            }

            if (!moodResults.verbatims.has(mood.mood)) {
              moodResults.verbatims.set(mood.mood, [ mood.comment ]);
            } else {
              moodResults.verbatims.get(mood.mood)!.push(mood.comment);
            }
          })

          this.moodResults = moodResults;
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
}
