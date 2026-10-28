import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';

import {
  loadChartIconSet,
  loadCommerceIconSet,
  loadCoreIconSet,
  loadEssentialIconSet,
  loadMiniIconSet,
  loadSocialIconSet,
  loadTechnologyIconSet,
  loadTextEditIconSet,
} from '@clr/angular/icon';

loadCommerceIconSet();
loadCoreIconSet();
loadEssentialIconSet();
loadMiniIconSet();
loadChartIconSet();
loadSocialIconSet();
loadTechnologyIconSet();
loadTextEditIconSet();

bootstrapApplication(App, appConfig).catch((err) => console.error(err));
