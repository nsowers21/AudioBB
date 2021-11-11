package edu.temple.audiobb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.picasso.Picasso
import org.json.JSONArray

import org.json.JSONException
import org.json.JSONObject
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels

import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Response

import java.lang.ref.WeakReference

class SearchActivity : AppCompatActivity() {

    val searchButton2: Button by lazy{
        findViewById(R.id.searchButton2)
    }
    val bookSearch: EditText by lazy{
        findViewById(R.id.booksearch)
    }
    lateinit var  adapter: JsonAdapter<ArrayList<Book>>
    lateinit var  books: ArrayList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        this.setFinishOnTouchOutside(false)
        searchButton2.setOnClickListener(
            //BookList.addBook(bookSearch.text.toString())
        )
    }
    private fun parseJSON(bookSearch: String){
        val library: ArrayList<Book>
        val response = Request("https://kamorris.com/lab/cis3515/search.php?term="+bookSearch)
        val parsableObjects = Moshi.Builder().build()
        adapter = parsableObjects.adapter(Types.newParameterizedType(ArrayList::class.java, Book::class.java))
        books = adapter.fromJson(url?.body?.source()!!)
    }
    private fun Request(url:String): Response?{
        var Request = Request.Builder().url(url).build()

    }
}
