import { Usuario } from './usuario';
import { DetalleVenta } from './detalleventa';

export class Venta {
    idVenta: number;
    usuario: Usuario;
    fecha: string;
    total : number;
    detalleVenta: DetalleVenta[];
    
}