export class Hotel {
  id: number;
  name: string;
  stars: number;

  constructor(id?: number, name?: string, stars?: number){
    this.id = id;
    this.name = name;
    this.stars = stars;
  }
}
