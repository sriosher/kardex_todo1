import { Venta } from 'src/app/_model/venta';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VentaService {
  url: string = `${environment.HOST}/ventas`;

  constructor(private http: HttpClient) { }

  registrar(venta: Venta) {
    return this.http.post(this.url, venta);
  }
  
}
