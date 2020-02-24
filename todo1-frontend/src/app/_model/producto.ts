import { Categoria } from './categoria';

export class Producto {
    idProducto: number;
    nombre: string;
    precio : number;    
    stock : number;
    img: string;
    estado: boolean;    
    categoria: Categoria;
}
