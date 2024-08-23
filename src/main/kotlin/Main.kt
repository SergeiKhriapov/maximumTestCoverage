const val ERROR = -1.0;
const val ERROR_LIMIT = -2.0

fun main() {
    println(
        calculateVkTransferCommission(
            "Maestro", 200.0, 0.0, false
        )
    )
}

fun calculateVkTransferCommission(
    cardType: String,
    transferAmount: Double,
    monthlyTransfers: Double,
    isVkPay: Boolean = false
): Double {
    val minTransferAmountForNoCommissionMasterMaestro = 300.0
    val maxMonthlyLimitForNoCommission = 75_000.0
    val commissionRateMasterMaestro = 0.006
    val fixedCommissionMasterMaestro = 20.0
    val commissionRateVisaMir = 0.0075
    val minCommissionVisaMir = 35.0
    val vkPaySingleLimit = 15_000.0
    val vkPayMonthlyLimit = 40_000.0
    val cardDailyLimit = 150_000.0
    val cardMonthlyLimit = 600_000.0

    if (isVkPay) {
        if (transferAmount > vkPaySingleLimit || monthlyTransfers + transferAmount > vkPayMonthlyLimit) {
            return ERROR_LIMIT
        }
    }

    if (transferAmount > cardDailyLimit || transferAmount + monthlyTransfers > cardMonthlyLimit) {
        return ERROR_LIMIT
    }

    return when (cardType) {
        "Maestro", "Mastercard" -> {
            if (transferAmount >= minTransferAmountForNoCommissionMasterMaestro
                && monthlyTransfers + transferAmount <= maxMonthlyLimitForNoCommission
            ) {
                0.0
            } else {
                transferAmount * commissionRateMasterMaestro + fixedCommissionMasterMaestro
            }
        }

        "Visa", "Мир" -> {
            val commission = transferAmount * commissionRateVisaMir
            if (commission <= minCommissionVisaMir) minCommissionVisaMir else commission
        }

        else -> ERROR
    }
}