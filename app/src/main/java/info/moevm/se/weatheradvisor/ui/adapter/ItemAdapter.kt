package info.moevm.se.weatheradvisor.ui.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.domain.entities.Item
import info.moevm.se.domain.entities.ItemTypes
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.clotheeditorscreen.ClotheEditorActivity
import info.moevm.se.weatheradvisor.ui.adapter.ItemAdapter.ItemHolder
import info.moevm.se.weatheradvisor.wardrobescreen.WardrobeActivity
import io.reactivex.Maybe

class ItemAdapter(val activity: WardrobeActivity, itemsStream: Maybe<List<Item>>) : RecyclerView.Adapter<ItemHolder>() {

    private var items = ArrayList<Item>()
    private var typeFilter: ItemTypes? = null
    private var tempFilter: Int? = null

    init {
        val inition = itemsStream.subscribe {
            it.asSequence().toCollection(items)
            updateItems()
            notifyDataSetChanged()
        }
    }

    fun setTypeFilter(filterByType: ItemTypes?) {
        typeFilter = filterByType
        updateItems()
    }

    fun setTempFilter(filterByTemp: Int?) {
        tempFilter = filterByTemp
        updateItems()
    }

    fun updateItemCheckState(item: Item, checked: Boolean){
        activity.updateItem(item, checked)
    }

    private fun updateItems() {
        var filteredByType = ArrayList<Item>()

        if (typeFilter != null) {
            items.asSequence()
                .filter {
                    it.type == typeFilter
                }
                .toCollection(filteredByType)
        } else  {
            filteredByType = items
        }

        var filteredByTemp = ArrayList<Item>()

        if (tempFilter != null) {
            filteredByType.asSequence()
                .filter {
                    it.tempFrom <= tempFilter!! && it.tempTo >= tempFilter!!
                }
                .toCollection(filteredByTemp)
        } else {
            filteredByTemp = filteredByType
        }
        items = filteredByTemp
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(parent.context.inflate(R.layout.wardrobe_item, parent))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.bind(position)

    inner class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.findViewById<TextView>(R.id.wardrobe_item_title).text = items[position].name
            view.findViewById<ImageView>(R.id.wardrobe_item_edit).setOnClickListener {
                val intent = Intent(activity, ClotheEditorActivity::class.java).apply {
                    putExtra(ITEM_ID_EXTRA, items[position].id)
                }
                activity.startActivity(intent)
                activity.finish()
            }
            view.findViewById<CheckBox>(R.id.wardrobe_item_checkbox).let {
                it.isChecked = items[position].selected
                it.setOnCheckedChangeListener { _, isChecked -> updateItemCheckState(items[position], isChecked) }
            }
        }
    }

    companion object {
        const val ITEM_ID_EXTRA = "ITEM_ID_EXTRA"
    }
}