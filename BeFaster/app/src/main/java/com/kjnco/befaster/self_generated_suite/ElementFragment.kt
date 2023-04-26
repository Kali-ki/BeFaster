package com.kjnco.befaster.self_generated_suite

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kjnco.befaster.R

class ElementFragment : Fragment() {

    private var arrow: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_element, container, false)

       arrow = arguments?.getInt("arrow")!!

       val arrowView: ImageView = view.findViewById(R.id.arrow) as ImageView

        // Adding the sent arrow to the view
        arrowView.setImageResource(R.drawable.transparent)
        Handler(Looper.getMainLooper()).postDelayed({
            arrowView.setImageResource(arrow)
        }, SelfGenerated.DELAY)

        Handler(Looper.getMainLooper()).postDelayed({
            (activity as? SelfGenerated)?.goToNextElement()
        }, SelfGenerated.DURATION)

        return view
    }

    companion object {
        private const val ARG_ARROW = "arrow"

        @JvmStatic
        fun newInstance(arrow: Int) : ElementFragment {
            val fragment = ElementFragment()
            val args = Bundle()
            args.putInt(ARG_ARROW, arrow)
            fragment.arguments = args
            return fragment
        }
    }
}