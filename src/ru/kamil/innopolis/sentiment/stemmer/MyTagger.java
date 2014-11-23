package ru.kamil.innopolis.sentiment.stemmer;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * Created by kamil on 23.11.14.
 */
public class MyTagger {
    private MyTagger(){}

    public static MaxentTagger tagger = new MaxentTagger("res/english-bidirectional-distsim.tagger");
}
