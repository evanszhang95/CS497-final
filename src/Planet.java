import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import org.omg.CORBA.portable.InputStream;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import static com.jogamp.opengl.GL.*; // GL constants
import static com.jogamp.opengl.GL2.*; // GL2 constants

public class Planet {

    private float yearSpeed;
    private float yearAngle;
    private float daySpeed;
    private float dayAngle;
    private float dist;
    private float radius;
    private GL2 gl;
    private GLU glu;

    private double red;
    private double green;
    private double blue;

    private final int slices = 16;
    private final int stacks = 16;

    private float speed = 0;
    private Texture texture;
    public Planet(GL2 gl, GLU glu, Texture planetTexture, float speed, float distance, float radius) {
        this.gl = gl;
        this.glu = glu;
        this.texture = planetTexture;

        this.speed = speed;
        this.dist = distance;
        this.radius = radius;
    }
//    public Planet(float ys, float ds, float d, float r, String text, GLU g,
//                  GL2 g2) {
//        yearSpeed = ys;
//        yearAngle = 0;
//        daySpeed = ds;
//        dayAngle = 0;
//        dist = d;
//        radius = r;
//        try {
//            texture = TextureIO.newTexture(new File(text), true);
//        } catch (IOException exc) {
//            exc.printStackTrace();
//            System.exit(1);
//        }
//        glu = g;
//        gl = g2;
//
//        red = Math.random();
//        green = Math.random();
//        blue = Math.random();
//    }

    public void drawPlanet() {
        // Apply texture.
        texture.enable(gl);
        texture.bind(gl);

        gl.glPushMatrix();
        {
            gl.glRotatef(yearAngle, 0.0f, -1.0f, 0.0f); // Rotate around the sun
            gl.glTranslatef(dist, 0.0f, 0.0f); // Move away from sun
            gl.glRotatef(dayAngle, 0.0f, -1.0f, 0.0f); // Rotate planet
            gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f); // Correctly orient planet
            // Draw planet
            GLUquadric quad = glu.gluNewQuadric();
            glu.gluQuadricTexture(quad, true);
            glu.gluQuadricDrawStyle(quad, GLU.GLU_FILL);
            glu.gluQuadricNormals(quad, GLU.GLU_FLAT);
            glu.gluQuadricOrientation(quad, GLU.GLU_OUTSIDE);

            glu.gluSphere(quad, radius, slices, stacks);
            glu.gluDeleteQuadric(quad);

        }
        gl.glPopMatrix();

        texture.disable(gl);
    }

//    public void drawPath() {
//        double inc = Math.PI / 24;
//        double max = 2 * Math.PI;
//        gl.glEnable(GL_LINE_SMOOTH);
//        gl.glLineWidth(2f);
//
//        gl.glBegin(GL_LINE_LOOP);
//        gl.glColor3d(red, green, blue);
//        for (double d = 0; d < max; d += inc) {
//            gl.glVertex3d(Math.sin(d) * dist, 0, Math.cos(d) * dist);
//        }
//        gl.glColor3f(1.0f, 1.0f, 1.0f);
//        gl.glEnd();
//
//    }

    public void update() {
        yearAngle += yearSpeed;
        dayAngle += daySpeed;
    }
}
