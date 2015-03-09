package com.pgl8.guajerez;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Clase dedicada a dibujar las líneas de separación de los elementos del recyclerview
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    // Constructor por defecto de un solo parámetro
    public DividerItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.line_divider);
    }

    // Función que dibuja las líneas divisorias de los elementos del recyclerview
    @Override
    public void onDrawOver(Canvas c, RecyclerView rv, RecyclerView.State state) {
        // se obtienen las líneas verticales de la vista
        int left = rv.getPaddingLeft();
        int right = rv.getWidth() - rv.getPaddingRight();

        // para cada elemento de la vista se dibujan las líneas horizontales
        int childCount = rv.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = rv.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}