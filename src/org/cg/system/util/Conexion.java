package org.cg.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestion de conexion a MySQL con patron Singleton.
 * Constructor privado, instancia unica sincronizada.
 * Lee credenciales desde db.properties (classpath o carpeta del proyecto).
 */
public class Conexion {

    private static Conexion instancia;
    private static final String CONFIG_FILE = "/db.properties";

    private final String url;
    private final String user;
    private final String password;

    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error Driver MySQL: " + e.getMessage());
            System.err.println("Asegurate de tener mysql-connector-j en Libraries del proyecto.");
        }

        Properties config = new Properties();
        InputStream in = null;

        // 1) Classpath (src/db.properties -> build/classes/db.properties)
        in = getClass().getResourceAsStream(CONFIG_FILE);

        // 2) Fallback: archivo en el proyecto (util si NetBeans no copio a classes)
        if (in == null) {
            String[] posibles = {
                "src/db.properties",
                "db.properties",
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "db.properties",
                System.getProperty("user.dir") + File.separator + "db.properties"
            };
            for (String ruta : posibles) {
                File f = new File(ruta);
                if (f.exists() && f.isFile()) {
                    try {
                        in = new FileInputStream(f);
                        System.out.println("[Conexion] Leyendo config desde: " + f.getAbsolutePath());
                        break;
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        if (in == null) {
            throw new IllegalStateException(
                    "No se encontro db.properties.\n"
                    + "  1. Copia db.properties.example como src/db.properties\n"
                    + "  2. Edita usuario y password de MySQL\n"
                    + "  3. Clean and Build el proyecto en NetBeans");
        }

        try (InputStream stream = in) {
            config.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Error al leer db.properties", e);
        }

        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");
        if (url == null || user == null || password == null) {
            throw new IllegalStateException(
                    "Faltan propiedades (db.url, db.user, db.password) en db.properties");
        }
    }

    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void cerrar(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }
}
