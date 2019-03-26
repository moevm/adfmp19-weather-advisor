package info.moevm.se.weatheradvisor.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import info.moevm.se.weatheradvisor.R
import kotlinx.android.synthetic.main.manikin.view.*

class Manikin @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.manikin, this, true)

        head.let {
            (it.layoutParams as MarginLayoutParams).setMargins(it.getHalfDrawableAndTextSize(), 0, 0, 0)
            it.requestLayout()
        }

        legs.let {
            (it.layoutParams as MarginLayoutParams).setMargins(it.getHalfDrawableAndTextSize(), 0, 0, 0)
            it.requestLayout()
        }

        feet.let {
            (it.layoutParams as MarginLayoutParams).setMargins(it.getHalfDrawableAndTextSize(), 0, 0, 0)
            it.requestLayout()
        }
    }


    fun setHeadAction() {

    }

    fun setOverbodyAction() {

    }

    fun setBodyAction() {

    }

    fun setLegsAction() {

    }

    fun setFeetAction() {
        
    }
}