package info.moevm.se.data.repositories

import info.moevm.se.data.local.dao.ItemDAO
import info.moevm.se.data.local.entities.map
import info.moevm.se.domain.entities.Item
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    val dao: ItemDAO
) {
    fun loadAll() = dao.all()
        .flatMap { Maybe.just(it.asSequence().map { it.map() }.toList()) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun save(item: Item) =
        Completable.fromAction { dao.insert(item.map()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    fun delete(item: Item) = dao.delete(item.map())

    fun update(item: Item) = dao.update(item.map())

    fun loadById(id: String) = dao.itemById(id)
        .map { it.map() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}