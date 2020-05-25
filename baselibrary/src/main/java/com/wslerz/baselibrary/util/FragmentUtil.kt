package com.wslerz.baselibrary.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.*

/**
 * @author by lzz
 * @date 2020/3/12
 * @description  碎片管理类
 * @param containerId  放置碎片{@link Fragment}的布局id
 * @param fragmentManager  FragmentManager  注意要区分当前界面是Activity还是Fragment
 * @param callback 切换碎片的监听
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
                switchFragment(view.getTag(it.id) as Int)
            }
        }
        return this;
    }

    fun switchFragment(whichFragment: Int = 0) {
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

/**
 * @param fragment  碎片
 * @param selectView  碎片对应的要变化状态的布局
 * @param selectViewList 碎片对应的要变化状态的布局列表
 */
class FragmentEntity(
    val fragment: Fragment,
    val selectView: View? = null,
    var selectViewList: MutableList<View?> = mutableListOf()
)