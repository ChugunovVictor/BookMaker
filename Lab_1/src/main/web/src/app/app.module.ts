
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { HttpClientModule } from "@angular/common/http";
import { HotelListComponent } from './hotel/hotel-list/hotel-list.component';
import { HotelEditComponent } from './hotel/hotel-edit/hotel-edit.component';
import { HotelCreateComponent } from './hotel/hotel-create/hotel-create.component';
import { RatingComponent } from './hotel/rating/rating.component';
import { cRoutingModule } from './routing.module';

@NgModule({
  declarations: [
    AppComponent,
    HotelListComponent, HotelEditComponent, HotelCreateComponent,
    RatingComponent
  ],
  imports: [
    BrowserModule, HttpClientModule, FormsModule,
    RouterModule, cRoutingModule
    ],
  providers: [],
  bootstrap: [ AppComponent ]
})
export class AppModule {

}



