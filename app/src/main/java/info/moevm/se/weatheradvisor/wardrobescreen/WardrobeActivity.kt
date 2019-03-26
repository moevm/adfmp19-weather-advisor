package info.moevm.se.weatheradvisor.wardrobescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.clotheeditorscreen.ClotheEditorActivity
import kotlinx.android.synthetic.main.activity_wardrobe.*
import kotlinx.android.synthetic.main.toolbar.*

class WardrobeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wardrobe)
        toolbar_title.text = "Wardrobe"
        toolbar_nav_button.apply {
            setImageResource(R.drawable.vd_navigate_back)
            setOnClickListener {
                onBackPressed()
            }
        }
        setActionBar(toolbar_view)
        wardrobe_fab.setOnClickListener { startActivity(Intent(this, ClotheEditorActivity::class.java)) }
    }
}
