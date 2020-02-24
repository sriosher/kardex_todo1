import { Rol } from './rol';

export class Usuario {
    idUsuario: number;
    username: string;
    clave : string;
    estado: boolean;
    roles: Rol[];
    nombres : string;
    apellidos: string;
    identificacion: string;
}