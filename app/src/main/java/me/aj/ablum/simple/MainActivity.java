package me.aj.ablum.simple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.aj.ablum.SelectImageActivity;
import me.aj.ablum.SelectType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SelectImageActivity.class);
        intent.putExtra(SelectImageActivity.EXTRA_SELECT_TYPE, SelectType.SINGLE);
        startActivity(intent);
    }
}
