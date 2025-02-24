package dev.joseluisgs

import dev.joseluisgs.model.Producto
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

    //Obtener los proveedores que tenga mas de 4 productos.
    println()
    println("Proveedores con más de 4 productos:")
    val proveedoresConMasProductos = productos.groupBy { it.idProveedor }.filter { it.value.count() > 4 }
    proveedoresConMasProductos.forEach {
        println("Proveedor: ${it.key} - Número de productos: ${it.value.count()}")
    }

    //Obtener el proveedor con la mayor cantidad de productos.
    println()
    println("Proveedor con mayor cantidad de productos:")
    val proveedorConMasProductos = productos.groupBy { it.idProveedor }.maxByOrNull { it.value.count() }
    println("Proveedor: ${proveedorConMasProductos?.key} - Número de productos: ${proveedorConMasProductos?.value?.count()}")

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
    productosPorCategoria.forEach { println("Categoría: ${it.key} - Número de productos: ${it.value}") }

    //Categoría más cara.
    println()
    println("Categoría más cara:")
    val categoriaMasCara = productosPorCategoria.maxByOrNull { it.value }?.key
    println(categoriaMasCara)

    //Categoría cuya suma de precios sea mayor.
    println()
    println("Categoría cuya suma de precios sea mayor:")
    val categoriaConPrecioMasAlto =
        productos.groupBy { it.idCategoría }.mapValues { it.value.map { it.precioUnidad }.sum() }
            .maxByOrNull { it.value }
    println(
        "Categoría: ${categoriaConPrecioMasAlto?.key} - Suma de precios: ${
            categoriaConPrecioMasAlto?.value?.roundTo(
                2
            )
        }"
    )

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

    // Probamos la cache al
    println()
    println("Probamos la cache")
    val productos2Id = service.getAll().map { it.id }
    println("Productos por id: $productos2Id")
    // Vamos a hacer una serie de consultas por id obtenidos aleatorios
    repeat(100) {
        val id = productos2Id.random()
        service.getById(id)
    }

    // Probamos el crud

    // Obtenemos un producto que no existe
    try {
        service.getById(9999)
    } catch (e: Exception) {
        println("ERROR ${e.message}")
    }

    // Obtenemos un producto que si existe
    val producto = service.getById(1)
    println("Producto: $producto")

    // Actualizamos un producto
    val nuevoProductoActual = producto.copy(nombre = "Nuevo Producto", precioUnidad = 100.0)
    try {
        val res = service.update(1, nuevoProductoActual)
        println("Producto actualizado: $res")
    } catch (e: Exception) {
        println("ERROR ${e.message}")
    }
    service.update(1, nuevoProductoActual)
    println("Producto actualizado: $producto")

    // Guardamos un producto nuevo
    val nuevoProducto = producto.copy(id = Producto.NEW_ID, nombre = "Nuevo Producto", precioUnidad = 100.0)
    try {
        val res = service.save(nuevoProducto)
        println("Producto guardado: $res")
    } catch (e: Exception) {
        println("ERROR ${e.message}")
    }

    // Borramos un producto
    try {
        val res = service.delete(1)
        println("Producto borrado: $res")
    } catch (e: Exception) {
        println("ERROR ${e.message}")
    }


}