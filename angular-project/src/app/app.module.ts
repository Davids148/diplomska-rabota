import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AngularFireDatabaseModule} from "angularfire2/database";
import {environment} from "../environments/environment";
import {AngularFireModule} from "angularfire2";
import {AppRoutingModule, routingComponents} from "./app-routing.module";


@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
