package dev.joseluisgs

import dev.joseluisgs.repository.ProductosRepositoryImpl
import dev.joseluisgs.service.ProductoServiceImpl
import dev.joseluisgs.storage.ProductosStorageCsv
import dev.joseluisgs.utils.roundTo

fun main() {
    println("Hola Productos")

    // Creamos el servicio de productos inyectando sus dependencias: repositorio y almacenamiento
    val service = ProductoServiceImpl(
        storage = ProductosStorageCsv(),
        repository = ProductosRepositoryImpl()
    )

    // Importar los productos desde un CSV
    service.importFromFile("data/products.csv")

    // Obtener todos los productos
    val productos = service.getAll()

    // Todos los productos
    println()
    println("Todos los productos:")
    productos.forEach { println(it) }

    //Nombre de los productos
    println()
    println("Nombre de los productos:")
    productos.forEach { println(it.nombre) }

    //Nombre de los productos cuyo stock es menor que 10
    println()
    println("Nombre de los productos cuyo stock es menor que 10:")
    productos.filter { it.unidadesEnStock < 10 }.forEach { println(it.nombre) }

    //Nombre de los productos donde el stock sea menor a 5 ordenador por unidades de stock ascendente
    println()
    println("Nombre de los productos donde el stock sea menor a 5 ordenador por unidades de stock ascendente:")
    productos.filter { it.unidadesEnStock < 5 }.sortedBy { it.unidadesEnStock }.forEach { println(it.nombre) }

    //Saca el número de proveedores existentes
    println()
    println("Número de proveedores existentes:")
    println(productos.map { it.idProveedor }.distinct().count())

    //Obtener por cada producto, el numero de existencias.
    println()
    println("Número de existencias por producto:")
    productos.forEach { println("${it.nombre} - ${it.unidadesEnStock}") }

    //Por cada proveedor, obtener el número de productos
    println()
    println("Número de productos por proveedor:")
    val proveedorProductos = productos.groupBy { it.idProveedor }.mapValues { it.value.count() }
    proveedorProductos.forEach { println("Proveedor: ${it.key} - Número de productos: ${it.value}") }

    //Por cada proveedor, obtener el número de productos ordenados por número de productos descendente.
    println()
    println("Número de productos por proveedor ordenados por número de productos descendente:")
    val proveedorProductosOrdenados =
        productos.groupBy { it.idProveedor }.mapValues { it.value.count() }.toList().sortedByDescending { it.second }
    proveedorProductosOrdenados.forEach { println("Proveedor: ${it.first} - Número de productos: ${it.second}") }

    //Por cada proveedor obtener la media del precio.
    println()
    println("Media de precio por proveedor:")
    val proveedorPrecioMedio =
        productos.groupBy { it.idProveedor }.mapValues { it.value.map { it.precioUnidad }.average().roundTo(2) }
    proveedorPrecioMedio.forEach { println("Proveedor: ${it.key} - Media de precio: ${it.value}") }

    //Obtener el producto más caro.
    println()
    println("Producto más caro:")
    println(productos.maxByOrNull { it.precioUnidad })

    //Obtener los proveedores que tenga mas de 5 productos.
    println()
    println("Proveedores con más de 5 productos:")
    val proveedoresConMasProductos = productos.groupBy { it.idProveedor }.filter { it.value.count() > 5 }
    proveedoresConMasProductos.forEach { println("Proveedor: ${it.key} - Número de productos: ${it.value.count()}") }

    //Obtener el proveedor con la mayor cantidad de productos.
    println()
    println("Proveedor con mayor cantidad de productos:")
    println(proveedoresConMasProductos.maxByOrNull { it.value.count() })

    //Obtener los proveedores cuya suma de precios supere 100.
    println()
    println("Proveedores cuya suma de precios supere 100:")
    val proveedoresConPrecioSuperiorA100 =
        productos.groupBy { it.idProveedor }.filter { it.value.map { it.precioUnidad }.sum() > 100 }
    proveedoresConPrecioSuperiorA100.forEach {
        println(
            "Proveedor: ${it.key} - Suma de precios: ${
                it.value.map { it.precioUnidad }.sum().roundTo(2)
            }"
        )
    }

    //Categorías y número de productos por categoría.
    println()
    println("Número de productos por categoría:")
    val productosPorCategoria = productos.groupBy { it.idCategoría }.mapValues { it.value.count() }

    //Categoría más cara.
    println()
    println("Categoría más cara:")
    val categoriaMasCara = productosPorCategoria.maxByOrNull { it.value }?.key
    println(categoriaMasCara)

    //Precio máximo, mínimo medio y cantidad por categoría.
    println()
    println("Precio máximo, mínimo medio y cantidad por categoría:")
    val precioMaximoMinimoMedioPorCategoria = productos.groupBy { it.idCategoría }.mapValues {
        val precioMaximo = it.value.maxOf { it.precioUnidad }
        val precioMinimo = it.value.minOf { it.precioUnidad }
        val precioMedio = it.value.map { it.precioUnidad }.average().roundTo(2)
        Triple(precioMaximo, precioMinimo, precioMedio)
    }
    precioMaximoMinimoMedioPorCategoria.forEach {
        println(
            "Categoría: ${it.key} - Precio máximo: ${it.value.first} - Precio mínimo: ${it.value.second} - Precio medio: ${it.value.third}"
        )
    }

    service.exportToFile("data/products_back.csv")
}