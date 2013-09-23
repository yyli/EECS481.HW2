package yyli.eecs481.hw2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	static final Integer TTT_STATUS_EMPTY = 0;
	static final Integer TTT_STATUS_X = 1;
	static final Integer TTT_STATUS_O = -1;
	static final Integer X_TURN = 0;
	static final Integer O_TURN = 1;
	static final Integer GAME_OVER = 2;
	
	protected enum GameStatus {
		IN_PROGRESS,
		X_WIN,
		O_WIN,
		TIE
	};
	
	protected Integer[] TicTacToeTilesId = {
		R.id.ttt11, R.id.ttt12, R.id.ttt13,
		R.id.ttt21, R.id.ttt22, R.id.ttt23,
		R.id.ttt31, R.id.ttt32, R.id.ttt33
	};
	
	protected Integer[] TicTacToeTilesStatus = new Integer[9];
	
	protected Integer turn = X_TURN;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
            	TextView statusText = (TextView) findViewById(R.id.statusText);
            	statusText.setText(getResources().getString(R.string.StatusText));
            	
				for (int i = 0; i < 9; i++) {
					TicTacToeTilesStatus[i] = TTT_STATUS_EMPTY;
				}
				
				for (int i = 0; i < 9; i++) {
					ImageView tile = (ImageView) findViewById(TicTacToeTilesId[i]);
					tile.setImageResource(0);
				}
				
				turn = X_TURN;
            }
        });
        
		for (int i = 0; i < 9; i++) {
			TicTacToeTilesStatus[i] = TTT_STATUS_EMPTY;
		}
		
		for (int i = 0; i < 9; i++) {
			ImageView tile = (ImageView) findViewById(TicTacToeTilesId[i]);
			tile.setOnClickListener(new TTTTileOnClickListener(i));
		}
    } 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    protected GameStatus getGameStatus() {
    	Integer row1sum = TicTacToeTilesStatus[0] + TicTacToeTilesStatus[1] + TicTacToeTilesStatus[2]; 
    	Integer row2sum = TicTacToeTilesStatus[3] + TicTacToeTilesStatus[4] + TicTacToeTilesStatus[5]; 
    	Integer row3sum = TicTacToeTilesStatus[6] + TicTacToeTilesStatus[7] + TicTacToeTilesStatus[8]; 
    	Integer col1sum = TicTacToeTilesStatus[0] + TicTacToeTilesStatus[3] + TicTacToeTilesStatus[6]; 
    	Integer col2sum = TicTacToeTilesStatus[1] + TicTacToeTilesStatus[4] + TicTacToeTilesStatus[7]; 
    	Integer col3sum = TicTacToeTilesStatus[2] + TicTacToeTilesStatus[5] + TicTacToeTilesStatus[8]; 
    	Integer diag1sum = TicTacToeTilesStatus[0] + TicTacToeTilesStatus[4] + TicTacToeTilesStatus[8]; 
    	Integer diag2sum = TicTacToeTilesStatus[2] + TicTacToeTilesStatus[4] + TicTacToeTilesStatus[6];
    	
    	Boolean tie = true;
    	
    	for (int i = 0; i < 9; i++) {
    		if (TicTacToeTilesStatus[i] == TTT_STATUS_EMPTY) {
    			tie = false;
    			break;
    		}
    	}

    	if (row1sum == 3 || row2sum == 3 || row3sum == 3 || 
    		col1sum == 3 || col2sum == 3 || col3sum == 3 || 
    		diag1sum == 3 || diag2sum == 3) {
    			return GameStatus.X_WIN;
    	} else if (
    		row1sum == -3 || row2sum == -3 || row3sum == -3 || 
			col1sum == -3 || col2sum == -3 || col3sum == -3 || 
    		diag1sum == -3 || diag2sum == -3) {
    			return GameStatus.O_WIN;
    	} else if (tie) {
    		return GameStatus.TIE;
    	} else {
    		return GameStatus.IN_PROGRESS;
    	}
    }
    
    public class TTTTileOnClickListener implements OnClickListener {
		Integer id;
		public TTTTileOnClickListener(Integer _id) {
			id = _id;
		}
		
        public void onClick(View v)
        {
        	ImageView tile = (ImageView) v;
        	if (turn != GAME_OVER && TicTacToeTilesStatus[id] == TTT_STATUS_EMPTY) {
            	TextView statusText = (TextView) findViewById(R.id.statusText);
            	if (turn == X_TURN) {
	            	tile.setImageResource(R.drawable.x);
					TicTacToeTilesStatus[id] = TTT_STATUS_X;
					turn = O_TURN;
					statusText.setText("O's Turn");
            	} else if (turn == O_TURN) {
	            	tile.setImageResource(R.drawable.o);
	            	TicTacToeTilesStatus[id] = TTT_STATUS_O;
					turn = X_TURN;
					statusText.setText("X's Turn");
            	}

            	GameStatus gStatus = getGameStatus();
            	if (gStatus != GameStatus.IN_PROGRESS) {
            		if (gStatus == GameStatus.TIE) {
	            		statusText.setText("Tie :( Click reset to play again!");
            		} else if (gStatus == GameStatus.X_WIN) {
	            		statusText.setText("X Wins!!! Click reset to play again!");
	            	} else if (gStatus == GameStatus.O_WIN) {
	            		statusText.setText("O Wins!!! Click reset to play again!");
	            	}
	        		turn = GAME_OVER;
            	}

//            	if (turn == GAME_OVER) {
//            		statusText.setText(getResources().getString(R.string.StatusText));
//            		turn = GAME_OVER;
//            	}
        	}
        }
    };
}