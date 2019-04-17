package info.moevm.se.weatheradvisor.mainscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import info.moevm.se.data.repositories.AccuWeatherRepository
import info.moevm.se.data.repositories.ItemRepository
import info.moevm.se.domain.entities.ItemTypes
import info.moevm.se.weatheradvisor.App
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.locationscreen.LocationActivity
import info.moevm.se.weatheradvisor.locationscreen.LocationActivity.Companion.LOCATION_CODE_EXTRA
import info.moevm.se.weatheradvisor.locationscreen.LocationActivity.Companion.LOCATION_NAME_EXTRA
import info.moevm.se.weatheradvisor.wardrobescreen.WardrobeActivity
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
import java.util.*
import javax.inject.Inject

class MainScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: AccuWeatherRepository

    @Inject
    lateinit var itemsRepository: ItemRepository

    var currentCode = DEFAULT_LOCATION_CODE

    @SuppressLint("CheckResult")
    fun updateData(code: String) {
        repository.loadWeatherForCity(code).subscribe(
            {
                weather_date.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it.date)
                weather_temp.text = String.format("%s\u00B0C", it.temp)
                weather_wind.text = String.format("Wind %s m/s", it.wind)
                weather_wet.text = String.format("Pressure %s mm", it.wet)
                weather_state.text = it.name
                putOnManikin()
                swipe_to_refresh.isRefreshing = false
            },
            {
                it.printStackTrace()
                swipe_to_refresh.isRefreshing = false
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)

        setContentView(R.layout.activity_main_screen)
        toolbar_title.text = getString(R.string.default_city)
        toolbar_nav_button.apply {
            setImageResource(R.drawable.vd_location)
            setOnClickListener {
                startActivityForResult(
                    Intent(this@MainScreenActivity, LocationActivity::class.java),
                    LOCATION_REQUEST
                )
            }
        }
        updateData(currentCode)
        setActionBar(toolbar_view)
        swipe_to_refresh.setOnRefreshListener {
            updateData(currentCode)
        }
        manikin.let {
            val temp = weather_temp.text.toString().substring(0, weather_temp.text.length - 2).toFloat().toInt()
            it.setHeadAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.HEAD)
                    putExtra(WARDROBE_TEMP_EXTRA, temp)
                    startActivity(this)
                }
            }
            it.setOverbodyAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.OVERBODY)
                    putExtra(WARDROBE_TEMP_EXTRA, temp)
                    startActivity(this)
                }
            }
            it.setBodyAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.BODY)
                    putExtra(WARDROBE_TEMP_EXTRA, temp)
                    startActivity(this)
                }
            }
            it.setLegsAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.LEGS)
                    putExtra(WARDROBE_TEMP_EXTRA, temp)
                    startActivity(this)
                }
            }
            it.setFeetAction {
                Intent(this, WardrobeActivity::class.java).apply {
                    putExtra(WARDROBE_FILTER_EXTRA, ItemTypes.FEET)
                    putExtra(WARDROBE_TEMP_EXTRA, temp)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) return
        when (requestCode) {
            LOCATION_REQUEST -> {
                val code = data.getIntExtra(LOCATION_CODE_EXTRA, 295212).toString()
                val name =
                    data.getStringExtra(LOCATION_NAME_EXTRA).let { if (it.isNotBlank()) it else "Saint-Petersburg" }
                toolbar_title.text = name
                currentCode = code
                updateData(code)
            }
            else -> throw IllegalStateException()
        }
    }

    @SuppressLint("CheckResult")
    private fun putOnManikin() {
        val temp = weather_temp.text.toString().substring(0, weather_temp.text.length - 2).toFloat().toInt()
        manikin.clearItems()
        itemsRepository.loadAll().subscribe { items ->
            items.asSequence()
                .filter {
                    it.type == ItemTypes.HEAD &&
                            it.tempFrom <= temp &&
                            it.tempTo >= temp
                }.toCollection(manikin.headItems)

            items.asSequence()
                .filter {
                    it.type == ItemTypes.OVERBODY &&
                            it.tempFrom <= temp &&
                            it.tempTo >= temp
                }.toCollection(manikin.overbodyItems)

            items.asSequence()
                .filter {
                    it.type == ItemTypes.BODY &&
                            it.tempFrom <= temp &&
                            it.tempTo >= temp
                }.toCollection(manikin.bodyItems)

            items.asSequence()
                .filter {
                    it.type == ItemTypes.LEGS &&
                            it.tempFrom <= temp &&
                            it.tempTo >= temp
                }.toCollection(manikin.legsItems)

            items.asSequence()
                .filter {
                    it.type == ItemTypes.FEET &&
                            it.tempFrom <= temp &&
                            it.tempTo >= temp
                }.toCollection(manikin.feetItems)

            manikin.dress()
        }
    }

    companion object {
        const val WARDROBE_FILTER_EXTRA = "WARDROBE_FILTER_EXTRA"
        const val WARDROBE_TEMP_EXTRA = "WARDROBE_TEMP_EXTRA"
        const val LOCATION_REQUEST = 0x001
        const val DEFAULT_LOCATION_CODE = "295212"
    }
}
