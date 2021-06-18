import java.lang.System ;
import java.util.Scanner ;

public class Joueur1 {
    char symbole ;

    public Joueur1(){
        this.symbole = 'X' ;
    }

    public void envoyerMessage(String message){
        System.out.println(message) ;
    }

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

    public Integer recevoirMessage(){
        Scanner usrInput = new Scanner(System.in) ;
        String message ;

        System.out.println("Entrez un entier ('FIN' pour arreter) :") ;
        message = usrInput.nextLine().toUpperCase() ;

        while ( !isNumeric(message) && !message.equals("FIN") ){
            System.out.println("La colone doit etre un entier") ;
            message = usrInput.nextLine() ;
        }
        if ( message.equals("FIN") ){
            return -1 ;
        }
        else{
            return Integer.parseInt(message) ;
        }
    }

    public Integer choixColone(char [][] grille, Integer size){
        boolean valid = false ;
        int colone ;
        System.out.println("Joueur 1 quelle colone ?") ;
        colone = this.recevoirMessage() ;

        while ( !valid ){
            if ( colone == -1 ){
                System.out.println("fin du jeu") ;
                System.exit(0) ;
                // colone = -1 ;
            }
            else{
                // pour le joueur colone dans [1, size]
                if ( colone > 0 && colone < size + 1){
                    if ( grille[0][colone-1] == '.' ){
                        valid = true ;
                    }
                    else{
                        System.out.println("Colone pleine, choisissez une autre colone") ;
                        colone = this.recevoirMessage() ;
                    }
                }
                else{
                    System.out.printf("La colone doit etre comprise entre 1 et %d\n", size) ;
                    colone = this.recevoirMessage() ;
                }
            }
        }
        return colone ;
    }
}
