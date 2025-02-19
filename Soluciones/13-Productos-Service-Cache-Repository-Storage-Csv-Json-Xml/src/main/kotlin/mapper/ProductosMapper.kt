package dev.joseluisgs.mapper

import dev.joseluisgs.dto.ProductoDto
import dev.joseluisgs.model.Producto

fun Producto.toDto(): ProductoDto {
    return ProductoDto(
        id = this.id,
        nombre = this.nombre,
        idProveedor = this.idProveedor,
        idCategoría = this.idCategoría,
        cantidadPorUnidad = this.cantidadPorUnidad,
        precioUnidad = this.precioUnidad,
        unidadesEnStock = this.unidadesEnStock,
        unidadesEnPedido = this.unidadesEnPedido,
        nivelDeReabastecimiento = this.nivelDeReabastecimiento,
        descontinuado = if (this.descontinuado) 1 else 0
    )
}

fun ProductoDto.toModel(): Producto {
    return Producto(
        id = this.id,
        nombre = this.nombre,
        idProveedor = this.idProveedor,
        idCategoría = this.idCategoría,
        cantidadPorUnidad = this.cantidadPorUnidad,
        precioUnidad = this.precioUnidad,
        unidadesEnStock = this.unidadesEnStock,
        unidadesEnPedido = this.unidadesEnPedido,
        nivelDeReabastecimiento = this.nivelDeReabastecimiento,
        descontinuado = this.descontinuado == 1
    )
}