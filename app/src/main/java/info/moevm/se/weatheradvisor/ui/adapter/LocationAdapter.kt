package info.moevm.se.weatheradvisor.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.domain.entities.Location
import info.moevm.se.ext.inflate
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.locationscreen.LocationActivity
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocationAdapter(val activity: LocationActivity) : RecyclerView.Adapter<LocationAdapter.LocationHolder>() {

    var locations: List<Location> = listOf()

    @SuppressLint("CheckResult")
    fun fetchLocations(dataStream: Observable<List<Location>>) {
        dataStream
            .observeOn(Schedulers.io())
            .flatMapIterable { it }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                    list -> locations = list
                    notifyDataSetChanged()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LocationHolder(parent.context.inflate(R.layout.location_city_item, parent))

    override fun getItemCount() = locations.size

    override fun onBindViewHolder(holder: LocationHolder, position: Int) = holder.bind(position)

    inner class LocationHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.findViewById<TextView>(R.id.location_city_item).apply {
                text = locations[position].name
                setOnClickListener {
                    activity.citySelected(locations[position])
                }
            }
        }
    }
}