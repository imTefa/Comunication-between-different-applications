package com.example.receiver.db

import com.example.receiver.models.User
import com.example.receiver.utils.EntityMapper
import javax.inject.Inject

class UserMapper
@Inject
constructor() :
    EntityMapper<UserEntity, User> {

    override fun mapFromEntity(entity: UserEntity): User {
        return User(
            id = entity.id,
            name = entity.name,
            userName = entity.userName,
            email = entity.email,
            phone = entity.phone,
            webSite = entity.webSite
        )
    }

    override fun mapToEntity(model: User): UserEntity {
        return UserEntity(
            id = model.id,
            name = model.name,
            userName = model.userName,
            email = model.email,
            phone = model.phone,
            webSite = model.webSite
        )
    }

    fun mapFromEntityList(entities: List<UserEntity>): List<User> {
        return entities.map { mapFromEntity(it) }
    }

}











