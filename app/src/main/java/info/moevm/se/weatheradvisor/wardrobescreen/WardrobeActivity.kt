package info.moevm.se.weatheradvisor.wardrobescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import info.moevm.se.data.repositories.ItemRepository
import info.moevm.se.domain.entities.Item
import info.moevm.se.domain.entities.ItemTypes
import info.moevm.se.weatheradvisor.App
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.clotheeditorscreen.ClotheEditorActivity
import info.moevm.se.weatheradvisor.mainscreen.MainScreenActivity.Companion.WARDROBE_FILTER_EXTRA
import info.moevm.se.weatheradvisor.mainscreen.MainScreenActivity.Companion.WARDROBE_TEMP_EXTRA
import info.moevm.se.weatheradvisor.ui.adapter.ItemAdapter
import kotlinx.android.synthetic.main.activity_wardrobe.wardrobe_fab
import kotlinx.android.synthetic.main.activity_wardrobe.wardrobe_list
import kotlinx.android.synthetic.main.toolbar.toolbar_nav_button
import kotlinx.android.synthetic.main.toolbar.toolbar_title
import kotlinx.android.synthetic.main.toolbar.toolbar_view
import javax.inject.Inject

class WardrobeActivity : AppCompatActivity() {

    private val adapter by lazy { ItemAdapter(this, repository.loadAll()) }

    @Inject
    lateinit var repository: ItemRepository

    private var filterByType: ItemTypes? = null
    private var filterByTemp: Int? = null

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

        intent?.let {
            filterByType = if (it.hasExtra(WARDROBE_FILTER_EXTRA)) {
                it.getSerializableExtra(WARDROBE_FILTER_EXTRA) as ItemTypes
            } else {
                null
            }

            filterByTemp = if (it.hasExtra(WARDROBE_TEMP_EXTRA)) {
                it.getIntExtra(WARDROBE_TEMP_EXTRA, 0)
            } else {
                null
            }
        }

        adapter.setTypeFilter(filterByType)
        adapter.setTempFilter(filterByTemp)
    }

    fun updateItem(item: Item, checked: Boolean) {
        item.selected = checked
        repository.update(item)
    }
}
