package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    Button goButton;
    TextView timerTextView;
    TextView scoreTextView;
    TextView sumTextView;
    TextView resultTextView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;
    ConstraintLayout gameLayout;
    MediaPlayer mediaPlayer;
    Button nextLevelButton;
    MediaPlayer mediaPlayer2;

    int result;
    char op;
    int operations;
    int score = 0;
    int numberOfQuestions = 0;

    int locationOfCorrectAnswer;


    ArrayList<Integer> answers = new ArrayList<Integer>();

    public void next(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity3.class);
        startActivity(intent);
    }

    public void chooseAnswer(View view){
        if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            resultTextView.setText("Correct!");
            score++;
        }else{
            resultTextView.setText("Wrong :/");
        }
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuetion();
    }

    public void start(View view){
        goButton.setVisibility(View.INVISIBLE);
        playAgain(findViewById(R.id.sumTextView));
        gameLayout.setVisibility(View.VISIBLE);
        mediaPlayer2.start();
    }

    public void newQuetion(){
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        operations = rand.nextInt(3);

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        switch (operations){
            case 0: result = a+b;
                op = '+';
                break;
            case 1: result = a*b;
                op = 'x';
                break;
            case 2: result = a-b;
                op = '-';
        }
        sumTextView.setText(Integer.toString(a) + " " + op + " " + Integer.toString(b));

        for(int i=0; i<4; i++){
            if(i==locationOfCorrectAnswer){
                answers.add(result);
            }else {
                int wrongAnswer = rand.nextInt(41);
                while(wrongAnswer == result){
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        newQuetion();
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");

        new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done!");
                mediaPlayer.start();
                if(score >= numberOfQuestions/2){
                    nextLevelButton.setVisibility(View.VISIBLE);
                }else {
                    playAgainButton.setVisibility(View.VISIBLE);
                }
            }
        }.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        goButton = findViewById(R.id.goButton);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextView);
        playAgainButton = findViewById(R.id.playAgainButton);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        goButton.setVisibility(View.VISIBLE);
        gameLayout = findViewById(R.id.gameLayout);
        gameLayout.setVisibility(View.INVISIBLE);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.guitar);
        nextLevelButton = findViewById(R.id.nextLevelButton);
    }
}
