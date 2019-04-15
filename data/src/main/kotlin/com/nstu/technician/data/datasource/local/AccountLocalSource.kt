package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.AccountDataSource
import com.nstu.technician.data.datasource.local.dao.AccountDao
import com.nstu.technician.domain.model.user.Account
import javax.inject.Inject

class AccountLocalSource @Inject constructor(
    private val accountDao: AccountDao
) : AccountDataSource {
    override suspend fun find(): Account? {
        return accountDao.find()
    }

    override suspend fun save(account: Account) {
        accountDao.save(account)
    }

}