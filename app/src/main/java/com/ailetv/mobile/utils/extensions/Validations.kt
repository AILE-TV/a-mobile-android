package com.ailetv.mobile.utils.extensions


fun String?.isValidOtp(): Boolean {
    return this?.length ?: 0 == 4
}