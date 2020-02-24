import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Producto } from '../_model/producto';
import { Subject, Observable } from 'rxjs';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  url: string = `${environment.HOST}/productos`;
  //sujeto
  productoCambio = new Subject<Producto[]>();
  mensajeCambio = new Subject<string>();
  buscarCambio = new Subject<Producto>();

  
  constructor(private http: HttpClient, private snackBar: MatSnackBar) { }


  listar() {
    return this.http.get<Producto[]>(this.url);
  }
 
  listarPageable(p: number, s: number) {
    return this.http.get<any>(`${this.url}/pageable?page=${p}&size=${s}`);
  }

  listarPorId(idProducto: number) {
    return this.http.get<Producto>(`${this.url}/${idProducto}`);
  }

  registrar(producto: Producto) {
    return this.http.post(this.url, producto);
  }

  modificar(producto: Producto) {
    return this.http.put(this.url, producto);
  }

  eliminar(idProducto: number) {
    return this.http.delete(`${this.url}/${idProducto}`);
  }

  subirFoto(archivo: File, id): Observable<HttpEvent<{}>>{
    let formData = new FormData();
    formData.append("archivo", archivo);
    formData.append("id", id);
    const req = new HttpRequest('POST', `${environment.HOST}/productos/upload`, formData, {
      reportProgress: true
    });
    
    return this.http.request(req);
    
  }
}
