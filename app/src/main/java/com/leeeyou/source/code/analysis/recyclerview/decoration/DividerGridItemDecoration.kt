package com.leeeyou.source.code.analysis.recyclerview.decoration

import android.content.Context
import android.graphics.Canvas
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

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun drawVertical(c: Canvas?, parent: RecyclerView?) {
        parent?.childCount?.also { childCount ->
            for (i in 0..childCount) {
                parent.getChildAt(i)?.also { childView ->
                    childView.layoutParams?.also {
                        val layoutParams = it as RecyclerView.LayoutParams
                        val left = childView.right + layoutParams.rightMargin
                        val right = left + mDivider.intrinsicWidth
                        val top = childView.top - layoutParams.topMargin
                        val bottom = childView.bottom + layoutParams.bottomMargin

                        if (!isLastColumn(parent, i, getSpanCount(parent), childCount)) {
                            mDivider.setBounds(left, top, right, bottom)
                            mDivider.draw(c)
                        }
                    }
                }
            }
        }
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        parent?.childCount?.also { childCount ->
            for (i in 0..childCount) {
                parent.getChildAt(i)?.also { childView ->
                    childView.layoutParams?.also {
                        val layoutParams = it as RecyclerView.LayoutParams
                        val left = childView.left - layoutParams.leftMargin
                        val right = childView.right + layoutParams.rightMargin
                        val top = childView.bottom + layoutParams.bottomMargin
                        val bottom = top + mDivider.intrinsicHeight

                        if (!isLastRaw(parent, i, getSpanCount(parent), childCount)) {
                            mDivider.setBounds(left, top, right, bottom)
                            mDivider.draw(c)
                        }
                    }
                }
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
        when (layoutManager) {
            is GridLayoutManager -> {
                val orientation = layoutManager.orientation
                if (orientation == GridLayoutManager.VERTICAL) {
                    // GridLayoutManager 且竖向滚动
                    if (childCount % spanCount == 0) {
                        if (pos >= childCount - spanCount) return true
                    } else {
                        //得到最后一行开始的位置坐标：总数 - 余下的元素 就是最后一行开始的位置坐标
                        val lastRawPositionStart = childCount - childCount % spanCount
                        if (pos >= lastRawPositionStart) return true
                    }
                } else {
                    // GridLayoutManager 且横向滚动
                    // 如果是最后一行，则不需要绘制底部
                    if ((pos + 1) % spanCount == 0) return true
                }
            }
            is StaggeredGridLayoutManager -> {
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
        }
        return false
    }

    private fun isLastColumn(parent: RecyclerView, pos: Int, spanCount: Int, childCount: Int): Boolean {
        val layoutManager = parent.layoutManager
        when (layoutManager) {
            is GridLayoutManager -> {
                val orientation = layoutManager.orientation
                if (orientation == GridLayoutManager.VERTICAL) {
                    // 如果是最后一列，则不需要绘制右边
                    if ((pos + 1) % spanCount == 0) return true
                } else {
                    if (childCount % spanCount == 0) {
                        if (pos >= childCount - spanCount) return true
                    } else {
                        // 如果是最后一列，则不需要绘制右边
                        val lastRawPositionStart = childCount - childCount % spanCount
                        if (pos >= lastRawPositionStart) return true
                    }
                }
            }
            is StaggeredGridLayoutManager -> {
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
        }
        return false
    }

}