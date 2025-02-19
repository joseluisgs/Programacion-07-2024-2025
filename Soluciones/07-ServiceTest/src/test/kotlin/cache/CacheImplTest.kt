package cache

import dev.joseluisgs.cache.CacheImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class CacheImplTest {
    
    private lateinit var lruCache: CacheImpl<String, String>
    private lateinit var fifoCache: CacheImpl<String, String>
    
    @BeforeEach
    fun setUp() {
        // Inicializa las cachés con una capacidad de 3 para probar
        lruCache = CacheImpl(3, CacheImpl.CacheType.LRU)
        fifoCache = CacheImpl(3, CacheImpl.CacheType.FIFO)
    }
    
    @Test
    fun `test LRU cache behavior`() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        lruCache.put("key3", "value3")
        
        // Accede a "key1" para que sea la más reciente
        assertNotNull(lruCache.get("key1"))
        
        // Añade un nuevo elemento, lo que debería eliminar "key2" (la menos utilizada)
        lruCache.put("key4", "value4")
        
        assertNull(lruCache.get("key2")) // "key2" debería haber sido eliminado
        assertNotNull(lruCache.get("key1")) // "key1" debería estar presente
        assertNotNull(lruCache.get("key3")) // "key3" debería estar presente
        assertNotNull(lruCache.get("key4")) // "key4" debería estar presente
    }
    
    @Test
    fun `test FIFO cache behavior`() {
        fifoCache.put("key1", "value1")
        fifoCache.put("key2", "value2")
        fifoCache.put("key3", "value3")
        
        // Añade un nuevo elemento, lo que debería eliminar "key1" (el primero en entrar)
        fifoCache.put("key4", "value4")
        
        assertNull(fifoCache.get("key1")) // "key1" debería haber sido eliminado
        assertNotNull(fifoCache.get("key2")) // "key2" debería estar presente
        assertNotNull(fifoCache.get("key3")) // "key3" debería estar presente
        assertNotNull(fifoCache.get("key4")) // "key4" debería estar presente
    }
    
    @Test
    @DisplayName("test cache clear")
    fun testCacheClear() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        
        lruCache.clear()
        
        assertEquals(0, lruCache.size())
        assertNull(lruCache.get("key1"))
        assertNull(lruCache.get("key2"))
    }
    
    @Test
    fun `test cache size`() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        
        assertEquals(2, lruCache.size())
        
        lruCache.put("key3", "value3")
        lruCache.put("key4", "value4") // Esto debería eliminar "key1" en LRU
        
        assertEquals(3, lruCache.size()) // Tamaño máximo es 3
    }
    
    @Test
    fun `test cache eviction policy for LRU`() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        lruCache.put("key3", "value3")
        
        // Accede a "key1" para que sea la más reciente
        assertNotNull(lruCache.get("key1"))
        
        // Añade un nuevo elemento, lo que debería eliminar "key2" (la menos utilizada)
        lruCache.put("key4", "value4")
        
        assertNull(lruCache.get("key2")) // "key2" debería haber sido eliminado
        assertNotNull(lruCache.get("key1")) // "key1" debería estar presente
        assertNotNull(lruCache.get("key3")) // "key3" debería estar presente
        assertNotNull(lruCache.get("key4")) // "key4" debería estar presente
    }
    
    @Test
    fun `test cache eviction policy for FIFO`() {
        fifoCache.put("key1", "value1")
        fifoCache.put("key2", "value2")
        fifoCache.put("key3", "value3")
        
        // Añade un nuevo elemento, lo que debería eliminar "key1" (el primero en entrar)
        fifoCache.put("key4", "value4")
        
        assertNull(fifoCache.get("key1")) // "key1" debería haber sido eliminado
        assertNotNull(fifoCache.get("key2")) // "key2" debería estar presente
        assertNotNull(fifoCache.get("key3")) // "key3" debería estar presente
        assertNotNull(fifoCache.get("key4")) // "key4" debería estar presente
    }
    
    @Test
    fun testClear() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        lruCache.clear()
        assertEquals(0, lruCache.size())
    }
    
    @Test
    fun testSize() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        assertEquals(2, lruCache.size())
    }
    
    @Test
    fun testKeys() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        assertAll(
            "keys",
            { assertTrue(lruCache.keys().contains("key1")) },
            { assertTrue(lruCache.keys().contains("key2")) }
        )
    }
    
    @Test
    fun testValues() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        
        assertAll(
            "values",
            { assertTrue(lruCache.values().contains("value1")) },
            { assertTrue(lruCache.values().contains("value2")) }
        )
        
    }
    
    @Test
    fun testEntries() {
        lruCache.put("key1", "value1")
        lruCache.put("key2", "value2")
        assertAll(
            "entries",
            { assertEquals("value1", lruCache.get("key1")) },
            { assertEquals("value2", lruCache.get("key2")) }
        )
    }
}