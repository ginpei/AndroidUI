package info.ginpei.androidui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class SpiralActivity extends AppCompatActivity {

    public static final String TAG = "G#SpiralActivity";
    private SeekBar rollingsSeekBar;
    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiral);

        rollingsSeekBar = (SeekBar) findViewById(R.id.seekBar_rollings);

        canvasView = new CanvasView(this);
        ((RelativeLayout) findViewById(R.id.layout_canvas)).addView(canvasView);

        rollingsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                canvasView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    class CanvasView extends View {

        Paint paint = null;
        private Path path;

        public CanvasView(Context context) {
            super(context);
            paint = new Paint();
            path = new Path();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            final int MIN_STROKE_WIDTH = 50;
            final float startOffset = ((float) 2) / 5;

            super.onDraw(canvas);

            // reset
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            // parameters
            int width = getWidth();
            int height = getHeight();
            float x0 = width / 2;
            float y0 = height / 2;

            int rollings = rollingsSeekBar.getProgress();
            int fineness = 60 * rollings;
            float canvasRadius = Math.min(x0, y0);
            float strokeWidth = Math.min(MIN_STROKE_WIDTH, canvasRadius * (1 - startOffset) / (rollings * 2));
            float spiralRadius = canvasRadius - strokeWidth / 2;

            // styles
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(strokeWidth);
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            paint.setStrokeCap(Paint.Cap.ROUND);
//            paint.setStrokeJoin(Paint.Join.ROUND);  // not so effective?

            // calculated values
            double wholeDegree = Math.PI * 2 * rollings;

            // loop to draw
            path.reset();
            float[] p0 = pos(x0, y0, spiralRadius, startOffset, wholeDegree, 0);
            path.moveTo(p0[0], p0[1]);
            for (int i = 1; i < fineness; i++) {
                float progress = ((float) i) / fineness;
                float[] pos = pos(x0, y0, spiralRadius, startOffset, wholeDegree, progress);
                path.lineTo(pos[0], pos[1]);
            }

            // then, draw
            canvas.drawPath(path, paint);
        }

        private float[] pos(float x0, float y0, float radius, float offsetStart, double wholeDegree, float progress) {
            float r = radius * offsetStart + radius * (1 - offsetStart) * progress;
            double d = wholeDegree * progress;

            return new float[]{
                    (float) (x0 + r * Math.cos(d)),
                    (float) (y0 + r * Math.sin(d)),
            };
        }
    }
}
