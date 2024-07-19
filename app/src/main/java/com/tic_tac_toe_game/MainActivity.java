package com.tic_tac_toe_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons;
    private int[][] winningPositions;
    private int flag = 0; // 0 for 'X' and 1 for 'O'
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[9];
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            buttons[i] = (Button) gridLayout.getChildAt(i);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckVal(v);
                }
            });
        }

        winningPositions = new int[][]{
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // for rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // for columns
                {0, 4, 8}, {2, 4, 6} // for diagonals
        };

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart();
            }
        });
    }

    public void CheckVal(View v) {
        Button btnCurrent = (Button) v;
        if (btnCurrent.getText().toString().equals("")) {
            count++;
            btnCurrent.setText(flag == 0 ? "X" : "O");
            flag = 1 - flag;

            if (count > 4) {
                if (checkWinner()) {
                    String winner = flag == 1 ? "X" : "O";
                    Toast.makeText(this, "Winner is " + winner, Toast.LENGTH_SHORT).show();
                    disableButtons();
                } else if (count == 9) {
                    Toast.makeText(this, "Match Drawn", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkWinner() {
        for (int[] position : winningPositions) {
            String a = buttons[position[0]].getText().toString();
            String b = buttons[position[1]].getText().toString();
            String c = buttons[position[2]].getText().toString();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public void Restart() {
        for (Button button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
        flag = 0;
        count = 0;
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }
}