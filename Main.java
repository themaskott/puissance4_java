import java.lang.System ;
import java.util.Arrays ;

public class Main {

    final static Integer TAILLEGRILLE = 10 ;

    public static void main(String[] args) throws Exception {
        

        // initailisation d une grille vide
        char [][] grille = Fonctions.initGrille(TAILLEGRILLE) ;
   
        // initialisation du joueur 1
        Joueur1 joueur1 = new Joueur1() ;

        // initialisation du joueur 2
        Joueur2 joueur2 = new Joueur2() ;

        // initialisation du jeu envoi des regles

        // affichage de la grille initiale
        joueur1.envoyerMessage(Fonctions.affichGrille(grille, TAILLEGRILLE)) ;

        int nbTour = 0 ;
        Integer colone ;
        // booleens de controle
        boolean winner = false ;
        boolean grillePleine = false ;

        System.out.println(joueur1.choixColone(grille, TAILLEGRILLE)) ;

    }
}
