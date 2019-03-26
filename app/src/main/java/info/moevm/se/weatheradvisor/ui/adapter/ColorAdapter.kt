package info.moevm.se.weatheradvisor.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.ext.colorList
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.ui.util.CircleOutline

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ColorHolder>() {

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
                    it.isSelected = true
                }
            }
        }
    }
}