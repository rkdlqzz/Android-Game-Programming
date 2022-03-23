package com.example.cardsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] BUTTON_IDS = new int[]{
            R.id.card_00, R.id.card_01, R.id.card_02, R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12, R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22, R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32, R.id.card_33
    };
    private int[] resIds = new int[] {
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_qh, R.mipmap.card_kd
    };

    private ImageButton previousImageButton;
    private int flips;
    private TextView scoreTextView;
    private int openCardCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setScore 호출할 때(점수갱신)마다 find id 하는 것 보단 onCreate에서 한번만 해주는 것이 좋다
        scoreTextView = findViewById(R.id.scoreTextView);

        startGame();
    }

    private void startGame() {
        Random r = new Random();
        for(int i = 0; i < resIds.length;i++) {
            int t = r.nextInt(resIds.length);
            int id = resIds[t];
            resIds[t] = resIds[i];
            resIds[i] = id;
        }

        openCardCount = BUTTON_IDS.length;
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            int resId = resIds[i];
            btn.setImageResource(R.mipmap.card_blue_back);
            btn.setVisibility(View.VISIBLE);
            btn.setTag(resId);
        }
        setScore(0);
        previousImageButton = null;
    }

    public void onBtnRestart(View view) {
        Log.d(TAG, "Restart");
        askRetry();
    }

    private void askRetry() {
        // 로컬변수 사용하지 않는 방식으로 많이들 사용함(아래 방식 x)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.restart); // string table 사용 (다른 언어 지원할 수 있게됨)
        builder.setMessage(R.string.restart_alert_msg);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startGame();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        // builder 생성, 설정 후에 alert 생성
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onBtnCard(View view) {
        if (!(view instanceof ImageButton)) {
            Log.e(TAG, "Not an ImageButton: " + view);
            return;
        }

        ImageButton imageButton = (ImageButton) view;
        int cardIndex = findButtonIndex(imageButton.getId());
        Log.d(TAG, "Card Index: " + cardIndex);

        // 같은 버튼을 눌렀으면 리턴 & toast message 출력
        if (previousImageButton == imageButton) {
            Log.d(TAG, "Same Image Button");
            Toast.makeText(this, R.string.same_card, Toast.LENGTH_SHORT).show();
            scoreTextView.setTextColor(Color.RED);  // scoreText의 색생 변경
            return;
        }
        scoreTextView.setTextColor(Color.GRAY);

        int resId = (Integer) imageButton.getTag();
        if (previousImageButton != null) {
            int previousResourceId = (Integer) previousImageButton.getTag();
            // 이전 버튼의 이미지와 지금 누른 버튼의 이미지가 같으면 안보이도록
            if (resId == previousResourceId) {
                imageButton.setVisibility(View.INVISIBLE);
                previousImageButton.setVisibility(View.INVISIBLE);
                openCardCount -= 2;
                if (openCardCount == 0) {
                    askRetry();
                }
                previousImageButton = null; // 같은 이미지면 이전 버튼 초기화(이전 버튼을 null로)
            } else {
                // 다르면 지금 누른 버튼 이미지 보여주도록
                imageButton.setImageResource(resId);
                setScore(flips + 1);
                // 다른 이미지 버튼 누르면 이전 이미지 버튼 다시 뒤집기
                previousImageButton.setImageResource(R.mipmap.card_blue_back);
                previousImageButton = imageButton;

            }
        } else {
            setScore(flips + 1);
            imageButton.setImageResource(resId);
            previousImageButton = imageButton;
        }
    }

    private void setScore(int score) {
        flips = score;
        Resources res = getResources();
        String format = res.getString(R.string.flips_fmt);
        String text = String.format(format, score);
        scoreTextView.setText(text);
    }

    private int findButtonIndex(int id) {
        for (int i = 0; i < BUTTON_IDS.length; i++) {
            if (id == BUTTON_IDS[i]) {
                return i;
            }
        }
        return -1;
    }
}