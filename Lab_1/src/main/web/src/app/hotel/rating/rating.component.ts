import { Component, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from "@angular/forms";

/* https://stackoverflow.com/questions/50618050/how-to-create-custom-input-component-with-ngmodel-working-in-angular-6 */

@Component({
  selector: 'rating',
  template: `<div class="row r_row">
               <div class="rating">
                 <input type="radio" id="value_5" name="rating" value="5" /><label for="value_5">5</label>
                 <input type="radio" id="value_4" name="rating" value="4" /><label for="value_4">4</label>
                 <input type="radio" id="value_3" name="rating" value="3" /><label for="value_3">3</label>
                 <input type="radio" id="value_2" name="rating" value="2" /><label for="value_2">2</label>
                 <input type="radio" id="value_1" name="rating" value="1" /><label for="value_1">1</label>
               </div>
             </div>`,
  styleUrls: ['./rating.component.css'],
  providers: [{
          provide: NG_VALUE_ACCESSOR
      }]
})
export class RatingComponent implements OnInit {

  value: number = 0;

  constructor() { }

  ngOnInit() {
  }

  onChange: (_: any) => void = (_: any) => {};

  updateChanges() {
      this.onChange(this.value);
  }

  registerOnChange(fn: any): void {
      this.onChange = fn;
  }

  setValue(n){

  }

}
