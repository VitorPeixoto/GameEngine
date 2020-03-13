package engine;

public class ResourceManager {

    public static String get(String fileName) {
        return ResourceManager.class.getResource(fileName).getPath();
    }

    public static String get(String parent, String fileName) {
        return ResourceManager.class.getResource(parent + fileName).getPath();
    }

    public static String get(String parent, String fileName, String extension) {
        return ResourceManager.class.getResource(parent + fileName + extension).getPath();
    }
}
