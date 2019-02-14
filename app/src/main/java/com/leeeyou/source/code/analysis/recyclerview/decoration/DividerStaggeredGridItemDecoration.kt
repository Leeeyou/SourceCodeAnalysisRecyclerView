package com.leeeyou.source.code.analysis.recyclerview.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView

class DividerStaggeredGridItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
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

                        mDivider.setBounds(left, top, right, bottom)
                        mDivider.draw(c)
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

                        mDivider.setBounds(left, top, right, bottom)
                        mDivider.draw(c)
                    }
                }
            }
        }
    }

}