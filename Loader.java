import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Loader extends JFrame {
	
	
	 
	private static final long serialVersionUID = 1L;
	private JPanel window;
	
	public Loader(String s, String type) {
		super(s);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		
		window=(JPanel)this.getContentPane();
		window.setLayout(new FlowLayout());
		this.loadgames(type);
		
	}
	
	
	public void loadgames(String type) {
		int num=0;
		try {


			   File file = new File(type);
			   FileReader f= new FileReader(file);
			   BufferedReader br = new BufferedReader(f);
			   
			   String line=br.readLine();
			   String nom;
			   while (line != null) {
				   	  num++;
				   	  nom=line.split(" ")[0];
				   	  nom=nom.concat(" : ");
				   	  nom=nom.concat(line.split(" ")[2]);
			    	  addSavedgame(line,num,nom);
			    	  line=br.readLine();
			   }
			   br.close();
			   f.close(
					   );
		}
			   
			  catch (FileNotFoundException e) {
				     
				    e.printStackTrace();
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
	}
	
	public void loadgame(String line, String nom) {
		int [][] plateau=new int [16][16];
		HashSet<Ligne> loadline=new HashSet<Ligne>(); 
		int s=3;
		String [] als=line.split(" ");
		
		for (int i=0;i<16;i++) {
			for (int j=0;j<16;j++) {
				plateau[i][j]=Integer.valueOf(als[s++]);
			}
		}
		int x1,x2,y1,y2,x0,y0,num;
		
		while(s<als.length) {
			x1=Integer.valueOf(als[s++]);
			y1=Integer.valueOf(als[s++]);
			x2=Integer.valueOf(als[s++]);
			y2=Integer.valueOf(als[s++]);
			x0=Integer.valueOf(als[s++]);
			y0=Integer.valueOf(als[s++]);
			num=Integer.valueOf(als[s++]);
			
			loadline.add(new Ligne(x1,y1,x2,y2,x0,y0,1,num)); //le 1 ne nous servira pas, cela est faux mais pas important
		}
		Jeumorpion interf= new Jeumorpion(String.valueOf(line.split(" ")[0]),Integer.valueOf(line.charAt(1)),plateau);
		interf.setVisible(true);
		interf.getmod().getgame().setlines(loadline);
		interf.getmod().setendgame(true);
		interf.getmod().nextmove();
		
	}

	public void addSavedgame(String ligne,int num,String nom) {
		
		JButton b=new JButton(nom);
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadgame(ligne,nom);
				dispose();


				
			}
			
			
		});
		window.add(b);
	}

}
