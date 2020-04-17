package com.wslerz.baselibrary.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.*

/**
 *
 * @author by lzz
 * @date 2020/3/12
 * @description  碎片管理类
 */
class FragmentUtil(private val containerId:Int) {
    private val fragmentEntityList = ArrayList<FragmentEntity>()
    private var currentFragment: androidx.fragment.app.Fragment? = null

    fun addFragment(fragmentEntity: FragmentEntity): FragmentUtil {
        fragmentEntityList.add(fragmentEntity)
        fragmentEntity.image?.tag = fragmentEntityList.indexOf(fragmentEntity)
        fragmentEntity.image?.setImageResource(fragmentEntity.iconDisSelect ?: 0)
        fragmentEntity.image?.setOnClickListener {
            it?.let {
                switchFragment(it.context, it.tag as Int)
            }
        }

        fragmentEntity.text?.tag = fragmentEntityList.indexOf(fragmentEntity)
        fragmentEntity.text?.setOnClickListener {
            it?.let {
                switchFragment(it.context, it.tag as Int)
            }
        }
        return this;
    }

    fun switchFragment(context: Context, whichFragment: Int = 0) {
        val fragmentEntity = fragmentEntityList[whichFragment]
        val fragment = fragmentEntity.fragment
        fragmentEntityList.forEach {
            if (it == fragmentEntity) {
                it.image?.setImageResource(it.iconSelect ?: 0)
            } else {
                it.image?.setImageResource(it.iconDisSelect ?: 0)
            }
        }

        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        if (fragment.isAdded) {
            currentFragment?.let {
                transaction.hide(it).show(fragment)
            }
            if (currentFragment == null) {
                transaction.show(fragment)
            }
        } else {
            currentFragment?.let {
                transaction.hide(it).add(containerId, fragment)
            }
            if (currentFragment == null) {
                transaction.add(containerId, fragment)
            }
        }
        currentFragment = fragment
        transaction.commit()
    }

    fun getFragmentEntity(
        fragment: Fragment,
        image: ImageView? = null,
        text: TextView? = null,
        iconSelect: Int? = 0,
        iconDisSelect: Int? = 0
    ) = FragmentEntity(fragment, image, text, iconSelect, iconDisSelect)
}

class FragmentEntity(
    val fragment: Fragment,
    val image: ImageView?,
    val text: TextView?,
    val iconSelect: Int?,
    val iconDisSelect: Int?
)