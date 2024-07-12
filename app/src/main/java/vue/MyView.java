package vue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import com.dawei.gesture.R;

public class MyView extends View {
    public float bitmipX;

    public float bitmipY;
    private Bitmap bitmap;
    public MyView(Context context) {
        super(context);

        bitmipX = 290;

        bitmipY = 130;

    }

    @Override

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.img);
// 定义绘图对象

        Paint paint = new Paint();

//缩放绘图内容
        float scale = 0.10f;
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

//开始绘图

        canvas.drawBitmap(scaledBitmap, bitmipX-44, bitmipY-28, paint);

        if (bitmap.isRecycled()){
            bitmap.recycle();// 回收图片

        }

    }

}

