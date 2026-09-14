package CRUD_Project.logic;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Configuración de la aplicación.
 *
 * <p>La URL base del backend REST se obtiene, por orden de prioridad:</p>
 * <ol>
 *   <li>La propiedad de sistema {@code -Dbase.uri=...}</li>
 *   <li>La clave {@code base.uri} del fichero {@code config.properties} (en el classpath)</li>
 *   <li>Un valor por defecto</li>
 * </ol>
 */
public final class Config {

    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
    private static final String DEFAULT_BASE_URI = "http://localhost:8080/crudserversideexample/webresources";
    private static final String BASE_URI = resolve();

    private Config() {
    }

    private static String resolve() {
        // 1. Propiedad de sistema
        String sys = System.getProperty("base.uri");
        if (sys != null && !sys.trim().isEmpty()) {
            return sys.trim();
        }
        // 2. config.properties en el classpath
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in != null) {
                Properties p = new Properties();
                p.load(in);
                String v = p.getProperty("base.uri");
                if (v != null && !v.trim().isEmpty()) {
                    return v.trim();
                }
            }
        } catch (Exception e) {
            LOGGER.warning("No se pudo leer config.properties; se usa el valor por defecto. " + e.getMessage());
        }
        // 3. Valor por defecto
        return DEFAULT_BASE_URI;
    }

    /** Devuelve la URL base del backend REST (por ejemplo, .../webresources). */
    public static String getBaseUri() {
        return BASE_URI;
    }
}
