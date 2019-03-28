package info.moevm.se.domain.repositories

interface OneDirectionMapper<From, To> : Mapper<From, To> {
    fun map(value: From) : To
}