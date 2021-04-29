package com.petr.example.mapapp.ui.common

import android.view.View
import android.view.animation.TranslateAnimation

fun View.slideUp(){
    visibility = View.VISIBLE
    val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
    animate.duration = 500
    animate.fillAfter = true
    this.startAnimation(animate)
}

fun View.slideDown() {
    visibility = View.VISIBLE
    val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
    animate.duration = 500
    animate.fillAfter = true
    this.startAnimation(animate)
}

fun View.hideKeyboard(){

}