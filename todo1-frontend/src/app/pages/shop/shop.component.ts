import { DetalleVenta } from './../../_model/detalleventa';
import { DetalleVentaDTO } from './../../_model/detalleVentaDTO';
import { Component, OnInit } from '@angular/core';
import { ProductoService } from 'src/app/_service/producto.service';
import { Producto } from 'src/app/_model/producto';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Venta } from './../../_model/venta';
import { Usuario } from './../../_model/usuario';
import { VentaService } from 'src/app/_service/venta.service';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css']
})
export class ShopComponent implements OnInit {

  productos: Producto[] = [];
  detalleVentaDTO: DetalleVentaDTO[] = [];
  detalleVenta: DetalleVenta[] = [];
  fechaSeleccionada: Date = new Date();

  idProducto: string;
  nombreProducto: string;
  precio: number;
  cantidad: number = 1;
  subtotal: number;
  mensaje: string;

  total: number = 0;

  prod: Producto;

  constructor(private productoService: ProductoService, private ventaService: VentaService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {

    this.cantidad = 1;
    this.productoService.mensajeCambio.subscribe(data => {
      this.snackBar.open(data, 'Aviso', {
        duration: 2000,
      });

    });


    this.listarProductos();
  }

  listarProductos() {
    this.productoService.listar().subscribe(data => {
      this.productos = data;
    })
  }

  agregar(p: Producto) {
    console.log(p);

    if (p.stock <= 0 && this.cantidad > p.stock) {
      this.productoService.mensajeCambio.next('este producto no esta disponible');
    } else {

      if (p.categoria.idCategoria != null && p.stock != null) {
        let detDTO = new DetalleVentaDTO();

        detDTO.idProducto = p.idProducto;
        detDTO.cantidad = this.cantidad;
        detDTO.subtotal = this.cantidad * p.precio;
        detDTO.nombreProducto = p.nombre;
        detDTO.precio = p.precio;
        this.detalleVentaDTO.push(detDTO);

        let det = new DetalleVenta();
        det.idProducto = p.idProducto;
        det.cantidad = this.cantidad;
        det.subtotal = this.cantidad * p.precio;
        this.detalleVenta.push(det);



        //this.precio = p.precio;
        this.idProducto = null;
        // this.cantidad = null;
        this.subtotal = null;
        // this.nombreProducto= null;
        //this.precio = null;
      }
    }


  }

  removerProducto(index: number) {
    this.detalleVenta.splice(index, 1);
    this.detalleVentaDTO.splice(index, 1);
  }

  estadoBotonRegistrar() {
    return (this.detalleVenta.length === 0);
  }

  aceptar() {
    // para esta prueba no se tomara el usuario de la sesion, y se definira manual
    let usuario = new Usuario();
    usuario.idUsuario = 1;
    usuario.username = "saulrios2006@gmail.com";
    usuario.clave = "123456";
    usuario.nombres = "Saul";
    usuario.apellidos = "Rios";
    usuario.identificacion = "925478569";
    usuario.estado = true;


    //ISODATE
    let tzoffset = (this.fechaSeleccionada).getTimezoneOffset() * 60000;
    let localISOTime = (new Date(Date.now() - tzoffset)).toISOString();
    let aux = `${localISOTime.substring(0, 10)} ${localISOTime.substring(11, 19)}`;
  
    for (let i of this.detalleVenta) {
      this.total += i.subtotal;
    }

      let venta = new Venta();
    venta.usuario = usuario;
    venta.fecha = aux;
    venta.total = this.total;
    venta.detalleVenta = this.detalleVenta;
    this.total = 0;

   




    this.ventaService.registrar(venta).subscribe(() => {

      for (let d of this.detalleVenta) {
        this.productoService.listarPorId(d.idProducto).subscribe(data => {
          let p = new Producto();
          p = data;
          p.stock = p.stock - d.cantidad;
  
          this.productoService.modificar(p).subscribe(data => {
  
          });
        });
  
      }
   
      this.snackBar.open("Se registró exitosamente, Gracias por su compra", "Aviso", { duration: 2000 });

      setTimeout(() => {
        this.limpiarControles();
      }, 2000);
    });






  }

  limpiarControles() {
    this.detalleVenta = [];
    this.mensaje = '';
  }


}
