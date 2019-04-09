package info.moevm.se.weatheradvisor.wardrobescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import info.moevm.se.data.repositories.ItemRepository
import info.moevm.se.weatheradvisor.App
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.clotheeditorscreen.ClotheEditorActivity
import info.moevm.se.weatheradvisor.ui.adapter.ItemAdapter
import kotlinx.android.synthetic.main.activity_wardrobe.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class WardrobeActivity : AppCompatActivity() {

    private val adapter by lazy { ItemAdapter(repository.loadAll()) }

    @Inject
    lateinit var repository: ItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        setContentView(R.layout.activity_wardrobe)
        toolbar_title.text = "Wardrobe"
        toolbar_nav_button.apply {
            setImageResource(R.drawable.vd_navigate_back)
            setOnClickListener {
                onBackPressed()
            }
        }
        setActionBar(toolbar_view)
        wardrobe_fab.setOnClickListener {
            startActivity(Intent(this, ClotheEditorActivity::class.java))
            finish()
        }

        wardrobe_list.apply {
            adapter = this@WardrobeActivity.adapter
            layoutManager = LinearLayoutManager(this@WardrobeActivity)
        }
    }
}
