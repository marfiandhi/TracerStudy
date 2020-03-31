package id.divascion.tracerstudy.util

import android.view.ViewGroup

class ViewManipulation {
    fun disableEnableControls(enable: Boolean, viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
//            child.isEnabled = enable
            child.isClickable = enable
            child.isFocusable = enable
            if (child is ViewGroup) {
                disableEnableControls(enable, child)
            }
        }
    }
}