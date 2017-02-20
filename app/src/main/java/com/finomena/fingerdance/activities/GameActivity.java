package com.finomena.fingerdance.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.finomena.fingerdance.R;
import com.finomena.fingerdance.adapters.GridAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by adarsh on 20/02/17.
 */

public class GameActivity extends AppCompatActivity {

    GridView gridView;
    Map<Integer, String> remainingViews, remainingViewsForSelection;
    int randomSelectedValue;
    int gameSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        gameSize = getIntent().getIntExtra("SELECTION", 0);

        InitialiseView();

        if (gameSize != 0) {
            InitialiseViewsInHashMap((gameSize * gameSize));
            SelectRandomView();
        }
    }

    private void InitialiseView() {
        gridView = (GridView) findViewById(R.id.game_grid);
    }

    private void InitialiseViewsInHashMap(int count) {
        remainingViews = new LinkedHashMap<>();
        remainingViewsForSelection = new LinkedHashMap<>();

        for (int i = 1; i <= count; i++) {
            remainingViews.put(i, "View" + i);
            remainingViewsForSelection.put(i, "View" + i);
        }
    }

    private void SelectRandomView() {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        List<Integer> valuesList = new ArrayList<Integer>(remainingViewsForSelection.keySet());
        int randomIndex = new Random().nextInt(valuesList.size());
        randomSelectedValue = valuesList.get(randomIndex);

        gridView.setNumColumns(gameSize);
        gridView.setAdapter(new GridAdapter(this, remainingViews, randomSelectedValue, (height), width, gameSize));
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getPointerCount() > 1) {
                    int pointerCount = motionEvent.getPointerCount();
                    for (int i = (pointerCount - 1); i < pointerCount; i++) {
                        int x = (int) motionEvent.getX(i);
                        int y = (int) motionEvent.getY(i);
                        int action = motionEvent.getActionMasked();
                        switch (action) {
                            case MotionEvent.ACTION_DOWN:
                                break;
                            case MotionEvent.ACTION_UP:
                                GameOver();
                                break;
                            case MotionEvent.ACTION_POINTER_DOWN:
                                int position = gridView.pointToPosition((int) x, (int) y);
                                if (position + 1 == randomSelectedValue) {
                                    //Continue the game
                                    ProceedToHighLightNextView();
                                } else {
                                    //Game over
                                    GameOver();
                                }
                                break;
                            case MotionEvent.ACTION_POINTER_UP:
                                GameOver();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                break;
                            default:
                        }
                    }

                } else {
                    int action = motionEvent.getActionMasked();

                    switch (action) {

                        case MotionEvent.ACTION_DOWN:

                            int currentXPosition = (int) motionEvent.getX();
                            int currentYPosition = (int) motionEvent.getY();
                            int position = gridView.pointToPosition((int) currentXPosition, (int) currentYPosition);

                            if (position + 1 == randomSelectedValue) {
                                //Continue the game
                                ProceedToHighLightNextView();
                            } else {
                                //Game over
                                GameOver();
                            }
                            break;

                        case MotionEvent.ACTION_MOVE:
                            break;

                        case MotionEvent.ACTION_UP:
                            GameOver();
                            break;

                        case MotionEvent.ACTION_CANCEL:
                            break;

                        case MotionEvent.ACTION_OUTSIDE:
                            break;
                    }
                }
                return true;
            }
        });
    }

    private void GameOver() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("GAME OVER !! \nResart ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        startActivity(new Intent(GameActivity.this, GameSizeActivity.class));
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.setCancelable(false);
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }

    private void ProceedToHighLightNextView() {
        //Remove the previously selected view from the HashMap
        remainingViewsForSelection.remove(randomSelectedValue);

        if (remainingViewsForSelection.size() > 0) {
            SelectRandomView();
        } else {
            GameOver();
        }
    }
}
