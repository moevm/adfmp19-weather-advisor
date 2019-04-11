package info.moevm.se.weatheradvisor.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.domain.entities.ItemTypes
import info.moevm.se.ext.colorList
import info.moevm.se.ext.drawable
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.ALL

class TypeAdapter : RecyclerView.Adapter<TypeAdapter.TypeHolder>() {

    private val clothes = listOf(
        TypeItem("head", R.drawable.vd_glasses),
        TypeItem("body", R.drawable.vd_shirt1),
        TypeItem("legs", R.drawable.vd_shorts1),
        TypeItem("feet", R.drawable.vd_shoe),
        TypeItem("feet", R.drawable.vd_shoe1),
        TypeItem("feet", R.drawable.vd_shoe2),
        TypeItem("feet", R.drawable.vd_shoe3),
        TypeItem("feet", R.drawable.vd_shoe4),
        TypeItem("feet", R.drawable.vd_shoe5),
        TypeItem("feet", R.drawable.vd_shoe6),
        TypeItem("feet", R.drawable.vd_shoe7)
    )

    private var currentColor = R.color.color_item1

    private var clothesToShow = clothes

    private var filter = ALL

    private lateinit var selectedItem: TypeItem

    init {
        if (clothesToShow.isNotEmpty()) {
            selectedItem = clothesToShow[0]
        }
    }

    fun getSelected() = when (selectedItem.type) {
        "head" -> ItemTypes.HEAD
        "overbody" -> ItemTypes.OVERBODY
        "body" -> ItemTypes.BODY
        "legs" -> ItemTypes.LEGS
        "feet" -> ItemTypes.FEET
        else -> throw IllegalArgumentException("Unknown type")
    }

    fun getSrc() = selectedItem.src

    fun filter(type: FilterType) {
        filter = type
        clothesToShow = if (filter == ALL) clothes else clothes.filter { it.type == filter.value }
        if (clothesToShow.isNotEmpty()) {
            selectedItem = clothesToShow[0]
        }
        clothesToShow.forEach {
            it.selected = false
        }
        selectedItem.selected = true
        notifyDataSetChanged()
    }

    fun itemColorChanged(color: Int) {
        currentColor = color
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TypeHolder(parent.context.inflate(R.layout.create_clothe_type_item, parent))

    override fun getItemCount() = clothesToShow.size

    override fun onBindViewHolder(holder: TypeHolder, position: Int) = holder.bind(position)

    inner class TypeHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.findViewById<ImageView>(R.id.create_clothe_type_item).apply {
                setImageDrawable(view.context.drawable(clothesToShow[position].src))
                imageTintList = context.colorList(currentColor)
            }
            view.setOnClickListener {
                clothesToShow.forEach {
                    it.selected = false
                }
                clothesToShow[position].let {
                    it.selected = true
                    selectedItem = it
                }
                notifyDataSetChanged()
            }
            view.isSelected = clothesToShow[position].selected
        }
    }
}

enum class FilterType(val value: String) {
    ALL("all"),
    HEAD("head"),
    OVERBODY("overbody"),
    BODY("body"),
    LEGS("legs"),
    FEET("feet")
}

data class TypeItem(
    val type: String,
    val src: Int,
    var selected: Boolean = false
)