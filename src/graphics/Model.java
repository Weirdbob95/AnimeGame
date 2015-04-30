package graphics;

import core.Vec3;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

public class Model {

    private final ArrayList<Vec3> vertices = new ArrayList(); // Vertex Coordinates
    private final ArrayList<Vec3> vertexNormals = new ArrayList(); // Vertex Coordinates Normals
    private final ArrayList<Vec3> vertexTex = new ArrayList(); // Vertex Coordinates Textures
    private final ArrayList<int[]> faces = new ArrayList(); // Array of Faces (vertex sets)
    private final ArrayList<int[]> facestexs = new ArrayList(); // Array of of Faces textures
    private final ArrayList<int[]> facesnorms = new ArrayList(); // Array of Faces normals

    private int objectlist;
    private int polyCount = 0;

    //// Statisitcs for drawing ////
    public double toppoint = 0;		// y+
    public double bottompoint = 0;	// y-
    public double leftpoint = 0;		// x-
    public double rightpoint = 0;	// x+
    public double farpoint = 0;		// z-
    public double nearpoint = 0;		// z+

    public Model(String name) {
        try {
            loadobject(new BufferedReader(new FileReader(name)));
            translate(new Vec3(0, 0, -farpoint));
            opengldrawtolist();
            polyCount = faces.size();
            cleanup();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not load model: " + name);
        }
    }

    private void cleanup() {
        vertices.clear();
        vertexNormals.clear();
        vertexTex.clear();
        faces.clear();
        facestexs.clear();
        facesnorms.clear();
    }

    private void loadobject(BufferedReader br) {
        int linecounter = 0;
        try {

            String newline;
            boolean firstpass = true;

            while (((newline = br.readLine()) != null)) {
                linecounter++;
                newline = newline.trim();
                if (newline.length() > 0) {
                    if (newline.charAt(0) == 'v' && newline.charAt(1) == ' ') {
                        String[] coordstext = newline.split("\\s+");
                        Vec3 coords = new Vec3(Double.valueOf(coordstext[3]), Double.valueOf(coordstext[1]), Double.valueOf(coordstext[2]));
                        //// check for farpoints ////
                        if (firstpass) {
                            rightpoint = coords.x;
                            leftpoint = coords.x;
                            toppoint = coords.y;
                            bottompoint = coords.y;
                            nearpoint = coords.z;
                            farpoint = coords.z;
                            firstpass = false;
                        }
                        if (coords.x > rightpoint) {
                            rightpoint = coords.x;
                        }
                        if (coords.x < leftpoint) {
                            leftpoint = coords.x;
                        }
                        if (coords.y > toppoint) {
                            toppoint = coords.y;
                        }
                        if (coords.y < bottompoint) {
                            bottompoint = coords.y;
                        }
                        if (coords.z > nearpoint) {
                            nearpoint = coords.z;
                        }
                        if (coords.z < farpoint) {
                            farpoint = coords.z;
                        }
                        /////////////////////////////
                        vertices.add(coords);
                    }
                    if (newline.charAt(0) == 'v' && newline.charAt(1) == 't') {
                        String[] coordstext = newline.split("\\s+");
                        Vec3 coords = new Vec3(Double.valueOf(coordstext[3]), Double.valueOf(coordstext[1]), Double.valueOf(coordstext[2]));
                        vertexTex.add(coords);
                    }
                    if (newline.charAt(0) == 'v' && newline.charAt(1) == 'n') {
                        String[] coordstext = newline.split("\\s+");
                        Vec3 coords = new Vec3(Double.valueOf(coordstext[3]), Double.valueOf(coordstext[1]), Double.valueOf(coordstext[2]));
                        vertexNormals.add(coords);
                    }
                    if (newline.charAt(0) == 'f' && newline.charAt(1) == ' ') {
                        String[] coordstext = newline.split("\\s+");
                        int[] v = new int[coordstext.length - 1];
                        int[] vt = new int[coordstext.length - 1];
                        int[] vn = new int[coordstext.length - 1];

                        for (int i = 1; i < coordstext.length; i++) {
                            String fixstring = coordstext[i].replaceAll("//", "/0/");
                            String[] tempstring = fixstring.split("/");
                            v[i - 1] = Integer.valueOf(tempstring[0]) - 1;
                            if (tempstring.length > 1) {
                                vt[i - 1] = Integer.valueOf(tempstring[1]) - 1;
                            } else {
                                vt[i - 1] = -1;
                            }
                            if (tempstring.length > 2) {
                                vn[i - 1] = Integer.valueOf(tempstring[2]) - 1;
                            } else {
                                vn[i - 1] = -1;
                            }
                        }
                        faces.add(v);
                        facestexs.add(vt);
                        facesnorms.add(vn);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Failed to read file: " + br.toString());
        } catch (NumberFormatException e) {
            System.out.println("Malformed OBJ (on line " + linecounter + "): " + br.toString() + "\r \r" + e.getMessage());
        }
    }

    public void opengldraw() {
        glCallList(objectlist);
    }

    private void opengldrawtolist() {
        objectlist = glGenLists(1);
        glNewList(objectlist, GL_COMPILE);

        for (int i = 0; i < faces.size(); i++) {
            int[] tempfaces = faces.get(i);
            int[] tempfacesnorms = facesnorms.get(i);
            int[] tempfacestexs = facestexs.get(i);
            //// Quad Begin Header ////
            int polytype;
            switch (tempfaces.length) {
                case 3:
                    polytype = GL_TRIANGLES;
                    break;
                case 4:
                    polytype = GL_QUADS;
                    break;
                default:
                    polytype = GL_POLYGON;
            }
            glBegin(polytype);
            ////////////////////////////
            for (int j = 0; j < tempfaces.length; j++) {
                if (tempfacesnorms[j] >= 0) {
                    vertexNormals.get(tempfacesnorms[j]).glNormal();
                }

                if (tempfacestexs[j] >= 0) {
                    vertexTex.get(tempfacestexs[j]).glTexCoord();
                }
                vertices.get(tempfaces[j]).glVertex();
            }
            //// Quad End Footer /////
            glEnd();
            ///////////////////////////
        }
        glEndList();
    }

    public int polyCount() {
        return polyCount;
    }

    private void translate(Vec3 amt) {
        for (int i = 0; i < vertices.size(); i++) {
            vertices.set(i, vertices.get(i).add(amt));
        }
    }
}
