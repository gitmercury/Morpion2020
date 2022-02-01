import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.HashSet;

import javax.swing.JPanel;

public class Game extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private int ecart;
	private boolean help=false;
	private HashSet<Circle> cercles;
	private HashSet<Ligne> lignes;
	
	
	//HashSet<Ligne> lines;
	
	public Game(int version,int [][] plateau) {
		double x=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double y=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		if (x>800 && y>600) {
			this.setSize(800,600);
		}
		else {
			this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}
		if (this.getWidth()/(18)<this.getHeight()/(18)) {
			this.ecart=this.getWidth()/(18);
		}
		else{
			this.ecart=this.getHeight()/(18);
			}
		
		cercles= new HashSet<Circle>();
		for (int i=0;i<16;i++) {
			for (int j=0;j<16;j++) {
				Circle c=new Circle(i,j,ecart,plateau[i][j]);
				
				cercles.add(c);
			}
		}
		lignes= new HashSet<Ligne>();
		
	
		
	
			
	}
	
	public int getecart() {return this.ecart;}
	public HashSet<Circle> getcercles(){return this.cercles;}
	public HashSet<Ligne> getlignes(){return this.lignes;}
	public void setlines(HashSet<Ligne> load) {this.lignes=load;}
	
	private void drawCircle(Graphics g, int x, int y, int r,int etat) { //https://www.developpez.net/forums/d18985/java/interfaces-graphiques-java/graphisme/2d/dessin-dessiner-cercle-partir-centre/
       if (etat==0 || (etat==2 && !help) ) { g.drawOval(x-r, y-r, 2*r, 2*r);}//tout les cercles non joues sont vides...
       else{
    	   if (etat==2 && help) {g.setColor(Color.green);}//...sauf si help est active auquel cas les jouables sont verts
    	   else if (etat==17){g.setColor(Color.blue);}//correspond au cercles a choisir lors d'une conccurence de lignes pour un point 
    	   else if (etat==13) {g.setColor(Color.red);}//point  deja joué mais en attente du choix de la ligne
    	   
    	   else {g.setColor(Color.black);}//point joué 
    	   g.fillOval(x-r, y-r, 2*r, 2*r);}
       	   
	}
	
	public void help() {//on change grace a cette foncttion l'etat des cercle et con leur couleur pour aider le joueur 
			if (this.help) {this.help=false;}
			else {this.help=true;}
			repaint();}
	
	
		
		
	
	
	protected void paintComponent(Graphics g) {
	    int i,x1,x2,y1,y2;
	    //on rempli le vide en gris
	    g.setColor(Color.orange);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    g.setColor(Color.black);//on dessine les lignes
	    for (i=1;i<17;i++) {
	    			
	    			x1=x2=i*ecart;
	    			y1=ecart;
	    			y2=16*y1;
	    			g.drawLine(x1, y1, x2, y2);			
	    }
	    for (i=1;i<17;i++) {
	    	
			
			y1=y2=i*ecart;
			x1=ecart;
			x2=16*x1;
			
			g.drawLine(x1, y1, x2, y2);
	    	}
	   for (Circle c: cercles) { //on dessine les cercles
		   g.setColor(Color.green);
		   if (c.getetat()==13) {
				   for (Ligne l: c.getcandidates()) {
					   g.drawLine(ecart*(l.getx1()+1),ecart*(l.gety1()+1),ecart*(l.getx2()+1),ecart*(l.gety2()+1));
			   
		   }}
		   g.setColor(Color.DARK_GRAY);
		   drawCircle(g,ecart*(c.getx()+1),ecart*(c.gety()+1),ecart/3,c.getetat());
		   
		   
		
		}
	  
	   for (Ligne l: lignes) {//on dessine les lignes et le numéro du coup sut le cercle en question
		   g.setColor(Color.red);
		   g.drawLine(ecart*(l.getx1()+1),ecart*(l.gety1()+1),ecart*(l.getx2()+1),ecart*(l.gety2()+1));
		   g.setColor(Color.white);
		   g.drawString(String.valueOf(l.getnumber()),(l.getx0()+1)*ecart-3,(l.gety0()+1)*ecart+4);
		   }
	   	g.setColor(Color.black);
	   	String s="Score : ";
	   	s=s.concat(String.valueOf(lignes.size()));
		g.drawString(s,18*ecart,8*ecart);
	}
	
	   
	}




	    
	    
	  
	
	
	



 