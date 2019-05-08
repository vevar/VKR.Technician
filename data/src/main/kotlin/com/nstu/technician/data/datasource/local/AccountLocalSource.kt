package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.entity.AccountDataSource
import com.nstu.technician.data.datasource.local.dao.AccountDao
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.until.toAccountDTO
import com.nstu.technician.data.until.toAccountEntity
import javax.inject.Inject

class AccountLocalSource @Inject constructor(
    private val accountDao: AccountDao
) : AccountDataSource {
    override suspend fun find(): AccountDTO? {
        return accountDao.find()?.toAccountDTO()
    }

    override suspend fun save(account: AccountDTO) {
        accountDao.save(account.toAccountEntity())
    }

}