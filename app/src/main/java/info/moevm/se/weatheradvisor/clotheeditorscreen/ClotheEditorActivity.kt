package info.moevm.se.weatheradvisor.clotheeditorscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.data.repositories.ItemRepository
import info.moevm.se.domain.entities.Item
import info.moevm.se.weatheradvisor.App
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.ui.adapter.ColorAdapter
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.ALL
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.BODY
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.FEET
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.HEAD
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.LEGS
import info.moevm.se.weatheradvisor.ui.adapter.FilterType.OVERBODY
import info.moevm.se.weatheradvisor.ui.adapter.TypeAdapter
import info.moevm.se.weatheradvisor.ui.util.ShiftedOutlineProvider
import info.moevm.se.weatheradvisor.wardrobescreen.WardrobeActivity
import kotlinx.android.synthetic.main.activity_clothe_editor.create_clothe_button
import kotlinx.android.synthetic.main.activity_clothe_editor.create_clothe_color_bar
import kotlinx.android.synthetic.main.activity_clothe_editor.create_clothe_text_edit
import kotlinx.android.synthetic.main.activity_clothe_editor.create_clothe_type_bar
import kotlinx.android.synthetic.main.activity_clothe_editor.create_clothe_type_spinner
import kotlinx.android.synthetic.main.activity_clothe_editor.temp_before_edit
import kotlinx.android.synthetic.main.activity_clothe_editor.temp_from_edit
import kotlinx.android.synthetic.main.toolbar.toolbar_nav_button
import kotlinx.android.synthetic.main.toolbar.toolbar_title
import kotlinx.android.synthetic.main.toolbar.toolbar_view
import javax.inject.Inject

class ClotheEditorActivity : AppCompatActivity() {

    private val colorAdapter: ColorAdapter by lazy { ColorAdapter(this) }
    private val typeAdapter: TypeAdapter by lazy { TypeAdapter() }

    @Inject
    lateinit var repository: ItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        setContentView(R.layout.activity_clothe_editor)
        toolbar_title.text = getString(R.string.editor_screen)
        toolbar_nav_button.apply {
            setImageResource(R.drawable.vd_navigate_back)
            setOnClickListener {
                onBackPressed()
            }
        }
        setActionBar(toolbar_view)
        create_clothe_text_edit.outlineProvider = ShiftedOutlineProvider
        create_clothe_type_spinner.outlineProvider = ShiftedOutlineProvider
        temp_from_edit.outlineProvider = ShiftedOutlineProvider
        temp_before_edit.outlineProvider = ShiftedOutlineProvider
        create_clothe_color_bar.apply {
            adapter = colorAdapter
            layoutManager = GridLayoutManager(
                this@ClotheEditorActivity,
                7,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

        create_clothe_type_bar.apply {
            adapter = typeAdapter
            layoutManager = GridLayoutManager(
                this@ClotheEditorActivity,
                7,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }

        create_clothe_type_spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val filter = when (position) {
                    0 -> ALL
                    1 -> HEAD
                    2 -> OVERBODY
                    3 -> BODY
                    4 -> LEGS
                    5 -> FEET
                    else -> ALL
                }
                typeAdapter.filter(filter)
            }
        }

        create_clothe_button.setOnClickListener {
            if (
                create_clothe_text_edit.text.isNotBlank() &&
                temp_from_edit.text.isNotBlank() &&
                temp_before_edit.text.isNotBlank()
            ) {
                repository.save(
                    Item(
                        create_clothe_text_edit.text.toString(),
                        typeAdapter.getSelected(),
                        colorAdapter.getSelected(),
                        typeAdapter.getSrc(),
                        temp_from_edit.text.toString().toInt(),
                        temp_before_edit.text.toString().toInt()
                    )
                )
                startActivity(Intent(this, WardrobeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Fill all fields before create the item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onColorChanged(newColor: Int) = typeAdapter.itemColorChanged(newColor)
}
