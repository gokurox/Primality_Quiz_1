package in.ac.iiitd.gursimran14041.primality_quiz_mc_assign1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import in.ac.iiitd.gursimran14041.primality_tester_mc_assign1.R;

public class MainActivity extends AppCompatActivity {

    // Declare Members for Views
    private TextView mQuestionTextView;
    private Button mYesButton,
                   mNoButton,
                   mNextButton;

    // Attributes
    private int mCurrentNumber;
    private boolean mCurrentPrimality;
    private final String mNumberState = "CURRENT_NUMBER";
    private final String mPrimalityState = "CURRENT_PRIMALITY";

    // TAG for Logging
    private static final String LOG_TAG = "PQ_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i (LOG_TAG, "Inside OnCreate");

        // Bind the Members to their respective views
        mQuestionTextView = (TextView) findViewById (R.id.questionTextView);
        mYesButton = (Button) findViewById (R.id.yesButton);
        mNoButton = (Button) findViewById (R.id.noButton);
        mNextButton = (Button) findViewById (R.id.nextButton);

        // setOnClickListeners for Buttons
        mYesButton.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkResponse (view);
            }
        });
        mNoButton.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkResponse (view);
            }
        });
        mNextButton.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                generateNextQuestion();
            }
        });

        // Call resumeState
        if (savedInstanceState != null) {
            Log.i (LOG_TAG, "Resuming State from savedInstanceState");
            mCurrentNumber = savedInstanceState.getInt (mNumberState);
            mCurrentPrimality = savedInstanceState.getBoolean (mPrimalityState);
            setQuestionTextView();
        }
        else {
            Log.i (LOG_TAG, "Creating new State");
            generateNextQuestion();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        Log.i (LOG_TAG, "Saving State in savedInstanceState");
        savedInstanceState.putInt (mNumberState, mCurrentNumber);
        savedInstanceState.putBoolean (mPrimalityState, mCurrentPrimality);
        super.onSaveInstanceState(savedInstanceState);
    }

    // Custom Functions
    private void setQuestionTextView () {
        String QUESTION = String.format ("Is %d a Prime Number ?", mCurrentNumber);
        mQuestionTextView.setText (QUESTION);
    }

    private void generateNextQuestion () {
        Random rand = new Random();
        mCurrentNumber = rand.nextInt(1000) + 1;
        setQuestionTextView ();

        mCurrentPrimality = true;
        for (int i = 2; i <= Math.sqrt(mCurrentNumber) +1; i++) {
            if (mCurrentNumber % i == 0) {
                mCurrentPrimality = false;
                break;
            }
        }
    }

    private void checkResponse (View view) {
        String CORRECT = "Your answer is CORRECT";
        String INCORRECT = "Your answer is INCORRECT";
        if (view.getId() == R.id.yesButton) {
            if (mCurrentPrimality)
                Toast.makeText (this, CORRECT, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText (this, INCORRECT, Toast.LENGTH_SHORT).show();
        }
        else {
            if (mCurrentPrimality)
                Toast.makeText (this, INCORRECT, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText (this, CORRECT, Toast.LENGTH_SHORT).show();
        }
    }
}
