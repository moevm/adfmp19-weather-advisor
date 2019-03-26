package info.moevm.se.weatheradvisor.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R

class LocationAdapter(var locations: List<String>) : RecyclerView.Adapter<LocationAdapter.LocationHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LocationHolder(parent.context.inflate(R.layout.location_city_item, parent))

    override fun getItemCount() = locations.size

    override fun onBindViewHolder(holder: LocationHolder, position: Int) = holder.bind(position)

    inner class LocationHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.findViewById<TextView>(R.id.location_city_item).apply {
                text = locations[position]
                setOnClickListener {

                }
            }
        }
    }
}