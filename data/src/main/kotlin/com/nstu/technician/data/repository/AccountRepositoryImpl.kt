package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.AccountDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.until.convertToDTO
import com.nstu.technician.data.until.convertToModel
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Named

class AccountRepositoryImpl @Inject constructor(
    @Named(LOCAL)
    private val accountLocalSource: AccountDataSource
) : AccountRepository {

    override suspend fun find(): Account? {
        return accountLocalSource.find()?.let {
            convertToModel(it)
        }
    }

    override suspend fun save(account: Account) {
        accountLocalSource.save(convertToDTO(account))
    }
}