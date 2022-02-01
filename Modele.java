import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Modele {

	private int [][] board;
	private boolean endgame;
	private Circle lastclicked;
	private int version;
	private Game game;
	
	//nouvelle partie
	public Modele(int version){
		
		this.version=version;
		this.board=new int[16][16];
		buildboard(this.board);
		this.endgame=false;
		this.game=new Game(this.version,this.board);
		
	}
	
	//partie chargee
	public Modele(int version,int [][]sauv){
		
		this.version=version;
		//this.board=new int[16][16];
		this.board=sauv;
		this.endgame=false;
		this.game=new Game(this.version,this.board);
		
	}
	
	//construction de notre plateau de jeu de base
	public void buildboard(int [][] board){
		//on rempli le tableau de 0 pour l'état initial 
		int i,j;
		for (i=0; i < 16 ; i++){
			for (j=0; j < 16 ; j++){ 
				board[i][j]=0; 
			}
		}
		//on crée l'état initial de notre board
		for ( i = 6 ; i < 10 ; i++){
			board [3][i]= 1 ;
			board [12][i]= 1 ;
			
		}
		for ( i = 3 ; i < 7 ; i++){
			board [6][i]= 1 ;
			board [9][i]= 1 ;
			
		}
		for ( i = 3 ; i < 7 ; i++){
			board [6][i]= 1 ;
			board [9][i]= 1 ;
			board [6][i+6]= 1 ;
			board [9][i+6]= 1 ;
		}
		board[4][6]=1;
		board[5][6]=1;
		board[10][6]=1;
		board[11][6]=1;
		
		board[4][9]=1;
		board[5][9]=1;
		board[10][9]=1;
		board[11][9]=1;
		
		board[7][3]=1;
		board[8][3]=1;
		board[7][12]=1;
		board[8][12]=1;
		
	}
	
	public void setlastclicked(Circle c) {this.lastclicked=c;}
	public Circle getlastclicked() {return this.lastclicked;}
	public Game getgame() {return this.game;}
	public int getversion() {return this.version;}
	
	public void setendgame(boolean b) {this.endgame=b;}
	public boolean getendgame() {return this.endgame;}
	
	
	
	
	public void nextmove() {
		//methode appele une fois quon a joue un coup pour preparer les données pour le suivant

		int compteur=0;
		boolean b;
		for (Circle c: this.game.getcercles()) {
			b=check(c);//actualise l'etat du cercle et du plateau et renvoi true si cest un cercle jouable
			if (b) {compteur++;}
			
		}
		
		if (compteur==0) {//si aucun coup n'est jouable c'est la fin de la partie
			
			this.end();
			
			
			
		}
	}
	
	//methode appele lorsque le joeur ne peut plus jouer
	private void end() {
		if (this.getendgame()) {return;} 
		this.setendgame(true);
		
		if (Menu.gethighscores().size()<10) { //tant que 10 parties nont pas ete joues chaque score est un highscore
			this.save("highscores.txt");
			Menu.gethighscores().add(this.game.getlignes().size());
			
		}
		else if(this.game.getlignes().size()>Menu.getlasthighscore()) { //highscore si score supérieur au dernier des highscores
		
			this.save("highscores.txt");
			Menu.gethighscores().remove(Menu.getlasthighscore());
			Menu.gethighscores().add(this.game.getlignes().size());

		}
		
		Menu.stop();
		
		

	}
	
	
	
	public void save(String par) { 
		/*on construit la chaine a sauvegarder dans le fichier
		la chaine est composé des infos suivantesdans lordre :
		 du nom, de la version(5t ou 5d), du nombre de coups joués, 
		 des valeurs de board puis des 3 paires de coordonnées de lines
		 */
		int [][] mat=this.board;
		String s=JOptionPane.showInputDialog("Entrez votre nom","Nom");
		if (s.equals("")) {s="Unknown";}
		s=s.concat(" ");
		s=s.concat(String.valueOf(this.getversion()));
		s=s.concat(" ");
		s=s.concat(String.valueOf(this.game.getlignes().size()));
		s=s.concat(" ");
		for (int i=0;i<16;i++) {
			for (int j=0;j<16;j++) {
				s=s.concat(String.valueOf(mat[i][j]));
				s=s.concat(" ");
			}
		}
		for (Ligne l:this.game.getlignes()) {
			s=s.concat(String.valueOf(l.getx1()));
			s=s.concat(" ");
			s=s.concat(String.valueOf(l.gety1()));
			s=s.concat(" ");
			s=s.concat(String.valueOf(l.getx2()));
			s=s.concat(" ");
			s=s.concat(String.valueOf(l.gety2()));
			s=s.concat(" ");
			s=s.concat(String.valueOf(l.getx0()));
			s=s.concat(" ");
			s=s.concat(String.valueOf(l.gety0()));
			s=s.concat(" ");
			s=s.concat(String.valueOf(l.getnumber()));
			s=s.concat(" ");
			
		}
		
		/*newhs est la liste des lignes quon va ecrire dans highscore,
		 en effet on ecrase l'ancien fichier, on recupere donc tout les
		 scores, on place en fonction du score le nouvel highscores dans la liste*/
		LinkedList<String> newhs=new LinkedList<String>();
		if (par.equals("highscores.txt")) {
			Boolean check=false;
			try {
				
				File file = new File(par);
				if (!file.exists()) { 
				    file.createNewFile();
				   }
				FileReader f=new FileReader(file);
				BufferedReader br = new BufferedReader(f);
				String l=br.readLine();
				
				while(l!=null) {
					if (this.game.getlignes().size()>Integer.valueOf(l.split(" ")[2]) && !check) {
						newhs.add(s);
						check=true;
					}
					newhs.add(l);
					l=br.readLine();
				
				}
				if (!check) {
					newhs.add(s);
				}
				
				br.close();
				f.close();
				file.delete();//on supprime le fichier afin de laisser la place au nouveau qui va prendre les valeurs de newhs
				
			}
			catch (FileNotFoundException e) {
			     
			    e.printStackTrace();
			    } 
			catch (IOException e) {
		   e.printStackTrace();
		  }
		  
		  
		}
		
		try {

			 
			   File file = new File(par);

			   // créer le fichier s'il n'existe pas
			   if (!file.exists()) {
			    file.createNewFile();
			   }
			  
			   FileWriter f = new FileWriter(file.getAbsoluteFile(),true);
		       BufferedWriter bw = new BufferedWriter(f);
			   if (par.equals("sauvegardes.txt")) {
				   bw.write(s);
				   bw.newLine();
			   }
			   else {
				  int a;
				  if (newhs.size()>10) {
					  a=10;
					  }
				  else {a=newhs.size();}
				  for (int i=0;i<a;i++) {
					  bw.write(newhs.get(i));
					  bw.newLine();
					  }
				  }
			   
			   
			   bw.close();
			   f.close();
			   
			  

			  } catch (IOException e) {
			   e.printStackTrace();
			  }
		newhs.clear();
	}
	
	public void auto() {
		//permet de jouer sans cliquer sur un cercle,
		Circle c=autochoix();//choix aussi aléatoire que possible 
		Ligne l =choixligne(c);
		this.game.getlignes().add(l);
		this.board[c.getx()][c.gety()]=1;
		int x1=l.getx1();
		int y1=l.gety1();
		int x2=l.getx2();
		int y2=l.gety2();
		
		int a=this.board[x1][y1];
		this.board[x1][y1]=a*l.getaxe();
		int b=this.board[x2][y2];
		this.board[x2][y2]=b*l.getaxe();
	}
		
	
	private Circle autochoix(){ 
		HashSet<Circle> elus=new HashSet<Circle>();
		Circle elu=null;
		for (Circle c: this.game.getcercles()) {
			if(c.getetat()==2) {
				elus.add(c);
			}
		}
		 int r=(int)Math.round(Math.random()*elus.size());
		 if (r==0) {r=1;}
		 int count=1; 
		 for (Circle c: elus) {
			 if (count==r) {elu=c;break;}
			 count++;
		 }
		 return elu;
		
		
	}
	
	private Ligne choixligne(Circle c){
		HashSet<Ligne> elues=new HashSet<Ligne>();
		 Ligne elue=null;
		for (Ligne l: c.getcandidates()) {
				elues.add(l);
				break;
			
		}
		int r=(int)Math.round(Math.random()*elues.size());
		if (r==0) {r=1;}
		int count=1;
		
		 for (Ligne l: elues) {
			 if (count==r) {elue=l;break;}
			 count++;
		 }
		 return elue;
		
	}
	
	
	
	
	
	public void click(Circle c) {   
		//methode appele lorsque l'on clique sur un cercle
		if (endgame) {return;}
		//aucun click nest pris en compte apres la fin du jeu
		Ligne L;	
		if (this.getlastclicked()==null	) {
			//on verifie quon a bien choisi la ligne avant de passer au coup suivant pour les situations ou on doit choisir
		if (c.getetat()==2) {//si le click se fait sur un cercel jouable, on le joue et on adapte le modele et les graphs
			board[c.getx()][c.gety()]=1;
			Iterator<Ligne> it = c.getcandidates().iterator();
			
			if (c.getcandidates().size()==1) {
				//si une seule ligne possible, on choisit celle ci
				L=it.next();
				Iterator<Circle> itc=this.game.getcercles().iterator();
			    while (itc.hasNext()) {
			    	//on actualise les valeur de board en multipliant les extremites de la ligne par la valeur associé la direction de la ligne
			    	Circle ce=itc.next();
			    	int a=0;
			    	if (ce.getx()==L.getx1() && ce.gety()==L.gety1()) {
			    		a=board[ce.getx()][ce.gety()];
			    		board[ce.getx()][ce.gety()]=a*L.getaxe();
			    		
			    		}
			    	else if (ce.getx()==L.getx2() && ce.gety()==L.gety2()) {
			    		a=board[ce.getx()][ce.gety()];
			    		board[ce.getx()][ce.gety()]=a*L.getaxe();
			    		}
			    	 
			    }
			    game.getlignes().add(L);
			     nextmove();
			    
			}
			
			else {
				//sinon on propose un choix parmis les lignes avec la fonctrion syiuvante
				choix(c);	
				
			}
			
		    
		    
		}
		}
		else if (c.getetat()==17) {
			//letat 17 correspond au cercle qui sera au bout de la ligne choisi lors d'une concurence entre plusisuers lignes 
			for (Ligne l : this.getlastclicked().getcandidates()) {
				//comme plus haut on actualise les valeur de boards
				if ((l.getx2()==c.getx() && l.gety2()==c.gety()) || (l.getx1()==c.getx() && l.gety1()==c.gety())) {
					 board[c.getx()][c.gety()]=1;
					int a= board[l.getx1()][l.gety1()];
					int b= board[l.getx2()][l.gety2()];
					 board[l.getx1()][l.gety1()]=a*l.getaxe();
					 board[l.getx2()][l.gety2()]=b*l.getaxe();

					 game.getlignes().add(l);
					 nextmove();
				    
					break;
				}
			}
			this.setlastclicked(null);	//on remet le lastclicked a null pour repermettre de selectionner les cercles jouables 			
		}
	}

	
	private void choix(Circle c) {
		//on modifie letat des cercles afin que lutilisateur puisse choisir quelle ligne passera par le point qu'il a selectione au click precedent
		HashSet<Ligne> lines=new HashSet<Ligne>();
		boolean v=false;//pour verifier quon ne confond pas le point a cliquer et le point deja clique avant
		c.setetat(13);
		for (Circle cer :  this.game.getcercles()) {
			for (Ligne l :c.getcandidates()) {
				if (cer.getx()==l.getx2() && cer.gety()==l.gety2()) {
					if (cer.getx()==c.getx() && cer.gety()==c.gety()) {
						v=true;
						lines.add(l);
						continue;
					}
					cer.setetat(17);
					break;
				}
				
			}
		}
		//le point clique originellement peut etre une extremite d'une potentielle droite, si cest le cas nous nous
		//assurons de prendre lautre extremité de la droite, d'ou "lines"
		if (v) {
			for (Circle cer :  this.game.getcercles()) {
				for (Ligne l :lines) {
					if (cer.getx()==l.getx1() && cer.gety()==l.gety1()) {
						cer.setetat(17);
						break;
					}
					
				}
			}
		}
		this.setlastclicked(c);	
		
	}
	
	private boolean check(Circle c) {
		//a la fin d'un tour on actualise l'etat de chaque cercle 
		boolean b=false;
		int x=c.getx();
		int y=c.gety();
		int up=0;
		int down=0;
		int right=0;
		int left=0;
		int upright=0;
		int downright=0;
		int upleft=0;
		int downleft=0;
		//on va compter le nombre de voisin pour chaque axe
		if (this.board[x][y]==2) {this.board[x][y]=0;c.getcandidates().clear();}
		if (this.board[x][y]==0 ) {
			
			/*On attribut un nombre premiers a chaque axe,
			 * ainsi dans la matrixe le nombre correspondant a chaque point est défini ainsi :
			 * non joué et non jouable :0
			 * non joué mais jouable :2
			 * joué : produit des nombre premier asoociés a chaque axe qui le traverse et 1 sinon
			 * 
			 * ainsi si on veut savoir par quelle genre de ligne (quel axe) le point est traversé par un certain axe
			 * il suffit de regarder si il est divisible par le nombre premier associé a cet axe
			*/
			
			//axe vertical
			x=c.getx();
			y=c.gety();
			while (y!=0 && up<4) {
				y--;
				if (this.board[x][y]%2!=0  && this.board[x][y]%3==0 && this.getversion()==1) {
					up=1;
					break;
				}
				if (this.board[x][y]%2!=0  && this.board[x][y]%3!=0) {
					up++;
					}
				else {break;}
			}
			x=c.getx();
			y=c.gety();
			while (y!=15 && down<4) {
				y++;
				if (this.board[x][y]%2!=0  && this.board[x][y]%3==0 && this.getversion()==1) {
					down=1;
					break;
				}
				if (this.board[x][y]%2!=0 && this.board[x][y]%3!=0) {down++;}
				else {break;}
			}
			//axe horizontal
			x=c.getx();
			y=c.gety();
			while (x!=0 && left<4) {
				x--;
				if (this.board[x][y]%2!=0  && this.board[x][y]%5==0 && this.getversion()!=0) {
					left=1;
					break;
				}
				if (this.board[x][y]%2!=0 && this.board[x][y]%5!=0) {left++;}
				else {break;}
			}
			x=c.getx();
			y=c.gety();
			while (x!=15 && right<4) {
				x++;
				if (this.board[x][y]%2!=0  && this.board[x][y]%5==0 && this.getversion()!=0) {
					right=1;
					break;
				}
				if (this.board[x][y]%2!=0 && this.board[x][y]%5!=0) {right++;}
				else {break;}
			}
			//axe diagonal 1
			x=c.getx();
			y=c.gety();
			while (y!=0 && x!=0 && upleft<4) {
				y--;
				x--;
				if (this.board[x][y]%2!=0  && this.board[x][y]%7==0 && this.getversion()==1) {
					upleft=1;
					break;
				}
				if (this.board[x][y]%2!=0 && this.board[x][y]%7!=0) {upleft++;}
				else {break;}
			}
			y=c.gety();
			x=c.getx();
			while (y!=15 && x!=15 && downright<4) {
				y++;
				x++;
				if (this.board[x][y]%2!=0  && this.board[x][y]%7==0 && this.getversion()==1) {
					downright=1;
					break;
				}
				if (this.board[x][y]%2!=0 && this.board[x][y]%7!=0) {downright++;}
				else {break;}
			}
			//axe diagonal 2
			x=c.getx();
			y=c.gety();
			while (y!=0 && x!=15 && upright<4) {
				y--;
				x++;
				if (this.board[x][y]%2!=0  && this.board[x][y]%11==0 && this.getversion()==1) {
					upright=1;
					break;
				}
				if (this.board[x][y]%2!=0 && this.board[x][y]%11!=0) {upright++;}
				else {break;}
			}
			y=c.gety();
			x=c.getx();
			while (y!=15 && x!=0 && downleft<4) {
				y++;
				x--;
				if (this.board[x][y]%2!=0  && this.board[x][y]%11==0 && this.getversion()==1) {
					downleft=1;
					break;
				}
				if (this.board[x][y]%2!=0 && this.board[x][y]%11!=0) {downleft++;}
				else {break;}
			}
			y=c.gety();
			x=c.getx();
			
			
			if (right+left>=4 || up+down>=4 || upright+downleft>=4 || upleft+downright>=4) {
				
				int num=this.getgame().getlignes().size()+1;
				//axe horizontal
				if (right+left>=4) {
					for (int j=4;j>=0;j--) {
						if (right==j) {
							for (int i=0;i<=left+j-4;i++) {
								c.getcandidates().add(new Ligne(x-4+j-i,y,x+j-i,y,x,y,5,num));
							}
							break;
						}
						
					}
				}
				//axe vertical
				if (up+down>=4) {
					for (int j=4;j>=0;j--) {
						if (down==j) {
							for (int i=0;i<=up+j-4;i++) {
								c.getcandidates().add(new Ligne(x,y-4+j-i,x,y+j-i,x,y,3,num));
							}
							break;
						}
						
					}
				}
				//axes diagonaux
				if (downright+upleft>=4) {
					for (int j=4;j>=0;j--) {
						if (downright==j) {
							for (int i=0;i<=upleft+j-4;i++) {
								c.getcandidates().add(new Ligne(x-4+j-i,y-4+j-i,x+j-i,y+j-i,x,y,7,num));
							}
							break;
						}
						
					}
				}
				if (upright+downleft>=4) {
					for (int j=4;j>=0;j--) {
						if (upright==j) {
							for (int i=0;i<=downleft+j-4;i++) {
								c.getcandidates().add(new Ligne(x-4+j-i,y+4-j+i,x+j-i,y-j+i,x,y,11,num));
							}
							break;
						}
						
					}
				
				}
				this.board[x][y]=2;
				b=true;
				
				
			}
		}
		c.setetat(this.board[c.getx()][c.gety()]);
		return b;
	}
	
}

			
				
				
				
			