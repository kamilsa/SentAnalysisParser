package ru.kamil.innopolis.sentiment.parser;

import ru.kamil.innopolis.sentiment.database.DBHelper;
import ru.kamil.innopolis.sentiment.stemmer.Stemmer;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.StringTokenizer;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
        # - Pound sign
        $ - Dollar sign
        '' - Close double quote
        `` - Open double quote
        ' - Close single quote
        ` - Open single quote
        , - Comma
        . - Final punctuation
        : - Colon, semi-colon
        -LRB- - Left bracket
        -RRB- - Right bracket
        CC - Coordinating conjunction
        CD - Cardinal number
        DT - Determiner
        EX - Existential there
        FW - Foreign word
        IN - Preposition
        JJ - Adjective
        JJR - Comparative adjective
        JJS - Superlative adjective
        LS - List Item Marker
        MD - Modal
        NN - Singular noun
        NNS - Plural noun
        NNP - Proper singular noun
        NNPS - Proper plural noun
        PDT - Predeterminer
        POS - Possesive ending
        PRP - Personal pronoun
        PP$ - Possesive pronoun
        RB - Adverb
        RBR - Comparative adverb
        RBS - Superlative Adverb
        RP - Particle
        SYM - Symbol
        TO - to
        UH - Interjection
        VB - Verb, base form
        VBD - Verb, past tense
        VBG - Verb, gerund/present participle
        VBN - Verb, past participle
        VBP - Verb, non 3rd ps. sing. present
        VBZ - Verb, 3rd ps. sing. present
        WDT - wh-determiner
        WP - wh-pronoun
        WP$ - Possesive wh-pronoun
        WRB - wh-adverb
*/
// * Created by kamil on 16.11.14.
 //*/
public class Main {

    public static void main(String args[]) throws Exception {
//        createDatabaseFromTxt("res/DepecheMood_freq.txt"); //createDatabase from txt if it necessary. If not comment this line!! Maybe db has already made.

//        ArrayList<File> list = SentParser.getNews();
//        File file[] = {list.get(0)};
//        Stemmer.stemFile(file);
        MaxentTagger tagger = new MaxentTagger("res/english-bidirectional-distsim.tagger");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out, "utf-8"));
        List<HasWord> sent = Sentence.toWordList("The", "slimy", "slug", "crawled", "over", "the", "long", ",", "green", "grass", ".");
        List<TaggedWord> taggedSent = tagger.tagSentence(sent);

        for (TaggedWord tw : taggedSent) {
//            if (tw.tag().startsWith("JJ")) {
//                pw.println(tw.word());
//            }
            System.out.println(tw.tag() + " " + tw.word());
        }

        pw.close();
    }

    public static void createDatabaseFromTxt(String fileStr) throws SQLException, ClassNotFoundException {
        DBHelper.getConnection();
        File file = new File(fileStr);
        String line = null;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            line = br.readLine();
            line = br.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        while (line != null) {
            try {
                StringTokenizer st = new StringTokenizer(line);

                String lemmaWord = st.nextToken();//слово + часть речи
                StringTokenizer stLemmaWord = new StringTokenizer(lemmaWord, "#");
                String lemma = stLemmaWord.nextToken();
                lemma = Stemmer.stemWord(lemma);
                String word = stLemmaWord.nextToken();

                float afraid = Float.parseFloat(st.nextToken());
                float amused = Float.parseFloat(st.nextToken());
                float angry = Float.parseFloat(st.nextToken());
                float annoyed = Float.parseFloat(st.nextToken());
                float dontCare = Float.parseFloat(st.nextToken());
                float happy = Float.parseFloat(st.nextToken());
                float insipred = Float.parseFloat((st.nextToken()));
                float sad = Float.parseFloat(st.nextToken());

//                System.out.println(lemma);
//                System.out.println(word);
//                System.out.println(afraid);
//                System.out.println(amused);
//                System.out.println(angry);
//                System.out.println(annoyed);
//                System.out.println(dontCare);
//                System.out.println(happy);
//                System.out.println(insipred);
//                System.out.println(sad);

                DBHelper.insertWord(lemma, word, afraid, amused, angry, annoyed, dontCare, happy, insipred, sad);

                line = br.readLine();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    line = br.readLine();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    ;
                }
            }
        }
        DBHelper.closeConnection();
    }
}
