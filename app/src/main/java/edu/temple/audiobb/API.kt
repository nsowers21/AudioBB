package edu.temple.audiobb

import android.util.SparseArray

class API {
    companion object URL {
        val DownloadedList: ArrayList<String> by lazy{
            ArrayList()
        }

        fun getBookDataUrl(id: Int): String {
            return "https://kamorris.com/lab/cis3515/book.php?id=${id}"
        }
    }
}