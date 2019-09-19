package engine.loaders;

import engine.ResourceManager;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class WavefrontLoader {
    private static BufferedReader reader;

    public WavefrontLoader() {}

    protected static WavefrontObject read(String fileName) throws IOException {
        List<Integer>  indexes  = new ArrayList<>();
        List<Vector3f> normals  = new ArrayList<>();
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> uvs      = new ArrayList<>();

        int[] indiceArray;
        float[] normalArray = null, verticeArray = null, uvArray = null;

        reader = new BufferedReader(new FileReader(ResourceManager.get("/models/", fileName, ".obj")));

        String line;
        boolean faces = false;
        while((line = reader.readLine()) != null) {
            // Vertex
            if(line.startsWith("v ")) {
                vertices.add(WavefrontLoader.getVertex(line));
            }
            // Vertex texture
            else if(line.startsWith("vt ")) {
                uvs.add(WavefrontLoader.getUVCoordinates(line));
            }
            // Vertex normals
            else if(line.startsWith("vn ")) {
                normals.add(WavefrontLoader.getNormals(line));
            }
            // First face
            else if(line.startsWith("f ") && !faces) {
                faces = true;

                uvArray     = new float[vertices.size() * 2];
                normalArray = new float[vertices.size() * 3];

                processFace(line, normalArray, uvArray, indexes, normals, uvs, true);
            }
            // Faces
            else if(line.startsWith("f ")) {
                processFace(line, normalArray, uvArray, indexes, normals, uvs, true);
            }
        }

        reader.close();
        indiceArray  = new int[indexes.size()];
        verticeArray = new float[vertices.size()*3];

        for(int pos = 0; pos < indexes.size(); pos++) {
            indiceArray[pos] = indexes.get(pos);
        }


        int vertexIndex = 0;
        for(Vector3f vertex : vertices) {
            verticeArray[vertexIndex++] = vertex.getX();
            verticeArray[vertexIndex++] = vertex.getY();
            verticeArray[vertexIndex++] = vertex.getZ();
        }

        return new WavefrontObject(indiceArray, verticeArray, normalArray, uvArray);
    }

    private static Vector3f getVertex(String line) {
        String[] splitted = line.split(" ");

        if(splitted.length != 4) throw new IllegalArgumentException("Incorrect vertex line.");
        return new Vector3f(Float.parseFloat(splitted[X]), Float.parseFloat(splitted[Y]), Float.parseFloat(splitted[Z]));
    }

    private static Vector2f getUVCoordinates(String line) {
        String[] splitted = line.split(" ");

        if(splitted.length != 3) throw new IllegalArgumentException("Incorrect vertex texture.");
        return new Vector2f(Float.parseFloat(splitted[U]), Float.parseFloat(splitted[V]));
    }

    private static Vector3f getNormals(String line) {
        String[] splitted = line.split(" ");

        if(splitted.length != 4) throw new IllegalArgumentException("Incorrect vertex normals.");
        return new Vector3f(Float.parseFloat(splitted[X]), Float.parseFloat(splitted[Y]), Float.parseFloat(splitted[Z]));
    }

    private static void processFace(String line, float[] normalArray, float[] uvArray,
                                    List<Integer> indexes, List<Vector3f> normals, List<Vector2f> uvs, boolean flipY) {

        String[] vertices = line.split(" ");
        if(vertices.length != 4) throw new IllegalArgumentException("Incorrect face vertices.");

        for(int index = 1; index < 4; index++) {
            String[] vertex = vertices[index].split("/");
            if(vertex.length != 3) throw new IllegalArgumentException("Incorrect vertex information.");

            int coordinate = Integer.parseInt(vertex[POS]) - 1;
            int uv         = Integer.parseInt(vertex[UV])  - 1;
            int normal     = Integer.parseInt(vertex[NOR]) - 1;

            indexes.add(coordinate);

            uvArray[coordinate*2]     =  uvs.get(uv).getX(); // U
            uvArray[coordinate*2 + 1] = (flipY ? (1 - uvs.get(uv).getY()) : uvs.get(uv).getY()); // V

            normalArray[coordinate*3]     = normals.get(normal).getX();
            normalArray[coordinate*3 + 1] = normals.get(normal).getY();
            normalArray[coordinate*3 + 2] = normals.get(normal).getZ();
        }
    }

    private static final int X = 1, Y = 2, Z = 3;
    private static final int U = 1, V = 2;
    private static final int POS = 0, UV = 1, NOR = 2;

}
