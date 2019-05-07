package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.user.Account

interface AccountRepository {

    suspend fun find(): Account?
    suspend fun save(account: Account)
}