package info.moevm.se.weatheradvisor.ui.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import info.moevm.se.ext.dpToPx

object ShiftedOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            8, 16, (view.width - 8), (view.height - 8), 10.dpToPx()
        )
    }
}