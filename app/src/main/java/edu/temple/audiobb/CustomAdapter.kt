package edu.temple.audiobb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(listofBooks: BookList, clicked: (Book)->Unit): RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
    val list = listofBooks
    val clicked = clicked

    class ViewHolder(itemView: View, clicked: (Book)->Unit): RecyclerView.ViewHolder(itemView){
        lateinit var Book:Book
        var title: TextView = itemView.findViewById(R.id.Booktitle)
        var author: TextView = itemView.findViewById(R.id.BookAuthor)
        init{
            title.setOnClickListener {
                clicked(Book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.book, parent, false), clicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //gets the position that was clicked from the list to give to the book class
        holder.author.text=list[position].title
        holder.title.text= list[position].author
        holder.Book= list[position]



    }//end of onBind

    override fun getItemCount(): Int {
        return list.size()
    }
}