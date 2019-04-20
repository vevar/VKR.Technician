package com.nstu.technician.data.di.model

import com.nstu.technician.data.database.AppDataBase
import com.nstu.technician.data.datasource.local.dao.*
import dagger.Module
import dagger.Provides

@Module
class DaoModule(private val appDataBase: AppDataBase) {

    @Provides
    fun provideUserDao(): UserDao {
        return appDataBase.getUserDao()
    }

    @Provides
    fun provideTechnicianDao(): TechnicianDao {
        return appDataBase.getTechnicianDao()
    }

    @Provides
    fun provideAccountDao(): AccountDao{
        return appDataBase.getAccountDao()
    }

    @Provides
    fun provideMaintenanceDao(): MaintenanceDao{
        return appDataBase.getMaintenanceDao()
    }

    @Provides
    fun provideFacilityDao(): FacilityDao{
        return appDataBase.getFacilityDao()
    }

    @Provides
    fun provideShiftDao():ShiftDao{
        return appDataBase.getShiftDao()
    }

    @Provides
    fun provideAddressDao(): AddressDao{
        return appDataBase.getAddressDao()
    }

    @Provides
    fun provideGPSDao():GpsDao{
        return appDataBase.getGPSDao()
    }

    @Provides
    fun provideArtifactDao(): ArtifactDao{
        return appDataBase.getArtifactDao()
    }

    @Provides
    fun provideUtilDao(): UtilDao{
        return appDataBase.getUtilDao()
    }

    @Provides
    fun provideContractDao(): ContractDao{
        return appDataBase.getContractDao()
    }
}