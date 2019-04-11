package info.moevm.se.weatheradvisor.ui.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.data.local.entities.ItemDB
import info.moevm.se.data.local.entities.map
import info.moevm.se.domain.entities.Item
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.clotheeditorscreen.ClotheEditorActivity
import info.moevm.se.weatheradvisor.ui.adapter.ItemAdapter.ItemHolder
import info.moevm.se.weatheradvisor.wardrobescreen.WardrobeActivity
import io.reactivex.Maybe

class ItemAdapter(val activity: WardrobeActivity, itemsStream: Maybe<List<Item>>) : RecyclerView.Adapter<ItemHolder>() {

    private var items = ArrayList<Item>()

    init {
        val inition = itemsStream.subscribe {
            it.asSequence().toCollection(items)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(parent.context.inflate(R.layout.wardrobe_item, parent))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.bind(position)

    inner class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.findViewById<TextView>(R.id.wardrobe_item_title).text = items[position].name
            view.findViewById<ImageView>(R.id.wardrobe_item_edit).setOnClickListener {
                activity.startActivity(Intent(activity, ClotheEditorActivity::class.java))
            }
        }
    }
}