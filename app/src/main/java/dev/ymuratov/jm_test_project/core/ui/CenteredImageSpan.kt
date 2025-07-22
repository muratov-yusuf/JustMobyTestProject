package dev.ymuratov.jm_test_project.core.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import androidx.core.graphics.withSave

class CenteredImageSpan(drawable: Drawable) : ImageSpan(drawable) {

    override fun draw(
        canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint
    ) {
        val drawable = drawable
        canvas.withSave {
            val fontMetrics = paint.fontMetricsInt
            val transY = (y + fontMetrics.descent + y + fontMetrics.ascent) / 2 - drawable.bounds.height() / 2

            translate(x, transY.toFloat())
            drawable.draw(this)
        }
    }
}
