package com.wslerz.androidproject.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wslerz.androidproject.route.RouterManager
import com.wslerz.baselibrary.util.FragmentEntity
import com.wslerz.baselibrary.util.FragmentUtil
import kotlinx.android.synthetic.main.main_activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragmentUtil: FragmentUtil by lazy {
        val fragmentUtil = FragmentUtil(R.id.content, supportFragmentManager)

        RouterManager.getHomeFragment()?.let {
            fragmentUtil.addFragment(
                FragmentEntity(it, ivHome)
            )
        }

        RouterManager.getCenterFragment()?.let {
            fragmentUtil.addFragment(
                FragmentEntity(it, ivCenter)
            )
        }



        fragmentUtil
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_main)
        fragmentUtil.switchFragment(this, 0)
    }
}
