package com.leeeyou.source.code.analysis.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout

class DividerLinearItemDecoration(context: Context, orientation: Int) : RecyclerView.ItemDecoration() {
    private val HORIZONTAL = LinearLayout.HORIZONTAL
    private val VERTICAL = LinearLayout.VERTICAL
    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    private var mDivider: Drawable
    private var mOrientation: Int = 0

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()

        mOrientation = orientation
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (mOrientation == HORIZONTAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(c: Canvas?, parent: RecyclerView?) {
        parent?.childCount?.also { childCount ->
            for (i in 0..(childCount - 2)) {
                parent.getChildAt(i)?.also { childView ->
                    childView.layoutParams?.also {
                        val layoutParams = it as RecyclerView.LayoutParams
                        val top = childView.top - layoutParams.topMargin
                        val bottom = childView.bottom + layoutParams.bottomMargin
                        val left = childView.right + layoutParams.rightMargin
                        val right = left + mDivider.intrinsicWidth

                        mDivider.setBounds(left, top, right, bottom)
                        mDivider.draw(c)
                    }
                }
            }
        }
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        parent?.childCount?.also { childCount ->
            for (i in 0..(childCount - 2)) {
                parent.getChildAt(i)?.also { childView ->
                    childView.layoutParams?.also {
                        val layoutParams = it as RecyclerView.LayoutParams
                        val top = childView.bottom + layoutParams.bottomMargin
                        val bottom = top + mDivider.intrinsicHeight
                        val left = childView.left - layoutParams.leftMargin
                        val right = childView.right + layoutParams.rightMargin + mDivider.intrinsicWidth

                        mDivider.setBounds(left, top, right, bottom)
                        mDivider.draw(c)
                    }
                }
            }
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        parent?.also {
            val childCount = parent.adapter.itemCount
            if (isLastOne(parent, (view?.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition, childCount)) {
                outRect?.set(0, 0, 0, 0)
            } else {
                if (mOrientation == VERTICAL) {
                    outRect?.set(0, 0, 0, mDivider.intrinsicHeight)
                } else {
                    outRect?.set(0, 0, mDivider.intrinsicWidth, 0)
                }
            }
        }
    }

    private fun isLastOne(parent: RecyclerView, pos: Int, childCount: Int): Boolean {
        return parent.layoutManager is LinearLayoutManager && pos == childCount - 1
    }

    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw IllegalArgumentException("Invalid orientation. It should be either HORIZONTAL or VERTICAL")
        }
        mOrientation = orientation
    }
}