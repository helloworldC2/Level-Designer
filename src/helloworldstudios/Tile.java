package helloworldstudios;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import javax.swing.Icon;

import helloworldstudios.util.SpriteSheet;



public  class Tile {


	public static final Tile[] tiles = new Tile[14];
	public static final Tile VOID = new Tile(0,'v',"Void");
	public static final Tile GRASS = new Tile(1,'g',"Grass");
	public static final Tile WATER = new Tile(2,'w',"Water");
	public static final Tile WALL = new Tile(3,'a',"Wall");
	public static final Tile SAND = new Tile(4,'s',"Sand");
	public static final Tile REDLIGHT = new Tile(5,'r',"Red Light");
	public static final Tile GREENLIGHT = new Tile(6,'l',"Green Light");
	public static final Tile START1 = new Tile(7,'S',"Start");
	public static final Tile START2 = new Tile(8,'R',"End");
	public static final Tile AMBERLIGHT = new Tile(9,'m',"Amber Light");
	public static final Tile TREASURE0 = new Tile(10,'z',"Treasure 0");
	public static final Tile TREASURE1 = new Tile(11,'x',"Treasure 1");
	public static final Tile TREASURE2 = new Tile(12,'y',"Treasure 2");
	public static final Tile TREASURE3 = new Tile(13,'p',"Treasure 3");
	
	

	protected byte id;
	protected int tileId;
	protected int x, y;
	float width = 32, height =32;
	int texture;
	float textureWidth = 1.0f/16.0f;
	float textureX,textureY;
	public char c;
	private String name;
	
	public Tile(int id,char c,String name) {
		this.id = (byte)id;
		this.c = c;
		this.name = name;
		if(tiles[id] != null) throw new RuntimeException("Duplicant tile id at " + id);
		tiles[id] = this;
	}
	
	public byte getId()
	{
		return id;
	}
	public static byte getId(char c)
	{
		for(Tile t: tiles){
			if(t.c==c)return t.id;
		}
		return 0;
	}
	
	private  void getTexture(int i){
		
		 textureX= i % 16 * textureWidth;//1 over how many per row
		 textureY= i /16 * textureWidth;
		 
		
	}
	public void render(Level level) {
		
		getTexture(this.getId());
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, SpriteSheet.tileSheet);
		glPushMatrix();
		glBegin(GL_QUADS);
		glTexCoord2f(textureX,textureY+textureWidth);
		glVertex2f(x, y);
		glTexCoord2f(textureX+textureWidth,textureY+textureWidth);
	    glVertex2f(x+width, y);
	    glTexCoord2f(textureX+textureWidth,textureY);
	    glVertex2f(x+width, y+height);
	    glTexCoord2f(textureX,textureY);
	    glVertex2f(x, y+height);
        glEnd();
        glPopMatrix();
        glDisable(GL_TEXTURE_2D);
      
	}
	public char getChar(byte id){
		for(Tile t: tiles){
			if(t.id==id)return t.c;
		}
		return 'v';
	}
	public char getChar(){
		return this.c;
	}
	public Tile setX(int x2) {
		// TODO Auto-generated method stub
		this.x = x2;
		return this;

	}

	public Tile setY(int x2) {
		// TODO Auto-generated method stub
		this.y = x2;
		return this;

	}

	public int getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	
}
