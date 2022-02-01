import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Menu extends  JFrame{
	
	
	private static ScheduledFuture<?> t; //Pour arreter IA
	private static ArrayList<Integer> highscores; //valeur des highscores
	private static int lasthighscore;
	private static final long serialVersionUID = 1L;

	public Menu(String a) {		
		super(a);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		
		highscores=new ArrayList<Integer>();
		this.hs();
		JPanel window=(JPanel)this.getContentPane();
		window.setLayout(new FlowLayout());
		
		
		JButton d=new JButton("PLAY 5D");
		d.addActionListener(this::cinqd);
	
		window.add(d);
		
		JButton t=new JButton("PLAY 5T");
		t.addActionListener(this::cinqt);
		window.add(t);
		
		JButton iad=new JButton("IA 5D");
		iad.addActionListener(this::iacinqd);
		window.add(iad);
		
		JButton iat=new JButton("IA 5T");
		iat.addActionListener(this::iacinqt);
		window.add(iat);
		
		JButton load=new JButton("LOAD");
		load.addActionListener(this::load);
		window.add(load);
		
		JButton h=new JButton("HIGHSCORES");
		h.addActionListener(this::highscore);
		
		window.add(h);
		}
	
	static public ArrayList<Integer> gethighscores(){
		return highscores;
	}
	
	static public int getlasthighscore(){
		return lasthighscore; //le score a battre
	}
	
	static public void stop() {
		//arrete l'automatisation
		t.cancel(true);
	}
	public void  hs() { //initialise la liste des highscores pour pouvoir comparer avec un potentiel nouvel highscore
		String [] split;
		lasthighscore=0;
		try {
		
			File file = new File("highscores.txt");
			FileReader f=new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			String l=br.readLine();
			int c=0;
			while(l!=null && c<10) {
			
				split=l.split(" ");
		
				highscores.add(Integer.valueOf(split[2]));
				if (Integer.valueOf(split[2])>lasthighscore) {
					lasthighscore=Integer.valueOf(split[2]);
				}
				l=br.readLine();
			
			}
			
			br.close();
			f.close();
		
			
		}
		catch (FileNotFoundException e) {
		     
		    e.printStackTrace();
		    
		    } 
		catch (IOException e) {
	   e.printStackTrace();
	  }
		
	}
	
	
	private void load(ActionEvent event) {
		Loader l= new Loader("Load","sauvegardes.txt");
		l.setVisible(true);
	}
	
	private void highscore(ActionEvent event) {
		Loader l= new Loader("Highscores","highscores.txt");
		l.setVisible(true);
	}
	


	private void cinqd(ActionEvent event) {
		Jeumorpion interf=new Jeumorpion(0);
		interf.setVisible(true);
		interf.getmod().nextmove();
	}
	
	private void cinqt(ActionEvent event) {
		Jeumorpion interf= new Jeumorpion(1);
		interf.setVisible(true);
		interf.getmod().nextmove();
	}
	
	private void iacinqd(ActionEvent event) { //https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
		Jeumorpion interf= new Jeumorpion(0);
		interf.setVisible(true);
		interf.getmod().nextmove();
		final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		t=executor.scheduleAtFixedRate(interf::ia, 0, 100, TimeUnit.MILLISECONDS);
	}
	
	
	
	
	private void iacinqt(ActionEvent event) {
		Jeumorpion interf= new Jeumorpion(1);
		interf.setVisible(true);
		interf.getmod().nextmove();
		final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		t=executor.scheduleAtFixedRate(interf::ia, 0, 100, TimeUnit.MILLISECONDS);
	}
	
}
