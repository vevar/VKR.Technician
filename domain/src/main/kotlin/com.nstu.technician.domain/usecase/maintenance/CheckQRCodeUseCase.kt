package com.nstu.technician.domain.usecase.maintenance

import com.nstu.technician.domain.model.facility.Facility
import com.nstu.technician.domain.tool.QRCodeRecognizer
import com.nstu.technician.domain.usecase.UseCase
import javax.inject.Inject


class CheckQRCodeUseCase<I> @Inject constructor(
    private val qrCodeRecognizer: QRCodeRecognizer<I>
) : UseCase<Boolean, CheckQRCodeUseCase.Param<I>>() {

    companion object {
        const val TAG = "CheckQRCodeUseCase"
    }

    private fun correctQR(facility: Facility) = "${facility.name} (${facility.identifier}) ${facility.address}"

    override suspend fun task(param: Param<I>): Boolean {
        val correctQR = correctQR(param.facility)
        return qrCodeRecognizer.getString(param.image).contains(correctQR)
    }

    class Param<I> private constructor(
        val image: I,
        val facility: Facility
    ) {

        companion object {
            fun <I> checkImageDataForFacility(image: I, facility: Facility): Param<I> {
                return Param(image, facility)
            }
        }
    }
}