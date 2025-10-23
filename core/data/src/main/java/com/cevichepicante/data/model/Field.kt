package com.cevichepicante.data.model

enum class Field(val fieldName: String) {
    SerialNo(fieldName = "RCP_SNO"),
    RecipeTitle(fieldName = "RCP_TTL"),
    CookingName(fieldName = "CKG_NM"),
    RegistererId(fieldName = "RGTR_ID"),
    RegistererName(fieldName = "RGTR_NM"),
    ViewCount(fieldName = "INQ_CNT"),
    RecommendedCount(fieldName = "RCMM_CNT"),
    ScrappedCount(fieldName = "SRAP_CNT"),
    CookingMethodCategory(fieldName = "CKG_MTH_ACTO_NM"),
    CookingOccasionCategory(fieldName = "CKG_STA_ACTO_NM"),
    CookingMaterialCategory(fieldName = "CKG_MTRL_ACTO_NM"),
    CookingKindCategory(fieldName = "CKG_KND_ACTO_NM"),
    CookingIntro(fieldName = "CKG_IPDC"),
    CookingMaterialContent(fieldName = "CKG_MTRL_CN"),
    CookingAmount(fieldName = "CKG_INBUN_NM"),
    CookingLevel(fieldName = "CKG_DODF_NM"),
    CookingTime(fieldName = "CKG_TIME_NM"),
    RegisteredTime(fieldName = "FIRST_REG_DT"),
    FoodImageUrl(fieldName = "RCP_IMG_URL")
}