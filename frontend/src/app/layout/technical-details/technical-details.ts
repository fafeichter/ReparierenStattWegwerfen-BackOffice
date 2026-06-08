import {Component} from '@angular/core';
import {buildInfo} from "../../../environments/build.info";
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-technical-details',
  imports: [
    DatePipe
  ],
  templateUrl: './technical-details.html',
  styleUrl: './technical-details.css',
})
export class TechnicalDetails {

  buildInfo = buildInfo;
}
