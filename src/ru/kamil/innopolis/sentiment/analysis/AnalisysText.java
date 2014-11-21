package ru.kamil.innopolis.sentiment.analysis;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import ru.kamil.innopolis.sentiment.database.DBHelper;
import ru.kamil.innopolis.sentiment.parser.New;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by kamil on 18.11.14.
 */
public class AnalisysText {
    private ArrayList<New> news;

    private String someTitle = "Wayne Rooney warns young England players of intimidating Glasgow crowd"; //-
    private String someText = "Wayne Rooney has talked to the younger members of the England squad to warn them about the atmosphere at " +
            "Celtic Park and ensure they are not intimidated in the friendly against Scotland.\n" +
            "\n" +
            "The England captain has taken players like Raheem Sterling, Ross Barkley and Saido " +
            "Berahino to one side after admitting the din whipped up by the home fans can “take you by surprise”." +
            " England have not played in Glasgow since 1999 and confront a resurgent Scotland who have lost only once – " +
            "away to the world champions, Germany – in 10 matches under Gordon Strachan. Roy Hodgson is expected to blood a " +
            "number of inexperienced players in his team’s final fixture of 2014.\n" +
            "\n" +
            "Rooney has visited this arena twice with Manchester United in European competition and is ready for an awkward evening. " +
            "Around 52,000 tickets have been sold and England have returned a large number of their allocation. " +
            "Police on both sides of the border are on the alert for potential crowd trouble. " +
            "All leave in Scotland has been cancelled and extra back-up has been drafted in from Dundee and Edinburgh after there were" +
            " 230 arrests 15 years ago.\n" +
            "\n" +
            "“I’ve spoken to a few of the younger players to make sure they are ready for the start of the game because, " +
            "if you are not, it can take you by surprise,” Rooney said. “It will be a big test for us. I’ve played up here a " +
            "few times and you know at the start of the game their fans will be really up for it. It will be quite intimidating if " +
            "you’re not used to it, so I wanted to make sure they’re ready for it to get them settled.";
    private String someUrl = "http://someurl.com";
    public New someNew = new New(someTitle, someText, someUrl);

    private MaxentTagger tagger = new MaxentTagger("res/english-bidirectional-distsim.tagger");

    public AnalisysText(){}
    public AnalisysText(ArrayList<New> news){
        this.news = news;
    }

    public ArrayList<ArrayList<StemmedWord>> procedureNew(New _new){
        StringTokenizer st = new StringTokenizer(_new.getText(), ".");
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

            String maxWords[] = new String[8];
            for(ArrayList<StemmedWord> list : sentences){
                int number = 0;
            for(StemmedWord word : list){
                    Vector<Float> v = DBHelper.getWord(word);
                    if(v == null) continue;
                    for (int i = 0; i < 8 ; i++) {
                        if (v.get(i) > maxTags[i])
                        {
                            maxTags[i] = v.get(i);
                            maxWords[i] = word.getText();

                        }
                        if (v.get(i) < minTags[i])
                        {
                            minTags[i] = v.get(i);
                            minWords[i] = word.getText();
                        }
                        sumTags[i] += v.get(i);
                        if (v.get(i)!=0)
                        wordNumber[i]++;

                    }
                number++;
                 }
                System.out.println("Sentence----------------");

                for (int i = 0; i< 8; i++)
                {

                   // sumTags[i] /= number;
                    System.out.println(names[i] +":" + " Max " + maxTags[i] + " Min " + minTags[i] + " Number " +wordNumber[i]);
                    System.out.println("--Statistic : "+ "Avr =" + sumTags[i]/number + " AvrW ="+sumTags[i]/wordNumber[i]);


                }
                minTags = new float[]{1,1,1,1,  1,1,1,1};
                maxTags = new float[8];
                sumTags = new float[8];
            }

//            for (int i=0;i<8;i++) {
//                System.out.println("Max " + names[i] + maxTags[i] + " " + maxWords[i] );
//                System.out.println("Min "+ names[i] + minTags[i] + " " + minWords[i] + " The sum of tags " + sumTags[i]);
//            }

//            Vector<Float> v = DBHelper.getWord(sentences.get(0).get(3));
////            System.out.println(sentences.get(0).get(3).getText());
//            for(Float f : v){
//                System.out.println(f);
//            }

            DBHelper.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally{
        }

        return sentences;
    }

}
