package com.wslerz.baselibrary.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.*

/**
 *
 * @author by lzz
 * @date 2020/3/12
 * @description  碎片管理类
 */
class FragmentUtil(
    private val containerId: Int,
    private val fragmentManager: FragmentManager,
    private val callback: ((newFragmentEntity: FragmentEntity, oldFragmentEntity: FragmentEntity?) -> Unit)? = null
) {

    private val fragmentEntityList = ArrayList<FragmentEntity>()
    private var currentFragment: FragmentEntity? = null

    fun addFragment(fragmentEntity: FragmentEntity): FragmentUtil {
        fragmentEntityList.add(fragmentEntity)
        fragmentEntity.selectViewList.add(fragmentEntity.selectView)
        fragmentEntity.selectViewList.forEach {
            it?.setTag(it.id, fragmentEntityList.indexOf(fragmentEntity))
            it?.isSelected = false
            it?.setOnClickListener { view ->
                switchFragment(view.context, view.getTag(it.id) as Int)
            }
        }
        return this;
    }

    fun switchFragment(context: Context, whichFragment: Int = 0) {
        val fragmentEntity = fragmentEntityList[whichFragment]
        if (fragmentEntity == currentFragment) {
            return
        }
        callback?.invoke(fragmentEntity, currentFragment)

        val fragment = fragmentEntity.fragment

        fragmentEntityList.forEach {
            it.selectViewList.forEach { view: View? ->
                view?.isSelected = it == fragmentEntity
            }
        }

        val transaction = fragmentManager.beginTransaction()
        if (fragment.isAdded) {
            currentFragment?.let {
                transaction.hide(it.fragment).show(fragment)
            } ?: let {
                transaction.show(fragment)
            }

        } else {
            currentFragment?.let {
                transaction.hide(it.fragment).add(containerId, fragment)
            } ?: let {
                transaction.add(containerId, fragment)
            }
        }
        currentFragment = fragmentEntity
        transaction.commit()
    }
}

class FragmentEntity(
    val fragment: Fragment,
    val selectView: View? = null,
    var selectViewList: MutableList<View?> = mutableListOf()
)