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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null

    //this creates aa bookView model using a special intiation
    private val viewModel: bookViewModel by activityViewModels()

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookListFragment.
         */
        // TODO: Rename and change types and number of parameters
        private const val ITEMS = "ITEMS"
        fun newInstance(books: BookList) =
            BookListFragment().apply {
                    arguments = bundleOf(ITEMS to books)

            }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_list, container, false)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recylcerView)

        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val boooks = arguments?.get("ITEMS") as? BookList?: throw NullPointerException("title is null")
        recyclerView?.adapter=CustomAdapter(activity as MainActivity, boooks, viewModel)
        return view
    }

    }
