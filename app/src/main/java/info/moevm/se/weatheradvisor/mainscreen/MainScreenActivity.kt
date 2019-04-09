package info.moevm.se.weatheradvisor.mainscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import info.moevm.se.data.repositories.AccuWeatherRepository
import info.moevm.se.data.repositories.ItemRepository
import info.moevm.se.domain.entities.ItemTypes
import info.moevm.se.weatheradvisor.App
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.locationscreen.LocationActivity
import info.moevm.se.weatheradvisor.wardrobescreen.WardrobeActivity
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.activity_main_screen.manikin
import kotlinx.android.synthetic.main.activity_main_screen.swipe_to_refresh
import kotlinx.android.synthetic.main.activity_main_screen.weather_date
import kotlinx.android.synthetic.main.activity_main_screen.weather_state
import kotlinx.android.synthetic.main.activity_main_screen.weather_temp
import kotlinx.android.synthetic.main.activity_main_screen.weather_wet
import kotlinx.android.synthetic.main.activity_main_screen.weather_wind
import kotlinx.android.synthetic.main.toolbar.toolbar_nav_button
import kotlinx.android.synthetic.main.toolbar.toolbar_title
import kotlinx.android.synthetic.main.toolbar.toolbar_view
import java.text.SimpleDateFormat
import javax.inject.Inject

class MainScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: AccuWeatherRepository

    @Inject
    lateinit var itemsRepository: ItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        setContentView(R.layout.activity_main_screen)
        toolbar_title.text = "Saint-Petersburg"
        toolbar_nav_button.apply {
            setImageResource(R.drawable.vd_location)
            setOnClickListener {
                startActivity(Intent(this@MainScreenActivity, LocationActivity::class.java))
            }
        }
        setActionBar(toolbar_view)
        swipe_to_refresh.run {
            setOnRefreshListener {
                repository.loadWeatherForCity().subscribe(
                    {
                        weather_date.text = SimpleDateFormat("dd.MM.yyyy").format(it.date)
                        weather_temp.text = "${it.temp}\u00B0C"
                        weather_wind.text = "Wind ${it.wind} m/s"
                        weather_wet.text = "Pressure ${it.wet} mm"
                        weather_state.text = it.name
                        putOnManikin()
                        isRefreshing = false
                    },
                    {
                        it.printStackTrace()
                        Log.e("CHRES", "FUCK UP")
                        isRefreshing = false
                    })
            }
        }
        manikin.let {
            it.setHeadAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.HEAD)
                    startActivity(this)
                }
            }
            it.setOverbodyAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.OVERBODY)
                    startActivity(this)
                }
            }
            it.setBodyAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.BODY)
                    startActivity(this)
                }
            }
            it.setLegsAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.LEGS)
                    startActivity(this)
                }
            }
            it.setFeetAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.FEET)
                    startActivity(this)
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item == null) {
            false
        } else {
            when (item.itemId) {
                R.id.wardrobe -> {
                    startActivity(Intent(this, WardrobeActivity::class.java))
                    return true
                }
                else -> false
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun putOnManikin() {
    }

    companion object {
        const val WARDROBE_FILTER_EXTRA = "WARDROBE_FILTER_EXTRA"
    }
}
