package com.wslerz.androidproject

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.wslerz.androidproject.route.RouterManager
import com.wslerz.baselibrary.util.FragmentEntity
import com.wslerz.baselibrary.util.FragmentUtil
import kotlinx.android.synthetic.main.main_activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragmentUtil: FragmentUtil by lazy {
        val fragmentUtil = FragmentUtil(R.id.content)
        RouterManager.getCenterFragment()?.let {
            fragmentUtil.addFragment(
                fragmentUtil.getFragmentEntity(it, text = ivCenter)
            )
        }

        RouterManager.getHomeFragment()?.let {
            fragmentUtil.addFragment(
                fragmentUtil.getFragmentEntity(it, text = ivHome)
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
