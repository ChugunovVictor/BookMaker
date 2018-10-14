import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <div>{{ test_value_1 }}</div>
    <rating [t]=1 [(ngModel)]="test_value_1"></rating>
    <br>
    <div>{{ test_value_2 }}</div>
    <rating [t]=2 [(ngModel)]="test_value_2"></rating>

  `,
  styles: []
})
export class AppComponent {
  title = 'app';
  test_value_1 = 4;
  test_value_2 = 2;
}
