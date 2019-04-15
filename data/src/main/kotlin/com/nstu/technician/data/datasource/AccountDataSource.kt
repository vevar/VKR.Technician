package com.nstu.technician.data.datasource

import com.nstu.technician.domain.model.user.Account

interface AccountDataSource {
    suspend fun find(): Account?

    suspend fun save(account: Account)
}
