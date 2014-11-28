package com.requiem.utilities;

import com.requiem.managers.SettingsManager;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Trent on 11/8/2014.
 */
public class GraphicsUtils {
    public static FloatBuffer flippedFloatBuffer(float... floats) {
        FloatBuffer returnBuffer = BufferUtils.createFloatBuffer(floats.length);
        returnBuffer.put(floats);
        returnBuffer.flip();

        return returnBuffer;
    }

    public static ByteBuffer flippedByteBuffer(byte... bytes) {
        return (ByteBuffer) ByteBuffer.allocateDirect(bytes.length).order(ByteOrder.nativeOrder()).put(bytes).flip();
    }

    public static float[] rowMajorToColumnMajor(float[] oldMatrix) {
        if (oldMatrix.length != 16) {
            throw new ArrayIndexOutOfBoundsException("The rowMajorToColumnMajor (float) conversion array length size is not 16.");
        }
        float[] newMatrix = new float[16];

        int newIndex = 0;
        for (int column = 0; column < 4; column++) {
            for (int row = 0; row < 4; row++) {
                int oldIndex = 4 * row + column;
                newMatrix[newIndex] = oldMatrix[oldIndex];

                newIndex++;
            }
        }

        return newMatrix;
    }

    public static double[] rowMajorToColumnMajor(double[] oldMatrix) {
        if (oldMatrix.length != 16) {
            throw new ArrayIndexOutOfBoundsException("The rowMajorToColumnMajor (double) conversion array length size is not 16.");
        }
        double[] newMatrix = new double[16];

        int newIndex = 0;
        for (int column = 0; column < 4; column++) {
            for (int row = 0; row < 4; row++) {
                int oldIndex = 4 * row + column;
                newMatrix[newIndex] = oldMatrix[oldIndex];

                newIndex++;
            }
        }

        return newMatrix;
    }

    public static void beingOrtho() {
        int[] resolution = SettingsManager.getResolution();
        glPushMatrix(); // Duplicate top MODELVIEW matrix
        glLoadIdentity(); // Reset new top GL_MODELVIEW to identity

        glMatrixMode(GL_PROJECTION); // Switch the matrix stack we are operating on to PROJECTION
        glPushMatrix(); // Duplicate top PROJECTION matrix
        glLoadIdentity(); // Reset (new) top PROJECTION to identity

        glOrtho(0, resolution[0], resolution[1], 0, 1, -1); // Set up ortho projection matrix

        glMatrixMode(GL_MODELVIEW);
    }

    public static void endOrtho() {
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
    }
}
