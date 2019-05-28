package com.example.todomobi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class MainActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new Step.Builder()
                .setTitle("This is header 1")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.img1) // int top drawable
                .build());

        addFragment(new Step.Builder()
                .setTitle("This is header 2")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.img2) // int top drawable
                .build());

        addFragment(new Step.Builder()
                .setTitle("This is header 3")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.img3) // int top drawable
                .build());
    }

    @Override
    public void finishTutorial() {
        // Open google sign when done with slides
        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void currentFragmentPosition(int position) {

    }
}
