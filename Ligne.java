//pas grand chose a dire ici 
public class Ligne {
	
	private int x0,y0; //coordonnées du point joué
	private int x1,x2,y1,y2;//coordonnée des extremite de la ligne 
	private int axe,number;//nombre premier associe a la direction et numéro du coup joué
	
	public Ligne(int x1,int y1,int x2,int y2, int x0, int y0,int axe,int number) {
		this.x1=x1;
		this.x2=x2;
		this.x0=x0;
		this.y1=y1;
		this.y2=y2;
		this.y0=y0;
		this.axe=axe; 
		this.number=number;	}
	
	public int getx1() {
		return this.x1;
	}
	public int getx2() {
		return this.x2;
	}
	
	public int gety1() {
		return this.y1;
	}
	public int gety2() {
		return this.y2;
	}
	public int getx0() {
		return this.x0;
	}
	
	public int gety0() {
		return this.y0;
	}
	public int getaxe() {
		return this.axe;
	}
	public int getnumber() {
		return this.number;
	}

}
