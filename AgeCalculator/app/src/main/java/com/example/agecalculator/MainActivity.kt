package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.agecalculator.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            buttonSelectDate.setOnClickListener { view ->
                pickDate(view)

            }
        }
    }
    
    private fun pickDate(view: View){
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                        Toast.makeText(this@MainActivity, "$selectedDayOfMonth/$selectedMonth/$selectedYear", Toast.LENGTH_SHORT).show()

                        val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                        binding.selectedDate.text = if (selectedMonth + 1 > 9) {
                            if (selectedDayOfMonth>9){
                                selectedDate
                            }else{
                                "0$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                            }

                        } else {
                            if (selectedDayOfMonth>9){
                                "$selectedDayOfMonth/0${selectedMonth + 1}/$selectedYear"
                            }else{
                                "0$selectedDayOfMonth/0${selectedMonth + 1}/$selectedYear"
                            }
                        }

                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                        val parsedDate = sdf.parse(selectedDate)

                        val selectedDateInMinutes = parsedDate!!.time / 60000
                        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                        val currentDateInMinutes = currentDate!!.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        binding.ageInMinutes.text = differenceInMinutes.toString()

                        binding.ageInDays.text = (differenceInMinutes/(1440.toLong())).toString()
                    },
                    year,
                    month,
                    day).show()
        }
    }
}