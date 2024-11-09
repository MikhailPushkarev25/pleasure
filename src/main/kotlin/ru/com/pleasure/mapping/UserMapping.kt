package ru.com.pleasure.mapping

import ru.com.pleasure.entity.User
import ru.pleasure.dto.RequestUser

fun RequestUser.toEntity(): User = User(
    username = username,
    surname = surname,
    password = password,
)