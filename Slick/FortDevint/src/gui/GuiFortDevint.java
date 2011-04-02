package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

import main.Partie;
import salle.SalleEntree;
import salle.SalleEpreuve;

import t2s.SIVOXDevint;

public class GuiFortDevint extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	private JTextPane title;
	private JTextPane body;
	private JTextPane help;
	private Partie p;
	private Color backgroundColor;
	private Color fontColor;
        private SIVOXDevint voix;
	
	public GuiFortDevint() {
	    super("Fort DeViNT");
	    voix = new SIVOXDevint(1);
	    try {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		{
		    title = new JTextPane();
		    getContentPane().add(title, BorderLayout.NORTH);
		    title.setFont(new Font("Arial",0,72));
		    title.setFocusable(false);
		}
		{
		    body = new JTextPane();
		    getContentPane().add(body, BorderLayout.CENTER);
		    body.setFont(new Font("Arial",0,48));
		    body.setFocusable(false);
		}
		{
		    help = new JTextPane();
		    getContentPane().add(help, BorderLayout.SOUTH);
		    help.setFont(new Font("Arial",0,32));
		    help.setFocusable(false);
		}
		pack();
		this.setSize(800, 600);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    this.setSize(800,600);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    addKeyListener(this);
	    p = new Partie();
	}

	public void menuPrincipal() {
	    backgroundColor = new Color(255,255,0);
	    fontColor = new Color(0,0,255);
	    // toutes les fen�tres ont des zones title, body et help
	    title.setText("Menu Principal");
	    title.setBackground(new Color(255,0,0));
	    title.setBackground(backgroundColor);
	    title.setForeground(fontColor);
	    body.setText("\n\tF1. Nouvelle Partie\n" +
			 "\tF2. H�, H�, Reprendre la derni�re partie\n" +
			 "\tF3. Conna�tre le but du jeu\n" +
			 "\tF4. Savoir les commandes clavier\n");
	    body.setBackground(backgroundColor);
	    body.setForeground(fontColor);
	    help.setText("Utilisez les touches F1 � F4");
	    help.setBackground(backgroundColor);
	    help.setForeground(fontColor);
	    pack();
	}

    public void menudifficulte() {
	backgroundColor = new Color(0,255,0);
	fontColor = new Color(255,0,255);
	title.setText("Difficult� : ");
	title.setBackground(new Color(255,0,0));
	title.setBackground(backgroundColor);
	title.setForeground(fontColor);
	body.setText("\n\tF1. facile\n" +
		     "\tF2. moyen\n" +
		     "\tF3. difficile\n" +
		     "\tF4. commandes clavier\n" +
		     "\tF5. retour au menu d'accueil\n");
	body.setBackground(backgroundColor);
	body.setForeground(fontColor);
	help.setText("Utilisez les touches F1 � F5");
	help.setBackground(backgroundColor);
	help.setForeground(fontColor);
	pack();
    }
	
    public void entrerPrenom() {
	backgroundColor = new Color(255,0,0);
	fontColor = new Color(0,255,255);
	title.setText("Entrez votre pr�nom");
	title.setBackground(new Color(255,0,0));
	title.setBackground(backgroundColor);
	title.setForeground(fontColor);
	if(p.getPrenom().equals("")) body.setText("\n\n\n\n\n\n");
	else body.setText("\n\n"+p.getPrenom()+"\n\n\n");
	body.setBackground(backgroundColor);
	body.setForeground(fontColor);
	help.setText("Validez votre pr�nom avec <Entr�e>");
	help.setBackground(backgroundColor);
	help.setForeground(fontColor);
	pack();
    }
	
    public void salle(boolean parle) {
	if (parle) {
	    voix.playText("Vous entrez dans " 
			  + p.getSalleCourante().getDescription()
			  + ", "+p.getPrenom() +
			  ". tapez �fe 4 pour savoir les commandes clavier.");
	}
	backgroundColor = p.getSalleCourante().getBackColor();
	fontColor = p.getSalleCourante().getFontColor();
	title.setText(p.getSalleCourante().getDescription());
	title.setBackground(backgroundColor);
	title.setForeground(fontColor);
	body.setText("\n\n\n");
	body.setBackground(backgroundColor);
	body.setForeground(fontColor);
	if(p.getSalleCourante() instanceof SalleEntree) 
	    help.setText("Entr�e: pour donner le mot devin�"
			 +"\nfl�che droite ou gauche: changer de salle"
			 +"\nF1 : rappel des indices\tF4 : commandes clavier");
	else
	    help.setText("Entr�e: �couter l'�nigme"
			 +"\nfl�ches droite et gauche: changer de salle"
			 +"\nF1 : rappel des indices\tF4 : commandes clavier");
	help.setBackground(backgroundColor);
	help.setForeground(fontColor);
	pack();
    }
	
    public void epreuve() {
	backgroundColor = p.getSalleCourante().getBackColor();
	fontColor = p.getSalleCourante().getFontColor();
	title.setText(p.getSalleCourante().getDescription());
	title.setBackground(backgroundColor);
	title.setForeground(fontColor);
	body.setText(((SalleEpreuve)p.getSalleCourante()).getEpreuve().toString());
	body.setBackground(backgroundColor);
	body.setForeground(fontColor);
	help.setText("r�pondre avec F1, F2, F3 ou le pav� num�rique.");
	help.setBackground(backgroundColor);
	help.setForeground(fontColor);
	pack();
    }
	
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    //zone des interactions clavier    
    public void keyPressed(KeyEvent evt) {
	voix.stop();
	voix.setVoix(1);
	//sortir si escape (rajout� une ligne, on quitte si Escape
	if(evt.getKeyCode()==KeyEvent.VK_ESCAPE) dispose();
	//on est dans le menu principal, menu d'accueil
	if (p.isInMenuPrincipal()) {
	    switch (evt.getKeyCode()) {
	    case KeyEvent.VK_F1:
		p.setMenuDifficulte();
		menudifficulte();
		voix.playText("Vous devez choisir votre difficult�. "+
		       "Pour savoir les commandes clavier, tapez �fe 4."); 
		break;
	    case KeyEvent.VK_F2:
		try {p.charger();} catch(Exception e) {e.printStackTrace();}
		salle(true);
		break;
	    case KeyEvent.VK_F3:
		voix.playText("Le but du jeu est de deviner un mot." +
			      "On gagne des indices en r�pondant aux questions"
			      +" pos�es dans les 7 salles du fort devint.");
		break;
	    case KeyEvent.VK_F4:
		voix.setVoix(1);
		voix.playText("Les commandes clavier : "+
			      "�fe 1: commencer une partie." +
			      "�fe 2 : reprendre la derni�re partie." +
			      "�fe 3 : connaitre le but du jeu." +
			      "�fe 4 : r��couter ces instructions."
			      +"Echap, pour terminer");
		break;
	    }
	}
	// Choix de la difficult� de la partie	
	else if (p.isInMenuDifficulte()) {
	    switch (evt.getKeyCode()) {
	    case KeyEvent.VK_F1:
		voix.playText("Tapez votre pr�nom et validez avec entr�e.");
		p.setdifficulte(0);
		p.init();
		p.setEntrerPrenom();
		entrerPrenom();
		break;
	    case KeyEvent.VK_F2:
		voix.playText("Tapez votre pr�nom et validez avec entr�e.");
		p.setdifficulte(1);
		p.init();
		p.setEntrerPrenom();
		entrerPrenom();
		break;
	    case KeyEvent.VK_F3:
		voix.playText("Tapez votre pr�nom et validez avec entr�e.");
		p.setdifficulte(2);
		p.init();
		p.setEntrerPrenom();
		entrerPrenom();
		break;
	    case KeyEvent.VK_F4:
		voix.setVoix(1);
		voix.playText("Choisissez la difficult� :"+ 
			      "�fe 1 : partie facile." +
			      "�fe 2 : moins facile." +
			      "�fe 3 : difficile." +
			      "�fe 4 : rappel des commandes." +
			      "�fe 5 : retour au menu principal."
			      +"Echap, pour terminer");
		break;
	    case KeyEvent.VK_F5:
		p.setMenuPrincipal();
		menuPrincipal();
		voix.setVoix(3);
		voix.playText("Pr�t pour une nouvelle partie ?"+
			      "rappelez vous ! � tout instant, "+	  
			      "la touche �fe 4 donne les commandes clavier.");
		break;
	    }
	}
	// fen�tre d'acquisition du pr�nom du joueur
	else if (p.isInEntrerPrenom()) {
	    switch (evt.getKeyCode()) {
	    case KeyEvent.VK_ENTER:
		p.setSalleEntree();
		salle(true);
		try {
		    p.sauvegarder();
		    System.out.println("ok: "+p.getSalleCourante().getDescription());
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
		break;
	    case KeyEvent.VK_F4:
		voix.setVoix(4);
		voix.playText("BackSpace efface le dernier caract�re entr�,"+
			      "Entr�e valide le pr�nom.");
	    case KeyEvent.VK_BACK_SPACE:
		voix.playText("Effacer");
		p.enleverLettrePrenom();
		entrerPrenom();
		break;
	    default:
		String c = new Character(evt.getKeyChar()).toString();
		if(c!=null) {
		    if(c.equals("t")) {
			voix.playShortText("t�");
		    }
		    else if(c.equals("y")) {
			voix.playShortText("i grec");
		    }
		    else if(c.equals("s")) {
			voix.playShortText("�ce");
		    }
		    else voix.playShortText(c);
		    p.ajouterLettrePrenom(c);
		    entrerPrenom();
		}
		break;
	    }	
	}
	// dans la salle d'entr�e du Fort DeVint
	else if (p.isInSalleEntree()) {
	    switch (evt.getKeyCode()) {
	    case KeyEvent.VK_ENTER:
		voix.playText("Taper le mot myst�rieux.");
		p.initPropositionMot();
		p.setPropositionMot();
		break;
	    case KeyEvent.VK_LEFT:
		p.allerAGauche();
		if(p.getSalleCourante() instanceof SalleEntree)
		    p.setSalleEntree();
		else
		    p.setSalle();
		salle(true);
		try {
		    p.sauvegarder();
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
		break;
	    case KeyEvent.VK_RIGHT:
		p.allerADroite();
		if(p.getSalleCourante() instanceof SalleEntree)
		    p.setSalleEntree();
		else
		    p.setSalle();
		salle(true);
		try {
		    p.sauvegarder();
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
		break;
	    case KeyEvent.VK_F4:
		voix.setVoix(4);
		voix.playText("fl�ches droite et gauche : pour changer de salle. "+
			      "Entr�e : pour proposer un mot solution." +
			      "�fe 1: pour rappeler vos indices."
			      +"Echap, pour terminer");
		break;
	    case KeyEvent.VK_F1:
		String tmp;
		if(p.getNbIndicesTrouves()>0) {
		    tmp = "Voici les indices trouv�s.";
		    for(int i=0;i<p.getNbIndicesTrouves();++i) {
			tmp+="Indice " + (i+1) + " : " + p.getMot().getIndice(i) + ".";
		    }
		}
		else
		    tmp = "Pas encore d'indice";
		    voix.playText(tmp);
		break;
	    }	
	}
	// en salle th�matique
	else if (p.isInSalle()) {
	    switch (evt.getKeyCode()) {
	    case KeyEvent.VK_ENTER:
		if (((SalleEpreuve) p.getSalleCourante()).isEpreuveDejaAccomplie()) {
		    voix.playText("Vous avez d�j� r�solu cette question.");
		}
		else {
		    p.setEpreuve();
		    ((SalleEpreuve) p.getSalleCourante()).newEpreuve();
		    epreuve();
		    ((SalleEpreuve) p.getSalleCourante()).getEpreuve().prononcer(voix);
		}
		break;
	    case KeyEvent.VK_LEFT:
		p.allerAGauche();
		if(p.getSalleCourante() instanceof SalleEntree)
		    p.setSalleEntree();
		else
		    p.setSalle();
		salle(true);
		try {
		    p.sauvegarder();
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
		break;
	    case KeyEvent.VK_RIGHT:
		p.allerADroite();
		if(p.getSalleCourante() instanceof SalleEntree)
		    p.setSalleEntree();
		else
		    p.setSalle();
		salle(true);
		try {
		    p.sauvegarder();
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
		break;	
	    case KeyEvent.VK_F4:
		voix.setVoix(4);
		voix.playText("fl�ches droite ou gauche :  changer de salle."+
			      " entr�e : passer l'�preuve de cette pi�ce." +
			      "�fe 1 : �couter les indices."
			      +"Echap, pour terminer");
		break;
	    case KeyEvent.VK_F1:
		String tmp;
		if(p.getNbIndicesTrouves()>0) {
		    tmp = "Voici les indices trouv�s.";
		    for(int i=0;i<p.getNbIndicesTrouves();++i) {
			tmp+="Indice " + (i+1) + " : " + p.getMot().getIndice(i) + ".";
		    }
		}
		else
		    tmp = "Vous n'avez pas encore trouv� d'indice";
		    voix.playText(tmp);
		break;	
	    }	
	}
	//durant l'�preuve
	else if(p.isInEpreuve()) {
	    boolean oui= false;
	    switch (evt.getKeyCode()) {
	    case KeyEvent.VK_F1:
		// Verif de la bonne r�p
		if(((SalleEpreuve) p.getSalleCourante()).getEpreuve().bonneReponse(1)){oui=true;}
		break;
	    case KeyEvent.VK_F2:
		// Verif de la bonne r�p
		if(((SalleEpreuve) p.getSalleCourante()).getEpreuve().bonneReponse(2)){oui=true;}
		break;
	    case KeyEvent.VK_F3:
		// Verif de la bonne r�p
		if(((SalleEpreuve) p.getSalleCourante()).getEpreuve().bonneReponse(3)){oui=true;}
		break;
	    }
	    System.out.println("Oui ? "+oui);
	    if (oui){
		voix.playText("Bravo, c'est la r�ponse, voici un indice, " 
			      + p.getMot().getIndice(p.getNbIndicesTrouves()));
		((SalleEpreuve) p.getSalleCourante()).setAccomplie();
		p.incrNbIndicesTrouves();
		p.setSalle();
		salle(false);
		try {
		    p.sauvegarder();
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
	    } else {
		p.setSalle();
		salle(false);
		voix.playText("Ce n'est pas la bonne r�ponse."+
			      " Pour recommencer, presser entr�e.");
		try {
		    p.sauvegarder();
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	else if (p.isInPropositionMot()) {
	    switch (evt.getKeyCode()) {
	    case KeyEvent.VK_ENTER:
		voix=new SIVOXDevint(2);
		if(p.isBonMot()){
		    voix.playText("F�licitations, " + p.getPrenom() 
				  + ", tu as gagn� !"+
				  "Pour recommencer, tapes �fe5");

		}
		else {
		    voix.playText("Non, ce n'est pas le mot myst�re.");
		    p.setSalleEntree();
		    salle(false);
		    try { p.sauvegarder(); }
		    catch(IOException e) {
			e.printStackTrace();
		    }
		}
		break;
	    case KeyEvent.VK_F5:
		    p.setMenuPrincipal();
		    p = new Partie();
		    menuPrincipal();
		    voix.setVoix(3);
		voix.playText("D'accord pour une nouvelle partie."+
			      "et rappelez vous ! � tout instant, "+	  
			      "la touche �fe 4 donne les commandes clavier.");
		    break;
	    case KeyEvent.VK_BACK_SPACE:
		voix.playShortText("Effacer");
		p.enleverLettrePropositionMot();
		break;
	    default:
		String c = new Character(evt.getKeyChar()).toString();
		if(c!=null) {
		    if(c.equals("t")) {
			voix.playShortText("t�");
		    }
		    else if(c.equals("y")) {
			voix.playText("i grec");
		    }
		    else if(c.equals("s")) {
			voix.playShortText("�ce");
		    }
		    else
			voix.playText(c);
		    p.ajouterLettrePropositionMot(c);
		}
		break;
	    }	
	}
    }
	
    public void keyTyped(KeyEvent evt) {
    }

    public void keyReleased(KeyEvent evt) {
    }
	

}
