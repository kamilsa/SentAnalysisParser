package ru.kamil.innopolis.sentiment.analysis;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.ArrayUtils;
import ru.kamil.innopolis.sentiment.database.DBHelper;
import ru.kamil.innopolis.sentiment.parser.New;
import org.apache.commons.math3.stat.StatUtils;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by kamil on 18.11.14.
 */
public class AnalisysText {
    private ArrayList<New> news;

    private String someTitle = "Wayne Rooney warns young England players of intimidating Glasgow crowd"; //-
    private String someText = "Everybody has to dye. Get ready to dye. Badly injured people over 1 million.";
    private String someUrl = "http://someurl.com";
    public New someNew = new New(someTitle, someText, someUrl);

    private MaxentTagger tagger = new MaxentTagger("res/english-bidirectional-distsim.tagger");

    public AnalisysText(){}
    public AnalisysText(ArrayList<New> news){
        this.news = news;
    }

    public ArrayList<ArrayList<StemmedWord>> procedureNew(New _new){
        StringTokenizer st = new StringTokenizer(_new.getText(), ".?!;");
        String currSentence;
        ArrayList<ArrayList<StemmedWord>> sentences = new ArrayList<ArrayList<StemmedWord>>();
        while(st.hasMoreTokens()){
            currSentence = st.nextToken();
            StringTokenizer st1 = new StringTokenizer(currSentence, " ");
            ArrayList<String> words = new ArrayList<String>();
            while(st1.hasMoreTokens()){
                words.add(st1.nextToken());
            }
            String[] wordArray = Arrays.copyOf(words.toArray(), words.size(), String[].class);
            List<HasWord> sent = Sentence.toWordList(wordArray);
            List<TaggedWord> taggedSent = tagger.tagSentence(sent);
            ArrayList<StemmedWord> wordList = new ArrayList<StemmedWord>();
            for(TaggedWord w : taggedSent){
                wordList.add(new StemmedWord(w.word(), w.tag()));
            }
            sentences.add(wordList);
        }

        try {
           DBHelper.getConnection();

//            float afraidSentSum = 0;
//            float amusedSentSum = 0;
//            float angrySentSum = 0;
//            float annoyedSentSum = 0;
//            float dont_careSentSum = 0;
//            float happySentSum = 0;
//            float inspiredSentSum = 0;
//            float sadSentSum = 0;


            float sumTags[] = new float[8];
            float minTags[];
            String minWords[] = new String[8];
            minTags = new float[]{1,1,1,1 , 1,1,1,1};
            float maxTags[] = new float[8];
            String names[] = {"afraid: ","amused: ","angry: ","annoyed: ","dont_care: ","happy: ","inspired: ","sad: "};
            int wordNumber[] = new  int[8];
           // double sentTags[] = new double[8];
            ArrayList<Double>[] sentTags = new ArrayList[8];
            String maxWords[] = new String[8];
            ArrayList<Double>[] meanTags = new ArrayList[8];
            ArrayList<Double>[] varTags = new ArrayList[8];
            HashMap<String, Double> probsMap = new HashMap<String, Double>();
            for (int i = 0; i < 8; i++) {
                meanTags[i] = new ArrayList<Double>();
                varTags[i] = new ArrayList<Double>();
            }

            for(ArrayList<StemmedWord> list : sentences){
                int number = 0;
                for (int i = 0; i < 8; i++) {
                    sentTags[i] = new ArrayList<Double>();

                }


            for(StemmedWord word : list){
                    Vector<Float> v = DBHelper.getWord(word);
                    if (word.getTag()=="v")
                    {

                    }


                    if(v == null) continue;
                    for (int i = 0; i < 8 ; i++) {
                        sentTags[i].add(Double.valueOf(v.get(i)));
                        }
                number++;
                 }
                System.out.println("Sentence----------------");
                double mean[] = new double[8];

                double variance[] = new double[8];

                for (int i = 0; i< 8; i++)
                {
                   double [] array = new double[number];
                    for (int j = 0; j < number; j++) {
                        array[j] = sentTags[i].get(j);
                    }


                    mean[i] = StatUtils.max(array);

                    variance[i] = StatUtils.mean(array) ;// variance

                    System.out.println(names[i]+ " :");
                    System.out.println("Max " + mean[i] + " Mean "+ variance[i]);

                   // System.out.println(" Mode " + Arrays.toString(stat1));
                    meanTags[i].add(mean[i]);
                    varTags[i].add(variance[i]);

                }



            }
            System.out.println(someText);
            double mean[] = new double[8];

            double variance[] = new double[8];

            for (int i = 0; i< 8; i++) {
                double[] array1 = new double[sentences.size()];
                double[] array2 = new double[sentences.size()];
                for (int j = 0; j < sentences.size(); j++) {

                    array1[j] = meanTags[i].get(j);
                    array2[j] = varTags[i].get(j);
                }

                mean[i] = StatUtils.mean(array1);
                variance[i] = StatUtils.variance(array1);// means


                System.out.println(names[i] + " :");
                System.out.println("Mean of max " + mean[i] + " Var of maxs " + variance[i]);
                System.out.println("Prob " + mean[i] * (1-variance[i]*10) );
                probsMap.put(names[i], mean[i] * (1-variance[i]*10));
                mean[i] = StatUtils.mean(array2);
                variance[i] = StatUtils.variance(array2);// variance
                System.out.println("Mean of vars " + mean[i] + " Var of vars" + variance[i]);


            }
            Double sum = 0d;
            for(String name : names){
                sum += probsMap.get(name);
            }
            for(String name : names){
                System.out.println(name + ": " + probsMap.get(name)*100/sum + " %");
            }
//            for (int i=0;i<8;i++) {
//                System.out.println("Max " + names[i] + maxTags[i] + " " + maxWords[i] );
//                System.out.println("Min "+ names[i] + minTags[i] + " " + minWords[i] + " The sum of tags " + sumTags[i]);
//            }

//            Vector<Float> v = DBHelper.getWord(sentences.get(0).get(3));
////            System.out.println(sentences.get(0).get(3).getStemText());
//            for(Float f : v){
//                System.out.println(f);
//            }

            DBHelper.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sentences;
    }

}
