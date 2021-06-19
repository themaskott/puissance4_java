// Jeu de puissance 4
// Joueur 1 en local
// Joueur 2 via le reseau : nc 127.0.0.1 4444

// @Maskott
// 18/06/2021


import java.lang.System ;
// import java.util.Arrays ;
// import java.lang.Math;
import java.net.* ;
// import java.io.* ;

// A faire : fonction menu initial
// A faire : sauvegarde fichier i/o
// commentaire des fonctions


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
        SupportJeu gameDatas ;

        // initialisation de la partie 
        gameDatas = Fonctions.initialiserJeu(TAILLEGRILLE, "save.txt") ;


        // initialisation du joueur 1
        joueur1 = new Joueur1() ;

        // recuperation de la grille pour son affichage
        grilleAafficher = Fonctions.affichGrille(gameDatas.grille, TAILLEGRILLE) ;

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

            Integer colone ;
            char symbole = '.' ;

            // recuperation du num du joueur a debuter
            int numJoueur = gameDatas.prochainJoueur ;
            Fonctions.envoyerMessage(joueur1, joueur2, "Le joueur " + Integer.toString(numJoueur) + " joue en premier") ;
            
            // booleens de controle
            boolean winner = false ;
            boolean grillePleine = false ;


            while ( !winner && !grillePleine ){
                gameDatas.nbTour += 1 ;
                numJoueur = numJoueur % 2 ;

                if ( numJoueur == 1 ){
                    joueur2.envoyerMessage("En attente d un mouvement du joueur 1")	;
			        colone = joueur1.choixColone(gameDatas.grille, TAILLEGRILLE) ;
                    if ( colone == - 1){
                        // -1 sert de flag pour l arret du jeu
                        Fonctions.finDeJeu( joueur1, joueur2, "Le joueur 1 a interrompu la partie", gameDatas.fichierSauvegarde ) ;
                        System.exit(0) ;
                    }
                    else{
                        colone -= 1 ; // Le joueur choisi une colone entre 1 et taille -> -1 pour revenir entre 0 et taille-1
                        symbole = joueur1.symbole ;
                    }
                }
                else{
                    joueur1.envoyerMessage("En attente d un mouvement du joueur 2")	;
			        colone = joueur2.choixColone(gameDatas.grille, TAILLEGRILLE) ;
                    if ( colone == - 1){
                        // -1 sert de flag pour l arret du jeu
                        Fonctions.finDeJeu( joueur1, joueur2, "Le joueur 2 a interrompu la partie", gameDatas.fichierSauvegarde ) ;
                        System.exit(0) ;
                    }
                    else{
                        colone -= 1 ; // Le joueur choisi une colone entre 1 et taille -> -1 pour revenir entre 0 et taille-1
                        symbole = joueur2.symbole ;
                    }
                }

                // sauvegarde du coup dans le fichier
                gameDatas.fichierSauvegarde.write( symbole + " " + Integer.toString(colone) + "\n" ) ;

                gameDatas.grille = Fonctions.positionnerPion(gameDatas.grille, TAILLEGRILLE, colone, symbole) ;
                grilleAafficher = Fonctions.affichGrille(gameDatas.grille, TAILLEGRILLE) ;
                Fonctions.envoyerMessage(joueur1, joueur2, grilleAafficher) ;
                winner = Fonctions.testVictoire(gameDatas.grille, TAILLEGRILLE, colone, symbole, NBPIONSVICTOIRE) ;
                grillePleine = Fonctions.testGrillePleine(gameDatas.grille, TAILLEGRILLE) ;

                numJoueur += 1 ;
            }

            msgFinal = Fonctions.Result(winner, numJoueur, gameDatas.nbTour ) ;
            Fonctions.finDeJeu( joueur1, joueur2, msgFinal, gameDatas.fichierSauvegarde ) ;
            socketServeur.close() ;

        }
        catch (Exception e) {
            e.printStackTrace();
          }
    }
}
