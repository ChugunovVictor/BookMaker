import { Component, forwardRef, ElementRef, ViewChild, Input, OnInit } from '@angular/core';
import { NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';

const noop = () => { };
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
  template: `<div class="t_d">
               <input *ngFor="let c of rate_values;" (change)="onChange($event, c.value)" type="radio"
                  [disabled]="disabled" [checked]="c.checked" name="group_{{group_number}}" class="t_l" value="c.value"
               />
             </div>`,
  styles: [`.t_d{ width: 300px; }
           .t_l{ width: 20px; float: right; font-size: 200%; visibility:hidden; color: gray; }
           .t_l:before{ content: "★"; visibility:visible; }
           .t_l:not(disabled):hover{ color: orange !important; }
           .t_l:not(disabled):hover ~ .t_l { color: orange  !important; }
           .t_l:disabled{pointer-events: none;}
           .t_l:checked ~ .t_l { color: yellow; }
           .t_l:checked { color: yellow; }`],
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})

export class RatingComponent implements ControlValueAccessor , OnInit {
      @Input() size : number = 5;
      @Input() disabled : boolean = false;
      group_number:number;
      rate_values: any[] = [];

      ngOnInit() {
          this.group_number = Math.floor(Math.random()*999);
          for(var i = this.size; i>0; i--){
                    this.rate_values.push( { value: i } );
          }
      }

      //The internal data model
      private innerValue: any = '';

      //Placeholders for the callbacks which are later provided by the Control Value Accessor
      private onTouchedCallback: () => void = noop;
      private onChangeCallback: (_: any) => void = noop;

      //get accessor
      get value(): any { return this.innerValue; };

      //set accessor including call the onchange callback
      set value(v: any) {
          for(var i = 0; i< this.rate_values.length; i++){
            if( this.rate_values[i].checked === 'true' ){
              this.innerValue = this.rate_values[i].value;
              this.onChangeCallback(this.innerValue);
            }
          }
      }

      //From ControlValueAccessor interface
      writeValue(value: any) {
        if (value !== this.innerValue) {
          this.innerValue = value;

          for(var i = 0; i< this.rate_values.length; i++){
            if( this.rate_values[i].value === value ){
              this.rate_values[i].checked = true;
            }
          }
        }
      }

      //From ControlValueAccessor interface
      registerOnChange(fn: any) { this.onChangeCallback = fn; }

      //From ControlValueAccessor interface
      registerOnTouched(fn: any) { this.onTouchedCallback = fn; }

      onChange($event, v) {
          this.innerValue = v;
          this.onChangeCallback(v);

          if(this.rate_values.length == 0){
            for(var i = 0; i<this.size; i++){
              this.rate_values.push( { value: i } );
            }
          }
      }
}
