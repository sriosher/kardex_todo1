import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material/material.module';
import { ProductosComponent } from './pages/productos/productos.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { CategoriasComponent } from './pages/categorias/categorias.component';
import { VentasComponent } from './pages/ventas/ventas.component';
import { UsuariosComponent } from './pages/usuarios/usuarios.component';
import { ShopComponent } from './pages/shop/shop.component';
import { ProductoDialogoComponent } from './pages/productos/producto-dialogo/producto-dialogo.component';
import { CarritoComponent } from './pages/carrito/carrito.component';


@NgModule({
  declarations: [
    AppComponent,
    ProductosComponent,
    CategoriasComponent,
    VentasComponent,
    UsuariosComponent,
    ShopComponent,
    ProductoDialogoComponent,
    CarritoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],

  exports: [],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
