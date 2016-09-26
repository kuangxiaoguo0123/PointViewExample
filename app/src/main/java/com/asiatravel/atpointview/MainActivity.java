package com.asiatravel.atpointview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.asiatravel.atpointview.view.PointView;

public class MainActivity extends AppCompatActivity {

    private PointView pointView;
    private PointView secondPointView;
    private PointView thirdPointView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointView = (PointView) findViewById(R.id.point_view);
        secondPointView = (PointView) findViewById(R.id.second_point_view);
        thirdPointView = (PointView) findViewById(R.id.third_point_view);

        findViewById(R.id.animation_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointView.startAnimation();
                secondPointView.startAnimation();
                thirdPointView.startAnimation();
            }
        });
    }
}
