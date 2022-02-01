import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class Jeumorpion extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	private Modele mod;
	
	
	
	//constructeur pour jouer nouvelle mod
	public Jeumorpion(int version) {
		super("Jeu Morpion");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		double x=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double y=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		if (x>800 && y>600) {
			this.setSize(800,600);
		}
		else {
			this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}
		this.setLocationRelativeTo(null);
		

		
		JPanel window=(JPanel)this.getContentPane();
		
		this.mod=new Modele(version);
		
		
		window.add(buildtb(),BorderLayout.NORTH);
		
		
		
		this.mod.getgame().addMouseListener(new MouseAdapter() {
			 
			 @Override
			public void mouseClicked(MouseEvent e) {

				for (Circle c :  mod.getgame().getcercles()) {
					
					if ((e.getButton() == 1) && c.contains(e.getX()-mod.getgame().getecart(), e.getY()-mod.getgame().getecart()) ){
						//on se trouve bien dans un cercle
						
						mod.click(c);
						repaint();
						}
			}
		}
		});	
		window.add(mod.getgame());

	
		
	}
	
	//constructeur pour charger une partie
	public Jeumorpion(String titre, int version,int [][] plateau) {
		super(titre);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		double x=Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double y=Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		if (x>800 && y>600) {
			this.setSize(800,600);
		}
		else {
			this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		}
		this.setLocationRelativeTo(null);

		
		JPanel window=(JPanel)this.getContentPane();
		this.mod=new Modele(version,plateau);
		
		
		window.add(buildtb(),BorderLayout.NORTH);
		
		
		
		this.mod.getgame().addMouseListener(new MouseAdapter() {
			 @Override
			public void mouseClicked(MouseEvent e) {
				for (Circle c :  mod.getgame().getcercles()) {
					
					if ((e.getButton() == 1) && c.contains(e.getX()-mod.getgame().getecart(), e.getY()-mod.getgame().getecart()) ){
						
						mod.click(c);
						repaint();
						}
			}
		}
		});	
		window.add(mod.getgame());

	
		
	}
	
	
	public Modele getmod() {return this.mod;}
	
	//pour recommencer le jeu
	private void startagain(ActionEvent event) {
		Jeumorpion interf= new Jeumorpion(this.mod.getversion());
		interf.setVisible(true);
		interf.mod.nextmove();
		this.dispose(); //on supp la fenetre actuelle et reste celle instancier au dessus
	}
	
	//retourmenu
	private void backhome(ActionEvent event) {
		this.dispose();
		Menu m=new Menu("Menu");
		m.setVisible(true);
	}
	
	
	private void autoButton(ActionEvent event) {this.ia();}
	
	public void ia() {
		
		this.mod.auto();
		this.mod.getgame().repaint();
		this.mod.nextmove();
		
	}
	
	
	private void tosave(ActionEvent event) { 
		this.mod.save("sauvegardes.txt");
	}
	
	private JToolBar buildtb() {
		JToolBar TB= new JToolBar();
		
		JButton home=new JButton("Menu");
		home.addActionListener(this::backhome);
		TB.add(home);
		
		JButton startagain=new JButton("Recommencer");
		startagain.addActionListener(this::startagain);
		TB.add(startagain);
		
		JButton help=new JButton("Aide");
		help.addMouseListener(new MouseAdapter() {
			 @Override
				public void mouseClicked(MouseEvent e) {
				 mod.getgame().help();
			 	}
		});
		
		TB.add(help);
		
		
		JButton save=new JButton("Sauvegarder");
		save.addActionListener(this::tosave);
		TB.add(save);

		JButton auto=new JButton("Auto");
		auto.addActionListener(this::autoButton);
		TB.add(auto);
		
		
	
		return TB;
		
	}
	
	

	
}
 