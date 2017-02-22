package test.cfor.se;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class ThaGLSurfaceView extends GLSurfaceView {

    public ThaGLSurfaceView(Context context){
        super(context);
        
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(new ThaGLRenderer());
    }
}
