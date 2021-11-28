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
    lateinit var controllerPosition: TextView
    lateinit var controllerDuration: TextView
    lateinit var seekBar: SeekBar
    lateinit var rewindButton: ImageButton
    lateinit var playButton: ImageButton
    lateinit var pauseButton: ImageButton
    lateinit var ffButton: ImageView
    lateinit var stopButton: ImageView



interface ControllerInterface{
    fun play(id:Int)
    fun pause()
    fun seek(position: Double)
    fun forward()
    fun rewind()
    fun stop()
}

}