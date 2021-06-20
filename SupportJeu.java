import java.io.OutputStreamWriter ;


    // Nom : supportJeau
    // But : classe utilisee pour l'initialisation du jeu et le suivi de la grille, des sauvegardes et des tours joués
    // créée pour contourner l'impossibilite en java de retourner plusieurs variables par une fonction
    // en l occurrence initialiserJeu()


public class SupportJeu {
    char [][] grille ;
    OutputStreamWriter fichierSauvegarde ;
    int prochainJoueur ;
    int nbTour ;

    public SupportJeu( char [][] grille, OutputStreamWriter fichierSauvegarde, int prochainJoueur, int nbTour ){
        this.grille = grille ;
        this. fichierSauvegarde = fichierSauvegarde ;
        this.prochainJoueur = prochainJoueur ;
        this.nbTour = nbTour ;      

    }

}
