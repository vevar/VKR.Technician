package com.nstu.technician.data.repository

import com.nstu.technician.domain.model.user.Account
import com.nstu.technician.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(

): AccountRepository {

    override suspend fun find(): Account {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(account: Account) {
        //TODO need impl
    }
}