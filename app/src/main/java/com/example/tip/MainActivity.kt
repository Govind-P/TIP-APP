package com.example.tip

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator

private const val TAG="MainActivity"
private const val INITIAL_TIP_PERCENT =15




class MainActivity : AppCompatActivity() {
    private lateinit var amountbox:EditText
    private lateinit var perbar: SeekBar
    private lateinit var tiptext: TextView
    private lateinit var percentage: TextView
    private lateinit var totalview: TextView
    private lateinit var response: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        amountbox= findViewById(R.id.amountbox)
        perbar=findViewById(R.id.perbar)
        tiptext=findViewById(R.id.tiptext)
        percentage=findViewById(R.id.percentage)
        totalview=findViewById(R.id.totalview)
        response=findViewById(R.id.response)

        perbar.progress= INITIAL_TIP_PERCENT
        percentage.text="$INITIAL_TIP_PERCENT%"
        computeTipTotal()
        perbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG,  "onProgressChanged $progress")
                percentage.text="$progress%"
                computeTipTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        amountbox.addTextChangedListener( object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipTotal()
            }

        })
    }

    @SuppressLint("RestrictedApi")
    private fun computeTipTotal() {
        if(perbar.progress<7){
            response.text="Poor"
        }
        else if(perbar.progress<13){
            response.text="Acceptable"
        }
        else if(perbar.progress<19){
            response.text="Good"
        }
        else if(perbar.progress<25){
            response.text="Great"
        }
        else{
            response.text="Perfect"
        }
        val color=ArgbEvaluator().evaluate(
            perbar.progress.toFloat()/perbar.max ,
            ContextCompat.getColor(this,R.color.color_worst_tip),
            ContextCompat.getColor(this,R.color.color_best_tip)
        )as Int
        response.setTextColor(color)
        if(amountbox.text.isEmpty()){
            tiptext.text=""
            totalview.text=""
            return
        }
        val base=amountbox.text.toString().toDouble()
        val tipper=perbar.progress
        val tips=base*tipper/100
        val finaltotal=base+tips
        tiptext.text="%.2f".format(tips)
        totalview.text="%.2f".format(finaltotal)
    }
}