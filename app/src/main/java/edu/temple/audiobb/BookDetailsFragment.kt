package edu.temple.audiobb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class BookDetailsFragment : Fragment() {

    private lateinit var author: TextView
    private lateinit var title: TextView
    private val viewModel: bookViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Rename and change types and number of parameters
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_details, container, false)
        // Inflate the layout for this fragment

        author= view.findViewById(R.id.authDet)
        title = view.findViewById(R.id.titleDet)
        viewModel.getBook().observe(viewLifecycleOwner, {
            activity?.title = it.title2
            title.text  = it.title2
            author.text = it.author2
        })
        return view
        }
}

