package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private var decimal=true
    private var operator=false

    fun numberAction(view: android.view.View) {
        if (view is Button) {
            if(view.text=="."){
                if(decimal){
                    tvDisplay.append(view.text)

                }
                decimal=false
            }else{
                tvDisplay.append(view.text)
            }
            operator=true
        }
    }
    fun operatorAction(view: android.view.View) {
        if (view is Button&&operator){
            tvDisplay.append(view.text)
            operator=false
            decimal=true
        }
    }
    fun allClearAction(view: android.view.View) {
        tvDisplay.text=""
        tvAnswer.text=""
        decimal=true
        operator=false
    }
    fun backSpaceAction(view: android.view.View) {
        val length=tvDisplay.length()
        if(length>0){

            if(tvDisplay.text[length-1] =='.') decimal=false

            if(tvDisplay.text[length-1] =='x'||
                tvDisplay.text[length-1] =='-'||
                tvDisplay.text[length-1] =='+'||
                tvDisplay.text[length-1] =='/')
                operator=true
            tvDisplay.text=tvDisplay.text.subSequence(0,length-1)
        }
    }
    fun equalsAction(view: android.view.View) {
        tvAnswer.text=result()
    }
    private fun result():String{
        val lis=list()
        if (lis.isEmpty()) return ""
        val divmul=divmul(lis)
        if(divmul.isEmpty()) return ""
        val answer=cal(divmul)
        return answer.toString()
    }
    private fun cal(list: MutableList<Any>):Double{
        var double=list[0] as Double
        for(i in list.indices){
            if(list[i] is Char&&i!=list.lastIndexOf(list)){
                val operator=list[i]
                val next=list[i+1] as Double
                if(operator=='+'){
                    double+=next
                }
                if (operator=='-'){
                    double-=next
                }
            }
        }

        return double
    }
    private fun divmul(list: MutableList<Any>):MutableList<Any>{
        var ans= list
        for(i in list.indices){
            if (list[i]=='/'||list[i]=='x'){
                ans=calculatedivmul(ans)
            }
        }

        return ans
    }
    private fun calculatedivmul(list: MutableList<Any>):MutableList<Any>{
        val newList= mutableListOf<Any>()
        var size=list.size
        for(i in list.indices){
            if(list[i] is Char&&i!=list.lastIndexOf(list)&&i<size){
                val operator=list[i]
                val next=list[i+1] as Double
                val prev=list[i-1] as Double
                when(operator){
                    'x' ->{
                        newList.add(prev*next)
                        size=i+1
                    }
                    '/'->{
                        newList.add(prev/next)
                        size=i+1
                    }
                    else ->{
                        newList.add(prev)
                        newList.add(operator)
                    }
                }

            }
            if(i>size){
                newList.add(list[i])
            }
        }
        return newList
    }
    private fun list():MutableList<Any>{
        val list= mutableListOf<Any>()
        var currDigit=""
        for(character in tvDisplay.text){
            if(character.isDigit()||character=='.'){
                currDigit+=character
            }else{
                list.add(currDigit.toDouble())
                currDigit=""
                list.add(character)
            }

        }
        if(currDigit!=""){
            list.add(currDigit.toDouble())
        }
        return list
    }

}