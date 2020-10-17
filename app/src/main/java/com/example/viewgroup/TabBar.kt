package com.example.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * @Description
 * 代码高手
 * val 对象不能变，但是对象的属性可以变
 */
class TabBar:LinearLayout {
    //保存所有item的模型数据
    var models :Array<ItemModel> = emptyArray()
        set(value) {
            field = value
            updataUI()
        }
    private var number =0
    private var current=0 //记录当前选中的栏目的索引
    private val items = mutableListOf<BarItem>()

    constructor(context: Context):super(context){
        initView()
    }
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){
        parseAttr(attrs)
        initView()
    }
    private fun parseAttr(attrs: AttributeSet){
       val typedArray= context.obtainStyledAttributes(attrs,R.styleable.TabBar)
        number=typedArray.getInteger(R.styleable.TabBar_number,0)
        typedArray.recycle()
    }

    private fun initView(){
        orientation= HORIZONTAL
        gravity=Gravity.CENTER_VERTICAL
     for(i in 0 until number){
         //创建BarItem
         BarItem(context).also {
             val lp=LinearLayout.LayoutParams(
                 LayoutParams.WRAP_CONTENT,
                 LayoutParams.WRAP_CONTENT
             ).apply {
                 weight = 1f
             }
             addView(it,lp)
             items.add(it)

             //监听点击的事件
             it.selectCallback = {index->
                  //还原之前选中的栏目的状态
                 items[current].mIsSelected=false
                 //保存索引
                 current = index
             }
         }
     }
    }
    private fun updataUI(){
        for((i,item) in items.withIndex()){
            //获取item对应的model
            val model= models[i]

            item.index=i
           item.normalIconID = model.normalIcon
            item.selectIconID=model.selectedIcon
            item.name= model.title
            item.highlightTextcolor=model.highlightColor
            item.mIsSelected=model.selected
        }
    }
}
