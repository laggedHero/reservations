package net.laggedhero.reservations.ui.common

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * https://github.com/bignerdranch/simple-item-decoration/blob/master/simpleitemdecoration/src/main/java/com/dgreenhalgh/android/simpleitemdecoration/grid/GridDividerItemDecoration.java
 */

class GridDividerItemDecoration(
    private val horizontalDivider: Drawable,
    private val verticalDivider: Drawable,
    private val numColumns: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val childIsInLeftmostColumn = parent.getChildAdapterPosition(view) % numColumns == 0
        if (!childIsInLeftmostColumn) {
            outRect.left = horizontalDivider.intrinsicWidth
        }

        val childIsInFirstRow = parent.getChildAdapterPosition(view) < numColumns
        if (!childIsInFirstRow) {
            outRect.top = verticalDivider.intrinsicHeight
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawHorizontalDividers(canvas, parent);
        drawVerticalDividers(canvas, parent);
    }

    private fun drawHorizontalDividers(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        val rowCount = childCount / numColumns
        val lastRowChildCount = childCount % numColumns

        for (i in 1 until numColumns) {
            val lastRowChildIndex: Int = if (i < lastRowChildCount) {
                i + rowCount * numColumns
            } else {
                i + (rowCount - 1) * numColumns
            }

            val firstRowChild = parent.getChildAt(i) ?: return
            val lastRowChild = parent.getChildAt(lastRowChildIndex) ?: return

            val dividerTop = firstRowChild.top
            val dividerRight = firstRowChild.left
            val dividerLeft = dividerRight - horizontalDivider.intrinsicWidth
            val dividerBottom = lastRowChild.bottom

            horizontalDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            horizontalDivider.draw(canvas)
        }
    }

    private fun drawVerticalDividers(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        val rowCount = childCount / numColumns
        for (i in 1..rowCount) {
            val rightmostChildIndex = if (i == rowCount) {
                parent.childCount - 1
            } else {
                i * numColumns + numColumns - 1
            }

            val leftmostChild = parent.getChildAt(i * numColumns) ?: return
            val rightmostChild = parent.getChildAt(rightmostChildIndex) ?: return

            val dividerLeft = leftmostChild.left
            val dividerBottom = leftmostChild.top
            val dividerTop = dividerBottom - verticalDivider.intrinsicHeight
            val dividerRight = rightmostChild.right

            verticalDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            verticalDivider.draw(canvas)
        }
    }
}
