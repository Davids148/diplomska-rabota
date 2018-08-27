import {Component, OnInit} from '@angular/core';
import {AngularFireDatabase, AngularFireList} from "angularfire2/database";
import {Image} from "../Entity/Image";
import {Observable} from "rxjs/index";
import {DomSanitizer, SafeHtml} from "@angular/platform-browser";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  images: Observable<Image[]>;
  imageList: Array<Image> = [];
  decodedImage: any = "";

  constructor(public db: AngularFireDatabase, private _sanitizer: DomSanitizer) {}

  async getData(){
    this.images = this.db.list('images').valueChanges();
  return this.images;
  }

  ngOnInit() {
    this.printData();
  }

  async printData() {
    this.imageList = [];
    await this.getData().then(this.images.forEach(item => {
      item.forEach(image => {
        this.decodedImage =  this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,' + image.encodedImage);
        this.imageList.push(this.decodedImage);
      });
    }));
  }
}
