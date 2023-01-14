
/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tensorflow.lite.examples.textclassification

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.tensorflow.lite.examples.textclassification.databinding.ActivityMainBinding
import org.tensorflow.lite.examples.textclassification.fragments.AnalysisFragment
import org.tensorflow.lite.examples.textclassification.fragments.HomeFragment
import org.tensorflow.lite.examples.textclassification.fragments.QuestionsFragment
var questionsAnswers = mutableMapOf<String, String>();
var results = mutableMapOf<String, Float>();
class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val activityMainBinding get() = _activityMainBinding!!
    // public set of questionmodel objects

    var currentQuestion: String? = null
    var currentAnswer: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // create adapter for questionsFragment recycler view
        replaceFragment(QuestionsFragment())


        // setOnItemSelectedListener is called when the user clicks on an item in the bottom sheet
        _activityMainBinding!!.bottomNavigationView.setOnItemSelectedListener {
            // switch to the selected item
            when (it.itemId) {
                R.id.questions -> {
                    replaceFragment(QuestionsFragment())
                    true
                }
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.analysis -> {
                    replaceFragment(AnalysisFragment())
                    true
                }
                else -> false
            }
        }
        for ((key, value) in questionsAnswers) {
            println("Question: $key")
            println("Answer: $value")
        }
        println("Questions and answers: $questionsAnswers")
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    fun addQuestion(question: String?, answer: String?) {
        questionsAnswers.put(question!!, answer!!)
        // print whole questionsAnswers
        println("Questions and answers: ${questionsAnswers.entries}")
        // print amount of entries
        println("Questions and answers: ${questionsAnswers.size}")
        for ((key, value) in questionsAnswers) {
            println("Question: $key")
            println("Answer: $value")
        }
    }

    fun calculateResult(question: String?, answer: String?) {
        return;
    }
}