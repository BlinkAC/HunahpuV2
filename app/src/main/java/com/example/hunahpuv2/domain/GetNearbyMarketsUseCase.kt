package com.example.hunahpuv2.domain

import com.example.hunahpuv2.data.repository.PlacesRepo

class GetNearbyMarketsUseCase(
    private val cords: String,
    private val rad: Int,
    private val type: String,
    private val key: String
) {
    private val repo = PlacesRepo()

    suspend operator fun invoke() = repo.getNearbyPlaces(cords, rad, type, key)
}