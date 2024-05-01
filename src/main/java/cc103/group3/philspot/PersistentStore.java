package cc103.group3.philspot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PersistentStore {
    private static final String DATA_DIR_NAME = "Philspot";
    private static final String DATA_FILE_NAME = "store.txt";

    public static void saveData(String data) {
        Path dataDir = getDataDir();
        createDataDirectory(dataDir);

        Path dataFile = dataDir.resolve(DATA_FILE_NAME);
        try (BufferedWriter writer = Files.newBufferedWriter(dataFile)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Failed to save store: " + e);
        }
    }

    public static Properties loadData() {
        Properties properties = new Properties();
        Path dataFile = getDataFile();

        if (!Files.exists(dataFile))
            saveData("");

        if (Files.exists(dataFile)) {
            try (InputStream stream = Files.newInputStream(dataFile)) {
                properties.load(stream);
            } catch (IOException e) {
                System.out.println("Failed to load store: " + e);
            }
        }

        return properties;
    }

    private static Path getDataDir() {
        String os = System.getProperty("os.name").toLowerCase();

        // Linux
        if (os.contains("linux")) {
            return Paths.get(System.getProperty("user.home"), "." + DATA_DIR_NAME);
        }

        // Windows
        return Paths.get(System.getenv("APPDATA"), DATA_DIR_NAME);
    }

    private static void createDataDirectory(Path dataDir) {
        if (!Files.exists(dataDir)) {
            try {
                Files.createDirectories(dataDir);
            } catch (IOException e) {
                System.out.println("Failed to create store dir: " + e);
            }
        }
    }

    private static Path getDataFile() {
        return getDataDir().resolve(DATA_FILE_NAME);
    }
}