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

    @Provides
    fun provideContractorDao(): ContractorDao{
        return appDataBase.getContractorDao()
    }

    @Provides
    fun provideGPSPointFromShiftDao(): GPSPointFromShiftDao{
        return appDataBase.getGPSPointFromShiftDao()
    }

    @Provides
    fun provideImplementsDao(): ImplementDao{
        return appDataBase.getImplementsDao()
    }

    @Provides
    fun provideImplementUnitDao(): ImplementUnitDao{
        return appDataBase.getImplementUnitDao()
    }

    @Provides
    fun provideComponentDao():ComponentDao{
        return appDataBase.getComponentDao()
    }

    @Provides
    fun provideComponentTypeDao(): ComponentTypeDao{
        return appDataBase.getComponentTypeDao()
    }

    @Provides
    fun provideComponentUnitDao(): ComponentUnitDao{
        return appDataBase.getComponentUnitDao()
    }
    @Provides
    fun provideJobTypeDao():JobTypeDao{
        return appDataBase.getJobTypeDao()
    }

    @Provides
    fun provideMaintenanceJobDao(): MaintenanceJobDao{
        return appDataBase.getMaintenanceJobDao()
    }
}