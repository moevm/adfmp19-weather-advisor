package info.moevm.se.weatheradvisor.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import info.moevm.se.domain.entities.Item
import info.moevm.se.ext.drawable
import info.moevm.se.weatheradvisor.R
import kotlinx.android.synthetic.main.manikin.view.body
import kotlinx.android.synthetic.main.manikin.view.feet
import kotlinx.android.synthetic.main.manikin.view.head
import kotlinx.android.synthetic.main.manikin.view.legs
import kotlinx.android.synthetic.main.manikin.view.overbody

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

    fun setHeadAction(action: () -> Unit) {
        head.setOnClickListener { action() }
    }

    fun setOverbodyAction(action: () -> Unit) {
        overbody.setOnClickListener { action() }
    }

    fun setBodyAction(action: () -> Unit) {
        body.setOnClickListener { action() }
    }

    fun setLegsAction(action: () -> Unit) {
        legs.setOnClickListener { action() }
    }

    fun setFeetAction(action: () -> Unit) {
        feet.setOnClickListener { action() }
    }

    fun setHeadItem(item: Item) = setSrcAndTitle(head, item)

    fun setOverbodyItem(item: Item) = setSrcAndTitle(overbody, item)

    fun setBodyItem(item: Item) = setSrcAndTitle(body, item)

    fun setLegsItem(item: Item) = setSrcAndTitle(legs, item)

    fun setFeetItem(item: Item) = setSrcAndTitle(feet, item)

    private fun setSrcAndTitle(clotheItem: ClotheItem, item: Item) = clotheItem.apply {
        setItemImage(context.drawable(item.srcId))
        setItemTitle(item.name)
    }
}