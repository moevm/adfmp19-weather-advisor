package info.moevm.se.weatheradvisor.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import info.moevm.se.domain.entities.Item
import info.moevm.se.domain.entities.ItemColors.COLOR1
import info.moevm.se.domain.entities.ItemColors.COLOR10
import info.moevm.se.domain.entities.ItemColors.COLOR11
import info.moevm.se.domain.entities.ItemColors.COLOR12
import info.moevm.se.domain.entities.ItemColors.COLOR2
import info.moevm.se.domain.entities.ItemColors.COLOR3
import info.moevm.se.domain.entities.ItemColors.COLOR4
import info.moevm.se.domain.entities.ItemColors.COLOR5
import info.moevm.se.domain.entities.ItemColors.COLOR6
import info.moevm.se.domain.entities.ItemColors.COLOR7
import info.moevm.se.domain.entities.ItemColors.COLOR8
import info.moevm.se.domain.entities.ItemColors.COLOR9
import info.moevm.se.ext.drawable
import info.moevm.se.ext.tint
import info.moevm.se.weatheradvisor.R
import kotlinx.android.synthetic.main.manikin.view.body
import kotlinx.android.synthetic.main.manikin.view.feet
import kotlinx.android.synthetic.main.manikin.view.head
import kotlinx.android.synthetic.main.manikin.view.legs
import kotlinx.android.synthetic.main.manikin.view.next_outfit
import kotlinx.android.synthetic.main.manikin.view.overbody
import kotlinx.android.synthetic.main.manikin.view.prev_outfit

class Manikin @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    val headItems = ArrayList<Item>()
    val overbodyItems = ArrayList<Item>()
    val bodyItems = ArrayList<Item>()
    val legsItems = ArrayList<Item>()
    val feetItems = ArrayList<Item>()

    private var currentHead = 0
    private var currentOverbody = 0
    private var currentBody = 0
    private var currentLegs = 0
    private var currentFeet = 0

    private val colors = listOf(
        R.color.color_item1,
        R.color.color_item2,
        R.color.color_item3,
        R.color.color_item4,
        R.color.color_item5,
        R.color.color_item6,
        R.color.color_item7,
        R.color.color_item8,
        R.color.color_item9,
        R.color.color_item10,
        R.color.color_item11,
        R.color.color_item12
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.manikin, this, true)
        adjustCells()

        prev_outfit.setOnClickListener { prevOutfit() }
        next_outfit.setOnClickListener { nextOutfit() }
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

    fun dress() {
        if (
            headItems.isNotEmpty() and
            (overbodyItems.isNotEmpty() or bodyItems.isNotEmpty()) and
            legsItems.isNotEmpty() and
            feetItems.isNotEmpty()
        ) {
            setHeadItem(headItems[currentHead])
            if (overbodyItems.isNotEmpty()) setOverbodyItem(overbodyItems[currentOverbody])
            if (bodyItems.isNotEmpty()) setBodyItem(bodyItems[currentBody])
            if (overbodyItems.isEmpty()) {
                overbody.setItemTitle(bodyItems[currentBody].name)
                overbody.hideTitle()
            }
            if (bodyItems.isEmpty()) {
                body.setItemTitle(overbodyItems[currentOverbody].name)
                body.hideTitle()
            }
            setLegsItem(legsItems[currentLegs])
            setFeetItem(feetItems[currentFeet])
            adjustCells()
        } else {
            head.apply {
                setItemTitle("")
                setItemImage(null)
            }
            overbody.apply {
                setItemTitle("")
                setItemImage(null)
            }
            body.apply {
                setItemTitle("")
                setItemImage(null)
            }
            legs.apply {
                setItemTitle("")
                setItemImage(null)
            }
            feet.apply {
                setItemTitle("")
                setItemImage(null)
            }
            adjustCells()
            Toast.makeText(
                context,
                "Your wardrobe is too weak. Please add at least one item to each category",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun nextOutfit() {
        when {
            currentHead < headItems.size-1 -> {
                currentHead++
                dress()
            }
            currentOverbody < overbodyItems.size-1 -> {
                currentOverbody++
                dress()
            }
            currentBody < bodyItems.size-1 -> {
                currentBody++
                dress()
            }
            currentLegs < legsItems.size-1 -> {
                currentLegs++
                dress()
            }
            currentFeet < feetItems.size-1 -> {
                currentFeet++
                dress()
            }
            else -> {
                /* DO NOTHING */
            }
        }

    }

    private fun prevOutfit() {
        when {
            currentFeet > 0 -> {
                currentFeet--
                dress()
            }
            currentLegs > 0 -> {
                currentLegs--
                dress()
            }
            currentBody > 0 -> {
                currentBody--
                dress()
            }
            currentOverbody > 0 -> {
                currentOverbody--
                dress()
            }
            currentHead > 0 -> {
                currentHead--
                dress()
            }
            else -> {
                /* DO NOTHING */
            }
        }
    }

    fun clearItems() {
        headItems.clear()
        overbodyItems.clear()
        bodyItems.clear()
        legsItems.clear()
        feetItems.clear()
        currentHead = 0
        currentOverbody = 0
        currentBody = 0
        currentLegs = 0
        currentFeet = 0
    }

    private fun setHeadItem(item: Item) = setSrcAndTitle(head, item)

    private fun setOverbodyItem(item: Item) = setSrcAndTitle(overbody, item)

    private fun setBodyItem(item: Item) = setSrcAndTitle(body, item)

    private fun setLegsItem(item: Item) = setSrcAndTitle(legs, item)

    private fun setFeetItem(item: Item) = setSrcAndTitle(feet, item)

    private fun setSrcAndTitle(clotheItem: ClotheItem, item: Item) = clotheItem.apply {
        val color = when(item.colors){
            COLOR1 -> this@Manikin.colors[0]
            COLOR2 -> this@Manikin.colors[1]
            COLOR3 -> this@Manikin.colors[2]
            COLOR4 -> this@Manikin.colors[3]
            COLOR5 -> this@Manikin.colors[4]
            COLOR6 -> this@Manikin.colors[5]
            COLOR7 -> this@Manikin.colors[6]
            COLOR8 -> this@Manikin.colors[7]
            COLOR9 -> this@Manikin.colors[8]
            COLOR10 -> this@Manikin.colors[9]
            COLOR11 -> this@Manikin.colors[10]
            COLOR12 -> this@Manikin.colors[11]
        }

        setItemImage(context.drawable(item.srcId))
        setItemColor(color)
        setItemTitle(item.name)
    }

    private fun adjustCells() {
        head.let {
            (it.layoutParams as MarginLayoutParams).setMargins(it.getHalfDrawableAndTextSize(), 0, 0, 0)
            it.requestLayout()
            it.invalidate()
        }

        legs.let {
            (it.layoutParams as MarginLayoutParams).setMargins(it.getHalfDrawableAndTextSize(), 0, 0, 0)
            it.requestLayout()
            it.invalidate()
        }

        feet.let {
            (it.layoutParams as MarginLayoutParams).setMargins(it.getHalfDrawableAndTextSize(), 0, 0, 0)
            it.requestLayout()
            it.invalidate()
        }

        body.requestLayout()
        overbody.requestLayout()
    }

}