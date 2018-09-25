package tech.nicesky.squaresloadingview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tech.nicesky.libsquaresloadingview.SquaresLoadingView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void loading(){

        int[] colorsDefault = {
                Color.parseColor("#C5523F"),
                Color.parseColor("#F2B736"),
                Color.parseColor("#499255"),
                Color.parseColor("#F2B736"),
                Color.parseColor("#499255"),
                Color.parseColor("#1875E5"),
                Color.parseColor("#499255"),
                Color.parseColor("#1875E5"),
                Color.parseColor("#C5523F"),
        };

        SquaresLoadingView squaresLoadingView = new SquaresLoadingView(this);
        squaresLoadingView.setAnimSpeed(0.5F);
        squaresLoadingView.setSquareAlpha(0.8F);
        squaresLoadingView.setColors(colorsDefault);
        squaresLoadingView.start();
        squaresLoadingView.stop();
    }
}
