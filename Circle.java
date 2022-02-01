import java.awt.geom.Ellipse2D;
import java.util.HashSet;

public class Circle extends Ellipse2D.Double{
    
	
	private static final long serialVersionUID = 1L;
	private int etat; //pour determiner sa couleur lors du draw
	private int x;
	private int y;
	private HashSet<Ligne> candidates; //lignes pouvant etre dessinés en jouant ce point/cercle
	
	public Circle(int x, int y,double ecart,int etat){
		super(x*ecart-ecart/5, y*ecart-ecart/5, 2*(ecart/5), 2*(ecart/5)); //on adapte nos données a la methode de construction de ELLipse2D
		this.x=x;
		this.y=y;
    	this.etat=etat;
    	
    	this.candidates= new HashSet<Ligne>(); 
    

    	}
	
	public int getx() {return this.x;}
	public int gety() {return this.y;}
	public int getetat() {return this.etat;}
	public void setetat(int etat) {this.etat=etat;}
	public HashSet<Ligne> getcandidates(){return this.candidates;}
}
	
	


