package main;
import gui.GuiFortDevint;

import t2s.SIVOXDevint;

public class Main {

    private static SIVOXDevint voix;

	public static void main(String args[]) {
		GuiFortDevint guiFD = new GuiFortDevint();
		voix=new SIVOXDevint(1);
		voix.playText("Bienvenue au fort devint."+
			"vous �tes dans le menu principal"+	  
			"Pour savoir les commandes clavier, tapez :  �fe 4.");
		guiFD.menuPrincipal();
	}
}
