package frsf.ia.grupo9.ui;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sun.awt.image.BufferedImageGraphicsConfig;
import sun.java2d.pipe.DrawImage;

public class JArbolPanel extends JPanel {
	protected BufferedImage imagen;

	public JArbolPanel(BufferedImage im) {
		super();
		this.setSize(new Dimension(800, 600));
		imagen = im;
	}

	public JArbolPanel() {
		super();
		imagen = null;
	}

	/*
	 * public void setImagen(Image imagen){ this.imagen = imagen;
	 * this.repaint(); }
	 */

	public void setImagen(String path) {
		try {
			this.setArbol(new File(path));
			this.repaint();
		} catch (IOException e) {
			System.out.println("No se abrio imagen.");
		}
	}

	public void setImagen(BufferedImage image) {
		try {
			this.setArbol(image);
			this.repaint();
		} catch (IOException e) {
			System.out.println("No se abrio imagen.");
		}
	}

	public BufferedImage getImagen() {
		return imagen;
	}

	private void setArbol(File file) throws IOException {
		if (file == null)
			imagen = null;
		else {
			imagen = ImageIO.read(file);
			//imagen = resizeTrick(imagen, 100, 200);
		}
	}

	private void setArbol(URL url) throws IOException {
		if (url == null)
			imagen = null;
		else {
			imagen = ImageIO.read(url);
		}
	}

	private void setArbol(BufferedImage image) throws IOException {
		if (image == null)
			imagen = null;
		else
			imagen = image;
	}

	public static BufferedImage blurImage(BufferedImage image) {
		float ninth = 1.0f / 9.0f;
		float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth,
				ninth, ninth };

		Map map = new HashMap();

		map.put(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		map.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		map.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		RenderingHints hints = new RenderingHints(map);
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel),
				ConvolveOp.EDGE_NO_OP, hints);
		return op.filter(image, null);
	}

	private static BufferedImage createCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage result = gc.createCompatibleImage(w, h,
				Transparency.TRANSLUCENT);
		Graphics2D g2 = result.createGraphics();
		g2.drawRenderedImage(image, null);
		g2.dispose();
		return result;
	}

	private static BufferedImage resize(BufferedImage image, int width,
			int height) {
		int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image
				.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	private static BufferedImage resizeTrick(BufferedImage image, int width,
			int height) {
		image = createCompatibleImage(image);
		image = resize(image, 100, 100);
		image = blurImage(image);
		image = resize(image, width, height);
		return image;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		//g.fillRect(0, 0, getWidth(), getHeight());
		if (imagen != null)
			g.drawImage(imagen, 0, 0,getWidth(), getHeight(), null);
		//Component c;
		/*for (int i = 0; i < getComponentCount(); i++) {
			c = getComponent(i);
			g.translate(c.getX(), c.getY());
			c.print(g);
			g.translate(-c.getX(), -c.getY());
		}*/
	}
}
