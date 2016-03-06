package frsf.ia.grupo9.log;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class RenderImage {
	
	private static String DEFAULT_DOT_PATH = "C:\\Program Files (x86)\\Graphviz2.26.3\\bin\\dot.exe";
	protected BufferedImage im;
	protected String path;
	
	public RenderImage(String dotPath,String fileInputPath,String fileOutputPath){
		im = null;
		path = null;
		this.dotToJPG(dotPath, fileInputPath, fileOutputPath);
	}
	
	public RenderImage(String fileInputPath,String fileOutputPath){
		this.dotToJPG(DEFAULT_DOT_PATH, fileInputPath, fileOutputPath);
	}
	
	public RenderImage(){}
	
	public BufferedImage getImagen(){	
		return im;
	}
	
	public String getPath(){
		return path;
	}
	
	public boolean dotToJPG(String dotPath,String fileInputPath,String fileOutputPath){
		try {
		      String tParam = "-Tjpg";
		      String tOParam = "-o";
		      String p = " -Kdot";
		        
		      String[] cmd = new String[6];
		      cmd[0] = dotPath;
		      cmd[1] = tParam;
		      cmd[2] = fileInputPath;
		      cmd[3] = tOParam;
		      cmd[4] = fileOutputPath;
		      cmd[5] = p;
		                     
		      Runtime rt = Runtime.getRuntime();
		      rt.exec( cmd );
		      //im = (Toolkit.getDefaultToolkit()).getImage(fileOutputPath);
		      im = ImageIO.read(new File(fileOutputPath));
		      path = new String(fileOutputPath);
		      
		      return true;
		      } catch (Exception ex) {
		      	return false;
		    } finally {
		    }
		
	}
	
	public boolean dotToJPG(String fileInputPath,String fileOutputPath){
		try {
		      String tParam = "-Tjpg";
		      String tOParam = "-o";
		      String p = " -Kdot";
		        
		      String[] cmd = new String[6];
		      cmd[0] = DEFAULT_DOT_PATH;
		      cmd[1] = tParam;
		      cmd[2] = fileInputPath;
		      cmd[3] = tOParam;
		      cmd[4] = fileOutputPath;
		      cmd[5] = p;
		                     
		      Runtime rt = Runtime.getRuntime();
		      rt.exec( cmd );
		      //im = (Toolkit.getDefaultToolkit()).getImage(fileOutputPath);
		      im = ImageIO.read(new File(fileOutputPath));
		      path = new String(fileOutputPath);
		      
		      return true;
		      } catch (Exception ex) {
		      	return false;
		    } finally {
		    }
		
	}
	


}
