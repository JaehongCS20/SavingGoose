package com.example.pj4test.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pj4test.MainActivity
import com.example.pj4test.ProjectConfiguration
import com.example.pj4test.audioInference.GooseClassifier
import com.example.pj4test.databinding.FragmentAudioBinding

class AudioFragment: Fragment(), GooseClassifier.DetectorListener {
    private val TAG = "AudioFragment"

    private var _fragmentAudioBinding: FragmentAudioBinding? = null

    private val fragmentAudioBinding
        get() = _fragmentAudioBinding!!

    // classifiers
    lateinit var gooseClassifier: GooseClassifier

    // views
    lateinit var snapView: TextView

    private var CameraInterval = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentAudioBinding = FragmentAudioBinding.inflate(inflater, container, false)

        return fragmentAudioBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snapView = fragmentAudioBinding.SnapView

        gooseClassifier = GooseClassifier()
        gooseClassifier.initialize(requireContext())
        gooseClassifier.setDetectorListener(this)
    }

    override fun onPause() {
        super.onPause()
        gooseClassifier.stopInferencing()
    }

    override fun onResume() {
        super.onResume()
        gooseClassifier.startInferencing()
    }

    override fun onResults(score: Float) {
        activity?.runOnUiThread {
            if (score > GooseClassifier.THRESHOLD) {
                snapView.text = "GOOSE"
                snapView.setBackgroundColor(ProjectConfiguration.activeBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.activeTextColor)
                if(CameraInterval == 0)
                    (activity as MainActivity?)!!.addCamera()
                CameraInterval = 100

            } else {
                snapView.text = "NO GOOSE"
                snapView.setBackgroundColor(ProjectConfiguration.idleBackgroundColor)
                snapView.setTextColor(ProjectConfiguration.idleTextColor)
                if(CameraInterval == 0)
                    (activity as MainActivity?)!!.deleteCamera()
                else
                    CameraInterval--
            }
        }
    }
}