import { Component, forwardRef, ElementRef, ViewChild, Input } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

const noop = () => {
};

/*
https://embed.plnkr.co/nqKUSPWb6w5QXr8a0wEu/?show=preview
https://almerosteyn.com/2016/04/linkup-custom-control-to-ngcontrol-ngmodel
*/

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => RatingComponent),
    multi: true
};

@Component({
  selector: 'rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css'],
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class RatingComponent implements ControlValueAccessor  {

      @Input() t : number;

      @ViewChild ("v1") v1: ElementRef;
      @ViewChild ("v2") v2: ElementRef;
      @ViewChild ("v3") v3: ElementRef;
      @ViewChild ("v4") v4: ElementRef;
      @ViewChild ("v5") v5: ElementRef;

      //The internal data model
      private innerValue: any = '';

      //Placeholders for the callbacks which are later provided
      //by the Control Value Accessor
      private onTouchedCallback: () => void = noop;
      private onChangeCallback: (_: any) => void = noop;

      //get accessor
      get value(): any {
          return this.innerValue;
      };

      //set accessor including call the onchange callback
      set value(v: any) {
          if (v !== this.innerValue) {
              this.innerValue = v;
              this.onChangeCallback(v);
          }
      }

      //From ControlValueAccessor interface
      writeValue(value: any) {
        if (value !== this.innerValue) {
          this.innerValue = value;

          switch(value){
            case 1 { this.v1.nativeElement.checked = true; break; }
            case 2 { this.v2.nativeElement.checked = true; break; }
            case 3 { this.v3.nativeElement.checked = true; break; }
            case 4 { this.v4.nativeElement.checked = true; break; }
            case 5 { this.v5.nativeElement.checked = true; break; }
          }

        }
      }

      //From ControlValueAccessor interface
      registerOnChange(fn: any) {
          this.onChangeCallback = fn;
      }

      //From ControlValueAccessor interface
      registerOnTouched(fn: any) {
          this.onTouchedCallback = fn;
      }

      onChange($event, v) {
          this.innerValue = v;
          this.onChangeCallback(v);
      }
}
