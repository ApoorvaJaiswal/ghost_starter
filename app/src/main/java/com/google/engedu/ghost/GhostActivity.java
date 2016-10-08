package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary,d;
    private boolean userTurn = false;
    private Random random = new Random();
    TextView t1,t2;
    Button b1,b2;
    String st="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            d = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
       /* try {
            InputStream inputStream = assetManager.open("words.txt");
            d = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }*/
        //onStart(null);
        b1=(Button)findViewById(R.id.challenge);
        b2=(Button)findViewById(R.id.restart);
        t1=(TextView)findViewById(R.id.ghostText);
        t2=(TextView)findViewById(R.id.gameStatus);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStart(v);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challenge(v);
            }
        });
        onStart(null);
    }
    void challenge(View v)
    {
        String s=t1.getText().toString();
        if((s.length()>=4) && (d.isWord(s)))
        {
            t2.setText("You win!!");
        }
        else
        {
            String word=d.getGoodWordStartingWith(s);
            if(word!=null)
            {
                t2.setText("Computer wins!");
            }
            else
                t2.setText("You win!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        char c=(char)event.getUnicodeChar();
        if((c>='a'&& c<='z')||(c>='A' && c<='Z')) {
            st=t1.getText().toString()+c;
            //Toast.makeText(this,st,Toast.LENGTH_SHORT).show();
            t1.setText(st);
            computerTurn();
        return false;
        }

        else
        return super.onKeyUp(keyCode, event);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn =random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        st="";
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return false;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String str = t1.getText().toString();
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        if ((d.isWord(str)) && (str.length() >= 4)) {
            t2.setText("Computer won");
        } else {
            String s = d.getGoodWordStartingWith(str);
            //Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
            if (s == null) {
                t2.setText("I challenge you! Computer wins");
            } else {
                t1.setText(s.substring(0, (str.length() + 1)));
            }
        }
        if (!(t2.getText().toString().equals("Computer won")) && (!(t2.getText().toString().equals("I challenge you! Computer wins")))) {
            userTurn = true;
            label.setText(USER_TURN);
        }
    }
}
