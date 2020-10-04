package com.july.viewlibrary.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.july.viewlibrary.R
import com.july.viewlibrary.customView.interfaceA.OnLabelViewClickListener
import com.june.basicslibrary.model.mmkv.KvData
import com.june.basicslibrary.utils.UtilToast
import kotlinx.android.synthetic.main.activity_search_view.*

class SearchViewActivity : AppCompatActivity() {

    private val TAG = "SearchView"

    private var a = 0
    private lateinit var listData: MutableList<String>  //LabelData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view)
        initListData()
        addsSearchItem()
    }

    private fun initListData() {

        val list =  mutableListOf( "whenYouLockMe",
            "第一个",
            "第二个个",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "第三个个个啊啊啊啊啊啊啊啊啊")

        //初始化数据
        searchInitData.setOnClickListener {
            KvData.setLabelData(list)
        }

        //显示当前数据
        searchShowList.setOnClickListener {
            val labelData = KvData.getLabelData<String>()
            Log.d(TAG,labelData.toString())
        }

        //保存输入的文本
        searchSaveEdit.setOnClickListener {
            val labelData = KvData.getLabelData<String>()
            val text = searchEdit.text.toString()
            if (labelData != null && text != "") {
                labelData.add(labelData.size,text)
                KvData.setLabelData(labelData)
            }
        }

        searchClear.setOnClickListener {
            searchView.clearAllData()  //调用方法清除数据
        }
    }

    private fun addsSearchItem() {

        val labelData = KvData.getLabelData<String>()

        if(labelData!= null){
            searchView.setLabelItemBackGround("#d76801")
            searchView.setSearchStringList(labelData)
        }

    /*    searchView.setOnItemClickListener(object : OnLabelViewClickListener {

            override fun onItemClickListener(view: View, position: Int, text: String) {
                UtilToast.show("点击了$position,text:$text")
            }
        })*/
    }

}