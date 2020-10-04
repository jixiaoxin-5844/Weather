package com.shijing.weather.view

import android.Manifest
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.bumptech.glide.Glide
import com.hjq.toast.ToastUtils
import com.june.basicslibrary.base.BaseActivity
import com.june.basicslibrary.utils.*
import com.permissionx.guolindev.PermissionX
import com.shijing.weather.R
import com.shijing.weather.data.storage.AppDataKv
import com.shijing.weather.data.storage.WeatherDataKv
import com.shijing.weather.utils.WeatherUtils
import com.shijing.weather.viewModel.MainViewModel
import com.shijing.weatherlibrary.interfaceA.OnImgClickListener
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_title.view.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override var barColor: Int = Color.parseColor("#EFEDED")
    private val viewModel by viewModels<MainViewModel>()

    private var mIsRelease = false
    private var mLocationClient: AMapLocationClient? = null

    //是否拥有定位权限
    private var permission = Common.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    private val requestCode = 20  //SettingActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AppDataKv.getFirstOpenApp()) {
            AppDataKv.setFirstOpenApp(false)
            //注册通知渠道 -> 消息渠道 ...
            createChannel()
            initPermissionX()
        }

        getLocation()

        initView()
        refreshData()

        //获取数据、存储、展示
        startObserve()
    }

    private fun openSelectCity() {
        WeatherUtils.openSelectCity(this, object : OnPickListener {
            override fun onPick(position: Int, data: City?) {
               // viewModel.locationId.value = data!!.code
              //  WeatherDataKv.setLocationId(data.code)
                viewModel.cityName.value = data!!.name
                WeatherDataKv.setCityName(data.name)
            }
            override fun onLocate() {
                L.d("CityPicker", "onLocate")
            }
            override fun onCancel() {
                L.d("CityPicker", "onLocate")
            }
        })
    }

    private fun initView() {

        if (permission) {
            WeatherLayout0.setLocationTextVisible()
            //定位，判断有无定位权限展示不同图标   !!!
            WeatherLayout0.setLocationPic(com.shijing.weatherlibrary.R.mipmap.location)
        } else {
            WeatherLayout0.setLocationPic(com.shijing.weatherlibrary.R.mipmap.no_location)
        }

        //定位按钮
        WeatherLayout0.setOnImgClickListener(object : OnImgClickListener {
            override fun click() {
                if (permission) {
                    UtilToast.show("正在重新定位")
                    getLocation()
                } else {
                    //打开选择城市
                    openSelectCity()
                }
            }
        })

        //下拉刷新
        mainSwipeRefresh.setOnRefreshListener {
            getLocation()
            refreshData()
        }

        Glide.with(this).load(R.mipmap.magic).into(mainTitle.itemTitle_img0)

        mainTitle.itemTitle_img1.setOnClickListener {
            startActivityForResult(Intent(this, SettingActivity::class.java),requestCode)
        }

    }


    // 刷新数据
    private fun refreshData() {
        WeatherLayout0.setDate(TimeUtils.getExactTime2())   //时间
        WeatherLayout0.setLocationText(WeatherDataKv.getLongitudeAndLatitude())
    }

    private fun startObserve() {

        viewModel.cityName.observe(this, {
            viewModel.getLocationInfo(it)  //获取获取城市id
            WeatherLayout0.setCityName(it)   //城市名
        })

        //获取数据
        viewModel.locationId.observe(this, {
            viewModel.getNowData(it)
            viewModel.getDailyData(it)
            viewModel.getHourlyData(it)
            viewModel.getSevenDaily(it)
        })

        viewModel.nowBean.observe(this, {
            WeatherLayout0.setNowBeanData(it)
            main_turnoverTime.text = it.updateTime
        })

        viewModel.dailyBean.observe(this, {
            WeatherLayout0.setDailyBeanData(it)
            WeatherLayout1.setUv(it.uvIndex)
            WeatherLayout3.setDailyBeanData(it)
        })

        viewModel.hourlyBean.observe(this, {
            WeatherLayout0.setRecycler0(it)
            val pop = it[0].pop
            WeatherLayout1.setRainfall(pop)
        })

        viewModel.sevenDailyBean.observe(this, {
            WeatherLayout2.setRecycler0(it)
            mainSwipeRefresh.isRefreshing = false  //下拉刷新完成
        })


    }

    //获取定位
    private fun getLocation() {
        mLocationClient = AMapLocationClient(this)
        //配置定位参数
        val aMapOption = AMapLocationClientOption()
        //低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位(默认高精度)
        // aMapOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置单次定位
        aMapOption.isOnceLocation = true
        mLocationClient!!.setLocationOption(aMapOption)
        mLocationClient!!.setLocationListener {
            if (it != null) {
                if (it.errorCode == 0) { //定位成功
                    viewModel.cityName.value = it.city
                    WeatherDataKv.setCityName(it.city)
                    WeatherDataKv.setLongitudeAndLatitude("${it.longitude}", "${it.latitude}")

                } else {
                    viewModel.cityName.value = WeatherDataKv.getCityName()
                    L.e(
                        "Amap", "location Error, " +
                                "ErrCode:" + it.errorCode + ", " +
                                "errInfo:" + it.errorInfo
                    )
                }
            }
        }
        mLocationClient!!.startLocation()
    }

    //请求权限
    private fun initPermissionX() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,

                )
            .onExplainRequestReason { scope, deniedList -> //拒绝
                val filter = deniedList.filter {
                    it == Manifest.permission.ACCESS_FINE_LOCATION
                }
                scope.showRequestReasonDialog(filter, "授予定位权限使用更加方便哦", "授予", "就不")
            }
            .onForwardToSettings { scope, deniedList -> //拒绝,不再提醒
                val filter = deniedList.filter {
                    it == Manifest.permission.ACCESS_FINE_LOCATION
                }
                scope.showForwardToSettingsDialog(filter, "需要去设置手动开启权限", "明白了", "就不")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    getLocation()
                    permission = true
                } else {
                    ToastUtils.show("拒绝了权限")
                }
            }
    }

    /**
     * 创建通知渠道的这部分代码,可以写在程序的任何位置，只需要保证在通知弹出之前调用就可以了。
     * 并且创建通知渠道的代码只在第一次执行的时候才会创建，
     * 以后每次执行创建代码系统会检测到该通知渠道已经存在了，
     * 因此不会重复创建，也并不会影响任何效率
     * */
    private fun createChannel() {
        val sendMessageId = getString(R.string.channel_sendMessageId)
        val sendMessage = getString(R.string.channel_sendMessage)
        val sendDescriptionMessage = getString(R.string.description_sendMessage)
        //API大于等于26才需要创建渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT  //通知重要性等级
            // val importanceLow = NotificationManager.IMPORTANCE_LOW  //通知重要性等级
            NotificationUtils.createChannel(
                this,
                sendMessageId,
                sendMessage,
                importance,
                sendDescriptionMessage
            )
        }
    }

    //****************************

    private fun release() {
        if (mIsRelease) {
            return
        }
        if (isFinishing) {
            //回收动作
            if (mLocationClient != null) {
                mLocationClient!!.stopLocation() //取消，关闭定位
                mLocationClient!!.onDestroy()
            }
            mIsRelease = true
        }
    }

    override fun onPause() {
        super.onPause()
        release()
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == this.requestCode && resultCode == 21){
            //设置页面返回 -> 手动选择城市
            val cityName = data?.getStringExtra("forSetting")
            viewModel.cityName.value = cityName
        }else if(requestCode == this.requestCode && resultCode == 22){
            //授予权限后，刷新一下
            getLocation()
        }
    }
}