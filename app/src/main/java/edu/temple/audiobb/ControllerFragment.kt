package edu.temple.audiobb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class ControllerFragment : Fragment() {

    lateinit var playButton: ImageButton
    lateinit var pauseButton: ImageButton
    lateinit var stopButton: ImageView
    var seekBar: SeekBar? = null
    var nowPlayingTextView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        val layout =  inflater.inflate(R.layout.fragment_controller, container, false)
        playButton = layout.findViewById(R.id.playButton)
        pauseButton = layout.findViewById(R.id.pauseButton)
        stopButton = layout.findViewById(R.id.stopButton)
        seekBar = layout.findViewById(R.id.seekBar)
        nowPlayingTextView = layout.findViewById(R.id.nowPlayingTextView)

        seekBar?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    (activity as ControllerInterface).seek(progress)
                }//end of if
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })//end of  seekBar onclickListener

        val onClickListener = View.OnClickListener {
            var parent = activity as ControllerInterface
            when(it.id){
                R.id.playButton -> parent.play()
                R.id.pauseButton -> parent.pause()
                R.id.stopButton -> parent.stop()
            }//end of when
        }//end of onClick

        //this is where the click of each button is made
        playButton.setOnClickListener(onClickListener)
        pauseButton.setOnClickListener(onClickListener)
        stopButton.setOnClickListener(onClickListener)

        return layout
    }//end of onCreate

    fun setNowPlaying(title: String){
        nowPlayingTextView?.text = title
    }//end of setNowPlaying

    fun setPlayProgress(progress : Int){
        seekBar?.setProgress(progress, true)
    }//end of setPlayProgress

    interface ControllerInterface{
        fun play()
        fun pause()
        fun stop()
        fun seek(position: Int)
    }//end of interface

}//end of fragment