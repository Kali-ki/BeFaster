package com.kjnco.befaster.self_generated_suite

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

class RestitutionActivity: AppCompatActivity() {

    companion object {
        private val arrows_restitution = mutableListOf<String>()
        private val arrow_pictures = mutableListOf<Int>()
    }

    init {
        arrow_pictures.add(R.drawable.blue_arrow_up)
        arrow_pictures.add(R.drawable.blue_arrow_down)
        arrow_pictures.add(R.drawable.blue_arrow_left)
        arrow_pictures.add(R.drawable.blue_arrow_right)
    }

    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restitution)

        // Starting counting time
        val startTime = System.currentTimeMillis()

        // Fetching the list of arrows
        val listOfArrows = intent.getIntegerArrayListExtra("listOfArrows")

        // Layout elements
        val gestureDetection: GestureView = findViewById<GestureView>(R.id.gesture_view)
        val score_description: TextView = findViewById<TextView>(R.id.score_descr)
        val score_msg: TextView = findViewById<TextView>(R.id.score)
        val restitution: ImageView = findViewById<ImageView>(R.id.restitution)

        // Elements' attributes
        score_description.text = "Votre score est de : ${score}"
        score_msg.text = "/${SelfGenerated.NUMBER_OF_ELEMENT}"
        var numberOfDrawings = gestureDetection.getNumberOfDrawings()
        var gestureType = gestureDetection.getGestureDirection()

        // Asking the user to reconstitute the sequence
        while(numberOfDrawings < SelfGenerated.NUMBER_OF_ELEMENT) {
            gestureType = gestureDetection.getGestureDirection()
            mappingGestureToPicture(gestureType, restitution)
            arrows_restitution.add(gestureType.toString())
            numberOfDrawings = gestureDetection.getNumberOfDrawings()
            score++
        }

        // Stopping counting time
        val endTime = System.currentTimeMillis()
        val answerTime = endTime - startTime

        // Converting the list of arrows into a list of string
        val arrows_directions = mutableListOf<String>()
        if (listOfArrows != null) {
            for (arrow in listOfArrows) {
                arrows_directions += when (arrow) {
                    R.drawable.arrow_up -> "Haut"
                    R.drawable.arrow_down -> "Bas"
                    R.drawable.arrow_left -> "Gauche"
                    R.drawable.arrow_right -> "Droite"
                    else -> ""
                }
            }
        }
        // Removing all the NONE values
        arrows_restitution.removeAll(listOf("NONE"))

        // Comparing the two lists
        val isAnswerCorrect = arrows_restitution == arrows_directions

        // Passing to the congrats activity
        val intent = Intent(this, CongratsActivity::class.java)
        intent.putExtra("isAnswerCorrect", isAnswerCorrect)
        intent.putExtra("time", answerTime)
        startActivity(intent)
    }

    /**
     * Method to convert the drawings to a picture
     */
    private fun mappingGestureToPicture (gesture: GestureType, picture: ImageView){
        when(gesture){
            GestureType.HAUT -> picture.setImageResource(arrow_pictures[0])
            GestureType.BAS -> picture.setImageResource(arrow_pictures[1])
            GestureType.GAUCHE -> picture.setImageResource(arrow_pictures[2])
            GestureType.DROITE -> picture.setImageResource(arrow_pictures[3])
            else -> {picture.setImageResource(R.drawable.transparent)}
        }
    }
}