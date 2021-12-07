package edu.temple.audiobb

import org.json.JSONObject
import java.io.Serializable

data class Book(val id: Int, val title: String, val author: String, var duration : Int, val coverURL: String) : Serializable {
    constructor(book: JSONObject) : this(book.getInt("id"), book.getString("title"), book.getString("author"),book.getInt("duration"), book.getString("cover_url"))
}
