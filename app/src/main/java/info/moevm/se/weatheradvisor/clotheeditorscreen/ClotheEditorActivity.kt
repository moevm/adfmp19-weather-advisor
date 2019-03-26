package info.moevm.se.weatheradvisor.clotheeditorscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.ui.adapter.ColorAdapter
import info.moevm.se.weatheradvisor.ui.adapter.TypeAdapter
import info.moevm.se.weatheradvisor.ui.util.ShiftedOutlineProvider
import kotlinx.android.synthetic.main.activity_clothe_editor.*
import kotlinx.android.synthetic.main.toolbar.*

class ClotheEditorActivity : AppCompatActivity() {

    private val colorAdapter: ColorAdapter by lazy { ColorAdapter() }
    private val typeAdapter: TypeAdapter by lazy { TypeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothe_editor)
        toolbar_title.text = "New Clothe"
        toolbar_nav_button.apply {
            setImageResource(R.drawable.vd_navigate_back)
            setOnClickListener {
                onBackPressed()
            }
        }
        setActionBar(toolbar_view)
        create_clothe_text_edit.outlineProvider = ShiftedOutlineProvider
        create_clothe_type_spinner.outlineProvider = ShiftedOutlineProvider
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
    }
}
