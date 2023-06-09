package org.tensorflow.lite.examples.textclassification.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.textclassification.R
import org.tensorflow.lite.examples.textclassification.ResultsModel
import kotlin.math.roundToInt

val defaultdata = listOf(ResultsModel("Sleep", "Sleep", -0.0f, -0.0f), ResultsModel("Nutrition", "Nutrition", -0.0f, -0.0f), ResultsModel("Stress", "Stress", -0.0f, -0.0f), ResultsModel("Alcohol", "Alcohol", -0.0f, -0.0f))
var resultsList: List<ResultsModel> = defaultdata

class AnalysisAdapter: RecyclerView.Adapter<AnalysisAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: AnalysisAdapter.ViewHolder, position: Int) {
        val question = resultsList[position].question
        val answer = resultsList[position].answer
        val negative = resultsList[position].negative
        val positive = resultsList[position].positive
        holder.question.text = question
        holder.answer.text = answer
        // remove scientific notation
        var n = negative?.times(100)?.roundToInt()?.toFloat()
        var p = positive?.times(100)?.roundToInt()?.toFloat()
        holder.result.text = "Negative: "+n.toString()+"% Positive: "+p.toString()+"%"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_analysis_empty, parent, false)
            return ViewHolder(view)
        }
        else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_analysis, parent, false)
            return ViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (resultsList[position].negative == -0.0f) {
            0
        } else {
            1
        }
    }

    override fun getItemCount(): Int {
        return resultsList.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView
        val answer: TextView
        val result: TextView
        init {
            question = itemView.findViewById(R.id.cardTextViewQuestion)
            answer = itemView.findViewById(R.id.cardTextViewAnswer)
            result = itemView.findViewById(R.id.cardTextViewResult)
        }
    }

    fun updateResult(Answer: String, Key: String, neg: Float, pos: Float) {
        resultsList = resultsList.filter { it.question != Key }
        resultsList = resultsList + ResultsModel(Key, Answer, neg, pos)
        notifyDataSetChanged()
    }

    fun getAnalysis(): List<ResultsModel> {
        return resultsList
    }
}