import { Injectable } from '@angular/core';
import {AngularFireDatabase} from "angularfire2/database-deprecated";
import {Image} from "./Entity/Image";
import {Observable} from "rxjs/index";

@Injectable({
  providedIn: 'root'
})
export class FirebaseService {

  images: Observable<Image[]>;

  constructor(public db: AngularFireDatabase) {

    this.images = db.list('images').valueChanges();
  }


  getFirebaseData(){

    return this.images;
  }
}
