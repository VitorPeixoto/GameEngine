package engine.loaders;

import engine.ResourceManager;
import engine.renderer.Material;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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
                vertices.add(WavefrontLoader.getVector3f(line));
            }
            // Vertex texture
            else if(line.startsWith("vt ")) {
                uvs.add(WavefrontLoader.getVector2f(line));
            }
            // Vertex normals
            else if(line.startsWith("vn ")) {
                normals.add(WavefrontLoader.getVector3f(line));
            }
            // First face
            else if(line.startsWith("f ") && !faces) {
                faces = true;

                normalArray = new float[vertices.size() * 3];

                if(uvs.size() > 0) {
                    uvArray     = new float[vertices.size() * 2];
                    processFace(line, normalArray, uvArray, indexes, normals, uvs, true);
                } else {
                    processFace(line, normalArray, indexes, normals);
                }
            }
            // Faces
            else if(line.startsWith("f ")) {
                if(uvs.size() > 0)
                    processFace(line, normalArray, uvArray, indexes, normals, uvs, true);
                else
                    processFace(line, normalArray, indexes, normals);
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

    protected static Material readMaterial(String fileName) throws IOException {
        reader = new BufferedReader(new FileReader(ResourceManager.get("/models/", fileName, ".mtl")));

        Vector3f ambientColor = null, diffuseColor = null, specularColor = null, emissiveColor = null;
        float specularExponent = 0, opticalDensity = 0, dissolveFactor = 0;
        String textureName = null;

        String line;
        while((line = reader.readLine()) != null) {
            // Specular exponent
            if(line.startsWith("Ns ")) {
                specularExponent = getFloat(line);
            }
            // Ambient Color
            else if(line.startsWith("Ka ")) {
                ambientColor = getVector3f(line);
            }
            // Specular Color
            else if(line.startsWith("Ks ")) {
                specularColor = getVector3f(line);
            }
            // Emission Color
            else if(line.startsWith("Ke ")) {
                emissiveColor = getVector3f(line);
            }
            // Diffuse Color
            else if(line.startsWith("Kd ")) {
                diffuseColor = getVector3f(line);
            }
            // Optical density
            else if(line.startsWith("Ni ")) {
                opticalDensity = getFloat(line);
            }
            // Dissolve Factor
            else if(line.startsWith("d ")) {
                dissolveFactor = getFloat(line);
            }
            // Mapped Texture
            else if(line.startsWith("map_Kd ")) {
                textureName = getString(line);
            }
        }

        reader.close();

        return new Material(ambientColor, diffuseColor, specularColor, emissiveColor, specularExponent, opticalDensity, dissolveFactor, textureName);
    }

    private static Vector3f getVector3f(String line) {
        String[] splitted = line.split(" ");

        if(splitted.length != 4) throw new IllegalArgumentException("Incorrect line format (Vector3f).");
        return new Vector3f(Float.parseFloat(splitted[X]), Float.parseFloat(splitted[Y]), Float.parseFloat(splitted[Z]));
    }

    private static Vector2f getVector2f(String line) {
        String[] splitted = line.split(" ");

        if(splitted.length != 3) throw new IllegalArgumentException("Incorrect line format (Vector2f).");
        return new Vector2f(Float.parseFloat(splitted[X]), Float.parseFloat(splitted[Y]));
    }

    private static float getFloat(String line) {
        String[] splitted = line.split(" ");

        if(splitted.length != 2) throw new IllegalArgumentException("Incorrect line format (float).");
        return Float.parseFloat(splitted[X]);
    }

    private static String getString(String line) {
        String[] splitted = line.split(" ");

        if(splitted.length != 2) throw new IllegalArgumentException("Incorrect line format (String).");
        return splitted[X];
    }

    protected static IntBuffer intToIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    protected static FloatBuffer floatToFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static void processFace(String line, float[] normalArray, float[] uvArray,
                                    List<Integer> indexes, List<Vector3f> normals, List<Vector2f> uvs, boolean flipY) {

        String[] vertices = line.split(" ");
        if (vertices.length != 4) throw new IllegalArgumentException("Incorrect face vertices.");

        for (int index = 1; index < 4; index++) {
            String[] vertex = vertices[index].split("/");
            if (vertex.length != 3) throw new IllegalArgumentException("Incorrect vertex information.");

            int coordinate = Integer.parseInt(vertex[POS]) - 1;
            int normal = Integer.parseInt(vertex[NOR]) - 1;
            int uv = Integer.parseInt(vertex[UV]) - 1;

            indexes.add(coordinate);

            uvArray[coordinate * 2] = uvs.get(uv).getX(); // U
            uvArray[coordinate * 2 + 1] = (flipY ? (1 - uvs.get(uv).getY()) : uvs.get(uv).getY()); // V

            normalArray[coordinate * 3] = normals.get(normal).getX();
            normalArray[coordinate * 3 + 1] = normals.get(normal).getY();
            normalArray[coordinate * 3 + 2] = normals.get(normal).getZ();
        }
    }

    private static void processFace(String line, float[] normalArray,
                                    List<Integer> indexes, List<Vector3f> normals) {

        String[] vertices = line.split(" ");
        if(vertices.length != 4) throw new IllegalArgumentException("Incorrect face vertices.");

        for(int index = 1; index < 4; index++) {
            String[] vertex = vertices[index].split("/");
            if(vertex.length != 3) throw new IllegalArgumentException("Incorrect vertex information.");

            int coordinate = Integer.parseInt(vertex[POS]) - 1;
            int normal     = Integer.parseInt(vertex[NOR]) - 1;

            indexes.add(coordinate);

            normalArray[coordinate*3]     = normals.get(normal).getX();
            normalArray[coordinate*3 + 1] = normals.get(normal).getY();
            normalArray[coordinate*3 + 2] = normals.get(normal).getZ();
        }
    }

    private static final int X = 1, Y = 2, Z = 3;
    private static final int POS = 0, UV = 1, NOR = 2;

}
