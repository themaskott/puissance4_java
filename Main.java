import java.lang.System ;
import java.util.Arrays ;
import java.lang.Math;
import java.net.* ;
import java.io.* ;


public class Main {
    //declaration de constantes
    final static Integer TAILLEGRILLE = 5 ;
    final static Integer NBPIONSVICTOIRE = 4 ;

    final static int PORT = 4444 ;

    public static void main(String[] args) throws Exception {

        Joueur1 joueur1 ;
        Joueur2 joueur2 ;
        char grille [][] ;
        String grilleAafficher ;
        String msgFinal ;

        // initialisation du joueur 1
        joueur1 = new Joueur1() ;

        // initailisation d une grille vide
        grille = Fonctions.initGrille(TAILLEGRILLE) ;
        grilleAafficher = Fonctions.affichGrille(grille, TAILLEGRILLE) ;

        try{
            // initialisation du socket en attente du joueur 2
            ServerSocket socketServeur = new ServerSocket(PORT);
            System.out.println("En attente du joueur 2");
            Socket socketClient = socketServeur.accept();   
            joueur2 = new Joueur2(socketClient);
            joueur2.start();

            // envoi des regles aux deux joueurs
            Fonctions.envoyerMessage( joueur1, joueur2, Fonctions.affichJeu(NBPIONSVICTOIRE) );

            // affichage de la grille initiale aux deux joeurs
            Fonctions.envoyerMessage( joueur1, joueur2, grilleAafficher ) ;

            int nbTour = 0 ;
            Integer colone ;
            char symbole = '.' ;

            // tirage du joueur a debuter
            int numJoueur = Fonctions.getRandomInt(1, 2) ;
            Fonctions.envoyerMessage(joueur1, joueur2, "Le joueur " + Integer.toString(numJoueur) + " joue en premier") ;
            
            // booleens de controle
            boolean winner = false ;
            boolean grillePleine = false ;


            while ( !winner && !grillePleine ){
                nbTour += 1 ;
                numJoueur = numJoueur % 2 ;

                if ( numJoueur == 1 ){
                    joueur2.envoyerMessage("En attente d un mouvement du joueur 1")	;
			        colone = joueur1.choixColone(grille, TAILLEGRILLE) ;
                    if ( colone == - 1){
                        // -1 sert de flag pour l arret du jeu
                        Fonctions.finDeJeu( joueur1, joueur2, "Le joueur 1 a interrompu la partie") ;
                        System.exit(0) ;
                    }
                    else{
                        colone -= 1 ; // Le joueur choisi une colone entre 1 et taille -> -1 pour revenir entre 0 et taille-1
                        symbole = joueur1.symbole ;
                    }
                }
                else{
                    joueur1.envoyerMessage("En attente d un mouvement du joueur 2")	;
			        colone = joueur2.choixColone(grille, TAILLEGRILLE) ;
                    if ( colone == - 1){
                        // -1 sert de flag pour l arret du jeu
                        Fonctions.finDeJeu( joueur1, joueur2, "Le joueur 2 a interrompu la partie") ;
                        System.exit(0) ;
                    }
                    else{
                        colone -= 1 ; // Le joueur choisi une colone entre 1 et taille -> -1 pour revenir entre 0 et taille-1
                        symbole = joueur2.symbole ;
                    }
                }
                grille = Fonctions.positionnerPion(grille, TAILLEGRILLE, colone, symbole) ;
                grilleAafficher = Fonctions.affichGrille(grille, TAILLEGRILLE) ;
                Fonctions.envoyerMessage(joueur1, joueur2, grilleAafficher) ;
                winner = Fonctions.testVictoire(grille, TAILLEGRILLE, colone, symbole, NBPIONSVICTOIRE) ;
                grillePleine = Fonctions.testGrillePleine(grille, TAILLEGRILLE) ;

                numJoueur += 1 ;
            }

            msgFinal = Fonctions.Result(winner, numJoueur, nbTour ) ;
            Fonctions.finDeJeu( joueur1, joueur2, msgFinal ) ;
        }
        catch (Exception e) {
            e.printStackTrace();
          }
    }
}
