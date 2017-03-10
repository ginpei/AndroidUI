package info.ginpei.androidui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SpiralActivity extends AppCompatActivity {

    public static final String TAG = "G#SpiralActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CanvasView(this));
    }

    class CanvasView extends View {

        Paint paint = null;

        public CanvasView(Context context) {
            super(context);
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // reset
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            // parameters
            int width = getWidth();
            int height = getHeight();
            int fineness = 1000;
            float x0 = width / 2;
            float y0 = height / 2;
            float radius = Math.max(x0, y0);
            int rollings = 10;

            // styles
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);

            // calculated values
            double wholeDegree = Math.PI * 2 * rollings;

            // loop to draw
            float x1 = x0;
            float y1 = y0;
            for (int i = 0; i < fineness; i++) {
                float progress = ((float) i) / fineness;
                float r = radius * progress;
                double d = wholeDegree * progress;

                float x2 = (float) (x0 + r * Math.cos(d));
                float y2 = (float) (y0 + r * Math.sin(d));

                canvas.drawLine(x1, y1, x2, y2, paint);
                x1 = x2;
                y1 = y2;
            }
        }
    }
}
