import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <div>{{ test_value_1 }}</div>
    <rating [size]=6 [(ngModel)]="test_value_1"></rating>
      <br>
    <div>{{ test_value_2 }}</div>
    <rating [size]=8 [(ngModel)]="test_value_2"></rating>
      <br>
    <div>{{ test_value_3 }}</div>
    <rating [disabled]=true [size]=5 [(ngModel)]="test_value_3"></rating>
  `,
  styles: []
})
export class AppComponent {
  title = 'app';
  test_value_1 = 4;
  test_value_2 = 2;
  test_value_3 = 3;
}
