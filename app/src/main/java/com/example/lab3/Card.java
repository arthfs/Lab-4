package com.example.lab3;

import androidx.annotation.NonNull;

public class Card {
    String question;
    String answer;
    String wrong1;
    String wrong2;
    public Card(String q,String a,String w1,String w2)
    {
        question = q;
        answer = a;
        wrong1 = w1;
        wrong2 = w2;
    }

    @NonNull
    @Override
    public String toString() {
        return "question :"+ question + "\nAnswer : "+answer +"\nWrong1 :" +wrong1+"\nWrong2 : " +wrong2 ;
    }
}
