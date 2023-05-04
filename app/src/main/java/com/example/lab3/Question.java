package com.example.lab3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;


public class Question extends AppCompatActivity {
    int numQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);


         numQuestion = getIntent().getIntExtra("numQuestion",1);
        getIntent().getStringExtra("option");

       //if the user is editing a question
         if (getIntent().getStringExtra("option").compareTo("edit")==0  )
         {

                EditText question = findViewById(R.id.newquestion);
                EditText answer = findViewById(R.id.newanswer);
                EditText wrong1 = findViewById(R.id.newwrong1);
                EditText wrong2 = findViewById(R.id.newwrong2);

                question.setText(getIntent().getStringExtra("question"));
                answer.setText(getIntent().getStringExtra("answer"));
                wrong1.setText(getIntent().getStringExtra("wrong1"));
                wrong2.setText(getIntent().getStringExtra("wrong2"));

         }

    }
    public void savecancel(View v)
    {
        EditText question = findViewById(R.id.newquestion);
        EditText answer = findViewById(R.id.newanswer);
        EditText wrong1 = findViewById(R.id.newwrong1);
        EditText wrong2 = findViewById(R.id.newwrong2);
        if (v.getId() == findViewById(R.id.cancel).getId()) {
            finish();
        } else {

            if (question.getText().toString().length() < 10 || answer.getText().toString().length() < 1 || wrong1.getText().toString().length() < 1 || wrong2.getText().toString().length() < 1) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent data = new Intent();
                List<String> answersArray = new ArrayList<>();

                //i get the questions and the choices
                answersArray.add (question.getText().toString().trim());
                answersArray.add (answer.getText().toString().trim());
                answersArray.add (wrong1.getText().toString().trim());
                answersArray.add (wrong2.getText().toString().trim());

                List<String> newAnswersArray = answersArray.subList(1,4);
                Collections.shuffle(newAnswersArray);


                String answers  = "";
                LinkedHashSet<String> questions = new LinkedHashSet <>();
                answers+=(question.getText().toString().trim());
                answers+=",";
                answers+=(answer.getText().toString().trim());
                answers+=",";
                answers+=(wrong1.getText().toString().trim());
                answers+=",";
                answers+=(wrong2.getText().toString().trim());

                 SharedPreferences preferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                 SharedPreferences ordered =  getSharedPreferences("orderedQuestions", Context.MODE_PRIVATE);
                 SharedPreferences.Editor editorOrdered = ordered.edit()  ;
                 SharedPreferences.Editor editorPreferences = preferences.edit()  ;


                //if there's only one question
                if (numQuestion+1 == 1) {

                    editorOrdered.putString("orderedQuestions","Question 1");
                    editorPreferences.putString("Question 1",answers);
                    editorOrdered.apply();
                    editorPreferences.apply();

                    data.putExtra("question", question.getText().toString());
                    data.putExtra("answer", newAnswersArray.get(0));
                    data.putExtra("wrong1",  newAnswersArray.get(1));
                    data.putExtra("wrong2",  newAnswersArray.get(2));
                    setResult(RESULT_OK, data);
                }
                //if the user is editing a question
                else if (getIntent().getStringExtra("option").compareTo("edit")!=0)
                {   String questionsSet = ordered.getString("orderedQuestions", "");
                    Object [] questionArray = questionsSet.split(",");
                   String lastQuestion = questionArray[questionArray.length-1].toString();
                   int n = (Character.getNumericValue ( lastQuestion.charAt(lastQuestion.length()-1) )+1) ;

                    questionsSet+=(",Question "+n);
                    questions.add("Question "+n);

                    String newQuestion =preferences.getString("myPreferences","");
                    newQuestion="Question "+n;

                    System.out.println(questionsSet);
                    editorOrdered.putString("orderedQuestions",questionsSet);
                    editorPreferences.putString(newQuestion,answers);
                    editorOrdered.apply();
                    editorPreferences.apply();

                    setResult(RESULT_OK, null);
                }
                else {
                    String questionsSet = ordered.getString("orderedQuestions", "");
                    Object [] questionArray = questionsSet.split(",");
                    String newAnswers = preferences.getString(questionArray[Important.current].toString(),"");
                    String newAnswer ="";
                    newAnswer+= question.getText();
                    newAnswer+=",";
                    newAnswer+= answer.getText();
                    newAnswer+=",";
                    newAnswer+= wrong1.getText();
                    newAnswer+=",";
                    newAnswer+= wrong2.getText();

                    System.out.println(newAnswers);
                    editorPreferences.putString(questionArray[Important.current].toString(),newAnswer);
                    editorPreferences.apply();

                    data.putExtra("question", question.getText().toString());
                    data.putExtra("answer",  newAnswersArray.get(0));
                    data.putExtra("wrong1",  newAnswersArray.get(1));
                    data.putExtra("wrong2",  newAnswersArray.get(2));
                    setResult(RESULT_OK, data);


                }


                Toast.makeText(this, "Question saved", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
}
