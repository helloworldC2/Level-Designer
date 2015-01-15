package helloworldstudios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Level {

	private Main main;
	private int width, height;
	private byte[] tiles;
	public Tile selected;
	public ArrayList<byte[]> editList = new ArrayList<byte[]>();
	private int posInEditList = -1;
	public Level(Main m) {
		this.main = m;
	}
	
	public boolean loadLevel(File path) throws IOException {
		String everything = "@";
		if(path.exists()){
		BufferedReader br = new BufferedReader(new FileReader(path));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        everything = sb.toString();
	    } finally {
	        br.close();
	    }
	    int x=-2;
	    int y=0;

	    String[] data = everything.split("@");
	    String[] header  = data[0].split(",");
	    this.width = Integer.parseInt(header[0]);
	    this.height = Integer.parseInt(header[1]);
	    y = this.height-1;
	    this.tiles = new byte[width * height];
	    for(int i =0;i<data[1].length();i++){
	    	if (data[1].charAt(i) ==';'){
	    		x=-2;
	    		y-=1;
	    		continue;
	    	}
	    		try{
	    		this.setTile(x,y,Tile.tiles[Tile.getId(data[1].charAt(i))]);
	    		}catch(Exception e){
	    			
	    		}
	    		x+=1;
	    }
	    
		}else{
	    this.width = 32;
		this.height = 32;
		this.tiles = new byte[width * height];
		for (int y = height-1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
			setTile(x, y, Tile.GRASS);
			}
		}
		}
		addToEditList();
		return false;
	}

	public boolean saveLevel(File file) {
		long time = System.currentTimeMillis();
		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file+".txt"), "utf-8"));// TODO add seperate folder for levels
	
			writer.write(width+","+height+"@");
			writer.write("\n");

			for (int y = height-1; y >= 0; y--) {
				for (int x = 0; x < width; x++) {
					writer.write(String.valueOf(getTile(x,y).getChar()));
				}
				writer.write(";\n");
			}
			writer.write("$");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
			}
		}
		long dt = System.currentTimeMillis()-time;
		System.out.println("Saved in: "+dt+" ms");
		System.out.println("Saved at: "+file.getAbsolutePath());
		return true;
	}

	public void setTile(int x, int y, Tile t) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		tiles[x + y * width] = t.getId();
	}

	public Tile getTile(int x, int y) {

		if (0 > x || x >= width || 0 > y || y >= height)

			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];

	}

	public void renderTiles() {

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				getTile(x, y).setX(x * 32).setY(y * 32).render(this);

			}
		}

	}

	public void addToEditList() {
		posInEditList++;
		byte[] t = tiles;
		editList.add(t);
		System.out.println("Added to list");
		
	}
	public void undo(){
		if(posInEditList<1){
			//System.out.println(posInEditList);
			return;
		}
		posInEditList-=1;
		System.out.println("Undone");
		System.out.println(editList.get(posInEditList));
		tiles = editList.get(posInEditList);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				System.out.print(tiles[x+y*width]);
			}
		}
	}
}
