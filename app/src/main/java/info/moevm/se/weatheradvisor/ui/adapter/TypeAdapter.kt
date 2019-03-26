package info.moevm.se.weatheradvisor.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.ext.drawable
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R

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

    private val allClothesNoTypes = clothes.asSequence().flatMap { it.value.asSequence() }.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TypeHolder(parent.context.inflate(R.layout.create_clothe_type_item, parent))

    override fun getItemCount() = clothes.asSequence().flatMap { it.value.asSequence() }.count()

    override fun onBindViewHolder(holder: TypeHolder, position: Int) = holder.bind(position)

    inner class TypeHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.findViewById<ImageView>(R.id.create_clothe_type_item).apply {
                setImageDrawable(view.context.drawable(allClothesNoTypes[position]))
            }
        }
    }
}