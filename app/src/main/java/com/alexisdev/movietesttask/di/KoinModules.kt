package com.alexisdev.movietesttask.di

import com.alexisdev.common.di.navigationModule
import com.alexisdev.data.di.dataModule
import com.alexisdev.domain.di.domainModule
import com.alexisdev.film_catalog.di.filmCatalogFeatureModule
import com.alexisdev.network.di.networkModule

internal val koinModules = listOf(
    networkModule,
    dataModule,
    domainModule,
    filmCatalogFeatureModule,
    navigationModule,
)