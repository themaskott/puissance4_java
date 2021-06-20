import java.lang.System ;
import java.util.Scanner ;


// Nom : Joueur1
// Class gestion du Joueur 1
// comprend les fonctions necessaire pour interroger le joueur 1 et recuperer ses reponses



public class Joueur1 {
    char symbole ;

    public Joueur1(){
        this.symbole = 'X' ;
    }

    // Nom : envoyerMessage
    // But : envoi d un message au joueur 1
    // Entree : le message (string)
    // Sortie : neant
    public void envoyerMessage(String message){
        System.out.println(message) ;
    }

    // Nom : isNumeric
    // But : test si une entree de type string peut être consideree comme un entier
    // Entree : une chaine de caracteres
    // Sortie : booleen
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // Nom : recevoirMessage
    // But : reception d un message du joueur 1, ici un entien (colone a jouer) ou 'fin'
    // Entree : neant
    // Sortie : le message ( string )
    public Integer recevoirMessage(){
        Scanner usrInput = new Scanner(System.in) ;
        String message ;

        this.envoyerMessage("Entrez un entier ('FIN' pour arreter) :") ;
        message = usrInput.nextLine().toUpperCase() ;

        while ( !isNumeric(message) && !message.equals("FIN") ){
            this.envoyerMessage("La colone doit etre un entier") ;
            message = usrInput.nextLine() ;
        }
        if ( message.equals("FIN") ){
            return -1 ;
        }
        else{
            return Integer.parseInt(message) ;
        }
    }

    // Nom : choixColone
    // But : Demande au joueur la colone a remplir et vérifie si l'entree est correcte et disponible au jeu
    // Entree : la grille, sa taille, le numero du joueur
    // Sortie : retourne la colone choisie par le joueur
    // verifie qu au moins une case est disponible dans la grille et que le numero correspond au range de la taille
    // possibilite d arreter la partie avec 'FIN' --> -1
    public Integer choixColone(char [][] grille, Integer size){
        boolean valid = false ;
        int colone ;
        this.envoyerMessage("Joueur 1 quelle colone ?") ;
        colone = this.recevoirMessage() ;

        while ( !valid ){
            if ( colone == -1 ){
                colone = -1 ;
                valid = true ;
            }
            else{
                // pour le joueur colone dans [1, size]
                if ( colone > 0 && colone < size + 1){
                    if ( grille[0][colone-1] == '.' ){
                        valid = true ;
                    }
                    else{
                        this.envoyerMessage("Colone pleine, choisissez une autre colone") ;
                        colone = this.recevoirMessage() ;
                    }
                }
                else{
                    this.envoyerMessage("La colone doit etre comprise entre 1 et " + Integer.toString(size)) ;
                    colone = this.recevoirMessage() ;
                }
            }
        }
        return colone ;
    }
}
