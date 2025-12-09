package com.cevichepicante.data.repository

import com.cevichepicante.model.Food
import com.cevichepicante.model.FoodRecipe
import com.cevichepicante.model.FoodSource
import com.cevichepicante.model.FoodType
import com.cevichepicante.model.RecipeMaterialData
import org.junit.Assert.assertEquals
import org.junit.Test

class FoodSourceTestRepository: BaseFoodSourceRepository() {
    override suspend fun insertFoodSourceList(): Boolean {
        return true
    }

    override suspend fun fetchFoodSourceList(): List<FoodSource> {
        return listOf()
    }

    override suspend fun fetchFoodList(): List<Food> {
        return listOf()
    }

    override suspend fun fetchFoodListFiltered(type: FoodType): List<Food> {
        return listOf()
    }

    override suspend fun fetchCookingKindList(): List<String> {
        return listOf()
    }

    override suspend fun fetchCookingMaterialList(): List<String> {
        return listOf()
    }

    override suspend fun fetchCookingOccasionList(): List<String> {
        return listOf()
    }

    override suspend fun fetchFoodDetail(foodId: String): FoodSource? {
        return null
    }

    override suspend fun fetchFoodRecipe(foodId: String): FoodRecipe? {
        return null
    }

    @Test
    fun material_string_to_recipe_material_data() {
        val materialContent = "[재료] 느타리버섯 1그릇 " +
                "[양념장] 고추장 1큰술| 고춧가루 1큰술| 다진 마늘 1작은술| " +
                "다진 파 1큰술| 깨소금 1작은술| 참기름 1큰술| 소금 1/3작은술| " +
                "맛간장 1큰술| 매실엑기스 1/2큰술| 설탕 1/2작은술"
        val processedData = listOf<RecipeMaterialData>(
            RecipeMaterialData(
                category = "재료",
                list = listOf("느타리버섯 1그릇")
            ),
            RecipeMaterialData(
                category = "양념장",
                list = listOf("고추장 1큰술", "고춧가루 1큰술", "다진 마늘 1작은술",
                    "다진 파 1큰술", "깨소금 1작은술", "참기름 1큰술", "소금 1/3작은술",
                    "맛간장 1큰술", "매실엑기스 1/2큰술", "설탕 1/2작은술")
            )
        )

        assertEquals(
            processedData,
            getMaterialList(materialContent)
                .map { RecipeMaterialData(it.first, it.second) }
        )
    }
}