package info.moevm.se.weatheradvisor.ui.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

object CircleOutline : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.apply {
            setOval(0, 0, view.width, view.height + 4)
        }
    }
}