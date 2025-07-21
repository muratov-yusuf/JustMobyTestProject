package dev.ymuratov.jm_test_project.feature.info.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalPaddingDecoration(
    private val startPaddingPx: Int, private val endPaddingPx: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        when (position) {
            0 -> outRect.left = startPaddingPx
            itemCount - 1 -> outRect.right = endPaddingPx
        }
    }
}
