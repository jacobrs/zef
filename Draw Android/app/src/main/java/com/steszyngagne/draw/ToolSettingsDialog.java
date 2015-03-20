package com.steszyngagne.draw;

import ca.qc.johnabbott.cs603.R;

import com.steszyngagne.draw.Tools.ToolName;
import com.steszyngagne.draw.Shapes.*;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ToolSettingsDialog extends Dialog {

    private ToolBox toolbox;
    private Paint previewPaint;

    /* Interface elements */
    private RadioButton radioRectangle;
    private RadioButton radioSquare;
    private RadioButton radioCircle;
    private RadioButton radioOval;
    private RadioButton radioLine;
    private SeekBar seekBarWidth;
    private ImageButton buttonStrokeColor;
    private ImageButton buttonFillColor;

    public ToolSettingsDialog(Context context, ToolBox tbox) {
        super(context);
        this.setContentView(R.layout.dialog_tools);
        this.setTitle(R.string.tools_dialog_title);
        this.setCanceledOnTouchOutside(true);

        this.toolbox = tbox;

        this.previewPaint = new Paint();
        this.previewPaint.setAntiAlias(true);
        this.previewPaint.setStrokeCap(Paint.Cap.ROUND);

        radioRectangle = (RadioButton) findViewById(R.id.radioRectangle);
        radioRectangle.setOnClickListener(new ToolClick(ToolName.RECTANGLE));

        radioLine = (RadioButton) findViewById(R.id.radioLine);
        radioLine.setOnClickListener(new ToolClick(ToolName.LINE));

        radioSquare = (RadioButton) findViewById(R.id.radioSquare);
        radioSquare.setOnClickListener(new ToolClick(ToolName.SQUARE));

        radioCircle = (RadioButton) findViewById(R.id.radioCircle);
        radioCircle.setOnClickListener(new ToolClick(ToolName.CIRCLE));

        radioOval = (RadioButton) findViewById(R.id.radioOval);
        radioOval.setOnClickListener(new ToolClick(ToolName.OVAL));

        switch(toolbox.getCurrentToolName()) {
            case RECTANGLE:
                radioRectangle.setChecked(true); break;
            case LINE:
                radioLine.setChecked(true); break;
            case CIRCLE:
                radioCircle.setChecked(true); break;
            case OVAL:
                radioOval.setChecked(true); break;
            case SQUARE:
                radioSquare.setChecked(true); break;
            default:
                radioLine.setChecked(true);
        }

        seekBarWidth = (SeekBar)this.findViewById(R.id.widthSeekBar);
        seekBarWidth.setProgress(toolbox.getStrokeWidth());
        seekBarWidth.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
                toolbox.setStrokeWidth(progress);
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {}

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {}
        });

        buttonStrokeColor = (ImageButton) findViewById(R.id.btnFG);
        buttonStrokeColor.setBackgroundColor(toolbox.getStrokeColor());
        buttonStrokeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ColorChooserDialog dialog = new ColorChooserDialog(getContext(), toolbox.getStrokeColor());
                dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toolbox.setStrokeColor(dialog.getColor());
                        buttonStrokeColor.setBackgroundColor(dialog.getColor());
                        updatePreview();
                    }
                });
                dialog.show();

            }
        });


        buttonFillColor = (ImageButton) findViewById(R.id.btnBG);
        buttonFillColor.setBackgroundColor(toolbox.getFillColor());

        buttonFillColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ColorChooserDialog dialog = new ColorChooserDialog(getContext(), toolbox.getFillColor());
                dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toolbox.setFillColor(dialog.getColor());
                        buttonFillColor.setBackgroundColor(dialog.getColor());
                        updatePreview();
                    }
                });
                dialog.show();
            }
        });

        Button done = (Button)this.findViewById(R.id.widthDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        updatePreview();
        this.show();
    }

    private void updatePreview() {

        ImageView image = (ImageView)findViewById(R.id.widthImageView);

        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);

        Shape s = null;
        switch(toolbox.getCurrentToolName()) {
            case LINE:
                s = new Line(50, 50, 350, 350, toolbox.getStrokeColor(), toolbox.getStrokeWidth());
                break;
            case RECTANGLE:
                s = new Rectangle(50, 75, 350, 325, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
                break;
            case SQUARE:
                s = new Rectangle(50, 50, 350, 350, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
                break;
            case OVAL:
                s = new Oval(50, 75, 350, 325, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
                break;
            case CIRCLE:
                s = new Oval(50, 50, 350, 350, toolbox.getStrokeColor(), toolbox.getStrokeWidth(), toolbox.getFillColor());
                break;
            default:
                s = new Line(30, 50, 370, 50, toolbox.getStrokeColor(), toolbox.getStrokeWidth());
        }

        s.draw(previewPaint, canvas, false);
        image.setImageBitmap(bitmap);
    }

    private class ToolClick implements View.OnClickListener {

        private ToolName name;

        public ToolClick(ToolName name) {
            this.name = name;
        }

        @Override
        public void onClick(View view) {
            toolbox.changeTool(name);
            updatePreview();
        }

    }


}
