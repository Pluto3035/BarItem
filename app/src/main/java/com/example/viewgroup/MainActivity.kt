package com.example.viewgroup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /**
     自定义ViewGroup
     1.为什么要自定义ViewGroup
         a.将多个子控件组合起来  形成一个完整体
         b.系统已有的布局方式满足不了需求，所以需要自定义ViewGroup
           FrameLayout
           LinearLayout
           RelativeLayout
           ConstraintLayout
        以上都是继承于ViewGroup的
     2.如何自定义ViewGroup :View
         a.在已有的容器上添加自己的功能(最简单)
           MyViewGroup :ConstraintLayout  ->ViewGroup
         b.如果自己想定义规则（复杂 灵活）
           MyViewGroup:ViewGroup

     查看源代码  双击shift -file 搜索 ViewGroup.java
     ViewGroup: MeasureSpec  int 类型
     由两部分组成  1.mode  2.size

          用于计算（测量）当前这个控件的具体尺寸
         MeasureSpecMode
        EXACTLY  01 精确的  200dp
        AT_MOST   10 最多不能超过某个值  match_parent -> 填充父容器的剩余控件   wrap_content
        UNSPECIFIED 00 无限，不做任何限制  滚动条ScrollView  RecycleView （很少使用）

         getMode  获取高两位 ->模式
         getSize 获取真正的尺寸  ->后30位 具体的尺寸
         makeMeasureSpec  将mode和size组合成measureSpec对象
          size + mode
         00000....1100100  + 01 00000...000 = 0100...1100100

     wrap_conten 控件有多少内容就是多少 （宽 高）
     为什么我们使用wrap_content时不是我们要的样子

     ViewGroup:
        spec:父容器本身给的测量尺寸
        padding:内间距
       getChildMeasureSpec()
     chileDimension 子控件的尺寸
     ViewRootImpl:measureChild()  ->getChildMeasureSpec()
     fmode = AT_MOST match_parent
     fsize = screen_width


          1.容器如何确定自己的尺寸
              父容器 -- 我给你的范围（限制） -> 所有子控件 确定 ->父容器的尺寸
                            父容器自己的  measureSpec
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //配置显示的属性
        mTabBar.models = arrayOf(
            ItemModel(R.drawable.home,R.drawable.home_selected,
                "主页",selected = true),

            ItemModel(R.drawable.video,R.drawable.video_selected,
                "视频",selected = false),

            ItemModel(R.drawable.circle,R.drawable.circle_selected,
                "圈子",selected = false),

            ItemModel(R.drawable.me,R.drawable.me_selected,
                "我",selected = false)
        )
    }
}
