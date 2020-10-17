package com.example.viewgroup

import android.graphics.Color
import android.icu.text.CaseMap

/**
 * @Description
 * 代码高手
 */

//数据类
data class ItemModel (var normalIcon:Int,
                      var selectedIcon:Int,
                      var title: String,
                      var highlightColor:Int=Color.RED,
                      var selected:Boolean
                    )