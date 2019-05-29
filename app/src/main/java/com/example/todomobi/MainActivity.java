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
                .setTitle("The beauty of photography")
                .setContent("express yourself with images")
                .setBackgroundColor(Color.parseColor("#90c030")) // int background color
                .setDrawable(R.drawable.img1) // int top drawable
                .build());

        addFragment(new Step.Builder()
                .setTitle("Researching with the right tool")
                .setContent("make life easier")
                .setBackgroundColor(Color.parseColor("#90c030")) // int background color
                .setDrawable(R.drawable.img2) // int top drawable
                .build());

        addFragment(new Step.Builder()
                .setTitle("The sea in it's beauty")
                .setContent("peaceful and calm")
                .setBackgroundColor(Color.parseColor("#90c030")) // int background color
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
