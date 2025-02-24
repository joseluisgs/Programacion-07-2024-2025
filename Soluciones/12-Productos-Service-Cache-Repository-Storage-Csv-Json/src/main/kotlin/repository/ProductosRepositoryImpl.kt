package dev.joseluisgs.repository

import dev.joseluisgs.model.Producto
import org.lighthousegames.logging.logging

/**
 * Implementación del repositorio de productos
 * Esta clase implementa la interfaz [ProductosRepository] para almacenar y gestionar productos en memoria.
 */
class ProductosRepositoryImpl : ProductosRepository {
    private val logger = logging()
    private val productos = mutableMapOf<Int, Producto>()

    init {
        logger.debug { "Inicializando repositorio de productos" }
    }

    /**
     * Obtiene todos los productos
     * @return Lista de productos
     */
    override fun getAll(): List<Producto> {
        logger.debug { "Obteniendo todos los productos" }
        return productos.values.toList()
    }

    /**
     * Obtiene un producto por su id
     * @param id Identificador del producto
     * @return Producto o null si no se encuentra
     */
    override fun getById(id: Int): Producto? {
        logger.debug { "Obteniendo producto con id: $id" }
        return productos[id]
    }

    /**
     * Guarda un producto
     * @param item Producto a guardar
     * @return Producto guardado
     */
    override fun save(item: Producto): Producto {
        logger.debug { "Guardando producto: $item" }
        // Generamos un id
        val id = productos.keys.maxOrNull()?.plus(1) ?: 1
        productos[id] = item.copy(id = id)
        return productos[id]!!
    }

    /**
     * Actualiza un producto
     * @param id Identificador del producto
     * @param t Producto a actualizar
     * @return Producto actualizado o null si no se encuentra
     */
    override fun update(id: Int, item: Producto): Producto? {
        logger.debug { "Actualizando producto con id: $id" }
        return productos.put(id, item)
    }

    /**
     * Borra un producto
     * @param id Identificador del producto
     * @return Producto borrado o null si no se encuentra
     */
    override fun delete(id: Int): Producto? {
        logger.debug { "Borrando producto con id: $id" }
        return productos.remove(id)
    }
}