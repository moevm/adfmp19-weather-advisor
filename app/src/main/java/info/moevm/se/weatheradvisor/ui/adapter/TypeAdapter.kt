package info.moevm.se.weatheradvisor.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.domain.entities.ItemTypes
import info.moevm.se.ext.drawable
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.ALL
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.BODY
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.FEET
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.HEAD
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.JACKETS
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.LEGS

class TypeAdapter : RecyclerView.Adapter<TypeAdapter.TypeHolder>() {

    private val clothes = mapOf(
        "head" to listOf(
            R.drawable.vd_glasses
        ),
        "overbody" to listOf(

        ),
        "body" to listOf(
            R.drawable.vd_shirt1
        ),
        "legs" to listOf(
            R.drawable.vd_shorts1
        ),
        "feet" to listOf(
            R.drawable.vd_shoe,
            R.drawable.vd_shoe1,
            R.drawable.vd_shoe2,
            R.drawable.vd_shoe3,
            R.drawable.vd_shoe4,
            R.drawable.vd_shoe5,
            R.drawable.vd_shoe6,
            R.drawable.vd_shoe7
        )
    )

    private var filter = "all"

    private val allClothesNoTypes = clothes.asSequence().flatMap { it.value.asSequence() }.toList()

    private var selectedType = 0

    fun getSelected() = when (selectedType) {
        0 -> ItemTypes.HEAD
        1 -> ItemTypes.OVERBODY
        2 -> ItemTypes.BODY
        3 -> ItemTypes.LEGS
        4 -> ItemTypes.FEET
        else -> throw IllegalArgumentException("Unknown type")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TypeHolder(parent.context.inflate(R.layout.create_clothe_type_item, parent))

    override fun getItemCount() = if (filter == "all") {
        clothes.asSequence().flatMap { it.value.asSequence() }.count()
    } else {
        clothes.getValue(filter).size
    }

    override fun onBindViewHolder(holder: TypeHolder, position: Int) = holder.bind(position)

    fun filter(type: FilterType) {
        filter = when (type) {
            ALL -> "all"
            HEAD -> "head"
            JACKETS -> "overbody"
            BODY -> "body"
            LEGS -> "legs"
            FEET -> "feet"
        }
        notifyDataSetChanged()
    }

    inner class TypeHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.findViewById<ImageView>(R.id.create_clothe_type_item).apply {
                if (filter == "all") {
                    setImageDrawable(view.context.drawable(allClothesNoTypes[position]))
                } else {
                    setImageDrawable(view.context.drawable(clothes.getValue(filter)[position]))
                }
            }
        }
    }
}

enum class FilterType {
    ALL, HEAD, JACKETS, BODY, LEGS, FEET
}