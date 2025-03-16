package com.alexisdev.film_catalog.mapper

interface Mapper<in T> {
    fun map(source: T)
}