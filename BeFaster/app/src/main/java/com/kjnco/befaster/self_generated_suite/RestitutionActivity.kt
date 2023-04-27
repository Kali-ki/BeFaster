package com.kjnco.befaster.self_generated_suite

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

class RestitutionActivity: AppCompatActivity(), GestureListener {

    companion object {
        private val arrow_pictures = mutableListOf<Int>()
    }

    init {
        arrow_pictures.add(R.drawable.blue_arrow_up)
        arrow_pictures.add(R.drawable.blue_arrow_down)
        arrow_pictures.add(R.drawable.blue_arrow_left)
        arrow_pictures.add(R.drawable.blue_arrow_right)
    }

    private lateinit var gestureDetection: GestureView
    private lateinit var imageRestitution: ImageView
    private lateinit var scoreDescription: TextView
    private lateinit var listOfArrows: ArrayList<Int>
    private var arrowsRestitution = mutableListOf<String>()
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restitution)

        // Fetching the list of arrows
        listOfArrows = intent.getIntegerArrayListExtra("listOfArrows") as ArrayList<Int>

        // Layout elements
        scoreDescription = findViewById<TextView>(R.id.score_descr)
        val score_msg: TextView = findViewById<TextView>(R.id.score)
        gestureDetection = findViewById<GestureView>(R.id.gesture_view)
        imageRestitution = findViewById<ImageView>(R.id.restitution)

        // Elements' attributes
        scoreDescription.text = "Votre score est de : ${score}"
        score_msg.text = "/${SelfGenerated.NUMBER_OF_ELEMENT}"
        gestureDetection.setNumberOfArrowsToDraw(SelfGenerated.NUMBER_OF_ELEMENT)
        gestureDetection.setGestureListener(this)
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

    override fun onGestureDetected(gestureType: GestureType) {
        mappingGestureToPicture(gestureType, imageRestitution)
        // Converting the gesture type to a string
        // Be careful, the first letter of the string must be uppercase
        val gestureDirection = toTitleCase(gestureType.toString())
        arrowsRestitution.add(toTitleCase(gestureDirection))
        score++
        scoreDescription.text = "Votre score est de : ${score}"
    }

    /**
     * Method to convert the first letter of a string to uppercase
     */
    private fun toTitleCase(input: String): String {
        return input.substring(0, 1).uppercase() + input.substring(1).lowercase()
    }
    override fun onGestureEnded() {
       // Converting the list of arrows into a list of string
        // listOfArrows is the list that is auto-generated
        val arrowsDirections = mutableListOf<String>()
        for (arrow in listOfArrows) {
            arrowsDirections += when (arrow) {
                R.drawable.arrow_up -> "Haut"
                R.drawable.arrow_down -> "Bas"
                R.drawable.arrow_left -> "Gauche"
                R.drawable.arrow_right -> "Droite"
                else -> ""
            }
        }

        // Comparing the two lists
        val isAnswerCorrect = arrowsRestitution == arrowsDirections

        // Getting the answer time
        val answerTime = gestureDetection.getAnswerTime()

        // Passing to the congrats activity
        val intent = Intent(this, CongratsActivity::class.java)
        intent.putExtra("isAnswerCorrect", isAnswerCorrect)
        intent.putExtra("answerTime", answerTime)
        startActivity(intent)
    }

}