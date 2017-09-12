package com.younchen.younsampleproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotFound404Activity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, NotFound404Activity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_found404);
    }
}
