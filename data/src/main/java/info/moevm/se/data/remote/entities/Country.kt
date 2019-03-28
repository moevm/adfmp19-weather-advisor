package info.moevm.se.data.remote.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("ID")
    @Expose
    val id: String
)