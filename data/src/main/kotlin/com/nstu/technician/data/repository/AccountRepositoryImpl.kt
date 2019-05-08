package com.nstu.technician.data.repository

import com.nstu.technician.data.datasource.entity.AccountDataSource
import com.nstu.technician.data.datasource.entity.LOCAL
import com.nstu.technician.data.until.toAccount
import com.nstu.technician.data.until.toAccountDTO
import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Named

class AccountRepositoryImpl @Inject constructor(
    @Named(LOCAL)
    private val accountLocalSource: AccountDataSource
) : AccountRepository {

    override suspend fun find(): Account? {
        return accountLocalSource.find()?.toAccount()
    }

    override suspend fun save(account: Account) {
        accountLocalSource.save(account.toAccountDTO())
    }
}