import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Opinion 
{
	//Attributs
	//Member
	private int id;
	private String avis;
//	private Map<String,Mot> avis_mots;
	private int polarite;
	
	//Class
	private int count=0;
	
	//Constructeur
	Opinion()
	{
		this.id = getCount();
//		this.avis_mots = new HashMap<String, Mot>();
		setCount(getCount() + 1);
	}
	Opinion(int polarite)
	{
		this();
		this.setPolarite(polarite);
	}
	Opinion(String avis)
	{
		this();
		this.avis = avis;
	}
	Opinion(String avis, int polarite)
	{
		this(avis);
		this.setPolarite(polarite);
	}
	
	//Get & Set
	public String getAvis() {
		return avis;
	}
	public void setAvis(String avis) {
		this.avis = avis;
	}

	public int getPolarite() {
		return polarite;
	}
	public void setPolarite(int polarite) {
		this.polarite = polarite;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public Map<String,Mot> getAvis_mots() {
//		return avis_mots;
//	}
//	public void setAvis_mots(Map<String,Mot> avis_mots) {
//		this.avis_mots = avis_mots;
//	}
	
	//Methode
	
	public String toString()
	{
		return "OPINION["+this.getId()+"] : "+this.getPolarite()+" "+this.getAvis();
	}


}
