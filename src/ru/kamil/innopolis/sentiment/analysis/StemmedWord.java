package ru.kamil.innopolis.sentiment.analysis;

import ru.kamil.innopolis.sentiment.stemmer.Stemmer;

/**
 * Created by kamil on 18.11.14.
 */

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

public class StemmedWord {
    private String text;
    private String tag;

    public String getText(){
        return text;
    }
    public String getTag(){
        return tag;
    }

    public void setText(String text){
        this.text = text;
    }
    public void setTag(String tag){
        this.tag = tag;
    }

    public StemmedWord(String text, String tag) {
        this.text = Stemmer.stemWord(text);
        if(tag.startsWith("JJ")){
            tag = "a";
        }
        else if(tag.equals("FW")){
            tag = "n";
        }
        else if(tag.startsWith("NN")){
            tag = "n";
        }
        else if(tag.startsWith("RB")){
            tag = "r";
        }
        else if(tag.equals("PRP") || tag.equals("PP$")){
            tag = "n";
        }
        else if(tag.startsWith("VB")){
            tag = "v";
        }
        else{
            tag = "n";
        }
        this.tag = tag;
    }
}
