import { Categoria } from './../../../_model/categoria';
import { Component, OnInit, Inject } from '@angular/core';
import { Producto } from 'src/app/_model/producto';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProductoService } from 'src/app/_service/producto.service';
import { HttpEventType } from '@angular/common/http';
import { switchMap } from 'rxjs/operators';
import { CategoriaService } from 'src/app/_service/categoria.service';
import { IfStmt } from '@angular/compiler';


@Component({
  selector: 'app-producto-dialogo',
  templateUrl: './producto-dialogo.component.html',
  styleUrls: ['./producto-dialogo.component.css']
})
export class ProductoDialogoComponent implements OnInit {

  producto: Producto;

  archivosSeleccionados: FileList;
  archivoSeleccionado: File;
  nombreArchivo: string;

  public progreso: number = 0;

  color = 'primary';
  mode = 'determinate';
  value = 0;
  bufferValue = this.progreso;

  productos: Producto[] = [];
  productoSeleccionada: Producto;
  categorias: Categoria[] = [];
  categoriaSeleccionada: Categoria;


  constructor(private dialogRef: MatDialogRef<ProductoDialogoComponent>,
    @Inject(MAT_DIALOG_DATA) private data: Producto,
    private productoService: ProductoService,
    private categoriaService: CategoriaService, ) { }

  ngOnInit(): void {

    this.producto = new Producto();
    this.producto.idProducto = this.data.idProducto;
    this.producto.nombre = this.data.nombre;
    this.producto.precio = this.data.precio;
    this.producto.stock = this.data.stock;
    this.producto.img = this.data.img;
    this.producto.estado = this.data.estado;
    this.producto.categoria = this.data.categoria;

    if (this.producto != null && this.producto.idProducto > 0) {
      this.categoriaSeleccionada = this.data.categoria;
    } else {
      this.categoriaSeleccionada = new Categoria();
    }



    this.productoService.productoCambio.subscribe(data => {
      this.producto.idProducto = this.data.idProducto;
      this.producto.nombre = this.data.nombre;
      this.producto.precio = this.data.precio;
      this.producto.stock = this.data.stock;
      this.producto.img = this.data.img;
      this.producto.estado = this.data.estado;
      this.producto.categoria = this.data.categoria;

      // this.categoriaSeleccionada= this.data.categoria;
    });






    this.listarCategoria();

  }

  listarCategoria() {
    this.categoriaService.listar().subscribe(data => {
      this.categorias = data;
    })
  }

  operar() {

    if (this.producto != null && this.producto.idProducto > 0) {

      this.productoService.modificar(this.producto).pipe(switchMap(() => {
        return this.productoService.listar();
      })).subscribe(data => {
        this.productoService.productoCambio.next(data);
        this.productoService.mensajeCambio.next('Se modificó exitosamente');
      });

    } else {
      this.producto.categoria = this.categoriaSeleccionada;
      this.productoService.registrar(this.producto).subscribe(() => {
        this.productoService.listar().subscribe(data => {
          this.productoService.productoCambio.next(data);
          this.productoService.mensajeCambio.next('Se registro exitosamente');
        });
      });

    }
    this.dialogRef.close();
  }


  cancelar() {
    this.dialogRef.close();
  }

  seleccionarArchivo(e: any) {


    this.nombreArchivo = e.target.files[0].name;

    this.archivosSeleccionados = e.target.files;
    this.progreso = 0;


  }

  subirArchivo() {
    this.archivoSeleccionado = this.archivosSeleccionados.item(0);
    if (!this.archivoSeleccionado || this.archivoSeleccionado.type.indexOf('image') < 0) {
      this.productoService.mensajeCambio.next('Debe seleccionar una foto de tipo Imagen');
      this.archivoSeleccionado = null;
    } else {
      this.productoService.subirFoto(this.archivoSeleccionado, this.producto.idProducto).subscribe(data => {

        if (data.type === HttpEventType.UploadProgress) {
          this.progreso = Math.round((data.loaded / data.total) * 100);
          this.value = this.progreso;
        } else if (data.type === HttpEventType.Response) {
          let response: any = data.body;
          this.productoService.listarPorId(this.producto.idProducto).subscribe(data => {
            this.producto.img = data.img;
          });


          this.productoService.productoCambio.next();
          this.productoService.mensajeCambio.next('La Imagen se a subido correctamente' + response.mensaje);
        }

      });
    }
  }


}
