package com.example.music2go;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends AppCompatActivity implements View.OnClickListener {
    private TextView playerOneScore,playerTwoScore,playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame, GoHome;

    private int playerOneScoreCount, playerTwoScoreCount,roundCount;
    boolean activePlayer;

    //p1 = 0
    //p2 = 1
    //empty = 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8}, //row
            {0,3,6},{1,4,7},{2,5,8}, //columns
            {0,4,8},{2,4,6}          //cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        resetGame = (Button) findViewById(R.id.resetGame);
        GoHome = findViewById(R.id.GoHome);
        GoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameFragment.this,home.class);
                startActivity(intent);
            }
        });
        for(int i = 0; i < buttons.length;i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id",getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }


    @Override
    public void onClick(View v) {

        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,
                buttonID.length()));

        if(activePlayer){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                //Toast.makeText(this,"Player One Won!",Toast.LENGTH_SHORT).show();
            }else {
                playerTwoScoreCount++;
                updatePlayerScore();
                //Toast.makeText(this,"Player Two Won!",Toast.LENGTH_SHORT).show();
            }
        }else if(roundCount == 9){
            playAgain();
            //Toast.makeText(this,"No Winner",Toast.LENGTH_SHORT).show();
        }else{
            activePlayer = !activePlayer;
        }
        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("Player One is Winning");
        }else if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("Player Two is Winning");
        }else{
            playerStatus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition : winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] !=2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
    public void playAgain(){
        roundCount = 0;
        activePlayer = true;

        for(int i = 0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}

