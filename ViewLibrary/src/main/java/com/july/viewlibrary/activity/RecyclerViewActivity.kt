package com.july.viewlibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.july.viewlibrary.R
import com.july.viewlibrary.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    private val listOf1 =
        mutableListOf("打开相册", "111111111111111111111", "2222222222",
            "333333333333333333333333333", "444444", "55555555555555", "6666", "777777", "8888888888888", "999")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initRecyclerView()

    }

    //流式布局
    private fun initRecyclerView() {
        val recyclerAdapter = RecyclerAdapter(R.layout.search_item, listOf1)
        //  recyclerAdapter.addChildClickViewIds(R.id.searchRootView)
        recyclerView0.run {
            layoutManager = StaggeredGridLayoutManager(3, 1)
         //   layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
            adapter = recyclerAdapter

        }

    }
}