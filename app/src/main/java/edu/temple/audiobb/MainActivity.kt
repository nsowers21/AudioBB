package edu.temple.audiobb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    private val viewModel: bookViewModel by viewModels()
    var landscape = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        landscape= findViewById<View>(R.id.containerDet)!=null
        val searchActivity = Intent(this, SearchActivity::class.java)
        //starts the main activity with the search button
        findViewById<Button>(R.id.searchButton).setOnClickListener{startActivity(searchActivity)}
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainer1) is BookDetailsFragment) {
        }
        if(landscape&&supportFragmentManager.findFragmentById(R.id.containerDet)!is BookDetailsFragment){
            supportFragmentManager.commit{
                add(R.id.containerDet,BookDetailsFragment())
            }
        }//end of if
        else{
            supportFragmentManager.popBackStack()
        }//end of else
        if(savedInstanceState==null){
            supportFragmentManager.commit{
                add(R.id.fragmentContainer1,BookListFragment())
            }
        }//end of if for instanceState
        else{
            supportFragmentManager.commit{
                replace(R.id.fragmentContainer1,BookDetailsFragment())
            }
        }//end of else for savedState
    }//end of onCreate

    //callbacks in reverse
    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.setSelectedBook(null)
    }//end of onBackPressed

    fun selectedBook(){
        if(!landscape){
            supportFragmentManager.commit {
                replace(R.id.fragmentContainer1,BookDetailsFragment())
                setReorderingAllowed(true)
            }
        }
    }//end of selectedBook

}//end of class

/*
      viewModel.setTwoPane(resources.configuration.orientation==Configuration.ORIENTATION_LANDSCAPE)
      //leaving blank to start
      viewModel.setSelectedBook(Book("",""))
      if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
          supportFragmentManager.popBackStack()
          supportFragmentManager.beginTransaction().
          add(R.id.containerDet, viewModel.bookDetFrag)
              .commit()
      }else{
          supportFragmentManager.popBackStack()
      }
      if(savedInstanceState==null){
          supportFragmentManager.commit{
              add(R.id.fragmentContainerView,viewModel.booklistFrag)
          }
      }

       */