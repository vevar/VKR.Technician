package com.nstu.technician.data.datasource

import com.nstu.technician.domain.model.user.Account

interface AccountDataSource {
    fun find(): Account?

    fun save(account: Account)
}
