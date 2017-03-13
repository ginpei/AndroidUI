package info.ginpei.androidui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class SpiralActivity extends AppCompatActivity {

    public static final String TAG = "G#SpiralActivity";
    private TextView rollingsTextView;
    private SeekBar rollingsSeekBar;
    private TextView finenessTextView;
    private SeekBar finenessSeekBar;
    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiral);

        rollingsTextView = (TextView) findViewById(R.id.text_rollings);
        rollingsSeekBar = (SeekBar) findViewById(R.id.seekBar_rollings);
        finenessTextView = (TextView) findViewById(R.id.text_fineness);
        finenessSeekBar = (SeekBar) findViewById(R.id.seekBar_fineness);

        canvasView = new CanvasView(this);
        ((RelativeLayout) findViewById(R.id.layout_canvas)).addView(canvasView);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
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
        };
        rollingsSeekBar.setOnSeekBarChangeListener(listener);
        finenessSeekBar.setOnSeekBarChangeListener(listener);
    }

    class CanvasView extends View {

        public static final int MIN_FINENESS = 4;
        final Paint bgPaint = new Paint();
        final Paint spiralPaint = new Paint();
        final Paint arcPaint = new Paint();
        private Path path;
        private Path arcPath = new Path();
        private RectF rect = new RectF();

        public CanvasView(Context context) {
            super(context);

            bgPaint.setStyle(Paint.Style.FILL);
            bgPaint.setColor(Color.WHITE);

            spiralPaint.setStyle(Paint.Style.STROKE);
            spiralPaint.setColor(Color.RED);
            spiralPaint.setAntiAlias(true);
            spiralPaint.setStrokeCap(Paint.Cap.ROUND);
//            spiralPaint.setStrokeJoin(Paint.Join.ROUND);  // not so effective?

            arcPaint.setStyle(Paint.Style.STROKE);
            arcPaint.setColor(Color.BLUE);
            arcPaint.setAntiAlias(true);
            arcPaint.setStrokeCap(Paint.Cap.ROUND);
//            arcPaint.setStrokeJoin(Paint.Join.ROUND);  // not so effective?

            path = new Path();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            final int MIN_STROKE_WIDTH = 50;
            final float startOffset = ((float) 2) / 5;

            super.onDraw(canvas);

            // reset
            canvas.drawPaint(bgPaint);

            // parameters
            int width = getWidth();
            int height = getHeight();
            float x0 = width / 2;
            float y0 = height / 2;

            int rollings = rollingsSeekBar.getProgress() + 1;  // at least 1
            int fineness = Math.max(MIN_FINENESS, finenessSeekBar.getProgress()) * rollings;
            rollingsTextView.setText(String.valueOf(rollings));
            finenessTextView.setText(String.valueOf(Math.max(MIN_FINENESS, finenessSeekBar.getProgress())));

            float canvasRadius = Math.min(x0, y0);
            float strokeWidth = Math.min(MIN_STROKE_WIDTH, canvasRadius * (1 - startOffset) / (rollings * 2));
            float spiralRadius = canvasRadius - strokeWidth / 2;

            // styles
            spiralPaint.setStrokeWidth(strokeWidth);

            // calculated values
            double wholeDegree = Math.PI * 2 * rollings;

            // loop to draw
            path.reset();
            float[] p0 = pos(x0, y0, spiralRadius, startOffset, wholeDegree, 0);
            path.moveTo(p0[0], p0[1]);
            for (int i = 0; i < fineness; i++) {
                float progress = ((float) i + 1) / fineness;
                float[] pos = pos(x0, y0, spiralRadius, startOffset, wholeDegree, progress);
                path.lineTo(pos[0], pos[1]);
            }

            // then, draw
            canvas.drawPath(path, spiralPaint);

            float wholeAngle = 360 * rollings;
            float startAngleOffset = -90;

            arcPath.reset();
            arcPath.moveTo(p0[0], p0[1]);
            for (int i = 0; i < fineness; i++) {
                float progress = ((float) i) / fineness;
                float radius = spiralRadius * startOffset + spiralRadius * (1 - startOffset) * progress;
                rect.set(x0 - radius, y0 - radius, x0 + radius, y0 + radius);
                Log.d(TAG, String.format("[%s] angle: %s", i, startAngleOffset + wholeAngle * progress));
                arcPath.arcTo(rect, startAngleOffset + wholeAngle * progress, wholeAngle / fineness);
            }

            arcPaint.setStrokeWidth(1);
            canvas.drawPath(arcPath, arcPaint);
        }

        private float[] pos(float x0, float y0, float radius, float offsetStart, double wholeDegree, float progress) {
            final double degreeOffset = -Math.PI * 2 / 4;  // start from 12 o'clock

            float r = radius * offsetStart + radius * (1 - offsetStart) * progress;
            double d = degreeOffset + wholeDegree * progress;

            return new float[]{
                    (float) (x0 + r * Math.cos(d)),
                    (float) (y0 + r * Math.sin(d)),
            };
        }
    }
}
