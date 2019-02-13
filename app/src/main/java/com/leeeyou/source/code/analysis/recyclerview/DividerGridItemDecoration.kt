package com.leeeyou.source.code.analysis.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

class DividerGridItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val ATTRS = intArrayOf(android.R.attr.listDivider)
    private var mDivider: Drawable

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun drawVertical(c: Canvas?, parent: RecyclerView?) {
        parent?.childCount?.also {
            for (i in 0..it) {
                val child = parent.getChildAt(i)
                val layoutParams = child.layoutParams as RecyclerView.LayoutParams
                val top = child.top - layoutParams.topMargin
                val bottom = child.bottom + layoutParams.bottomMargin
                val left = child.right + layoutParams.rightMargin
                val right = left + mDivider.intrinsicWidth

                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(c)
            }
        }
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        parent?.childCount?.also {
            for (i in 0..it) {
                val child = parent.getChildAt(i)
                val layoutParams = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + layoutParams.bottomMargin
                val bottom = top + mDivider.intrinsicHeight
                val left = child.left - layoutParams.leftMargin
                val right = child.right + layoutParams.rightMargin + mDivider.intrinsicWidth

                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(c)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect?, itemPosition: Int, parent: RecyclerView?) {
        parent?.also {
            val spanCount = getSpanCount(parent)
            val childCount = parent.adapter.itemCount
            when {
                isLastRaw(parent, itemPosition, spanCount, childCount) -> outRect?.set(0, 0, mDivider.intrinsicWidth, 0)
                isLastColum(parent, itemPosition, spanCount, childCount) -> outRect?.set(0, 0, mDivider.intrinsicWidth, 0)
                else -> outRect?.set(0, 0, mDivider.intrinsicWidth, 0)
            }
        }
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        var spanCount: Int = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }

    private fun isLastRaw(parent: RecyclerView, pos: Int, spanCount: Int, childCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            //得到最后一行开始的位置坐标：总数 - 余下的元素 就是最后一行开始的位置坐标
            val lastRawPositionStart = childCount - childCount % spanCount
            if (pos >= lastRawPositionStart) return true
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                val lastRawPositionStart = childCount - childCount % spanCount
                // 如果是最后一行，则不需要绘制底部
                if (pos >= lastRawPositionStart) return true
            } else {
                // StaggeredGridLayoutManager 且横向滚动
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) return true
            }
        }
        return false
    }

    private fun isLastColum(parent: RecyclerView, pos: Int, spanCount: Int, childCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            // 如果是最后一列，则不需要绘制右边
            if ((pos + 1) % spanCount == 0) return true
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager.orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是最后一列，则不需要绘制右边
                if ((pos + 1) % spanCount == 0) return true
            } else {
                val lastRawPositionStart = childCount - childCount % spanCount
                // 如果是最后一列，则不需要绘制右边
                if (pos >= lastRawPositionStart) return true
            }
        }
        return false
    }
}