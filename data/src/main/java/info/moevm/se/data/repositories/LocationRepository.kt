package info.moevm.se.data.repositories

import info.moevm.se.data.local.dao.LocationDAO
import info.moevm.se.data.local.entities.map
import info.moevm.se.data.remote.entities.RegionRM
import info.moevm.se.data.remote.entities.map
import info.moevm.se.data.remote.services.RestAPI
import info.moevm.se.domain.entities.Location
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    val dao: LocationDAO,
    val retrofit: Retrofit
) {

    fun saveLocations(data: List<Location>) = Completable
        .create { data.forEach { dao.insert(it.map()) } }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun saveLocation(data: Location) = Completable
        .create { dao.insert(data.map()) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun loadLocations() = Maybe
        .create<List<Location>> {
            dao.all()
        }
        .toObservable()
        .filter { !it.isEmpty() }
        .switchIfEmpty { downloadLocations() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun loadLocationsAuto(prefix: String) = retrofit.create(RestAPI::class.java).getLocationAuto(prefix)
        .toObservable()
        .flatMapIterable { it }
        .map { it.map() }
        .toList()
        .toObservable()



    fun loadLocationsByPrefix(prefix: String): Observable<List<Location>> = Observable
//        .create<List<Location>> {
//            dao.itemByPrefix(prefix)
//        }
//        .filter { !it.isEmpty() }
//        .switchIfEmpty { downloadLocations() }
        .fromCallable { downloadLocations() }
        .flatMapIterable { it.blockingGet() }
        .filter { it.name.startsWith(prefix.substring(0, prefix.length - 1)) }
        .toList()
        .toObservable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    private fun downloadLocations(): Maybe<List<Location>> {
        return Maybe
            .fromCallable { RegionRM.values().toList() }
            .map { convertToLocation(it) }
            .doOnSuccess { saveLocations(it) }
    }

    private fun convertToLocation(regions: List<RegionRM>): List<Location> {
        return ArrayList<Location>().apply {
            regions.forEach { region ->
                retrofit.create(RestAPI::class.java)
                    .getCountries(region.region)
                    .blockingGet()
                    .forEach { country ->
                        retrofit.create(RestAPI::class.java)
                            .getCities(country.id)
                            .blockingGet()
                            .forEach { city ->
                                val location = retrofit.create(RestAPI::class.java)
                                    .getLocation(country.id, city.id, city.name)
                                    .blockingGet()
                                    .first()
                                add(Location(0, city.name, location.code))
                            }
                    }
            }
        }
    }
}
