package com.jiayang.coordinatorlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jiayang.coordinatorlayout.toolbarfolding.ToolBarFoldingActivity;
import com.jiayang.coordinatorlayout.toolbarhidden.ToolBarHiddenActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void testHidden(View view) {
        Intent intent = new Intent(this, ToolBarHiddenActivity.class);
        startActivity(intent);

    }

    public void testFolding(View view) {
        Intent intent = new Intent(this, ToolBarFoldingActivity.class);
        startActivity(intent);
    }
}
