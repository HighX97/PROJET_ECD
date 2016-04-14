import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class Corpus
{
	static public Opinion[] opinions;
	static public Map<String, Mot> words;
	static public Corpus_Function crp_fnc;

	/*
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	* -------------------------------------------------------------------------------------------------------------------
	*/
	//
	//	public static void use(Corpus corpus)
	//	{
	//		corpus.
	//	}

	public static void main(String [] args)
	{
		opinions = new Opinion[2000];
		words = new HashMap<String, Mot>();
		crp_fnc = new Corpus_Function();

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~LOAD AND STORE DATA~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~00_LOAD AND STORE DATA~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("input() : start\n");

		crp_fnc.pause(5);
		List<Our_path_model> p = new ArrayList<Our_path_model>();
		crp_fnc.input(p);
		System.out.println("input() : succeeded\n");


		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~WRITE BOOLEAN MODEL ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~01_WRITE BOOLEAN MODEL ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		crp_fnc.pause(5);
		crp_fnc.boolean_model_Write_Arff();
		System.out.println("boolean_model_Write_Arff() : succeeded\n");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_WRITE TERMWEITING ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_WRITE TERMWEITING MODEL ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~REMOVE STOP WORD~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~00_02_REMOVE STOP WORD~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("remove_stop_words(stopwords) : start\n");
		List<String> stopwords = new ArrayList<String>();
		stopwords.add("a");
		stopwords.add("about");
		stopwords.add("above");
		stopwords.add("above");
		stopwords.add("across");
		stopwords.add("after");
		stopwords.add("afterwards");
		stopwords.add("again");
		stopwords.add("against");
		stopwords.add("all");
		stopwords.add("almost");
		stopwords.add("alone");
		stopwords.add("along");
		stopwords.add("already");
		stopwords.add("also");
		stopwords.add("although");
		stopwords.add("always");
		stopwords.add("am");
		stopwords.add("among");
		stopwords.add("amongst");
		stopwords.add("amoungst");
		stopwords.add("amount");
		stopwords.add("an");
		stopwords.add("and");
		stopwords.add("another");
		stopwords.add("any");
		stopwords.add("anyhow");
		stopwords.add("anyone");
		stopwords.add("anything");
		stopwords.add("anyway");
		stopwords.add("anywhere");
		stopwords.add("are");
		stopwords.add("around");
		stopwords.add("as");
		stopwords.add("at");
		stopwords.add("back");
		stopwords.add("be");
		stopwords.add("became");
		stopwords.add("because");
		stopwords.add("become");
		stopwords.add("becomes");
		stopwords.add("becoming");
		stopwords.add("been");
		stopwords.add("before");
		stopwords.add("beforehand");
		stopwords.add("behind");
		stopwords.add("being");
		stopwords.add("below");
		stopwords.add("beside");
		stopwords.add("besides");
		stopwords.add("between");
		stopwords.add("beyond");
		stopwords.add("bill");
		stopwords.add("both");
		stopwords.add("bottom");
		stopwords.add("but");
		stopwords.add("by");
		stopwords.add("call");
		stopwords.add("can");
		stopwords.add("cannot");
		stopwords.add("cant");
		stopwords.add("co");
		stopwords.add("con");
		stopwords.add("could");
		stopwords.add("couldnt");
		stopwords.add("cry");
		stopwords.add("de");
		stopwords.add("describe");
		stopwords.add("detail");
		stopwords.add("do");
		stopwords.add("done");
		stopwords.add("down");
		stopwords.add("due");
		stopwords.add("during");
		stopwords.add("each");
		stopwords.add("eg");
		stopwords.add("eight");
		stopwords.add("either");
		stopwords.add("eleven");
		stopwords.add("else");
		stopwords.add("elsewhere");
		stopwords.add("empty");
		stopwords.add("enough");
		stopwords.add("etc");
		stopwords.add("even");
		stopwords.add("ever");
		stopwords.add("every");
		stopwords.add("everyone");
		stopwords.add("everything");
		stopwords.add("everywhere");
		stopwords.add("except");
		stopwords.add("few");
		stopwords.add("fifteen");
		stopwords.add("fify");
		stopwords.add("fill");
		stopwords.add("find");
		stopwords.add("fire");
		stopwords.add("first");
		stopwords.add("five");
		stopwords.add("for");
		stopwords.add("former");
		stopwords.add("formerly");
		stopwords.add("forty");
		stopwords.add("found");
		stopwords.add("four");
		stopwords.add("from");
		stopwords.add("front");
		stopwords.add("full");
		stopwords.add("further");
		stopwords.add("get");
		stopwords.add("give");
		stopwords.add("go");
		stopwords.add("had");
		stopwords.add("has");
		stopwords.add("hasnt");
		stopwords.add("have");
		stopwords.add("he");
		stopwords.add("hence");
		stopwords.add("her");
		stopwords.add("here");
		stopwords.add("hereafter");
		stopwords.add("hereby");
		stopwords.add("herein");
		stopwords.add("hereupon");
		stopwords.add("hers");
		stopwords.add("herself");
		stopwords.add("him");
		stopwords.add("himself");
		stopwords.add("his");
		stopwords.add("how");
		stopwords.add("however");
		stopwords.add("hundred");
		stopwords.add("ie");
		stopwords.add("if");
		stopwords.add("in");
		stopwords.add("inc");
		stopwords.add("indeed");
		stopwords.add("interest");
		stopwords.add("into");
		stopwords.add("is");
		stopwords.add("it");
		stopwords.add("its");
		stopwords.add("itself");
		stopwords.add("keep");
		stopwords.add("last");
		stopwords.add("latter");
		stopwords.add("latterly");
		stopwords.add("least");
		stopwords.add("less");
		stopwords.add("ltd");
		stopwords.add("made");
		stopwords.add("many");
		stopwords.add("may");
		stopwords.add("me");
		stopwords.add("meanwhile");
		stopwords.add("might");
		stopwords.add("mill");
		stopwords.add("mine");
		stopwords.add("more");
		stopwords.add("moreover");
		stopwords.add("most");
		stopwords.add("mostly");
		stopwords.add("move");
		stopwords.add("much");
		stopwords.add("must");
		stopwords.add("my");
		stopwords.add("myself");
		stopwords.add("name");
		stopwords.add("namely");
		stopwords.add("neither");
		stopwords.add("never");
		stopwords.add("nevertheless");
		stopwords.add("next");
		stopwords.add("nine");
		stopwords.add("no");
		stopwords.add("nobody");
		stopwords.add("none");
		stopwords.add("noone");
		stopwords.add("nor");
		stopwords.add("not");
		stopwords.add("nothing");
		stopwords.add("now");
		stopwords.add("nowhere");
		stopwords.add("of");
		stopwords.add("off");
		stopwords.add("often");
		stopwords.add("on");
		stopwords.add("once");
		stopwords.add("one");
		stopwords.add("only");
		stopwords.add("onto");
		stopwords.add("or");
		stopwords.add("other");
		stopwords.add("others");
		stopwords.add("otherwise");
		stopwords.add("our");
		stopwords.add("ours");
		stopwords.add("ourselves");
		stopwords.add("out");
		stopwords.add("over");
		stopwords.add("own");
		stopwords.add("part");
		stopwords.add("per");
		stopwords.add("perhaps");
		stopwords.add("please");
		stopwords.add("put");
		stopwords.add("rather");
		stopwords.add("re");
		stopwords.add("same");
		stopwords.add("see");
		stopwords.add("seem");
		stopwords.add("seemed");
		stopwords.add("seeming");
		stopwords.add("seems");
		stopwords.add("serious");
		stopwords.add("several");
		stopwords.add("she");
		stopwords.add("should");
		stopwords.add("show");
		stopwords.add("side");
		stopwords.add("since");
		stopwords.add("sincere");
		stopwords.add("six");
		stopwords.add("sixty");
		stopwords.add("so");
		stopwords.add("some");
		stopwords.add("somehow");
		stopwords.add("someone");
		stopwords.add("something");
		stopwords.add("sometime");
		stopwords.add("sometimes");
		stopwords.add("somewhere");
		stopwords.add("still");
		stopwords.add("such");
		stopwords.add("system");
		stopwords.add("take");
		stopwords.add("ten");
		stopwords.add("than");
		stopwords.add("that");
		stopwords.add("the");
		stopwords.add("their");
		stopwords.add("them");
		stopwords.add("themselves");
		stopwords.add("then");
		stopwords.add("thence");
		stopwords.add("there");
		stopwords.add("thereafter");
		stopwords.add("thereby");
		stopwords.add("therefore");
		stopwords.add("therein");
		stopwords.add("thereupon");
		stopwords.add("these");
		stopwords.add("they");
		stopwords.add("thickv");
		stopwords.add("thin");
		stopwords.add("third");
		stopwords.add("this");
		stopwords.add("those");
		stopwords.add("though");
		stopwords.add("three");
		stopwords.add("through");
		stopwords.add("throughout");
		stopwords.add("thru");
		stopwords.add("thus");
		stopwords.add("to");
		stopwords.add("together");
		stopwords.add("too");
		stopwords.add("top");
		stopwords.add("toward");
		stopwords.add("towards");
		stopwords.add("twelve");
		stopwords.add("twenty");
		stopwords.add("two");
		stopwords.add("un");
		stopwords.add("under");
		stopwords.add("until");
		stopwords.add("up");
		stopwords.add("upon");
		stopwords.add("us");
		stopwords.add("very");
		stopwords.add("via");
		stopwords.add("was");
		stopwords.add("we");
		stopwords.add("well");
		stopwords.add("were");
		stopwords.add("what");
		stopwords.add("whatever");
		stopwords.add("when");
		stopwords.add("whence");
		stopwords.add("whenever");
		stopwords.add("where");
		stopwords.add("whereafter");
		stopwords.add("whereas");
		stopwords.add("whereby");
		stopwords.add("wherein");
		stopwords.add("whereupon");
		stopwords.add("wherever");
		stopwords.add("whether");
		stopwords.add("which");
		stopwords.add("while");
		stopwords.add("whither");
		stopwords.add("who");
		stopwords.add("whoever");
		stopwords.add("whole");
		stopwords.add("whom");
		stopwords.add("whose");
		stopwords.add("why");
		stopwords.add("will");
		stopwords.add("with");
		stopwords.add("within");
		stopwords.add("without");
		stopwords.add("would");
		stopwords.add("yet");
		stopwords.add("you");
		stopwords.add("your");
		stopwords.add("yours");
		stopwords.add("yourself");
		stopwords.add("yourselves");
		stopwords.add("the");

		crp_fnc.pause(5);
		crp_fnc.remove_stop_words(stopwords);
		System.out.println("remove_stop_words(stopwords) : succeeded\n");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_01_CALCULE TF~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_01_CALCULE TERME FREQUENCY~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("termWeiting_TF() : start\n");
		crp_fnc.pause(5);
		crp_fnc.termWeiting_TF();
		System.out.println("termWeiting_TF() : succeeded\n");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CALCULE IDF~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_02_CALCULE INVERSE DOCUMENT FREQUENCY~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		System.out.println("termWeiting_IDF() : start\n");
		crp_fnc.pause(5);
		crp_fnc.termWeiting_IDF();
		System.out.println("termWeiting_IDF() : succeeded\n");

		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CALCULE TDF_IDF~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_03_TF-IDF WEIGHTING~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("termWeiting_TF_IDF() : start\n");
		crp_fnc.pause(5);
		crp_fnc.termWeiting_TF_IDF();
		System.out.println("termWeiting_TF_IDF() : succeeded\n");


//		//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DOCUMENT LENGTH NORMALIZATION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_04_DOCUMENT LENGTH NORMALIZATION~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		crp_fnc.termWeiting_Doc_Length_Norlamisation();
//		crp_fnc.pause(5);

				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~WRITE TERMWEITING ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~02_05_WRITE TERMWEITING ARFF FILE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("termWeiting_Write_Arff() : start\n");
		crp_fnc.pause(5);
		crp_fnc.termWeiting_Write_Arff();
		System.out.println("termWeiting_Write_Arff() : succeeded\n");
		//
		//		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Weka~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~03_WEKA~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println("weka_exemple00() : start\n");
//		crp_fnc.pause(5);
//		 crp_fnc.weka();
//		System.out.println("weka_exemple00() : succeeded\n");


		System.out.println("\n==============================================================" +
				"\t\t" +
				"Fin" +
				"\n==============================================================");

	}

}
