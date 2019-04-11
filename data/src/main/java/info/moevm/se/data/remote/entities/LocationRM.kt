package info.moevm.se.data.remote.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocationRM(
    @SerializedName("Key")
    @Expose
    val code: Int
)