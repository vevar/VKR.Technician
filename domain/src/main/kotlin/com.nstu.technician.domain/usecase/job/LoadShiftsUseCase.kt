package com.nstu.technician.domain.usecase.job

import com.nstu.technician.domain.model.Shift
import com.nstu.technician.domain.model.common.OwnDateTime
import com.nstu.technician.domain.usecase.UseCase
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

class LoadShiftsUseCase @Inject constructor(

) : UseCase<List<Shift>, Unit>() {

    override suspend fun task(param: Unit): List<Shift> {
        delay(1_000)
        return generateListShifts()
    }

    private fun generateListShifts(): MutableList<Shift> {
        val currentDate = Calendar.getInstance()

        val list = mutableListOf<Shift>()

        for (i in -7..7) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = currentDate.timeInMillis
            calendar.add(Calendar.DAY_OF_MONTH, i)
            list.add(Shift(i + 8, OwnDateTime(calendar.timeInMillis)))
        }
        return list
    }

}