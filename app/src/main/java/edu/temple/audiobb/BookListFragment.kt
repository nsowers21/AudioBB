package edu.temple.audiobb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import java.lang.NullPointerException

class BookListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null

    //this creates aa bookView model using a special intiation
    private val viewModel: bookViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the booklistfragment layout
        return inflater.inflate(R.layout.fragment_book_list,container,false)
    }//end of view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clicked:(Book)->Unit={
            book: Book ->  viewModel.setSelectedBook(book)
            (activity as BookInterFace).selectedBook()
        }
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recylcerView)
        recyclerView.adapter=CustomAdapter(BookList,clicked)
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }//end of onViewCreated
}//end of BooklistFragment
private const val ITEMS = "ITEMS"
