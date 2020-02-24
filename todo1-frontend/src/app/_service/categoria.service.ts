import { Injectable } from '@angular/core';
import { Categoria } from '../_model/categoria';
import { Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  categoriaCambio = new Subject<Categoria[]>();
  mensajeCambio = new Subject<string>();

  url: string = `${environment.HOST}/categorias`;

  constructor(private http: HttpClient) { }

  listar() {
    return this.http.get<Categoria[]>(this.url);
  }

  listarPageable(p: number, s: number) {
    return this.http.get<any>(`${this.url}/pageable?page=${p}&size=${s}`);
  }
  
  listarPorId(idCategoria: number) {
    return this.http.get<Categoria>(`${this.url}/${idCategoria}`);
  }

  registrar(categoria: Categoria) {
    return this.http.post(this.url, categoria);
  }

  modificar(categoria: Categoria) {
    return this.http.put(this.url, categoria);
  }

  eliminar(idCategoria: number) {
    return this.http.delete(`${this.url}/${idCategoria}`);
  }
}
