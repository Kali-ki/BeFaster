package com.kjnco.befaster.suite_to_generate

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.self_generated_suite.GestureListener
import com.kjnco.befaster.self_generated_suite.GestureType
import com.kjnco.befaster.self_generated_suite.GestureView

class ExtraArrowActivity : AppCompatActivity(), GestureListener {

    companion object {
        var currentElement = 0
    }

    private lateinit var instructionWithTime: TextView
    private lateinit var instruction2: TextView
    private lateinit var gestureDetection: GestureView
    private lateinit var listOfArrows: ArrayList<String>
    private var answerTime: Double = 0.0
    private var arrowsRestitution = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extra_arrow)

        // Clearing the list of arrows
        arrowsRestitution.clear()

        // Fetching the list of arrows
        listOfArrows = intent.getStringArrayListExtra("listOfArrows") as ArrayList<String>

        // Fetching the time took to reconstitute the suite of arrows
        answerTime = intent.getDoubleExtra("answerTime", 0.0)

        // Layout elements
        instructionWithTime = findViewById<TextView>(R.id.instruction_1)
        instruction2 = findViewById<TextView>(R.id.instruction_2)
        gestureDetection = findViewById<GestureView>(R.id.gesture_view)

        // Setting the rules
        setInstructionMessage()

        // Elements' attributes
        gestureDetection.setNumberOfArrowsToDraw(listOfArrows.size+1)
        gestureDetection.setGestureListener(this)
    }

    /**
     * Method to set the rules,
     *     depending whether the game has already begun or not
     */
    @SuppressLint("SetTextI18n")
    private fun setInstructionMessage() {
        if(listOfArrows.size >= 1) {
            instructionWithTime.text = getText(R.string.suite_to_generate_rule_4_fr).toString() + " " + answerTime.toString() +"s,"
        }else {
            instructionWithTime.text = getText(R.string.suite_to_generate_rule_6_fr).toString()
            instruction2.visibility = TextView.GONE
        }

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
            val gestureDirection = toTitleCase(gestureType.toString())
            listOfArrows.add(gestureDirection)

            // Trigger the suite to show for the second user
            onGestureEnded()
        }

        builder.setNegativeButton(R.string.confirm_drawing_cancel_fr) { _, _ ->
            gestureDetection.clear()
        }
        return builder
    }

    /**
     * Method to save the list of arrows with one new element
     *     and then trigger the display for the second user
     */
    private fun onGestureEnded() {
        // Hiding the rules for the display
        instructionWithTime.visibility = TextView.GONE
        instruction2.visibility = TextView.GONE

        // Displaying the list of arrows
        val fragment = ElementFragment.newInstance(listOfArrows[0])
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    /**
     * Method to convert the first letter of a string to uppercase
     */
    private fun toTitleCase(input: String): String {
        return input.substring(0, 1).uppercase() + input.substring(1).lowercase()
    }

    /**
     * Method to go to the next element to display
     */
    fun goToNextElement() {
        currentElement++
        if (currentElement < listOfArrows.size) {
            val fragment = ElementFragment.newInstance(listOfArrows[currentElement])
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }else {
            currentElement = 0
            // Go to the restitution activity
            val intent = Intent(this, RestitutionActivity::class.java)
            intent.putExtra("listOfArrows", listOfArrows)
            startActivity(intent)
        }
    }
}