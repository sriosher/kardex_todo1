import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes } from '@angular/router';
import { ProductosComponent } from './pages/productos/productos.component';
import { RouterModule } from "@angular/router";
import { CategoriasComponent } from './pages/categorias/categorias.component';
import { VentasComponent } from './pages/ventas/ventas.component';
import { UsuariosComponent } from './pages/usuarios/usuarios.component';
import { ShopComponent } from './pages/shop/shop.component';

const routes: Routes = [
  {path: '', component: ShopComponent},
  {path: 'productos', component: ProductosComponent},
  {path: 'categorias', component: CategoriasComponent},
  {path: 'ventas', component: VentasComponent},
  {path: 'empleados', component: UsuariosComponent},
  {path: 'shop', component: ShopComponent},
];


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
