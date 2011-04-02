package main;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import salle.Salle;
import salle.SalleEntree;
import salle.SalleEpreuve;
import t2s.SIVOXDevint;

public class Partie implements Serializable{

    private int etat;
    private ArrayList<Salle> salles;
    private int salleCourante;
    private Mot mot; // le mot � deviner
    private int nbIndicesTrouves;
    private int difficulte;
    private String prenom; // le pr�nom du joueur
    private String propositionMot;
    private SIVOXDevint vox;	
	
    // etat vaut les valeurs suivantes :
    // 0 : menu principal
    // 1 : menu difficult�
    // 2 : ecran des scores
    // 3 : dans une salle (sauf la salle d'entr�e)
    // 4 : dans la salle d'entr�e
    // 5 : en plein dans une �preuve
    // 6 : en train de proposer un mot
    // 7 : en train de taper son pr�nom
	
    public Partie() {
	etat = 0;
	difficulte = 0;
	salles = new ArrayList<Salle>();
	salleCourante = 0;
	mot = new Mot();
	prenom="";
	nbIndicesTrouves=0;
	propositionMot="";
    }

    public void init() {
	// en d�but d'une nouvelle partie...
	prenom = "";
	propositionMot="";
	salles = new ArrayList<Salle>();
	nbIndicesTrouves=0;
	//cr�ation des salles
	salles.add(new SalleEntree());
	//salles d'�preuve : nom salle, fichier question, type �preuve,
	// couleur du texte, couleur du fond
	salles.add(new SalleEpreuve("la salle des rythmes.",
				    "../ressources/data/questions/rythmes"
				    +difficulte+".txt",1,new Color(0,0,128)
				    , new Color(128,128,255)));
	salles.add(new SalleEpreuve("la salle des m�lodies.","../ressources/data/questions/melodies"+difficulte+".txt",1,new Color(0,128,0),new Color(128,255,128)));
	salles.add(new SalleEpreuve("la salle des bruits.","../ressources/data/questions/bruits"+difficulte+".txt",2,new Color(128,0,0),new Color(255,128,128)));
	salles.add(new SalleEpreuve("la salle des instruments de musique.","../ressources/data/questions/instruments"+difficulte+".txt",2,new Color(128,128,0),new Color(255,255,128)));
	salles.add(new SalleEpreuve("la salle culture musicale.","../ressources/data/questions/culturemusicale"+difficulte+".txt",0,new Color(128,0,128),new Color(255,128,255)));
	salles.add(new SalleEpreuve("la salle culture g�n�rale.","../ressources/data/questions/culturegenerale"+difficulte+".txt",0,new Color(0,128,128),new Color(128,255,255)));
    }
	
    public String getPrenom() {
	return prenom;
    }
	
    public void ajouterLettrePrenom(String c) {
	prenom += c;		
    }
	
    public void enleverLettrePrenom() {
	if (prenom.length()>0)
	    prenom = prenom.substring(0,prenom.length()-1);
    }
	
    public void initPropositionMot() {
	propositionMot = "";
    }
	
    public String getPropositionMot() {
	return propositionMot;
    }
	
    public void ajouterLettrePropositionMot(String c) {
	propositionMot += c;		
    }
	
    public void enleverLettrePropositionMot() {
	propositionMot = propositionMot.substring(0,propositionMot.length()-1);
    }
	
    public void setdifficulte(int difficulte) {
	this.difficulte = difficulte;
    }
	
    public Salle getSalleCourante() {
	return salles.get(salleCourante);
    }
	
    public int getNbIndicesTrouves() {
	return nbIndicesTrouves;
    }
	
    public Mot getMot() {
	return mot;
    }
	
    public boolean isBonMot() {
	return (this.mot.getMot().equalsIgnoreCase(propositionMot));
    }
		
    public void allerADroite() {
	++salleCourante;
	if(salleCourante==7)
	    salleCourante=0;
    }
	
    public void allerAGauche() {
	--salleCourante;
	if(salleCourante==-1)
	    salleCourante=6;
    }
	
    public void incrNbIndicesTrouves() {
	++nbIndicesTrouves;
    }
	
    public boolean isInMenuPrincipal() {
	return (etat==0);
    }

    public boolean isInMenuDifficulte() {
	return (etat==1);
    }
	
    public boolean isInScores() {
	return (etat==2);
    }

    public boolean isInSalle() {
	return (etat==3);
    }
	
    public boolean isInSalleEntree() {
	return (etat==4);
    }
	
    public boolean isInEpreuve() {
	return (etat==5);
    }
	
    public boolean isInPropositionMot() {
	return (etat==6);
    }
	
    public boolean isInEntrerPrenom() {
	return (etat==7);
    }
	

    public void setMenuPrincipal() {
	etat=0;
    }

    public void setMenuDifficulte() {
	etat=1;
    }
	
    public void setScores() {
	etat=2;
    }

    public void setSalle() {
	etat=3;
    }
	
    public void setSalleEntree() {
	etat=4;
    }
	
    public void setEpreuve() {
	etat=5;
    }
	
    public void setPropositionMot() {
	etat=6;
    }
	
    public void setEntrerPrenom() {
	etat=7;
    }
	

    public void sauvegarder() throws IOException {
        //tester si le rep est pr�sent, le cr�er sinon
        File rep = new File("./sauvegarde");
        if (!rep.exists()) {rep.mkdir();}
        File f = new File("../ressources/sauvegarde/sauvegarde.txt");
        ObjectOutputStream oos = 
	    new ObjectOutputStream(new FileOutputStream(f,false));
        oos.writeObject(this);
        oos.close();
        System.out.println("Jeu sauvegard�");
    }


    public void charger() throws IOException, ClassNotFoundException {
	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("../ressources/sauvegarde/sauvegarde.txt"));
	Partie p = (Partie) ois.readObject();
	this.etat = p.etat;
	this.difficulte = p.difficulte;
	this.salles = p.salles;
	this.salleCourante = p.salleCourante;
	this.mot = p.mot;
	this.prenom=p.prenom;
	this.nbIndicesTrouves=p.nbIndicesTrouves;
	this.propositionMot=p.propositionMot;
    }

}
