package helloworldstudios;

import helloworldstudios.util.CustomDisplay;
import helloworldstudios.util.SpriteSheet;

import java.awt.Canvas;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;




public class Main {

	private int fps;
	public final float width = 32*32, height = 32*32;
	public JFrame frame;
	public Canvas canvas;

	public Level level;
	public Tile selectedTile = Tile.GRASS;
	private void run() {
		canvas = new Canvas();
		frame = new JFrame();
		new CustomDisplay().create(false,this);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		init();

		while (!Display.isCloseRequested()) {

			tick();
		
			render();
	
				update();

		
		}

		// exit(0);

	}

	private void tick() {
		if(Mouse.isButtonDown(0)){
			level.setTile((int)(Mouse.getX()*CustomDisplay.getxScale())>>5, (int)(Mouse.getY()*CustomDisplay.getyScale())>>5, selectedTile);
		}
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0) {
		            System.out.println("Left button pressed");
		        }
		    }else {
		        if (Mouse.getEventButton() == 0) {
		            System.out.println("Left button released");
		            level.addToEditList();
		        }
		    }
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)&&Keyboard.isKeyDown(Keyboard.KEY_Z)){
			level.undo();
		}
	}

	private void render() {
		level.renderTiles();

	}

	private void update() {
		resize(canvas.getWidth(), canvas.getHeight());
		Display.sync(60);
		Display.update();
	}

	private void resize(int i, int j) {
		GL11.glViewport(0, 0, i, j);
		CustomDisplay.setyScale(height / Display.getHeight());
		CustomDisplay.setxScale(width / Display.getWidth());
	}

	private void init() {
		this.level = new Level(this);
		try {
			level.loadLevel(new File(""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpriteSheet.openSheet();

	}

	public static void main(String[] args) {

		try {
			System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/lib/native/windows");
		} catch (UnsatisfiedLinkError e) {
			System.out
					.println("LWJGL natives not found!");
			JOptionPane
					.showMessageDialog(
							null,
							"LWJGL natives not found!",
							"", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		new Main().run();

	}

}
