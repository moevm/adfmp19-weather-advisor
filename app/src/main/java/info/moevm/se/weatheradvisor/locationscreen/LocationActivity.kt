package info.moevm.se.weatheradvisor.locationscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.ui.adapter.LocationAdapter
import info.moevm.se.weatheradvisor.ui.util.ShiftedOutlineProvider
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.toolbar.*

class LocationActivity : AppCompatActivity() {


    private val adapter: LocationAdapter by lazy { LocationAdapter(listOf("AAAAA", "BBBB", "CCCCCC", "DDDDD")) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        toolbar_title.text = "Country"
        toolbar_nav_button.apply {
            setImageResource(R.drawable.vd_navigate_back)
            setOnClickListener {
                onBackPressed()
            }
        }
        setActionBar(toolbar_view)
        location_search_cover.outlineProvider = ShiftedOutlineProvider
        location_list.apply {
            adapter = this@LocationActivity.adapter
            layoutManager = LinearLayoutManager(this@LocationActivity)
        }
    }
}
