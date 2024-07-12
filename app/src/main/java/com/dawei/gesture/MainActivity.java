package com.dawei.gesture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dawei.gesture.databinding.ActivityMainBinding;

import vue.MyView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'gesture' library on application startup.
    static {
        System.loadLibrary("gesture");
    }
    // 定义一个常量，表示长按时间阈值，单位为毫秒
    private static final long LONG_PRESS_TIME = 1000;
    // 记录按下的时间
    private long touchDownTime = 0;
    private GestureDetector mGestureDetector;
    private final String TAG="tag";
    private int offsetX, offsetY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideSystemUi();
        // Example of a call to a native method
//        TextView tv = binding.sampleText;
//        tv.setText(stringFromJNI());
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        Intent intent = new Intent(this,MainActivity2.class);

        // 循环生成按钮
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 23; col++) {
                // 计算当前按钮的ID
                int buttonId = 0 + (row * 30) + col;
                Button button = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row), GridLayout.spec(col));
                params.width = 80;  // 设置按钮宽度
                params.height = 80; // 设置按钮高度
                params.setMargins(7, 7, 7, 7);
                button.setLayoutParams(params);
                button.setText(String.valueOf(buttonId));
                button.setBackground(getResources().getDrawable(R.drawable.button_bg));
                // 设置按钮点击事件监听器
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 获取按钮在屏幕上的位置

                        int[] location = new int[2];
                        v.getLocationOnScreen(location);
                        int x = location[0];
                        int y = location[1];
                        Toast.makeText(MainActivity.this, "Button clicked: " + buttonId+"  x="+x+"  y="+y, Toast.LENGTH_SHORT).show();
                        //跳转
                        startActivity(intent);
                        button.setBackground(getResources().getDrawable(R.drawable.bg));
                    }
                });
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        button.setBackground(getResources().getDrawable(R.drawable.button_bg_longpress));
                        return false;
                    }
                });
                button.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if ( motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                            // 恢复按钮释放时的原始背景颜色
                            button.setBackground(getResources().getDrawable(R.drawable.button_bg));
                        }
                        return false;
                    }
                });
                // 将按钮添加到GridLayout中
                gridLayout.addView(button);
            }
        }

//
//        View movableImage = findViewById(R.id.movableImage);
//
//        // 设置触摸监听器
//        movableImage.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        // 计算偏移量，使图片的中心与手指位置对齐
//                        offsetX = (int) (event.getRawX() - v.getX() - v.getWidth() / 2);
//                        offsetY = (int) (event.getRawY() - v.getY() - v.getHeight() / 2);
//                        // 更新图片位置
//                        v.setX(event.getRawX() - v.getWidth() / 2 - offsetX);
//                        v.setY(event.getRawY() - v.getHeight() / 2 - offsetY);
//                        break;
//                }
//                return true; // 返回 true 表示消费了触摸事件
//            }
//        });
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.main_framelayout);
        final MyView mouse = new MyView(this);
        mouse.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motionEvent) {
                mouse.bitmipX = motionEvent.getX();//获取重新绘制图片的X位置

                mouse.bitmipY = motionEvent.getY();//获取重新绘制图片的Y位置

                mouse.invalidate();

                return false;

            }

        });
        frameLayout.addView(mouse);
        View overlayView = findViewById(R.id.overlay_view);
        overlayView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: x="+event.getX()+" y="+event.getY());

                return false;
            }
        });

    }

    private void hideSystemUi() {
        // 隐藏状态栏（通知栏
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        // 隐藏底部操作栏（导航栏）
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                View.SYSTEM_UI_FLAG_FULLSCREEN);
        // 使状态栏透明
        window.setStatusBarColor(Color.TRANSPARENT);
        // 使导航栏透明
        window.setNavigationBarColor(Color.TRANSPARENT);
    }
    /**
     * A native method that is implemented by the 'gesture' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}