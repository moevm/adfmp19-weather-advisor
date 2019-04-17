package info.moevm.se.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat

fun Context.inflate(@LayoutRes layout: Int, viewGroup: ViewGroup, attachedToParent: Boolean = false): View =
    LayoutInflater.from(this).inflate(layout, viewGroup, attachedToParent)

fun Context.drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

fun Context.color(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.colorList(@ColorRes id: Int) = ContextCompat.getColorStateList(this, id)