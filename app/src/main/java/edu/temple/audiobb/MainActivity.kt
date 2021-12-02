package edu.temple.audiobb

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import edu.temple.audlibplayer.PlayerService
import com.android.volley.Request


class MainActivity : AppCompatActivity() , BookListFragment.BookSelectedInterface, ControllerFragment.ControllerInterface{

    //intializing everything
    private lateinit var bookListFragment: BookListFragment
    private lateinit var mediaControlBinder: PlayerService.MediaControlBinder
    private var connected = false
    private val isSingleContainer : Boolean by lazy{
        findViewById<View>(R.id.container2) == null
    }

    private val selectedBookViewModel : bookViewModel by lazy {
        ViewModelProvider(this).get(bookViewModel::class.java)
    }
    private val playingBookViewModel : PlayingBookViewModel by lazy {
        ViewModelProvider(this).get(PlayingBookViewModel::class.java)
    }

    private val bookListViewModel : BookList by lazy {
        ViewModelProvider(this).get(BookList::class.java)
    }
    companion object {
        const val BOOKLISTFRAGMENT_KEY = "BookListFragment"
    }
    //done intialization

    val audioBookHandler = Handler(Looper.getMainLooper()){ msg ->
        msg.obj?.let { msgObj ->
            val bookProgress = msgObj as PlayerService.BookProgress
            if(playingBookViewModel.getBookPlaying().value == null){
               Volley.newRequestQueue(this)
                   .add(JsonObjectRequest(Request.Method.GET,API.getBookDataUrl(bookProgress.bookId),null,{ jsonObject ->
                       playingBookViewModel.setBookPlaying(Book(jsonObject))
                       if( selectedBookViewModel.getBook().value==null){
                           selectedBookViewModel.setSelectedBook(playingBookViewModel.getBookPlaying().value)
                           bookSelected()
                       }
                   },{}))
            }
            supportFragmentManager.findFragmentById(R.id.controlFragmentContainerView)?.run{
                with(this as ControllerFragment){
                    playingBookViewModel.getBookPlaying().value?.also{
                        setPlayProgress(((bookProgress.progress/it.duration.toFloat())*100).toInt())
                    }
                }
            }
        }//end of msg
        true
    }//end of handler

    private val searchRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
       supportFragmentManager.popBackStack()
       it.data?.run {
           bookListViewModel.copyBooks(getSerializableExtra(BookList.BOOKLIST_KEY)as BookList)
           bookListFragment.bookListUpdated()
       }
    }//end of searchRequest`

    private val serviceConnect = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mediaControlBinder = service as PlayerService.MediaControlBinder
            mediaControlBinder.setProgressHandler(audioBookHandler)
            connected = true
        }//end of CONNECTED
        override fun onServiceDisconnected(name: ComponentName?) {
            connected=false
        }//end of DISCONNECTED
    }//end of service connect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playingBookViewModel.getBookPlaying().observe(this,{
            (supportFragmentManager.findFragmentById(R.id.controlFragmentContainerView) as ControllerFragment).setNowPlaying(it.title)
        })

        // If we're switching from one container to two containers
        // clear BookDetailsFragment from container1
        if(supportFragmentManager.findFragmentById(R.id.container1) is BookDetailsFragment
            && selectedBookViewModel.getBook().value!=null){
            supportFragmentManager.popBackStack()
        }

        if(savedInstanceState ==null){
            bookListFragment= BookListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container1,bookListFragment, BOOKLISTFRAGMENT_KEY)
                .commit()
        }else{
            bookListFragment=supportFragmentManager.findFragmentByTag(BOOKLISTFRAGMENT_KEY) as BookListFragment
            if(isSingleContainer&&selectedBookViewModel.getBook().value!=null){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
        }

        if(!isSingleContainer&& supportFragmentManager.findFragmentById(R.id.container2) !is BookDetailsFragment)
            supportFragmentManager.beginTransaction()
                .add(R.id.container2,BookDetailsFragment())
                .commit()
        findViewById<ImageButton>(R.id.searchButton).setOnClickListener{
            searchRequest.launch(Intent(this, SearchActivity::class.java))
        }//end of portrait mode

        bindService(Intent(this, PlayerService::class.java)
            , serviceConnect
            , BIND_AUTO_CREATE
        )//end of bind service

    }//end of onCreate

    //callbacks in reverse
    override fun onBackPressed() {
        selectedBookViewModel.setSelectedBook(null)
        super.onBackPressed()
    }//end of onBackPressed

    override fun bookSelected(){
        if(isSingleContainer){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1,BookDetailsFragment())
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }
    }//end of selectedBook

    override fun play() {
        if(connected && selectedBookViewModel.getBook().value!=null){
            Log.d("Button pressed", "Play button")
            mediaControlBinder.play(selectedBookViewModel.getBook().value!!.id)
            playingBookViewModel.setBookPlaying(selectedBookViewModel.getBook().value)
            startService(Intent(this, PlayerService::class.java))
        }
    }//end of play fun

    override fun pause() {
        if(connected) mediaControlBinder.pause()
    }//end of pause

    override fun stop() {
        if(connected) mediaControlBinder.stop()
        stopService(Intent(this, PlayerService::class.java))
    }//end of stop

    override fun seek(position: Int) {
        if(connected &&mediaControlBinder.isPlaying) mediaControlBinder
            .seekTo((playingBookViewModel
                .getBookPlaying().value!!.duration*(position.toFloat()/100)).toInt())
    }//end of seek

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnect)
    }//end of onDestroy

}//end of class
