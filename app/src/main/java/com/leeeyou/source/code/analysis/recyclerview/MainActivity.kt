package com.leeeyou.source.code.analysis.recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.leeeyou.source.code.analysis.recyclerview.adapter.SongAdapter
import com.leeeyou.source.code.analysis.recyclerview.adapter.StaggerSongAdapter
import com.leeeyou.source.code.analysis.recyclerview.decoration.DividerGridItemDecoration
import com.leeeyou.source.code.analysis.recyclerview.decoration.DividerLinearItemDecoration
import com.leeeyou.source.code.analysis.recyclerview.decoration.DividerStaggeredGridItemDecoration
import com.leeeyou.source.code.analysis.recyclerview.widget.RcyLog
import com.leeeyou.source.code.analysis.recyclerview.widget.SRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val mutableListOf: MutableList<String> = mutableListOf()
    private lateinit var dividerLinearItemDecoration: DividerLinearItemDecoration
    private lateinit var dividerGridItemDecoration: DividerGridItemDecoration
    private lateinit var dividerStaggeredGridItemDecoration: DividerStaggeredGridItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab_add.setOnClickListener {
            (recyclerView.adapter as SongAdapter).addData(3)
        }

        fab_remove.setOnClickListener {
            val songAdapter = recyclerView.adapter as SongAdapter
            if (songAdapter.itemCount > 3) {
                (songAdapter).removeData(3)
            } else {
                Toast.makeText(this@MainActivity, "增加点再来删除吧", Toast.LENGTH_SHORT).show()
            }
        }

        fab_refresh.setOnClickListener {
            (recyclerView.adapter as SongAdapter).notifyItemChanged(4)
        }

        dividerLinearItemDecoration = DividerLinearItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        dividerGridItemDecoration = DividerGridItemDecoration(this@MainActivity)
        dividerStaggeredGridItemDecoration = DividerStaggeredGridItemDecoration(this@MainActivity)

        initData()
        verticalLinear()
    }

    private fun verticalLinear() {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = SongAdapter(mutableListOf, tvCreateAndBind = tv_create_and_bind)
        recyclerView.removeItemDecoration(dividerLinearItemDecoration)
        recyclerView.removeItemDecoration(dividerGridItemDecoration)
        recyclerView.removeItemDecoration(dividerStaggeredGridItemDecoration)
        dividerLinearItemDecoration.setOrientation(DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerLinearItemDecoration)
        recyclerView.setOnLayoutListener(object : SRecyclerView.onLayoutListener {
            override fun beforeLayout() {
                recyclerView.setAllCache()
            }

            override fun afterLayout() {
                RcyLog.loaAllCache(tv_recycler_field, recyclerView)
            }
        })
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                //RcyLog.logScrapCache(recyclerView);
                //RcyLog.logCache("onScroll时：", recyclerView);
                //RcyLog.logPool(recyclerView);
                RcyLog.loaAllCache(tv_recycler_field, recyclerView)
                scroll.scrollBy(dx, Math.abs(dy))
            }
        })
        showDebugBox()
    }

    private fun goneDebugBox() {
        tv_recycler_field.visibility = View.GONE
        scroll.visibility = View.GONE
    }

    private fun showDebugBox() {
        tv_recycler_field.visibility = View.VISIBLE
        scroll.visibility = View.VISIBLE
    }

    private fun horizontalLinear() {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = SongAdapter(mutableListOf, RecyclerView.HORIZONTAL, tv_create_and_bind)
        recyclerView.removeItemDecoration(dividerLinearItemDecoration)
        recyclerView.removeItemDecoration(dividerGridItemDecoration)
        recyclerView.removeItemDecoration(dividerStaggeredGridItemDecoration)
        dividerLinearItemDecoration.setOrientation(DividerItemDecoration.HORIZONTAL)
        recyclerView.addItemDecoration(dividerLinearItemDecoration)
        goneDebugBox()
    }

    private fun verticalGrid() {
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = SongAdapter(mutableListOf, RecyclerView.VERTICAL, tv_create_and_bind)
        recyclerView.removeItemDecoration(dividerLinearItemDecoration)
        recyclerView.removeItemDecoration(dividerGridItemDecoration)
        recyclerView.removeItemDecoration(dividerStaggeredGridItemDecoration)
        recyclerView.addItemDecoration(dividerGridItemDecoration)
        goneDebugBox()
    }

    private fun horizontalGrid() {
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = SongAdapter(mutableListOf, RecyclerView.HORIZONTAL, tv_create_and_bind)
        recyclerView.removeItemDecoration(dividerLinearItemDecoration)
        recyclerView.removeItemDecoration(dividerGridItemDecoration)
        recyclerView.removeItemDecoration(dividerStaggeredGridItemDecoration)
        recyclerView.addItemDecoration(dividerGridItemDecoration)
        goneDebugBox()
    }

    private fun verticalStaggeredGrid() {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = StaggerSongAdapter(mutableListOf, RecyclerView.VERTICAL)
        recyclerView.removeItemDecoration(dividerLinearItemDecoration)
        recyclerView.removeItemDecoration(dividerGridItemDecoration)
        recyclerView.removeItemDecoration(dividerStaggeredGridItemDecoration)
        recyclerView.addItemDecoration(dividerStaggeredGridItemDecoration)
        goneDebugBox()
    }

    private fun horizontalStaggeredGrid() {
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, GridLayoutManager.HORIZONTAL)
        recyclerView.adapter = SongAdapter(mutableListOf, RecyclerView.HORIZONTAL, tv_create_and_bind)
        recyclerView.removeItemDecoration(dividerLinearItemDecoration)
        recyclerView.removeItemDecoration(dividerGridItemDecoration)
        recyclerView.removeItemDecoration(dividerStaggeredGridItemDecoration)
        recyclerView.addItemDecoration(dividerGridItemDecoration)
        goneDebugBox()
    }

    private fun initData() {
        for (i in 0..51 step 1) {
            mutableListOf.add("Item $i")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        fab_add.visibility = if (item.itemId != R.id.action_VerticalStaggeredGridLayout) View.VISIBLE else View.INVISIBLE
        fab_remove.visibility = if (item.itemId != R.id.action_VerticalStaggeredGridLayout) View.VISIBLE else View.INVISIBLE

        return when (item.itemId) {
            R.id.action_VerticalLinearLayout -> {
                verticalLinear()
                true
            }
            R.id.action_HorizontalLinearLayout -> {
                horizontalLinear()
                true
            }
            R.id.action_VerticalGridLayout -> {
                verticalGrid()
                true
            }
            R.id.action_HorizontalGridLayout -> {
                horizontalGrid()
                true
            }
            R.id.action_VerticalStaggeredGridLayout -> {
                verticalStaggeredGrid()
                true
            }
            R.id.action_HorizontalStaggeredGridLayout -> {
                horizontalStaggeredGrid()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    public static void scrollToBottom(final View scroll, final View innerView) {
//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            public void run() {
//                if (scroll == null || innerView == null) {
//                    return;
//                }
//                int offset = innerView.getMeasuredHeight() - scroll.getHeight();
//                if (offset < 0)
//                    offset = 0;
//
//                scroll.scrollTo(0, offset);
//            }
//        });
//    }

}
