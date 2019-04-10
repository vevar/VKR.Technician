package com.nstu.technician.domain.repository

import com.nstu.technician.domain.model.user.Account

interface AccountRepository {

    fun find(): Account
    fun save(account: Account)
}