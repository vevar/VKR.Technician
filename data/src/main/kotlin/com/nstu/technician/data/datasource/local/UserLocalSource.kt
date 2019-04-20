package com.nstu.technician.data.datasource.local

import com.nstu.technician.data.datasource.AccountDataSource
import com.nstu.technician.data.datasource.LOCAL
import com.nstu.technician.data.datasource.UserDataSource
import com.nstu.technician.data.datasource.local.dao.UserDao
import com.nstu.technician.data.datasource.local.dao.UtilDao
import com.nstu.technician.data.dto.user.AccountDTO
import com.nstu.technician.data.dto.user.UserDTO
import com.nstu.technician.data.until.convertToUserDTO
import com.nstu.technician.data.until.convertToUserEntity
import com.nstu.technician.data.until.getObject
import javax.inject.Inject
import javax.inject.Named

class UserLocalSource @Inject constructor(
    private val utilDao: UtilDao,
    private val userDao: UserDao,
    @Named(LOCAL)
    private val accountLocalSource: AccountDataSource
) : UserDataSource {

    override suspend fun findById(id: Long): UserDTO? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun find(): UserDTO? {
        return accountLocalSource.find()?.let { accountDTO ->
            userDao.find()?.convertToUserDTO(accountDTO)
        }
    }

    override suspend fun findByAccount(account: AccountDTO): UserDTO? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(obj: UserDTO) {
        utilDao.transaction {
            accountLocalSource.save(obj.account.getObject())
            userDao.save(obj.convertToUserEntity())
        }
    }
}