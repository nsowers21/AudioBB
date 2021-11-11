package edu.temple.audiobb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.moshi.JsonAdapter
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import org.json.JSONException

import java.lang.ref.WeakReference

class SearchActivity : AppCompatActivity() {

    val searchButton: Button by lazy{
        findViewById(R.id.searchButton2)
    }
    val bookSearch: EditText by lazy{
        findViewById(R.id.booksearch)
    }
    val volleyQueue: RequestQueue by lazy{
        Volley.newRequestQueue(this)
    }
    val closeButton: Button by lazy{
        findViewById(R.id.cancelButton)
    }
    private lateinit var library: ArrayList<Book>


    lateinit var  adapter: JsonAdapter<ArrayList<Book>>
    lateinit var  books: ArrayList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        this.setFinishOnTouchOutside(false)
        searchButton.setOnClickListener {
            parseJSON(bookSearch)
            BookList.addBooks(library)
        }//end of search button on click listener
        closeButton.setOnClickListener{
            setResult(RESULT_OK,Intent())
            this.finish()
        }//end of close button click listener
    }//end of onCreate

    private fun parseJSON(bookSearch: String){
        val url = "https://kamorris.com/lab/cis3515/search.php?term="+bookSearch
        JsonObjectRequest(url
            ,{
                try {
                    library.add(it)
                }
                catch(e:JSONException){
                    e.printStackTrace()
                }
            },null
        )
    //end of parseJSON}


}
