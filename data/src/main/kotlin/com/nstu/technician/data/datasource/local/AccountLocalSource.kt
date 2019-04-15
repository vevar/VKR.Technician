package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.AccountDataSource
import com.nstu.technician.domain.model.user.Account
import javax.inject.Inject

class AccountLocalSource @Inject constructor(

): AccountDataSource {
    override fun find(): Account? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(account: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}