package com.example.viewgroup

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * @Description
 * 代码高手
 */
/**
 1.实现一个标签的功能（组合）
     显示图片和文字
     内容由外部设置
     接收点击事件  切换状态
知识点  ：
      当继承于系统自带的Layout时 已经规定好了规则
      只关心如何添加子集的子控件进去
     在哪里添加？
          这个控件被构建（调用构造函数时）
 */
class BarItem: LinearLayout{
    var selectCallback:((Int)->Unit)?=null  //一个高阶函数
    var index= -1
     var normalIconID:Int = 0
      var selectIconID:Int = 0 //选中图片
      var name:String ="null"
         set(value) {
             field=value
             titleTextView?.text=value
         }
    var highlightTextcolor = 0
    var mIsSelected:Boolean = false //记录是否选中
        set(value) {
            field = value
            updateUI()
        }
    private  var iconImageView:ImageView ?= null
    private  var titleTextView:TextView ?= null

    constructor(context: Context) :super(context){
        paresAttr(null)
        initView()
    }
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){
        //解析属性
        paresAttr(attrs)
        initView()
    }

    private fun  paresAttr(attrs:AttributeSet?){
        //将BarItem定义的属性从attrs里面解析出来
       val typedArray=  context.obtainStyledAttributes(attrs,R.styleable.BarItem)
        //解析每一个属性
        normalIconID= typedArray.getResourceId(R.styleable.BarItem_normalIcon,R.drawable.home)
        selectIconID= typedArray.getResourceId(R.styleable.BarItem_selestIcon,R.drawable.home_selected)
        name= typedArray.getString(R.styleable.BarItem_title).toString()
        highlightTextcolor=typedArray.getColor(R.styleable.BarItem_highlightTextColor,Color.RED)
        mIsSelected = typedArray.getBoolean(R.styleable.BarItem_selected,false)
        //回收
        typedArray.recycle()
    }
    //添加子视图
    private fun initView(){
        //配置布局方向
        orientation = VERTICAL
        gravity=Gravity.CENTER
        //添加图片
      iconImageView=  ImageView(context).apply {
            //布局参数 （尺寸）
            val lp = LinearLayout.LayoutParams(
                dp2px(32),dp2px(32))
            //设置图片
            setImageResource(normalIconID)
            //将控件添加到容器中
            addView(this,lp)
        }
        //添加文字
       titleTextView= TextView(context).apply {
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            addView(this,lp)
        }
        updateUI()
    }

    //将dp值转化为像素
    private fun dp2px(dp:Int):Int=
        ( dp*context.resources.displayMetrics.density).toInt()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action==MotionEvent.ACTION_DOWN){
            if (!mIsSelected){
            mIsSelected = true
            //回调被点击的事件
                selectCallback?.let {
                    it(index)
                }
            }
        }
        return true
    }
    private fun updateUI(){
        if(mIsSelected){
            iconImageView?.setImageResource(selectIconID)
            titleTextView?.setTextColor(highlightTextcolor)
        }else{
            iconImageView?.setImageResource(normalIconID)
            titleTextView?.setTextColor(Color.BLACK)
        }
    }
}