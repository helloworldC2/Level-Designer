package helloworldstudios.util;

import helloworldstudios.Main;
import helloworldstudios.Tile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;



public class CustomDisplay implements WindowListener {

	private static float yScale;
	private static float xScale;
	final JFileChooser fc = new JFileChooser();

	public void create(boolean isFullscreen,final Main main) {
		if (!isFullscreen) {
			main.frame.addWindowListener(this);
			main.canvas.setBounds(0, 0, (int) main.width,(int) main.height);
			main.canvas.setIgnoreRepaint(true);
			main.canvas.setFocusable(true);
			JMenuBar menubar = new JMenuBar();
			JMenu file = new JMenu("File");
			fc.addChoosableFileFilter(new MapFileFilter());
			fc.setAcceptAllFileFilterUsed(false);
			JMenuItem save = new JMenuItem("Save");
	        save.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	
	            	int returnVal = fc.showDialog(main.frame,"Save");
	            	if(returnVal == 0){
	            		File file = fc.getSelectedFile();
	            		main.level.saveLevel(file);
	            	}
	            	
	            }
	        });
	        JMenuItem load = new JMenuItem("Open");
	        load.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	
	            	int returnVal = fc.showOpenDialog(main.frame);
	            	if(returnVal == 0){
	            		File file = fc.getSelectedFile();
	            		try {
							main.level.loadLevel(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
	            	}
	            	
	            }
	        });
	        JMenuItem neew = new JMenuItem("New");
	        neew.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {

						//main.level.blankLevel(file);
	            }
	        });
	        file.add(neew);
	        file.add(save);
	        file.add(load);
	        JMenu tiles = new JMenu("Tiles");
	        JMenuItem wall = new JMenuItem("Wall");
	        wall.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                main.selectedTile = Tile.WALL;
	            }
	        });
	        JMenuItem sand = new JMenuItem("sand");
	        sand.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                main.selectedTile = Tile.SAND;
	            }
	        });
	        JMenuItem water = new JMenuItem("water");
	        water.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                main.selectedTile = Tile.WATER;
	            }
	        });
	        JMenuItem redlight = new JMenuItem("redlight");
	        redlight.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                main.selectedTile = Tile.REDLIGHT;
	            }
	        });
	        JMenuItem greenlight = new JMenuItem("greenlight");
	        greenlight.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                main.selectedTile = Tile.GREENLIGHT;
	            }
	        });
	        JMenuItem start1 = new JMenuItem("start1");
	        start1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                main.selectedTile = Tile.START1;
	            }
	        });
	        JMenuItem start2 = new JMenuItem("start2");
	        start2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	                main.selectedTile = Tile.START2;
	            }
	        });
	        JMenuItem grass = new JMenuItem("Grass");
	        grass.setMnemonic(KeyEvent.VK_D);
	        grass.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent event) {
	            	 main.selectedTile = Tile.GRASS;
	            }
	        });
	        tiles.add(grass);
	        tiles.add(water);
	        tiles.add(wall);
	        tiles.add(sand);
	        tiles.add(redlight);
	        tiles.add(greenlight);
	        tiles.add(start1);
	        tiles.add(start2);
	        menubar.add(file);
	        menubar.add(tiles);

	        main.frame.setJMenuBar(menubar);

			
			main.frame.add(main.canvas);
			main.frame.pack();
			main.frame.setLocationRelativeTo(null);
			main.frame.setFocusable(true);
			main.frame.setVisible(true);
			main.canvas.requestFocus();
			main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			main.frame.setTitle("Robot Level Designer");
//			BufferedImage img = null;
//			try {
//				img = ImageIO.read(this.getClass().getResourceAsStream(
//						"/shield.png"));
//				main.frame.setIconImage(img);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

			System.out.println(main.canvas.isDisplayable());

			try {
				Display.setParent(main.canvas);
				Display.create();
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			try {
				Display.setFullscreen(true);

				Display.setTitle("Robot Level Designer");
				Display.create();
				Display.setResizable(true);
				System.out.println(Display.getDesktopDisplayMode().toString());
				setyScale(main.height / Display.getHeight());
				setxScale(main.width / Display.getWidth());
				// Display.setParent(game.window);
			} catch (LWJGLException e) {
				System.err.println("Display creation failed :(");
			}
		}
		setyScale(main.height / Display.getHeight());
		setxScale(main.width / Display.getWidth());

	}

	public static float getxScale() {
		return xScale;
	}

	public static void setxScale(float xScale) {
		CustomDisplay.xScale = xScale;
	}

	public static float getyScale() {
		return yScale;
	}

	public static void setyScale(float yScale) {
		CustomDisplay.yScale = yScale;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
