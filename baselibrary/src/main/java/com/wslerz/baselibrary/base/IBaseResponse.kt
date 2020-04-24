package com.wslerz.baselibrary.base

/**
 * @author by lzz
 * @date 2019/12/3
 * @description 接口返回的数据的基类   为了兼容多个后台返回不同的数据格式
 */
interface IBaseResponse<T> {
    /**
     * 根据状态码判断否成功
     */
    fun isSuc(): Boolean

    /**
     * 获取提示信息
     */
    fun obtainMessage(): String

    /**
     * 获取数据
     */
    fun obtainData(): T?

    /**
     * 获取当前返回的状态码
     */
    fun obtainCode(): String


}