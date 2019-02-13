package com.leeeyou.source.code.analysis.recyclerview

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val mutableListOf: MutableList<String> = mutableListOf()
    private lateinit var dividerItemDecoration: DividerItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        dividerItemDecoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)

        initData()
        linearLayoutVertical()
    }

    private fun linearLayoutVertical() {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = SongAdapter(mutableListOf)
        recyclerView.removeItemDecoration(dividerItemDecoration)
        dividerItemDecoration.setOrientation(DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun linearLayoutHorizontal() {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = SongAdapter(mutableListOf, RecyclerView.HORIZONTAL)
        recyclerView.removeItemDecoration(dividerItemDecoration)
        dividerItemDecoration.setOrientation(DividerItemDecoration.HORIZONTAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun verticalGridLayout() {
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerGridItemDecoration(this@MainActivity))
    }

    private fun horizontalGridLayout() {
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(DividerGridItemDecoration(this@MainActivity))
    }

    private fun verticalStaggeredGridLayout() {
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, GridLayoutManager.VERTICAL)
        recyclerView.adapter = SongAdapter(mutableListOf, RecyclerView.VERTICAL)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun horizontalStaggeredGridLayout() {
        recyclerView.layoutManager = StaggeredGridLayoutManager(4, GridLayoutManager.HORIZONTAL)
        recyclerView.adapter = SongAdapter(mutableListOf, RecyclerView.HORIZONTAL)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun initData() {
        for (i in 0..100 step 2) {
            mutableListOf.add("Line $i")
        }

        mutableListOf.add(4, "Line Line Line Line Line Line Line Line Line 8")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_VerticalLinearLayout -> {
                linearLayoutVertical()
                true
            }
            R.id.action_HorizontalLinearLayout -> {
                linearLayoutHorizontal()
                true
            }
            R.id.action_VerticalGridLayout -> {
                verticalGridLayout()
                true
            }
            R.id.action_HorizontalGridLayout -> {
                horizontalGridLayout()
                true
            }
            R.id.action_VerticalStaggeredGridLayout -> {
                verticalStaggeredGridLayout()
                true
            }
            R.id.action_HorizontalStaggeredGridLayout -> {
                horizontalStaggeredGridLayout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
