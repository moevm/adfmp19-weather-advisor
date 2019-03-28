package info.moevm.se.domain.repositories

import io.reactivex.Completable
import io.reactivex.Maybe

interface Repository<T> {
    fun load(): Maybe<T>
    fun save(value: T): Completable
}