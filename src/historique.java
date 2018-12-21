
public class historique {

	int nbrhistorique;
	Conversation[] Tab_conv;			
	public historique() {
		this.nbrhistorique=0;
		Tab_conv=new Conversation[1000];
	}
	
	
	public String[][] get_historique(String user) {
		int i=0;
		while(Tab_conv[i].get_user()!=user) {
			i++;
		}
		return Tab_conv[i].get_Conversation(); 
	}
	
	private void new_historique(String user) {
		Tab_conv[nbrhistorique]=new Conversation(user);	
		nbrhistorique++;
		// instancie Conversation avec le user et ajoute au tableau Tab_conv
	}
	
	public void add_to_historique(String user1/* utilisateur de la session */,String user2/*utilisateur ayant envoyé le message */, String conv) {	
		int i=0;
		while(Tab_conv[i].get_user()!=user1) {
			i++;
		}
		Tab_conv[i].add_conversation(user2,conv);
		
		// appel add_conversation avec conv, de la classe conversation avec l'user
		
	}
	
	
	
}
