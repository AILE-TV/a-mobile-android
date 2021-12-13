package com.ailetv.mobile.utils

fun isValidPaymentAmount(currentAmount: Double, minAmount: Double, maxAmount: Double): Boolean {
  if (currentAmount <= 0)
    return false

  return currentAmount in minAmount..maxAmount
}