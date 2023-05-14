package com.kjnco.befaster.suite_to_generate

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.self_generated_suite.GestureListener
import com.kjnco.befaster.self_generated_suite.GestureType
import com.kjnco.befaster.self_generated_suite.GestureView

class RestitutionActivity: AppCompatActivity(), GestureListener {

    private lateinit var gestureDetection: GestureView
    private lateinit var imageRestitution: ImageView
    private lateinit var listOfArrows: ArrayList<String>
    private var arrowsRestitution = ArrayList<String>()
    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restitution)

        // Fetching the list of arrows
        listOfArrows = intent.getStringArrayListExtra("listOfArrows") as ArrayList<String>

        // Layout elements
        gestureDetection = findViewById<GestureView>(R.id.gesture_view)
        imageRestitution = findViewById<ImageView>(R.id.restitution)

        // Elements' attributes
        gestureDetection.setNumberOfArrowsToDraw(listOfArrows.size)
        gestureDetection.setGestureListener(this)
    }

    /**
     * Method to convert the drawings to a picture
     */
    private fun mappingGestureToPicture (gesture: GestureType, picture: ImageView){
        when(gesture){
            GestureType.HAUT -> picture.setImageResource(SuiteToGenerate.arrow_pictures[0])
            GestureType.BAS -> picture.setImageResource(SuiteToGenerate.arrow_pictures[1])
            GestureType.GAUCHE -> picture.setImageResource(SuiteToGenerate.arrow_pictures[2])
            GestureType.DROITE -> picture.setImageResource(SuiteToGenerate.arrow_pictures[3])
            else -> {picture.setImageResource(R.drawable.transparent)}
        }
    }

    override fun onGestureDetected(gestureType: GestureType) {
        // Starting the timer
        if (gestureDetection.getNumberOfArrows() == 0) {
            startTime = System.currentTimeMillis()
        }
        mappingGestureToPicture(gestureType, imageRestitution)
        // Adding an Alert Dialog to show the gesture type
        //     and to let the user confirm if the gesture is correct

        // Creating an alert dialog to confirm the gesture
        val builder = createAlertDialogBuilder(gestureType)
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Method to create a builder for the alert dialog
     *      to confirm the gesture
     *      yes: add the gesture to the list of gestures
     *      no: clear the Gesture View
     */
    private fun createAlertDialogBuilder(gestureType: GestureType): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        val customLayout = LayoutInflater.from(this).inflate(
            R.layout.alert_dialog_drawing,
            null
        )
        builder.setView(customLayout)
        val imageView = customLayout.findViewById<ImageView>(R.id.alert_dialog_iv)
        mappingGestureToPicture(gestureType, imageView)
        builder.setTitle(R.string.confirm_drawing_fr)
        builder.setMessage(R.string.confirm_drawing_descr_fr)
        builder.setPositiveButton(R.string.yes_fr) { _, _ ->

            // Converting the gesture type to a string
            // Be careful, the first letter of the string must be uppercase
            gestureDetection.incrementNumberOfArrows()
            val gestureDirection = toTitleCase(gestureType.toString())
            if (gestureDirection == listOfArrows[arrowsRestitution.size]) {
                arrowsRestitution.add(gestureDirection)
                imageRestitution.setImageResource(R.drawable.transparent)
            } else {
                failToReconstituteTheSuite()
            }

            //Executes onGestureEnded when all arrows are drawn
            if (gestureDetection.getNumberOfArrows() >= gestureDetection.getNumberOfArrowsToDraw()) {
                onGestureEnded()
            }
        }

        builder.setNegativeButton(R.string.confirm_drawing_cancel_fr) { _, _ ->
            gestureDetection.clear()
        }
        return builder
    }

    /**
     * Method to handle the case which in the user failes to reconstitute the suite
     */
    private fun failToReconstituteTheSuite() {
        // Getting the answer time
        val answerTime = (System.currentTimeMillis() - startTime).toDouble() / 1000

        // Getting the number of arrows remaining
        val numberOfArrowsRemaining = listOfArrows.size - arrowsRestitution.size

        // Going to the CongratsActivity
        val intent = Intent(this, CongratsActivity::class.java)
        intent.putExtra("answerTime", answerTime)
        intent.putExtra("numberOfArrowsRemaining", numberOfArrowsRemaining)
        startActivity(intent)
    }

    /**
     * Method to convert the first letter of a string to uppercase
     */
    private fun toTitleCase(input: String): String {
        return input.substring(0, 1).uppercase() + input.substring(1).lowercase()
    }

    private fun onGestureEnded() {
        // Comparing the two lists
        val isAnswerCorrect = arrowsRestitution == listOfArrows

        // Getting the answer time
        val answerTime = (System.currentTimeMillis() - startTime).toDouble() / 1000

        // The user succeed, allows to add one more arrow
        goingToExtraArrowActivity(arrowsRestitution, answerTime)
    }

    /**
     * Method to go to the ExtraArrowActivity
     *     to add one more arrow to the list
     */
    private fun goingToExtraArrowActivity(arrowsDirections: ArrayList<String>, answerTime: Double) {
        val intent = Intent(this, ExtraArrowActivity::class.java)
        intent.putStringArrayListExtra("listOfArrows", arrowsRestitution)
        intent.putExtra("answerTime", answerTime)
        startActivity(intent)
    }

}