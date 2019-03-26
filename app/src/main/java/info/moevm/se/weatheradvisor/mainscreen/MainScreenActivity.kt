package info.moevm.se.weatheradvisor.mainscreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.locationscreen.LocationActivity
import info.moevm.se.weatheradvisor.wardrobescreen.WardrobeActivity
import kotlinx.android.synthetic.main.toolbar.*

class MainScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        toolbar_title.text = "Saint-Petersburg"
        toolbar_nav_button.apply {
            setImageResource(R.drawable.vd_location)
            setOnClickListener {
                startActivity(Intent(this@MainScreenActivity, LocationActivity::class.java))
            }
        }
        setActionBar(toolbar_view)
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
}
