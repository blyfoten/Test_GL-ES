package test.cfor.se;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class ThaGLRenderer implements GLSurfaceView.Renderer {
    private int mProgram;
    private int maPositionHandle;
	private FloatBuffer triangleVB;
    private final String vertexShaderCode = 
        "attribute vec4 vPosition; \n" +
        "void main(){              \n" +
        " gl_Position = vPosition; \n" +
        "}                         \n";
    
    private final String fragmentShaderCode = 
        "precision mediump float;  \n" +
        "void main(){              \n" +
        " gl_FragColor = vec4 (0.63671875, 0.76953125, 0.22265625, 1.0); \n" +
        "}                         \n";
    
    private int loadShader(int type, String shaderCode){
        
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type); 
        
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        
        return shader;
    }
    private void initShapes(){
        
        float triangleCoords[] = {
            // X, Y, Z
            -0.5f, -0.25f, 0,
             0.5f, -0.25f, 0,
             0.0f,  0.559016994f, 0
        }; 
        
        // initialize vertex Buffer for triangle  
        ByteBuffer vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                triangleCoords.length * 4); 
        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
        triangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
        triangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
        triangleVB.position(0);            // set the buffer to read the first coordinate
    
    }
	
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
    
        // Set the background frame color
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        
        initShapes();
        
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        
        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // creates OpenGL program executables
        
        // get handle to the vertex shader's vPosition member
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    }
    
    public void onDrawFrame(GL10 unused) {
    
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);
        
        // Prepare the triangle data
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, 12, triangleVB);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
    
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }
  
}