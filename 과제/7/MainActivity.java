package com.example.imageswitcher;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    protected static final int[] RES_IDS = new int[]{
            R.mipmap.category_color,
            R.mipmap.author_color,
            R.mipmap.graph_color,
            R.mipmap.mail_color,
            R.mipmap.map_color
    };

    protected int page;

    protected ImageButton prevBtn;
    protected ImageButton nextBtn;

    private SharedPreferences preferences;

    // 최초 1회 호출
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // setPage 호출 전에 버튼 생성
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);

        // 저장된 page가 있으면 해당 page를 불러온다 (SharedPreferences)
        preferences = getSharedPreferences("PageInfo", MODE_PRIVATE);
        page = preferences.getInt("page", 1);
        setPage(page);

        //setPage(1);
    }

    public void onBtnPrev(View view) {
        Log.d(TAG, "Prev Button Pressed");
        setPage(page - 1);
    }

    public void onBtnNext(View view) {
        Log.d(TAG, "Next Button Pressed");
        setPage(page + 1);
    }

    private void setPage(int newPage) {
        if (newPage < 1 || newPage > RES_IDS.length)
            return;

        // 변경된 page를 저장
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("page", newPage);
        editor.commit();

        page = newPage;
        String text = page + " / " + RES_IDS.length;
        TextView tv = findViewById(R.id.pageText);
        tv.setText(text);

        ImageView iv = findViewById(R.id.contentImageView);
        iv.setImageResource(RES_IDS[page - 1]);

        prevBtn.setEnabled(newPage != 1);
        nextBtn.setEnabled(newPage != RES_IDS.length);
    }
}