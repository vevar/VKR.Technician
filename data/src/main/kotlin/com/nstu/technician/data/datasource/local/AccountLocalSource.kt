package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.AccountDataSource
import com.nstu.technician.data.datasource.local.dao.AccountDao
import com.nstu.technician.data.dto.user.AccountDTO
import javax.inject.Inject

class AccountLocalSource @Inject constructor(
    private val accountDao: AccountDao
) : AccountDataSource {
    override suspend fun find(): AccountDTO? {
        return accountDao.find()
    }

    override suspend fun save(account: AccountDTO) {
        accountDao.save(account)
    }

}