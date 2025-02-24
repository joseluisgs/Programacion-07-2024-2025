package config

import dev.joseluisgs.config.Config
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.pathString

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Para que se cree una instancia por clase
class ConfigTest {
    
    @AfterAll
    fun cleanUp() {
        // Borramos los directorios de datos y backup
        val dataDir = Path.of(Config.configProperties.dataDir)
        val backupDir = Path.of(Config.configProperties.backupDir)
        
        Files.deleteIfExists(dataDir)
        Files.deleteIfExists(backupDir)
    }
    
    
    @Test
    @DisplayName("Config deben ser leídas correctamente")
    fun loadConfigFromResources() {
        val configProperties = Config.configProperties
        
        val expectedDataDir = Path.of(System.getProperty("user.dir"), "testData").pathString
        val expectedBackupDir = Path.of(System.getProperty("user.dir"), "testBackup").pathString
        
        assertEquals(expectedDataDir, configProperties.dataDir)
        assertEquals(expectedBackupDir, configProperties.backupDir)
        
        assertTrue(Files.exists(Path.of(configProperties.dataDir)), "Data directory should exist")
        assertTrue(Files.exists(Path.of(configProperties.backupDir)), "Backup directory should exist")
        
    }
    
    @Test
    @DisplayName("Config deben ser leídas correctamente con valores por defecto")
    fun loadDefaultProperties() {
        // Creamos manualmente una instancia con valores por defecto
        val configProperties = Config.ConfigProperties()
        
        // Usamos las rutas relativas "data" y "backup" como valores esperados directamente
        val expectedDataDir = "data"
        val expectedBackupDir = "backup"
        
        assertEquals(expectedDataDir, configProperties.dataDir)
        assertEquals(expectedBackupDir, configProperties.backupDir)
        
        val actualDataDirPath = Path.of(System.getProperty("user.dir"), configProperties.dataDir)
        val actualBackupDirPath = Path.of(System.getProperty("user.dir"), configProperties.backupDir)
        
    }
}