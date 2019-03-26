package info.moevm.se.ext

import android.content.res.Resources

fun Int.dpToPx() =
    this.toFloat() * (Resources.getSystem().displayMetrics.densityDpi / 160f)