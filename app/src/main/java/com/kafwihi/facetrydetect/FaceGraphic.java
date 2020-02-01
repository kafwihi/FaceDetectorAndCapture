package com.kafwihi.facetrydetect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.gms.vision.face.Face;
import com.kafwihi.facetrydetect.camera.GraphicOverlay;

class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float GENERIC_POS_OFFSET = 20.0f;
    private static final float GENERIC_NEG_OFFSET = -20.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;
    private Bitmap bitmap;
    private Bitmap op;

    FaceGraphic(GraphicOverlay overlay,int c) {
        super(overlay);
        bitmap = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.snap);
        op = bitmap;
    }

    void setId(int id) {
        mFaceId = id;
    }
    void updateFace(Face face,int c) {
        mFace = face;
        bitmap = BitmapFactory.decodeResource(getOverlay().getContext().getResources(),  R.drawable.snap);
        op = bitmap;
        op = Bitmap.createScaledBitmap(op, (int) scaleX(face.getWidth()),
                (int) scaleY(((bitmap.getHeight() * face.getWidth()) / bitmap.getWidth())), false);
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if(face == null) return;
        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        canvas.drawBitmap(op, left, top, new Paint());
    }
}
