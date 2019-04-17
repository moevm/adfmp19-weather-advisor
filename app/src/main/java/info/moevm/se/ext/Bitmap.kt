package info.moevm.se.ext

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.bitmap(): Bitmap {
    return when {
        this is BitmapDrawable -> this.bitmap
        this is VectorDrawable ->
            Bitmap.createBitmap(
                this.intrinsicWidth,
                this.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            ).also {
                Canvas(it).let {
                    this.run {
                        setBounds(0, 0, it.width, it.height)
                        draw(it)
                    }
                }
            }
        else -> throw IllegalArgumentException("Unsupported drawable type")
    }
}

fun Drawable.tint(color: Int) = DrawableCompat.setTint(this, color)