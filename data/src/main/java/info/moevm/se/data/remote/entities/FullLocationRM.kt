package info.moevm.se.data.remote.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import info.moevm.se.domain.entities.Location

data class FullLocationRM(
    @SerializedName("Key")
    @Expose
    val code: Int,

    @SerializedName("LocalizedName")
    @Expose
    val name: String,

    @SerializedName("Country")
    val country: CountryRM
)

fun FullLocationRM.map() = Location(
    id = 0,
    name = name + " (${country.id})",
    code = code
)