import java.util.ArrayList;
import java.util.List;


public class Mot 
{
	//Attributs
	//Member
	private int id;
	private String value;
	private boolean[] boolMod;
	private double[] tf;
	private double idf;
	private double[] tf_idf;
	private Our_occurence[] occurrence;
	private int occurrenceTotal;
	private int nbDoc;
	//Classe
	private static int count=0;
	
	//
	Mot()
	{
		this.setId(++count);
		setOccurrenceTotal(0);
		occurrence=new Our_occurence[2000];
		boolMod = new boolean[2000];
		tf=new double[2000];
		tf_idf=new double[2000];
		nbDoc=0;
		occurrenceTotal=0;
	}
	
	Mot(String value)
	{
		this();
		this.setValue(value);
	}
	
	Mot(Mot mot)
	{
		this();
		this.setValue(mot.getValue());
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public double[] getTf() {
		return tf;
	}

	public void setTf(double[] tf,int pos) {
		this.tf = tf;
	}
	
	public double getTf_Pos(int pos) {
		return tf[pos];
	}

	public void setTf_Pos(double tf,int pos) {
		this.tf[pos] = tf;
	}

	public double getIdf() {
		return idf;
	}

	public void setIdf(double idf) {
		this.idf = idf;
	}

	public Our_occurence[] getOccurrence() {
		return occurrence;
	}
	
	public Our_occurence getOccurrencePos(int pos) {
		return occurrence[pos];
	}
	
	public void setOccurrencePos(Our_occurence occurrence, int pos) 
	{
		this.occurrence[pos] = occurrence;
		this.setOccurrenceTotal(this.getOccurrenceTotal()+occurrence.getValue());
	}

	public void setOccurrence(Our_occurence[] occurrence) {
		this.occurrence = occurrence;
	}
	public void innOccurrence(int pos) 
	{
		this.occurrence[pos].setValue(this.occurrence[pos].getValue() + 1);
		incOccurrenceTotal();
	}
	
	//Methode
	
	public String toString()
	{
		return "MOT["+this.getId()+"] = "+" Val:"+this.getValue()+" Occ:"+this.getOccurrence()+" Idf:"+this.getIdf()+" Tf:"+this.getTf()+" Tf_Idf:"+this.getTf_idf();
	}

	public double[] getTf_idf() {
		return tf_idf;
	}
	
	public double getTf_idf_Pos(int pos) 
	{
		return tf_idf[pos];
	}

	public void setTf_idf(double[] tf_idf) {
		this.tf_idf = tf_idf;
	}
	public void setTf_idf_pos(double tf_idf,int pos) {
		this.tf_idf[pos] = tf_idf;
	}

	public int getOccurrenceTotal() {
		return occurrenceTotal;
	}

	public void setOccurrenceTotal(int occurrenceTotal) {
		this.occurrenceTotal = occurrenceTotal;
	}
	
	public void incOccurrenceTotal() {
		this.occurrenceTotal++;
	}

	public int getNbDoc() {
		return nbDoc;
	}

	public void setNbDoc(int nbDoc) {
		this.nbDoc = nbDoc;
	}
	public void incNbDoc() {
		this.nbDoc++;
	}

	public boolean[] getBoolMod() {
		return boolMod;
	}
	
	public int getBoolMod_Pos_Int(int pos) {
		if (boolMod[pos])
		{
			return 1;
		}
		//sinon
		return 0;
	}

	public void setBoolMod(boolean[] boolMod) {
		this.boolMod = boolMod;
	}


}
