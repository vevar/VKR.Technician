package com.nstu.technician.data.datasource

import com.nstu.technician.data.dto.user.AccountDTO

interface AccountDataSource {
    suspend fun find(): AccountDTO?

    suspend fun save(account: AccountDTO)
}
