import java.lang.System ;
import java.util.Scanner ;
import java.io.* ;

public class Fonctions {

    // Nom : initialiserJeu
    // But : affichage du menu, creation ou reprise d une partie
    // Entree : taille de la grille pour l initialisation d une nouvelle grille et le nom du fichier de sauvegarde
    // Sortie : un objet "SupportJeu" contenant les donnees d initialisation
    // Fichier : 1re ligne = taille de la partie / x lignes = 'symbole colone'
    public static SupportJeu initialiserJeu( Integer size, String filename ){
        SupportJeu gameDatas ;
        String menu ;
        Scanner usrInput = new Scanner( System.in)  ;
        String choix ;
        int prochainJoueur ;
        int nbTour ;
        char [][] grille ;

        menu = "\tJeu de puissance 4\n\tVous souhaitez :\n\t1 - Une nouvelle partie\n\t2 - Reprendre la derniere partie\n\t3 - Quitter le jeu\n\t>>>" ;

        System.out.println( menu ) ;
        choix = usrInput.nextLine() ;

        if ( choix.equals("1") ){  // nouvelle partie
            try{
                FileOutputStream output = new FileOutputStream( filename ) ;
                OutputStreamWriter fichierSauvegarde = new OutputStreamWriter( output, "UTF-8" ) ;
                fichierSauvegarde.write( Integer.toString(size) + "\n " ) ;
                prochainJoueur = getRandomInt(1, 2) ;
                nbTour = 0 ;         
                grille = initGrille(size) ;

                gameDatas = new SupportJeu( grille, fichierSauvegarde, prochainJoueur, nbTour ) ;

                return gameDatas ;
            }
            catch( IOException error ) {
                error.printStackTrace() ; 
            }
        }
        else if ( choix.equals("2") ){  // reprise de la sauvegarde
            nbTour = 0 ;
             try{
                FileInputStream fichierSauvegarde = new FileInputStream( filename ) ;
                Scanner scanner = new Scanner( fichierSauvegarde ) ;

                // lit la 1re ligne = taille de la grille et cree une grille vide de cette taille
                size = Integer.parseInt( scanner.nextLine() ) ;
                grille = initGrille( size ) ;

                String[] tmp ;
                char [] symbole = {' '} ;
                Integer colone ;
                while( scanner.hasNextLine() ){  // lecture du fichier ligne a ligne
                    nbTour += 1 ;
                    tmp = scanner.nextLine().split(" ") ;
                    symbole = tmp[0].toCharArray(); // forcer la conversion du symbole string vers char
                    colone = Integer.parseInt( tmp[1] ) ; // conversion de la colone string vers int
                    grille = positionnerPion(grille, size, colone, symbole[0]) ;
                }
                scanner.close() ;
                
                // verification du dernier joueur a avoir joue
                if ( symbole[0] == 'X' ){ prochainJoueur = 2 ; }
                else { prochainJoueur = 1 ; }
                    
                // reouvertur du fichier de sauvegarde en mode ajout
                FileOutputStream output = new FileOutputStream(filename, true) ;
                OutputStreamWriter fichierSauvegarde2 = new OutputStreamWriter(output, "UTF-8") ;

                gameDatas = new SupportJeu(grille, fichierSauvegarde2, prochainJoueur, nbTour) ;
                return gameDatas ;

            }
            catch ( IOException error ){
                error.printStackTrace() ;
            }
        }
        else {
            System.exit(0) ;
        }
             
        return null ;

    }

    // Nom : affichJeu
    // But : Affichage des regles du jeu
    // Entree : neant
    // Sortie : une chaine de caracteres = les regles du jeu a afficher
    public static String affichJeu( Integer nbPions ) {
        String message = String.format( "\tJeu de puissance 4\n\tJoueur 1 joue avec les X\n\tJoueur 2 joue avec les O\n\tLe premier a aligner %d  pions gagne :)\n", nbPions ) ;
        return message ;
    }
    
    // Nom : iniGrille
    // But : initialise un grille de jeu vide
    // Entree : un entier n, taille de la grille
    // Sortie : un tableau n x n rempli de "."
    public static char [][] initGrille( Integer size ){
        char [][] grille = new char [size][size] ;
        for ( int i = 0 ; i < size ; i++ ){
            for ( int j = 0 ; j < size ; j++ ){
                grille[i][j] = '.' ;
            }
        }
        return grille ;
    }

    // Nom : affichGrille
    // But : Affichage la grille
    // Entree : nom de la grille et sa taille
    // Sortie : une chaine de caracteres = la grille prete a son affichage
    // Avec au dessous les numeros des colones
    public static String affichGrille( char [][] grille, Integer size ){
        String grilleAafficher = "";
        
        for( char [] ligne: grille){
            for ( char c: ligne){
                grilleAafficher += c + " " ;
            }
            grilleAafficher += "\n" ;
        }
        for ( int i = 1 ; i < size +1 ; i++ ){
            grilleAafficher += Integer.toString(i) + " " ;
        }
        grilleAafficher += "\n" ;
        return grilleAafficher ;
    }

    // Nom : positionnerPion
    // But : insere un pion dans la colone designee
    // Entree : la grille, sa taille, la colone a remplir et le symbole du joueur
    // Sortie : revoie une grille completee d un symbole
    public static char [][] positionnerPion ( char[][] grille, Integer size, Integer colone, char symbole ){   
        int l = 0 ;
        while( grille[l][colone] == '.' ){
            if ( l == size - 1){
                break ;
            }
            else {
                l += 1 ;
            }
        }

        if (( l == size - 1 ) && grille[l][colone] == '.' ){
            grille[l][colone] = symbole ;
        }
        else{
            grille[l-1][colone] = symbole ;
        }
        return grille ;
    }

    // Nom : testVictoire
    // But : a partir des coordonnees du dernier pion insere, test les conditions de voctoire
    // Entree : la grille, sa taille, ligne et colone du dernier pion, le symbole du joueur, le nb de pions a aligner pour gagner
    // Sortie : revoie un booleen a True en cas de victoire 
    public static boolean testVictoire( char [][] grille, Integer size, Integer colone, char symbole, Integer nbPions ){
        boolean winner = false ;
        String motifWinner = "" ;
        String motifLigne = "" ;
        String motifColone = "" ;
        String motifDiag1 = "" ;
        String motifDiag2 = "" ;

        // concatenation de nbPions symboles
        for ( int i = 0 ; i < nbPions ; i++ ){ motifWinner += symbole ; }

        // concatenation des symboles de la colone
        for ( int i = 0 ; i < size ; i++){ motifColone += grille[i][colone] ; }

        // trouver la ligne du dernier pion insere dans cette colone
        int ligne = 0 ;
        while( grille[ligne][colone] == '.' ){  ligne += 1 ; }
        
        // concatenation des syuboles de cette ligne
        for ( int i = 0 ; i < size ; i++ ){ motifLigne += grille[ligne][i] ; }

        // diagonale 1
        // concatenation de nbPions en diagonale de part et d autre du pion ligne, colone
        // haut/gauche -> bas/droite

        motifDiag1 += symbole ;
        for ( int i = 1 ; i < nbPions ; i++ ){
            if ( (ligne + i < size ) && ( colone + i < size ) ){
                motifDiag1 = motifDiag1 + grille[ligne+i][colone+i] ;
            }
        }
        for ( int i = 1 ; i < nbPions ; i++ ){
            if ( (ligne - i > -1 ) && ( colone - i > -1 ) ){
                motifDiag1 = grille[ligne-i][colone-i] + motifDiag1 ;
            }
        }
        
        // diagonale 2
        // concatenation de nbPions en diagonale de part et d autre du pion ligne, colone
        // bas/gauche -> haut/droite  
        
        motifDiag2 += symbole ;
        for ( int i = 1 ; i < nbPions ; i++ ){
            if ( (ligne - i > -1 ) && ( colone + i < size )){
                motifDiag2 = motifDiag2 + grille[ligne-i][colone+i] ;
            }
        }
        for ( int i = 1 ; i < nbPions ; i++ ){
            if ( (ligne + i < size ) && ( colone - i > -1 )){
                motifDiag2 = grille[ligne+i][colone-i] + motifDiag2 ;
            }
        }
        if ( motifColone.contains(motifWinner) || motifLigne.contains(motifWinner) || motifDiag1.contains(motifWinner) || motifDiag2.contains(motifWinner) ){
            winner = true ;
        }
        return winner ;
    }

    // Nom : testGrillePleine
    // But : cherche la presence d une case libre dans la gille (ie ligne du haut suffit)
    // Entree : la grille
    // Sortie : un booleen
    public static boolean testGrillePleine( char [][] grille, Integer size ){
        boolean pleine = true ;
        // il est juste besoin de tester la premiere ligne
        for ( int i = 0 ; i < size ; i++ ){
            if ( grille[0][i] == '.'){
                pleine = false ;
            }   
        }
        return pleine ;
    }
    
    // Nom : envoyerMessage
    // But : envoie le meme message aux deux joueurs
    // Entree : les deux objets joueurs et le message a envoyer
    // Sortie : neant
    public static void envoyerMessage( Joueur1 joueur1, Joueur2 joueur2, String message ){
        joueur1.envoyerMessage(message) ;
        joueur2.envoyerMessage(message) ;
    }

    // Nom : Result
    // But : affiche le resultat de la partie
    // Entree : joueur ayant potentiellement gagne, nb de coup joues
    // Sortie : une chaine de caracteres a afficher correspandant au resultat de la partie
    // Attention : toute la grille peut etre pleine et personne ne gagne
    public static String Result(boolean winner, Integer joueur, Integer nbTour){
        Integer nbCoup = 0 ; 
        if ( nbTour % 2 == 0 ){
            nbCoup = nbTour / 2 ;
        }
        else{
            nbCoup = nbTour / 2 + 1 ;
        }
        if ( winner ){
            return "Victoire du joueur " + Integer.toString(joueur) + " en " + Integer.toString(nbCoup) + " coups" ;
        }
        else{
            return "Egalite, pas de vainqueur" ;
        }
    }

    // Nom : finDuJeu
    // But : clore la partie en cas de victoire, egalite ou arret par l un des joueurs
    // Entree : les deux objets joueurs, le descripteur de fichier a clore, et le message a envoyer
    // Sortie : neant
    public static void finDeJeu( Joueur1 joueur1, Joueur2 joueur2, String message, OutputStreamWriter fichierSauvegarde ) throws Exception {
        Fonctions.envoyerMessage(joueur1, joueur2, message) ;
        joueur2.shutDownSocket() ;
        fichierSauvegarde.close() ;
        System.exit(0) ;
    }

    // Nom : getRandomInt
    // But : fournir un entier aleatoire (ici 1 ou 2 correspondant au numero du joueur)
    // Entree : deux entiers, bornes min et max de l intervalle
    // Sortie : un entier (1 ou 2)   
    public static int getRandomInt(int min, int max) {
        // definir l intervalle
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min ;
        return rand ;
    }

}
