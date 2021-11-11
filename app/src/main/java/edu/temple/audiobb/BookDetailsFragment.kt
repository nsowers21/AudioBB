package edu.temple.audiobb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class BookDetailsFragment : Fragment() {

    private val viewModel: bookViewModel by activityViewModels()
    private lateinit var cover:ImageView
    private lateinit var title:TextView
    private lateinit var author:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_details, container, false)
        //Get the view from the layout to wire up
        cover = view.findViewById<ImageView>(R.id.bookCover)
        author= view.findViewById<TextView>(R.id.authDet)
        title = view.findViewById<TextView>(R.id.titleDet)

        return view
        }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBookSelected().observe(requireActivity(), {
            changeBook(it)
        })

    }
    //Changes the book cover, title, and author everytime a new one is selected
    private fun changeBook(Book: Book){
        Book?.run {
            title.text
            Picasso.get().load(coverURL).into(cover)

        }

    }


}

