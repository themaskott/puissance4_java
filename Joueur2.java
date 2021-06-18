import java.net.* ;
import java.io.* ;

public class Joueur2 extends Thread {
    char symbole ;
    Socket socket ;
    BufferedReader in ;
    PrintStream out ;

    public Joueur2(Socket socket) throws Exception{
        this.symbole = 'O' ;
        this.socket = socket ;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintStream(socket.getOutputStream());
        System.out.println("Connexion avec le client : " + socket.getInetAddress());
    }

    public void envoyerMessage(String message) {
        this.out.println(message);
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
        String message;
        message = "" ;
        this.envoyerMessage("Entrez un entier ('FIN' pour arreter) :") ;
        try{
            message = this.in.readLine().toUpperCase() ;
        }
        catch (Exception e) {
            e.printStackTrace() ;
        }
        while ( !isNumeric(message) && !message.equals("FIN") ){
            this.envoyerMessage("La colone doit etre un entier") ;
            try{
                message = this.in.readLine().toUpperCase() ;
            }
            catch (Exception e){
                e.printStackTrace() ;
            }
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
        this.envoyerMessage("Joueur 2 quelle colone ?") ;
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
                    this.envoyerMessage("La colone doit etre comprise entre 1 et " + Integer.toBinaryString(size)) ;
                    colone = this.recevoirMessage() ;
                }
            }
        }
        return colone ;
    }

    public void shutDownSocket() throws Exception {
        try{
            this.socket.close() ;
        }
        catch(Exception e){
            e.printStackTrace() ;
        }
    }

}
