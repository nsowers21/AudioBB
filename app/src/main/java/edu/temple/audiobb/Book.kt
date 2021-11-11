package edu.temple.audiobb

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Book(var id: Int,var title: String, var author:String, var coverURL: String):Serializable


