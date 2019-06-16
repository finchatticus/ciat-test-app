package com.example.testapp.domain.mapper

interface Mapper<in From, out To> {

    fun map(many: Iterable<From>) = many.map { map(it) }

    fun map(from: From): To

}