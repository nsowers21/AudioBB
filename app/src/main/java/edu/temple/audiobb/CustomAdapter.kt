package edu.temple.audiobb

import android.system.Os.remove
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val activity: MainActivity, private var listofBooks: BookList, var viewModel: bookViewModel): RecyclerView.Adapter<CustomAdapter.Viewholder>(){
    class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title: TextView = itemView.findViewById(R.id.Booktitle)
        var author: TextView = itemView.findViewById(R.id.BookAuthor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book, parent, false)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        //gets the position that was clicked from the list to give to the book class
        holder.author.text=listofBooks[position].author
        holder.title.text= listofBooks[position].title
        //setting an on clicklistener for the book that is clicked
        holder.itemView.setOnClickListener{
            viewModel.setSelectedBook(listofBooks[position].toString())
            viewModel.getTwoPane().observe(activity,{
                if(!it){
                    activity.supportFragmentManager.commit{
                       remove(viewModel.BookDetailsFragment)
                    }
                }
            })
        }

    }

    override fun getItemCount(): Int {
        return listofBooks.size
    }
}