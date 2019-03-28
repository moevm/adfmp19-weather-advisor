package info.moevm.se.data.remote.mappers

import info.moevm.se.data.remote.entities.Location
import info.moevm.se.domain.repositories.OneDirectionMapper

class RemoteLocationToLocation : OneDirectionMapper<Location, info.moevm.se.domain.entities.Location> {
    override fun map(value: Location) = info.moevm.se.domain.entities.Location()
}