package info.moevm.se.weatheradvisor.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.domain.entities.ItemColors
import info.moevm.se.ext.colorList
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.clotheeditorscreen.ClotheEditorActivity
import info.moevm.se.weatheradvisor.ui.util.CircleOutline
import java.lang.IllegalArgumentException

class ColorAdapter(val activity: ClotheEditorActivity) : RecyclerView.Adapter<ColorAdapter.ColorHolder>() {

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

    private var selectedColor = 0

    fun getSelected(): ItemColors = when (selectedColor) {
        0 -> ItemColors.COLOR1
        1 -> ItemColors.COLOR2
        2 -> ItemColors.COLOR3
        3 -> ItemColors.COLOR4
        4 -> ItemColors.COLOR5
        5 -> ItemColors.COLOR6
        6 -> ItemColors.COLOR7
        7 -> ItemColors.COLOR8
        8 -> ItemColors.COLOR9
        9 -> ItemColors.COLOR10
        10 -> ItemColors.COLOR11
        11 -> ItemColors.COLOR12
        else -> throw IllegalArgumentException("Unknown color")
    }

    fun colorChanged(newColor: Int) = activity.onColorChanged(newColor)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ColorHolder(parent.context.inflate(R.layout.create_clothe_color_item, parent))

    override fun getItemCount() = colors.size

    override fun onBindViewHolder(holder: ColorHolder, position: Int) = holder.bind(position)

    inner class ColorHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.findViewById<View>(R.id.create_clothe_color_item).apply {
                backgroundTintList = view.context.colorList(colors[position])
                outlineProvider = CircleOutline
                setOnClickListener {
                    selectedColor = position
                    colorChanged(colors[position])
                    notifyDataSetChanged()
                }
                view.isSelected = position == selectedColor
            }
        }
    }
}