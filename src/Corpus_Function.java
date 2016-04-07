import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Corpus_Function
{
	//Champs
	public Opinion[] opinions;
	public Map<String, Mot> words;

	//Constructeurs
	Corpus_Function()
	{
		opinions = new Opinion[2000];
		words = new HashMap<String, Mot>();
	}

	Corpus_Function(Opinion[] opinions, Map<String, Mot> words)
	{
		this.opinions = opinions;
		this.words = words;
	}

	//Méthodes
	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	//Read input documents
	public  void input(List<Our_path_model> path_model)
	{
		String csvFilePolarite = "/home/lowx/Documents/Professionnel/Etude/Master_AIGLE/M1/Extraction_Connaissances_Données/Projet/labels.csv";
		String csvFileAvis = "/home/lowx/Documents/Professionnel/Etude/Master_AIGLE/M1/Extraction_Connaissances_Données/Projet/dataset.csv";
		BufferedReader brPolarite = null;
		BufferedReader brAvis = null;
		String line = "";
		String cvsSplitBy = "\n";

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPORT DATA FROM DATASET.CSV~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Ouverture d'un bloc try_catch
		try {
			//Import des données brut dans un buffer
			brAvis = new BufferedReader(new FileReader(csvFileAvis));
			//k : numero ligne
			int k=0;
			//Pour chaque ligne du buffer
			while ((line = brAvis.readLine()) != null)
			{
				//Récupération de l'avis sur le document k[0,2000]
				String[] avis = line.split(cvsSplitBy);
				//Création d'un option à partir de l'avis sur le document k.
				//La polarité de l'avis sera récupépéré lors de la lecture du document label.csv
				opinions[k] = new Opinion(avis[0]);
				//On incremente k pour la prochaine ligne
				k++;
			}
		}
		catch (FileNotFoundException e) 	//Document non trouvé : path incorrecte
		{
			e.printStackTrace();
		}
		catch (IOException e) 				//Exception d'entrée sortie
		{
			e.printStackTrace();
		}
		finally 							//Lors qu'on a lu toutes les lignes du buffer
		{
			if (brAvis != null)
			{
				try
				{
					brAvis.close();		//Fermeture buffer de lecture
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPORT DATA FROM LABEL.CSV~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		//Ouverture d'un bloc try_catch
		try
		{
			//Import des données brut dans un buffer
			brPolarite = new BufferedReader(new FileReader(csvFilePolarite));
			//k : numero ligne
			int k=0;
			while ((line = brPolarite.readLine()) != null)
			{
				//Récupération de la polarité sur le document k
				String[] polarite = line.split(cvsSplitBy);
				//Affectation de la polarité à l'opinion k créé ci-dessus
				opinions[k].setPolarite(Integer.parseInt(polarite[0]));
				//On incremente k pour la prochaine ligne
				k++;

			}
		}
		catch (FileNotFoundException e) 	//Document non trouvé : path incorrecte
		{
			e.printStackTrace();
		}
		catch (IOException e) 				//Exception d'entrée sortie
		{
			e.printStackTrace();
		}
		finally 							//Lors qu'on a lu toutes les lignes du buffer
		{
			if (brPolarite != null) {
				try {
					brPolarite.close();	//Fermeture buffer de lecture
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Import suceeded");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IDENTIFICATION DES MOTS PRÉSENT DANS LES DOCUMENTS DU CORPUS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		System.out.println("words.size() : "+words.size());
		//Pour chaque document du texte
		for (int l=0;l<2000;l++)
		{
			//Pour l'opinion du document l
			//Pour l'avis de l'opinion du document l
			String phrase = opinions[l].getAvis();
			String delims = "[ ]+";
			String[] tokens = phrase.split(delims);

			//Pour chaque mot s dans l'avis du document l
			for(String s : tokens)
			{
				String s_remove_stop_caractere = remove_stop_caractere(s);
				this.pause(2);
				//Si le mot n'est pas encore présent dans le dictionnaire des mots "words"
				if(words.get(s_remove_stop_caractere) == null)
				{

					//On insert le mot s dans le dictionnaire des mots "words"

					words.put(s_remove_stop_caractere,new Mot(s_remove_stop_caractere));
					Mot m = words.get(s_remove_stop_caractere);
					//Création d'un instance d'occument (occ,maxOcc) pour la ligne courante
					Our_occurence occurrence =  new Our_occurence(1, tokens.length);
					//Affection de l'instance d'occurence au mot
					m.setOccurrencePos(occurrence, l);
					//
					m.getBoolMod()[l]=true;
				}
				//Si le mot est présent dans le dictionnaire des mots "words"
				else
				{
					//Récupération de l'objet mort
					Mot m = words.get(s_remove_stop_caractere);
					//Si l'occurence du mot pour la ligne courante est null
					if(m.getOccurrencePos(l) ==null)
					{
						//Création d'un instance d'occument (occ,maxOcc) pour la ligne courante
						Our_occurence occurrence =  new Our_occurence(1, tokens.length);
						//Affection de l'instance d'occurence au mot
						m.setOccurrencePos(occurrence, l);
						//
						m.getBoolMod()[l]=true;
					}
					else
					{
						//Incrémentation de l'instance d'occurence du mot
						m.innOccurrence(l);
					}
					System.out.println("----");
					System.out.println(m.getOccurrencePos(l).getValue());
					System.out.println(m.getOccurrenceTotal());
				}
			}
		}
		System.out.println("words.size() : "+words.size());
	}

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	public  void boolean_model_Write_Arff()
	{
		String booleanModel_Arff = "";
		List<String> lines_booleanModel_Arff = new ArrayList<String>();

		String relation = "@relation";
		String relationName = "opinions";
		String attribute = "@attribute";
		String data = "@data";

		booleanModel_Arff = booleanModel_Arff + relation +" "+ relationName;
		lines_booleanModel_Arff.add(relation +" "+ relationName);
//				System.out.println(booleanModel_Arff);
		booleanModel_Arff = booleanModel_Arff + "\n";
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			System.out.println("Mot : "+entry_s_m.getValue().getValue()+" STRING");
			booleanModel_Arff = booleanModel_Arff + "\n" + attribute + " \""+entry_s_m.getKey()+"\" STRING";
			lines_booleanModel_Arff.add(attribute + " \""+entry_s_m.getKey()+"\" STRING");
		}
//				System.out.println(booleanModel_Arff);
		lines_booleanModel_Arff.add(data);
		booleanModel_Arff = booleanModel_Arff + "\n";
		int l;
		for (l=0;l<1;l++)
//		for (l=0;l<2000;l++)
		{
			System.out.println(l);
			int i=0;
			String line="";
			for(Entry<String, Mot> entry_s_m : words.entrySet())
			{
//				if(l%10000 ==0)
//				{
//					System.out.println("Mot["+i+"/"+l+"] : "+entry_s_m.getValue().getValue());
////				System.out.println("Mot["+i+"/"+l+"] : "+entry_s_m.getValue().getValue());
//				}
					if (i==0)
					{
						booleanModel_Arff = booleanModel_Arff +"\n"+"{";
						booleanModel_Arff = booleanModel_Arff + i + " " + entry_s_m.getValue().getBoolMod_Pos_Int(l);
						line += "\n"+"{";
						line += i + " " + entry_s_m.getValue().getBoolMod_Pos_Int(l);
//						System.out.println(line);
//						pause(5);
					}
					if (i>0)
					{
						booleanModel_Arff += "," + i + " " + entry_s_m.getValue().getBoolMod_Pos_Int(l);
						line += ","+ i + " " + entry_s_m.getValue().getBoolMod_Pos_Int(l);
						if (i%77 == 0)
						{
//							System.out.println(line);
						}
					}
					if (i == words.size())
					{
						booleanModel_Arff = booleanModel_Arff +"}";
						line = line +"}";
//						System.out.println(line);
//						pause(5);
					}
				i++;
			}
			lines_booleanModel_Arff.add(line);
			System.out.println(line);
		}
		try {
			System.out.println("writeLargerTextFile(\"opinion_terme_weiting.arff\",lines_booleanModel_Arff); Do");
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
			writeLargerTextFile("opinion_booleanModel"+dateFormat.format(cal.getTime())+".arff",lines_booleanModel_Arff);
			System.out.println("writeLargerTextFile(\"opinion_terme_weiting.arff\",lines_booleanModel_Arff); Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/


	public  void termWeiting_TF()
	{
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			String s = entry_s_m.getKey();
			Mot m = words.get(s);
			Our_occurence occurrencenull =  new Our_occurence(0, 1);
			for (int l=0;l<2000;l++)
			{
				if (m.getOccurrencePos(l) == null)
				{
					m.setOccurrencePos(occurrencenull, l);
				}
				else
				{
					System.out.println("-------------------------------------------------------");
					System.out.println(m);
					System.out.println(m.getOccurrenceTotal());
					System.out.println(words.get(entry_s_m.getKey()).getOccurrenceTotal());
					System.out.println(m.getOccurrencePos(l).getValue());
					System.out.println(m.getOccurrencePos(l).getMax());
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				}
				double tf=(double) m.getOccurrencePos(l).getValue() / (double) m.getOccurrencePos(l).getMax();
				int pos = l;
				m.setTf_Pos(tf, pos);
				words.replace(s, m);
				if(tf>0)
				{
					System.out.println("mot : "+entry_s_m.getValue().getValue());
					System.out.println("tf : "+tf);
				}
			}
		}

	}

	public  void termWeiting_IDF()
	{
		int nbDoc;
		int MaxnbDoc=0;
		int MinnbDoc=1975;
		double idfMax = 0;

		double idfMin = 100;
		Mot motMaxOccurence=new Mot();
		motMaxOccurence.setOccurrenceTotal(0);
		Mot motMostPresent=new Mot();

		Mot motMinOccurence=new Mot();
		motMinOccurence.setOccurrenceTotal(21235);
		Mot motLeastPresent=new Mot();

		int nbDocIdfMax=0;
		Mot motIdfMAx=new Mot();
		int nbDocIdfMin=0;
		Mot motIdfMin=new Mot();
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			System.out.println(entry_s_m.getValue().getValue());
			System.out.println(entry_s_m.getValue().getOccurrenceTotal());
			System.out.println(motMaxOccurence.getValue());
			System.out.println(motMaxOccurence.getOccurrenceTotal());

			if(entry_s_m.getValue().getOccurrenceTotal() >0)
			{

			}
			if (motMaxOccurence.getOccurrenceTotal() < entry_s_m.getValue().getOccurrenceTotal())
			{
				System.out.println(entry_s_m.getValue().getValue());
				System.out.println(entry_s_m.getValue().getOccurrenceTotal());
				motMaxOccurence = entry_s_m.getValue();
			}
			if (motMinOccurence.getOccurrenceTotal() > entry_s_m.getValue().getOccurrenceTotal())
			{
				motMinOccurence = entry_s_m.getValue();
			}
			nbDoc=0;
			int g;

			for (g=0;g<2000;g++)
			{
				if (entry_s_m.getValue().getOccurrencePos(g).getValue() >0)
				{
					entry_s_m.getValue().incNbDoc();
					nbDoc++;
				}
			}
			if(nbDoc > MaxnbDoc)
			{

				MaxnbDoc = nbDoc;
				motMostPresent = entry_s_m.getValue();
			}
			if(nbDoc < MinnbDoc)
			{
				MinnbDoc = nbDoc;
				motLeastPresent = entry_s_m.getValue();
			}
			entry_s_m.getValue().setIdf(Math.log((double) 2000 / (double) entry_s_m.getValue().getNbDoc()));

			if (idfMax < Math.log((double) 2000 / (double) nbDoc))
			{
				idfMax = Math.log((double) 2000 / (double) nbDoc);
				motIdfMAx =  entry_s_m.getValue();
				nbDocIdfMax = nbDoc;
			}
			if (idfMin > Math.log((double) 2000 / (double) nbDoc))
			{
				idfMin = Math.log((double) 2000 / (double) nbDoc);
				motIdfMin=  entry_s_m.getValue();
				nbDocIdfMin = nbDoc;
			}
		}
		System.out.println("idfMax : "+idfMax);
		System.out.println("idfMin : "+idfMin);
		System.out.println("Le motMaxOccurence : "+motMaxOccurence);
		System.out.println("Le motMostPresent : "+motMostPresent +"\n"+ MaxnbDoc);
		System.out.println("Le motMinOccurence : "+motMinOccurence);
		System.out.println("Le motLeastPresent : "+motLeastPresent +"\n"+ MinnbDoc);
		System.out.println("Le motIdfMAx : "+motIdfMAx);
		System.out.println("Le nbDocIdfMax : "+nbDocIdfMax);
		System.out.println("Le motIdfMin : "+motIdfMin);
		System.out.println("Le nbDocIdfMin : "+nbDocIdfMin);
	}

	public  void termWeiting_TF_IDF()
	{
		double tfMax = 0;
		double tfMin = 100;
		double tf_idfMax = 0;
		double tf_idfMin = 100;
		int l;
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			for (l=0;l<2000;l++)
			{
				entry_s_m.getValue().setIdf(words.get(entry_s_m.getKey()).getIdf());
				entry_s_m.getValue().setTf_idf_pos(entry_s_m.getValue().getTf_Pos(l) * entry_s_m.getValue().getIdf(),l);
				if (tfMax < entry_s_m.getValue().getTf_Pos(l))
				{
					tfMax = entry_s_m.getValue().getTf_Pos(l);
				}
				if (tfMin > entry_s_m.getValue().getTf_Pos(l))
				{
					tfMin = entry_s_m.getValue().getTf_Pos(l);
				}
				if (tf_idfMax < entry_s_m.getValue().getTf_idf_Pos(l))
				{
					tf_idfMax = entry_s_m.getValue().getTf_idf_Pos(l);
				}
				if (tf_idfMin > entry_s_m.getValue().getTf_idf_Pos(l))
				{
					tf_idfMin = entry_s_m.getValue().getTf_idf_Pos(l);
				}
			}
		}
		System.out.println("tfMax : "+tfMax);
		System.out.println("tfMin : "+tfMin);
		System.out.println("tf_idfMax : "+tf_idfMax);
		System.out.println("tf_idfMin : "+tf_idfMin);

	}


	public  void termWeiting_Write_Arff()
	{
		String termWeiting_Arff = "";
		List<String> lines_termWeiting_Arff = new ArrayList<String>();

		String relation = "@relation";
		String relationName = "opinions";
		String attribute = "@attribute";
		String data = "@data";

		termWeiting_Arff = termWeiting_Arff + relation +" "+ relationName;
		lines_termWeiting_Arff.add(relation +" "+ relationName);

		termWeiting_Arff = termWeiting_Arff + "\n";
		int k=0;
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			k++;
			System.out.println("Mot["+k+"] : "+entry_s_m.getValue().getValue());
			termWeiting_Arff = termWeiting_Arff + "\n" + attribute + " \""+entry_s_m.getKey()+"\"";
			lines_termWeiting_Arff.add(attribute + " \""+entry_s_m.getKey()+"\"");
		}
		termWeiting_Arff = termWeiting_Arff + "\n";
		int l;
		for (l=0;l<2000;l++)
		{
			int i=0;
			String line="";
			for(Entry<String, Mot> entry_s_m : words.entrySet())
			{
				System.out.println("Mot["+i+"] : "+entry_s_m.getValue().getValue());
					if (i==0)
					{
						termWeiting_Arff = termWeiting_Arff +"\n"+"{";
						termWeiting_Arff = termWeiting_Arff + i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
						line = line +"\n"+"{";
						line = line + i + " " + entry_s_m.getValue().getTf_idf();
						System.out.println(line);
						pause(5);
					}
					if (i>0)
					{
						termWeiting_Arff += "," + i + " " + entry_s_m.getValue().getTf_idf_Pos(l);
						line += ","+ i + " " + entry_s_m.getValue().getTf_idf();
						if (i%77 == 0)
						{
							System.out.println(line);
							pause(5);
						}
					}
					if (i == words.size())
					{
						termWeiting_Arff = termWeiting_Arff +"}";
						line = line +"}";
						System.out.println(line);
						pause(5);
					}
				i++;
			}
			lines_termWeiting_Arff.add(line);
			System.out.println(line);
		}
		try {
			System.out.println("writeLargerTextFile(\"opinion_terme_weiting.arff\",lines_termWeiting_Arff); Do");
			writeLargerTextFile("opinion_terme_weiting.arff",lines_termWeiting_Arff);
			System.out.println("writeLargerTextFile(\"opinion_terme_weiting.arff\",lines_termWeiting_Arff); Done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	public String remove_stop_caractere(String p_crct)
	{
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		Matcher m = p.matcher(p_crct);
		System.out.println("mot :"+p_crct);
		if(m.find())
		{
			String rslt = p_crct.substring(0, m.end());
			System.out.println("Remove_stop_caractere mot :"+rslt);
		    return rslt;
		}
		return p_crct;
	}
	public  void remove_stop_words(List<String> stop_words)
	{
		System.out.println("words.size() :"+words.size());
		for (Opinion op : opinions)
		{
			for(String s : stop_words)
			{
				words.remove(s);
			}
		}
		System.out.println("words.size() :"+words.size());
	}

	public  void auto_remove_stop_words_by_occ()
	{
		System.out.println("words.size() :"+words.size());
		List<String> stop_words= new ArrayList<>();
		for(Entry<String, Mot> entry_s_m : words.entrySet())
		{
			if (entry_s_m.getValue().getIdf() <2)
			{
				System.out.println(entry_s_m.getKey());
				System.out.println("words.remove("+entry_s_m.getKey()+")");
				System.out.println(words.get(entry_s_m.getKey()));
				stop_words.add(entry_s_m.getKey());
			}
		}
		remove_stop_words(stop_words);
		System.out.println("words.size() :"+words.size());
	}

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/



	void weka()
	{
		//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~WEKA~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//		//https://weka.wikispaces.com/Programmatic+Use
		//		// Declare two numeric attributes
		//		Attribute Attribute1 = new Attribute("firstNumeric");
		//		System.out.println("Attribute1 : "+Attribute1);
		//		Attribute Attribute2 = new Attribute("secondNumeric");
		//		System.out.println("Attribute2 : "+Attribute2);
		//
		//		// Declare a nominal attribute along with its values
		//		FastVector fvNominalVal = new FastVector(3);
		//		fvNominalVal.addElement("blue");
		//		fvNominalVal.addElement("gray");
		//		fvNominalVal.addElement("black");
		//		System.out.println("fvNominalVal : "+fvNominalVal);
		//		Attribute Attribute3 = new Attribute("aNominal", fvNominalVal);
		//		System.out.println("Attribute3 : "+Attribute3);
		//
		//		// Declare the class attribute along with its values
		//		FastVector fvClassVal = new FastVector(2);
		//		fvClassVal.addElement("positive");
		//		fvClassVal.addElement("negative");
		//		System.out.println("fvClassVal : "+fvClassVal);
		//		Attribute ClassAttribute = new Attribute("theClass", fvClassVal);
		//		System.out.println("ClassAttribute : "+ClassAttribute);
		//
		//		// Declare the feature vector
		//		FastVector fvWekaAttributes = new FastVector(4);
		//		fvWekaAttributes.addElement(Attribute1);
		//		fvWekaAttributes.addElement(Attribute2);
		//		fvWekaAttributes.addElement(Attribute3);
		//		fvWekaAttributes.addElement(ClassAttribute);
		//		System.out.println("fvWekaAttributes : "+fvWekaAttributes);
		//
		//		// Create an empty training set
		//		Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);
		//		System.out.println("----------------------------------------------------------------------");
		//		System.out.println("isTrainingSet : "+isTrainingSet);
		//		// Set class index
		//		isTrainingSet.setClassIndex(3);
		//		System.out.println("----------------------------------------------------------------------");
		//		System.out.println("isTrainingSet : "+isTrainingSet);
		//
		//		// Create the instance
		//		Instance iExample = new Instance(4);
		//		iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 1.0);
		//		iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 0.5);
		//		iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "gray");
		//		iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "positive");
		//		// add the instance
		// 		isTrainingSet.add(iExample);
		// 		System.out.println("----------------------------------------------------------------------");
		//		System.out.println("isTrainingSet : "+isTrainingSet);
		//		// Create the instance
		//		Instance iExample0 = new Instance(4);
		//		iExample0.setValue((Attribute)fvWekaAttributes.elementAt(0), 1.0);
		//		iExample0.setValue((Attribute)fvWekaAttributes.elementAt(1), 0.5);
		//		iExample0.setValue((Attribute)fvWekaAttributes.elementAt(2), "black");
		//		iExample0.setValue((Attribute)fvWekaAttributes.elementAt(3), "negative");
		//		// add the instance
		// 		isTrainingSet.add(iExample0);
		// 		System.out.println("----------------------------------------------------------------------");
		//		System.out.println("isTrainingSet : "+isTrainingSet);
		//		int k = 50;
		//		Instance[] instances = new Instance[k];
		//		for(int i=0; i<k; i++)
		//		{
		//			Instance iExample1 = new Instance(4);
		//			float randA1 = (float) (Math.random() * 20);
		//			float randA2 = (float) (Math.random() * 20);
		//			int randA3 = (int) (Math.random() * 3);
		//			int randA4 = (int) (Math.random() * 2);
		//			iExample1.setValue((Attribute)fvWekaAttributes.elementAt(0), randA1/20);
		//			iExample1.setValue((Attribute)fvWekaAttributes.elementAt(1), randA2/20);
		//			iExample1.setValue((Attribute)fvWekaAttributes.elementAt(2), (String) fvNominalVal.elementAt(randA3));
		//			iExample1.setValue((Attribute)fvWekaAttributes.elementAt(3), (String) fvClassVal.elementAt(randA4));
		//			instances[i]=iExample1;
		//			isTrainingSet.add(instances[i]);
		//			System.out.println("----------------------------------------------------------------------");
		//			System.out.println("isTrainingSet : "+isTrainingSet);
		//
		//		}
	}

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/

	final  Charset ENCODING = StandardCharsets.UTF_8;
	void writeLargerTextFile(String aFileName, List<String> aLines) throws IOException
	{
		Path path = Paths.get(aFileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, ENCODING)){
			for(String line : aLines){
				writer.write(line);
				writer.newLine();
			}
		}
	}

	public void pause(int seconde)
	{
		try {
			Thread.sleep(seconde*1000);                 //5000 milliseconds is five second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
