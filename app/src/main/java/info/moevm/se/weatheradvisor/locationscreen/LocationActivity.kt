package info.moevm.se.weatheradvisor.locationscreen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import info.moevm.se.data.repositories.LocationRepository
import info.moevm.se.domain.entities.Location
import info.moevm.se.weatheradvisor.App
import info.moevm.se.weatheradvisor.R
import info.moevm.se.weatheradvisor.mainscreen.MainScreenActivity.Companion.LOCATION_REQUEST
import info.moevm.se.weatheradvisor.ui.adapter.LocationAdapter
import info.moevm.se.weatheradvisor.ui.util.ShiftedOutlineProvider
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_location.location_list
import kotlinx.android.synthetic.main.activity_location.location_search_cover
import kotlinx.android.synthetic.main.activity_location.location_text_edit
import kotlinx.android.synthetic.main.toolbar.toolbar_nav_button
import kotlinx.android.synthetic.main.toolbar.toolbar_title
import kotlinx.android.synthetic.main.toolbar.toolbar_view
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: LocationRepository

    private val adapter: LocationAdapter by lazy { LocationAdapter(this) }

    private lateinit var editTextDisposable: Disposable

    fun citySelected(location: Location) {
        val intent = Intent().apply {
            putExtra(LOCATION_CODE_EXTRA, location.code)
            putExtra(LOCATION_NAME_EXTRA, location.name)
        }
        setResult(LOCATION_REQUEST, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        toolbar_title.text = getString(R.string.location)
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

        editTextDisposable = Observable.create<String> {
            val watcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!it.isDisposed) {
                        it.onNext(s.toString())
                    }
                }
            }
            it.setCancellable { location_text_edit.removeTextChangedListener(watcher) }
            location_text_edit.addTextChangedListener(watcher)
        }
            .debounce(1, TimeUnit.SECONDS)
            .subscribe {
                if (it.isNotBlank()) {
                    adapter.fetchLocations(repository.loadLocationsAuto(it))
                }
            }
    }

    override fun onDestroy() {
        editTextDisposable.dispose()
        super.onDestroy()
    }

    companion object {
        const val LOCATION_CODE_EXTRA = "LOCATION_CODE_EXTRA"
        const val LOCATION_NAME_EXTRA = "LOCATION_NAME_EXTRA"
    }
}
