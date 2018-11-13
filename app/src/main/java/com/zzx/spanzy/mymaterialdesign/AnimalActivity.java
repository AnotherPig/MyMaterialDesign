package com.zzx.spanzy.mymaterialdesign;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class AnimalActivity extends AppCompatActivity {
    public static final String ANIMAL_NNAME = "animal_name";
    public static final String ANIMAL_IMAGE_ID = "animal_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        Intent intent = getIntent();
        final String animalName = intent.getStringExtra(ANIMAL_NNAME);
        int animalImageId = intent.getIntExtra(ANIMAL_IMAGE_ID,0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        ImageView animalImageView = findViewById(R.id.animal_image_view);
        TextView animalContentText = findViewById(R.id.animal_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(animalName);
        Glide.with(this).load(animalImageId).into(animalImageView);
        String animalContent = generateAnimalContent(animalName);
        animalContentText.setText(animalContent);
        FloatingActionButton fab_collect = findViewById(R.id.fab_collect);
        fab_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,animalName +" has collected",Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(AnimalActivity.this,
                                        animalName +" has removed", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }

    private String generateAnimalContent(String animalName) {
        StringBuilder animalContent = new StringBuilder();
        for (int i = 0; i < 500 ; i++) {
            animalContent.append(animalName);
        }
        return animalContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
