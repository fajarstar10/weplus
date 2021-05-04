package id.weplus.helper

import java.util.*

fun getAge(year: Int, month: Int, day: Int): String? {
    val dob: Calendar = Calendar.getInstance()
    val today: Calendar = Calendar.getInstance()
    dob.set(year, month, day)
    var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    val ageInt = age
    return ageInt.toString()
}