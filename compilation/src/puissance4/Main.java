package puissance4;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;
public class Main 
{
    static final int screenWidth = 700;
    static final int screenHeight = 700;
    private final double near = 100.0;
    private final double far = 10000.0;
    private final double nearPercent = 10.0;
    
    public Texture texture;
    public static void main(String[] args) throws IOException
    {
        Main main = new Main();
        main.start();
    }
    
    public void start()
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
            
//            DisplayMode[] modes = Display.getAvailableDisplayModes();
//            Display.setDisplayMode(modes[ modes.length-1 ]);
//            Display.setDisplayMode(modes[ 0 ]);
//            Display.setFullscreen(true);
            
            Display.create();
        }
        catch (LWJGLException e)
        {
            System.out.println("Exception: " + e.getMessage());
            System.exit(0);
        }
        
        // init OpenGL here : HYYYper complex => don't tuche lol
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        double right = screenWidth * 0.5 * nearPercent / 100.0;
        double top = screenHeight * 0.5 * nearPercent / 100.0;
        glFrustum(-right, right, -top, top, near, far);
        glMatrixMode(GL_MODELVIEW);
        fu.init();
        
        while (!Display.isCloseRequested())
        {
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glLoadIdentity();

            glEnable(GL_TEXTURE_2D);
            glDisable(GL_DEPTH_TEST);

            //here
            fu.draw();
            fu.actionPerformed();
            fu.win();
            //*here
            
            Display.update();
            try
            {
                Thread.sleep(1000 / 30); // 30 frames par seconde
            }
            catch (Exception e)
            {
                System.out.println("Exception: " + e.getMessage());
                break;
            }
            
        }
        
        Display.destroy();
        System.out.println("main: exit");
    }

}
