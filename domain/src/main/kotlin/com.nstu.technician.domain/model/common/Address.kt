package com.nstu.technician.domain.model.common

import com.alxminayev.generator.data.Model
import com.nstu.technician.domain.TypesCity
import com.nstu.technician.domain.TypesHome
import com.nstu.technician.domain.TypesOffice
import com.nstu.technician.domain.TypesStreet
import kotlinx.serialization.Serializable

@Serializable
@Model
data class Address(
    val city: String,
    val street: String,
    val home: String,
    val type: Int,
    val location: GpsObject,
    val office: String? = null
) : java.io.Serializable{

    fun cityType(): Int {
        return type and 0x0F
    }

    fun streetType(): Int {
        return type and 0x0F0 shr 4
    }

    fun homeType(): Int {
        return type and 0x0F00 shr 8
    }

    fun officeType(): Int {
        return type and 0x0F000 shr 12
    }

    private fun toStringShort(): String {
        return TypesStreet[streetType()] + " " + street + "," +
                " " + TypesHome[homeType()] + home +
                if (officeType() == 0) "" else ", " + TypesOffice[officeType()] + office
    }

    override fun toString(): String {
        return (if (cityType() == 0) "" else TypesCity[cityType()] + city + ",") + toStringShort()
    }
}
