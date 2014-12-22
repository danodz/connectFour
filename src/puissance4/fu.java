package puissance4;

import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class fu 
{
    static HashMap<String,Texture> imgs;
    public static final int ACTIVECAMERA = 0x01;
    
    static float height;
    static float width;
    static float imgHeight;
    static float imgWidth;
    public static int cooldown = 5;
    
    public static int cursor = 12;
    public static int areaCursor = 4;
    
    public static boolean player1Turn = true;
    
    public static int[][] area = new int[7][6];
    
    public static boolean p1Won = false;
    public static boolean p2Won = false;
    public static int winHeight;
    public static int winWidth;
    public static int winAngle;
    
    
    public static void draw()
    {
        glTranslated(0.0f, 0.0f, -1000);
        bindTexture("grille");
        drawTexture(0,-50,imgWidth,imgHeight,1,0);
        
        
        for(int x = 0; x < 7; x++)
        {
            for(int y = 0; y < 6; y++)
            {
                if(area[x][y] == 1)
                {
                    bindTexture("green");
                    drawTexture(-287 + x*100,-312 + y*100,imgWidth,imgHeight,1,0);
                }
                
                if(area[x][y] == 2)
                {
                    bindTexture("blue");
                    drawTexture(-287 + x*100,-312 + y*100,imgWidth,imgHeight,1,0);
                }
            }
        }
        
        if(player1Turn)
        {
            bindTexture("green");
        }
        else
        {
            bindTexture("blue");
        }
        
        drawTexture(cursor,300,imgWidth,imgHeight,0.5f,0);
        
    }
    
    public static void actionPerformed()
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_R))
        {
            for(int x = 0; x < 7; x++)
            {
                for(int y = 0; y < 6; y++)
                {
                    area[x][y] = 0;
                    player1Turn = true;
                    p1Won = false;
                    p2Won = false;
                    winWidth = 0;
                    winHeight = 0;
                    winAngle = 0;
                }
            }
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) & player1Turn & cooldown >= 5 & cursor != 312)
        {
            cursor += 100;
            areaCursor += 1;
            cooldown = 0;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) & player1Turn & cooldown >= 5 & cursor != -288)
        {
            cursor -= 100;
            areaCursor -= 1;
            cooldown = 0;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D) & !player1Turn & cooldown >= 5 & cursor != 312)
        {
            cursor += 100;
            areaCursor += 1;
            cooldown = 0;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_A) & !player1Turn & cooldown >= 5 & cursor != -288)
        {
            cursor -= 100;
            areaCursor -= 1;
            cooldown = 0;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_S) & !player1Turn & cooldown >= 5 & area[areaCursor - 1][5] == 0 & !p1Won)
        {
            for(int i = 0; i < 7; i++)
            {
                if(area[areaCursor - 1][i] == 0)
                {
                    area[areaCursor - 1][i] = 2;
                    i = 10;
                }
            }
            player1Turn = true;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) & player1Turn & cooldown >= 5 & area[areaCursor - 1][5] == 0 & !p2Won)
        {   
            for(int i = 0; i < 7; i++)
            {
                if(area[areaCursor - 1][i] == 0)
                {
                    area[areaCursor - 1][i] = 1;
                    i = 10;
                }
            }
            player1Turn = false;
        }
        
        cooldown += 1;
        
    }
    
    public static void win()
    {
        for(int x = 0; x < 6; x++)
        {
            for(int y = 0; y < 5; y++)
            {
                if(!player1Turn)
                {
                    if(x < 4)
                    {
                        if(area[x][y] == area[x+1][y] & area[x][y] == area[x+2][y] & area[x][y] == area[x+3][y]  & area[x][y] != 0)
                        {
                            p1Won = true;
                        }
                    }

                    if(y < 3)
                    {
                        if(area[x][y] == area[x][y+1] & area[x][y] == area[x][y+2] & area[x][y] == area[x][y+3]  & area[x][y] != 0)
                        {
                            p1Won = true;
                        }
                    }

                    if(y < 3 & x < 4)
                    {
                        if(area[x][y] == area[x+1][y+1] & area[x][y] == area[x+2][y+2] & area[x][y] == area[x+3][y+3]  & area[x][y] != 0)
                        {
                            p1Won = true;
                        }
                    }

                    if(x-3 >= 0 & y+3 < 6)
                    {
                        if(area[x][y] == area[x-1][y+1] & area[x][y] == area[x-2][y+2] & area[x][y] == area[x-3][y+3]  & area[x][y] != 0)
                        {
                            p1Won = true;
                        }
                    }
                }
                
                if(player1Turn)
                {
                    if(x < 4)
                    {
                        if(area[x][y] == area[x+1][y] & area[x][y] == area[x+2][y] & area[x][y] == area[x+3][y]  & area[x][y] != 0)
                        {
                            p2Won = true;
                        }
                    }

                    if(y < 3)
                    {
                        if(area[x][y] == area[x][y+1] & area[x][y] == area[x][y+2] & area[x][y] == area[x][y+3]  & area[x][y] != 0)
                        {
                            p2Won = true;
                        }
                    }

                    if(y < 3 & x < 4)
                    {
                        if(area[x][y] == area[x+1][y+1] & area[x][y] == area[x+2][y+2] & area[x][y] == area[x+3][y+3]  & area[x][y] != 0)
                        {
                            p2Won = true;
                        }
                    }

                    if(x-3 >= 0 & y+3 < 6)
                    {
                        if(area[x][y] == area[x-1][y+1] & area[x][y] == area[x-2][y+2] & area[x][y] == area[x-3][y+3]  & area[x][y] != 0)
                        {
                            p2Won = true;
                        }
                    }
                }
                
            }
        }
                
                
               
        
        if(p1Won)
        {   
            bindTexture("greenWon");
            
            
            if(winAngle < 720)
            {
                for(int i = 0; i < 12; i++)
                {
                winWidth += 1;
                winHeight += 1;
                winAngle += 1;
                }
            }
                drawTexture(0,0,winWidth,winHeight,1,winAngle);
                
            
        }
        
        if(p2Won)
        {
            bindTexture("blueWon");
            if(winAngle < 720)
            {
                for(int i = 0; i < 12; i++)
                {
                winWidth += 1;
                winHeight += 1;
                winAngle += 1;
                }
            }
            drawTexture(0,0,winWidth,winHeight,1,winAngle);
            
        }
        
        
    }
    
        public static void bindTexture(String img)
    {
       try
       {
           imgs.get(img).bind();
           height = imgs.get(img).getHeight();
           width = imgs.get(img).getWidth();

           imgHeight = imgs.get(img).getImageHeight();
           imgWidth = imgs.get(img).getImageWidth();
           
           glTexParameteri( GL_TEXTURE_2D , GL_TEXTURE_WRAP_S , GL_CLAMP );
           glTexParameteri( GL_TEXTURE_2D , GL_TEXTURE_WRAP_T , GL_CLAMP );
           
           glTexParameteri( GL_TEXTURE_2D , GL_TEXTURE_MAG_FILTER , GL_NEAREST );
           glTexParameteri( GL_TEXTURE_2D , GL_TEXTURE_MIN_FILTER , GL_NEAREST );
       }
       catch (Exception ex)
       {
           System.out.println("binding exception img : " + img);
       }
    }
    
    public static Texture loadImgJar(String location)
    {
        try
        {
            return TextureLoader.getTexture("PNG", fu.class.getResourceAsStream("/"+location) );
        }
        catch (IOException ex) 
        {
            Logger.getLogger(fu.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static void init()
    {
        imgs = new HashMap();
        
        loadImgs();
        
        /*
        File f = new File("data");
        File[] fs = f.listFiles();
        String name;
        
        for( int i = 0 ; i < fs.length ; i++ )
        {
            name = fs[i].toString().split("/")[ fs[i].toString().split("/").length -1 ];

            name = name.split(".png")[0];
            
            if ( name.charAt(0) != '.' )
            {
                imgs.put( name , loadImg( fs[i].toString() ) );
            }
        }
        */
    }
    
    public static void loadImgs()
    {
    	int loadTech = 0;
        
        if ( loadTech == 0 )
        {
            try
            {
                CodeSource src = fu.class.getProtectionDomain().getCodeSource();

                if( src != null )
                {
                    URL jar = src.getLocation();

                    ZipInputStream zip = new ZipInputStream( jar.openStream());
                    ZipEntry ze;
                    
                    while( ( ze = zip.getNextEntry() ) != null )
                    {
                        String entryName = ze.getName();

                        String[] parts = entryName.split("/");

                        if ( parts.length > 2 && parts[ 0 ].equals("Data") &&  parts[ 1 ].equals("imgs") )
                        {
                            String name = entryName.toString().split("/")[ entryName.toString().split("/").length -1 ];

                            name = name.split(".png")[0];

                            if ( name.charAt(0) != '.' )
                            {
                                imgs.put( name , loadImgJar( entryName ) );
                            }
                        }
                    }

                }
                else
                    System.out.println("faild");
            }
            catch( Exception e )  { System.out.println( e ); }

            try
            {
                URL url = fu.class.getResource("/Data/imgs/");
                File f = null;

                f = new File( url.toURI() );

                File[] fs = f.listFiles();
                String name;
                for( int i = 0 ; i < fs.length ; i++ )
                {
                    name = fs[i].toString().split("/")[ fs[i].toString().split("/").length -1 ];

                    name = name.split(".png")[0];

                    if ( name.charAt(0) != '.' )
                    {
                        imgs.put( name , loadImg( fs[i].toString() ) );
                    }
                }
            }
            catch( Exception e ) {  }
        
        }
        else if ( loadTech == 1 )
        {
            try
            {
                CodeSource src = fu.class.getProtectionDomain().getCodeSource();

                if( src != null )
                {
                    URL jar = src.getLocation();

                    ZipInputStream zip = new ZipInputStream( jar.openStream());
                    ZipEntry ze;

                    while( ( ze = zip.getNextEntry() ) != null )
                    {
                        String entryName = ze.getName();

                        String[] parts = entryName.split("/");

                        if ( parts.length == 4 &&  parts[ 1 ].equals("imgs") )
                        {
                            String name = entryName.toString().split("/")[ entryName.toString().split("/").length -1 ];

                            name = name.split(".png")[0];

                            if ( name.charAt(0) != '.' )
                            {
                                imgs.put( name , loadImgJar( entryName ) );
                            }
                        }
                    }

                }
                else
                    System.out.println("faild");

                System.out.println("end test");
            }
            catch( Exception e )  { System.out.println( e ); }

            URL url = fu.class.getResource("/Data/imgs/");
            File f = null;

            try
            {
                f = new File( url.toURI() );
            }
            catch (URISyntaxException ex) { Logger.getLogger(fu.class.getName()).log(Level.SEVERE, null, ex); }

            File[] fs = f.listFiles();
            String name;
            for( int i = 0 ; i < fs.length ; i++ )
            {
                name = fs[i].toString().split("/")[ fs[i].toString().split("/").length -1 ];

                name = name.split(".png")[0];

                if ( name.charAt(0) != '.' )
                {
                    imgs.put( name , loadImg( fs[i].toString() ) );
                }
            }
        }
    }
    
    public static Texture loadImg(String location)
    {
       try 
       {
           return TextureLoader.getTexture("PNG", new FileInputStream(location));
       }
       catch (IOException ex) 
       {
           return null;
       }
    }
    
    public static void drawTexture(float x , float y , float alpha , float angle , int bits)
    {
       if (( bits & ACTIVECAMERA ) > 0)
           drawTexture( x, y, imgWidth , imgHeight , alpha , angle );
       else
           drawTexture( x , y , imgWidth , imgHeight , alpha , angle );
    }

    public static void drawTexture(float x, float y, float h, float l,float alpha, float angle)
    {
        glColor4f(1, 1, 1, alpha);

        glPushMatrix();
        
        glTranslatef(x, y, 0.0f);
        glRotatef(angle, 0.0f, 0.0f, h);
        glTranslatef(-h / 2, l / 2, 0.0f);
        
        glBegin(GL_QUADS);
        // start
        
        glTexCoord2f( width , 0 );
        glVertex2f( h , 0 );
        
        glTexCoord2f( width , height );
        glVertex2f( h , -l );
        
        glTexCoord2f( 0 , height );
        glVertex2f( 0 , -l );
        
        glTexCoord2f( 0 , 0 );
        glVertex2f( 0 , 0 );
        
        // end
        glEnd();

        glPopMatrix();
    }
    
    
//    public static void bindTexture(String img)
//    {
//       try
//       {
//           imgs.get(img).bind();
//           height = imgs.get(img).getHeight();
//           width = imgs.get(img).getWidth();
//
//           imgHeight = imgs.get(img).getImageHeight();
//           imgWidth = imgs.get(img).getImageWidth();
//       }
//       catch (Exception ex)
//       {
//           System.out.println("binding exception img : " + img);
//       }
//    }
//    
//    public static void init()
//    {
//        imgs = new HashMap();
//        
//        File f = new File("data");
//        File[] fs = f.listFiles();
//        String name;
//        
//        for( int i = 0 ; i < fs.length ; i++ )
//        {
//            name = fs[i].toString().split("/")[ fs[i].toString().split("/").length -1 ];
//
//            name = name.split(".png")[0];
//            
//            if ( name.charAt(0) != '.' )
//            {
//                imgs.put( name , loadImg( fs[i].toString() ) );
//            }
//        }
//    }
//    
//    public static Texture loadImg(String location)
//    {
//       try 
//       {
//           return TextureLoader.getTexture("PNG", new FileInputStream(location));
//       }
//       catch (IOException ex) 
//       {
//           return null;
//       }
//    }
//
//    public static void drawTexture(float x, float y, float h, float l,float alpha, float angle)
//    {
//        glColor4f(1, 1, 1, alpha);
//
//        glPushMatrix();
//        
//        glTranslatef(x, y, 0.0f);
//        glRotatef(angle, 0.0f, 0.0f, h);
//        glTranslatef(-h / 2, l / 2, 0.0f);
//
//        glBegin(GL_QUADS);
//        // start
//        glVertex2f(0, 0);
//        glTexCoord2f(width, 0.0f);
//
//        glVertex2f(h, 0);
//        glTexCoord2f(width, height);
//
//        glVertex2f(h, -l);
//        glTexCoord2f(0.0f, height);
//
//        glVertex2f(0, -l);
//        glTexCoord2f(0.0f, 0.0f);
//
//        // end
//        glEnd();
//
//        glPopMatrix();
//    }
}