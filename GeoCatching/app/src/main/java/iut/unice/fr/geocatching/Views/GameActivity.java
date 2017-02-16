package iut.unice.fr.geocatching.Views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import iut.unice.fr.geocatching.Models.Figure;
import iut.unice.fr.geocatching.Models.FigureList;
import iut.unice.fr.geocatching.R;

public class GameActivity extends Activity {


    private int level;
    private int sequenceFigureMaxSize;
    private Boolean hasWon;
    private Boolean hasAlreadyPlayed;
    private FigureList figureList;
    private ImageButton triangleImageButton;
    private ImageButton ovalImageButton;
    private ImageButton squareImageButton;
    private ImageButton polygonImageButton;

    private Figure triangle;
    private Figure oval;
    private Figure square;
    private Figure polygon;
    private List<Figure> sequenceFigureComputer;
    private List<Figure> sequenceFigurePlayer;

    
    public GameActivity(){
        // Contains the list of all possible figures
        figureList = new FigureList();

        // init figure object from the list
        triangle = figureList.getFigureFromNameInFigureList("Triangle");
        oval = figureList.getFigureFromNameInFigureList("Oval");
        square = figureList.getFigureFromNameInFigureList("Square");
        polygon = figureList.getFigureFromNameInFigureList("Polygon");

        // init figure sequence for computer and for the player
        sequenceFigureComputer = new ArrayList<>();
        sequenceFigurePlayer = new ArrayList<>();
        hasWon = false;
        hasAlreadyPlayed = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // get level chosen from the main activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            level = extras.getInt("level");
        }


        // init max size of the cat sequence using the level
        switch (level) {
            case 1:
                sequenceFigureMaxSize = 6;
                break;
            case 2:
                sequenceFigureMaxSize = 9;
                break;
            case 3:
                sequenceFigureMaxSize = 12;
                break;
            case 4:
                sequenceFigureMaxSize = 16;
                break;
            default:
                sequenceFigureMaxSize = 6;
                break;
        }

        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Animation of the computer are played
        playAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    /**
     * Initialization of the figure images button with the view
     * Initialization of the pictures for the figure in the image button
     */
    private void initImageButtons() {
        // Init image button
        ovalImageButton = (ImageButton) findViewById(oval.getImageButtonId());
        triangleImageButton = (ImageButton) findViewById(triangle.getImageButtonId());
        polygonImageButton = (ImageButton) findViewById(polygon.getImageButtonId());
        squareImageButton = (ImageButton) findViewById(square.getImageButtonId());

        // Picture attribution of the figure for the image button
        ovalImageButton.setImageResource(oval.getSmallPictureId());
        triangleImageButton.setImageResource(triangle.getSmallPictureId());
        polygonImageButton.setImageResource(polygon.getSmallPictureId());
        squareImageButton.setImageResource(square.getSmallPictureId());
    }

    private void initView() {
        initImageButtons();
        updateListFigure();
        initClickListenerButtons();
        initSequenceFigureComputer();

    }


    /**
     * Initialisation of click listener for the different image button
     * For the player click  it animate the current figure, add it to the player's figure sequence and
     * check if the choice correspond to the computer template
     */
    private void initClickListenerButtons() {

        // init of the click listener triangle image button
        triangleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                triangle.animateFigure(view.getContext(), triangleImageButton);
                sequenceFigurePlayer.add(triangle);
                compareFigureSequence(view.getContext());
            }
        });

        // init of the click listener maine coon image button
        ovalImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                oval.animateFigure(view.getContext(), ovalImageButton);
                sequenceFigurePlayer.add(oval);
                compareFigureSequence(view.getContext());

            }
        });

        // init of the click listener polygon image button
        polygonImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                polygon.animateFigure(view.getContext(), polygonImageButton);
                sequenceFigurePlayer.add(polygon);
                compareFigureSequence(view.getContext());
            }
        });

        // init of the click listener of the square image button
        squareImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                square.animateFigure(view.getContext(), squareImageButton);
                sequenceFigurePlayer.add(square);
                compareFigureSequence(view.getContext());
            }
        });


    }

    /**
     * Check if the player's figure sequence correspond to the computer's figure sequence
     * @param context
     */
    public void compareFigureSequence(Context context) {
        hasAlreadyPlayed = true;

        for (int i = 0; i < sequenceFigurePlayer.size() && i < sequenceFigureComputer.size(); i++) {
            // The player lose
            if (!sequenceFigurePlayer.get(i).equals(sequenceFigureComputer.get(i))) {
                sequenceFigurePlayer.get(i).cancelSound(context);
                disableAllButton();
                hasWon = false;
                // Return to map activity with the result of the game
                setResult(Activity.RESULT_OK, new Intent().putExtra("hasAlreadyPlayed", hasAlreadyPlayed).putExtra("hasWon", hasWon));
                finish();
                break;
            }
        }
        // The player win, the game redirect to the maps
        if (sequenceFigurePlayer.equals(sequenceFigureComputer)) {

            disableAllButton();
            sequenceFigurePlayer.get(sequenceFigurePlayer.size() - 1).cancelSound(context);

            // Return to map activity with the result of the game
            hasWon = true;
            setResult(Activity.RESULT_OK, new Intent().putExtra("hasAlreadyPlayed", hasAlreadyPlayed).putExtra("hasWon", hasWon));
            finish();

        }
    }

    // Create the random figure sequence of the computer
    private void initSequenceFigureComputer() {
        int minimum = 0;
        int maximum = figureList.size() - 1;
        for(int i = 0; i < sequenceFigureMaxSize; ++i){
            // The random is slightly modified to avoid the same sequence of 1 figure only
            if(i == 3 % 2){
                sequenceFigureComputer.add(figureList.getFigureList().get(3));
            } else if (i == 2 % 3){
                sequenceFigureComputer.add(figureList.getFigureList().get(2));
            }
            else {
                int randomNum = minimum + (int)(Math.random() * maximum);
                sequenceFigureComputer.add(figureList.getFigureList().get(randomNum));
            }
        }
    }

    /**
     * This function will enable the figure's image button to able the player to play
     */
    private void enableAllButton() {
        ovalImageButton.setEnabled(true);
        polygonImageButton.setEnabled(true);
        squareImageButton.setEnabled(true);
        triangleImageButton.setEnabled(true);
    }

    /**
     * This function will disable the figure's image button to able the computer to play without being disturbed by the player
     */
    private void disableAllButton() {
        ovalImageButton.setEnabled(false);
        polygonImageButton.setEnabled(false);
        squareImageButton.setEnabled(false);
        triangleImageButton.setEnabled(false);
    }


    /**
     * This function will animate each button after it's clicked
     */
    private void playAnimation() {
        for (int i = 0; i < sequenceFigureComputer.size(); i++) {

            final int finalI = i;
            final Context context = this;
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    disableAllButton();
                    sequenceFigureComputer.get(finalI).animateFigure(context,
                            (ImageButton)findViewById(sequenceFigureComputer.get(finalI).getImageButtonId()));
                    if(finalI == sequenceFigureComputer.size() - 1){
                        enableAllButton();
                    }
                }
            }, 1000 * i);
            if(i == sequenceFigureComputer.size() - 1){
                enableAllButton();
            }
        }

    }

    /**
     * The list of figure is updated with the last version of figure initialised during the game
     * To avoid inconsistencies and problems
     */
    private void updateListFigure() {
        List<Figure> tempListFigure = new ArrayList<>();
        tempListFigure.add(triangle);
        tempListFigure.add(oval);
        tempListFigure.add(polygon);
        tempListFigure.add(square);
        figureList.setFigureList(tempListFigure);
    }

    @Override
    protected void onPause () {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
