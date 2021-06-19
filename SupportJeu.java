import java.io.OutputStreamWriter ;

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
