
public class Conversation {
	
	int nbrmessage;
	String user_dest;
	String[][] Histo_conversation; 	//Tableau (dynamique ?) 3 dimensions, emetteur, message et heure d'envoie
	
	
	public Conversation(String user) {
		Histo_conversation=new String[3][1000];
		nbrmessage=0;
		this.user_dest=user;
	}
	
	public String get_user() {
		return user_dest;
	}
	
	public void add_conversation(String user, String text) {
		// ajoute le text au tableau Histo_conversation avec l'user et l'heure de l'envoie ( comment ???)
		Histo_conversation[0][nbrmessage]=user;
		Histo_conversation[1][nbrmessage]=text;
		Histo_conversation[2][nbrmessage]=null/* lheure actuel */;
		nbrmessage++;
	}

	public String[][] get_Conversation() {
		return Histo_conversation ; //retourne le tableau Histo_conversation
	}
}
