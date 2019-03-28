package info.moevm.se.domain.repositories

interface TwoDirectionMapper<From, To> : Mapper<From, To> {
    fun mapTo(value: To): From
    fun mapFrom(value: From): To
}