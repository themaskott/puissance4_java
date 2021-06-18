import java.lang.System ;

public class Fonctions {

    public static String affichJeu(Integer nbPions){
        String message = String.format("\tJeu de puissance 4\n\tJoueur 1 joue avec les X\n\tJoueur 2 joue avec les O\n\tLe premier a aligner %d  pions gagne :)\n", nbPions) ;
        return message ;
    }
    
    public static char [][] initGrille( Integer size ){
        char [][] grille = new char [size][size] ;
        for ( int i = 0 ; i < size ; i++ ){
            for ( int j = 0 ; j < size ; j++ ){
                grille[i][j] = '.' ;
            }
        }
        return grille ;
    }

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
                System.out.println(Integer.toString(i));
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

}
