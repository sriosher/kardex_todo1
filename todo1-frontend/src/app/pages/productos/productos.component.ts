import { Component, OnInit, ViewChild } from '@angular/core';
import { Producto } from 'src/app/_model/producto';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ProductoService } from 'src/app/_service/producto.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { ProductoDialogoComponent } from './producto-dialogo/producto-dialogo.component';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {

  cantidad: number = 0;
  displayedColumns = ['id', 'img','nombre','precio','stock', 'acciones'];
  dataSource: MatTableDataSource<Producto>;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  

  constructor(private productoService: ProductoService,private dialog : MatDialog, private snackBar: MatSnackBar, public route: ActivatedRoute) { }

  ngOnInit(): void {

    this.productoService.mensajeCambio.subscribe(data => {
      this.snackBar.open(data, 'Aviso', {
        duration: 2000,
      });

    });

    this.productoService.listar().subscribe(data => {
     
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });

    this.productoService.productoCambio.subscribe(data => {
      
      this.productoService.listar().subscribe(data => {
        this.dataSource = new MatTableDataSource(data);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      });
      
      
    });
  }


  filtrar(valor: string) {
    this.dataSource.filter = valor.trim().toLowerCase();
  }

  eliminar(idProducto: number) {
       
    this.productoService.eliminar(idProducto).subscribe(() => {
          this.productoService.productoCambio.next();
          this.productoService.mensajeCambio.next('Producto eliminado correctamente');
    });
    
  }

  abrirDialogo(producto? : Producto){
    let prod = producto != null ? producto : new Producto();
    this.dialog.open(ProductoDialogoComponent, {
      width: '450px',
      data: prod
    });
  }

  mostrarMas(e: any){
    this.productoService.listarPageable(e.pageIndex, e.pageSize).subscribe(data => {
      
      this.cantidad = data.totalElements;
      this.dataSource = new MatTableDataSource(data.content);
      this.dataSource.sort = this.sort;
      
    });
  }




}
