package com.luwu.xgo_robot.mActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.luwu.xgo_robot.R;

import static com.luwu.xgo_robot.mMothed.PublicMethod.hideBottomUIMenu;

public class AboutActivity extends AppCompatActivity {

    private ImageButton aboutBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutBtnBack = findViewById(R.id.aboutBtnBack);
        aboutBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        hideBottomUIMenu(AboutActivity.this);
    }
}
