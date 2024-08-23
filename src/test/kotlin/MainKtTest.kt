import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateVkTransferCommissionTest {

    @Test
    fun testMaestroCard_NoCommission() {
        val result = calculateVkTransferCommission(
            "Maestro", 300.0, 0.0
        )
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun testMaestroCard_Commission() {
        val result = calculateVkTransferCommission(
            "Maestro", 200.0, 0.0
        )
        assertEquals(20.0 + 200.0 * 0.006, result, 0.01)
    }

    @Test
    fun testMastercard_NoCommission() {
        val result = calculateVkTransferCommission(
            "Mastercard", 300.0, 0.0
        )
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun testMastercard_Commission() {
        val result = calculateVkTransferCommission(
            "Mastercard", 200.0, 0.0
        )
        assertEquals(20.0 + 200.0 * 0.006, result, 0.01)
    }

    @Test
    fun testVisaCard_Commission() {
        val result = calculateVkTransferCommission(
            "Visa", 100.0, 0.0
        )
        assertEquals(35.0, result, 0.01)
    }

    @Test
    fun testVisaCard_NoMinimumCommission() {
        val result = calculateVkTransferCommission(
            "Visa", 5000.0, 0.0
        )
        assertEquals(5000.0 * 0.0075, result, 0.01)
    }

    @Test
    fun testMirCard_Commission() {
        val result = calculateVkTransferCommission(
            "Мир", 100.0, 0.0
        )
        assertEquals(35.0, result, 0.01)
    }

    @Test
    fun testMirCard_NoMinimumCommission() {
        val result = calculateVkTransferCommission(
            "Мир", 5000.0, 0.0
        )
        assertEquals(5000.0 * 0.0075, result, 0.01)
    }

    // VK Pay Tests
    @Test
    fun testVkPay_OverSingleLimit() {
        val result = calculateVkTransferCommission(
            "Maestro", 20000.0, 0.0, isVkPay = true
        )
        assertEquals(-2.0, result, 0.01)
    }

    @Test
    fun testVkPay_OverMonthlyLimit() {
        val result = calculateVkTransferCommission(
            "Maestro", 10000.0, 35000.0, isVkPay = true
        )
        assertEquals(-2.0, result, 0.01)
    }

    @Test
    fun testVkPay_AtSingleLimit() {
        val result = calculateVkTransferCommission(
            "Maestro", 15000.0, 0.0, isVkPay = true
        )
        assertEquals(0.0, result, 0.01)
    }

    @Test
    fun testVkPay_AtMonthlyLimit() {
        val result = calculateVkTransferCommission(
            "Maestro", 0.0, 40000.0, isVkPay = true
        )
        assertEquals(20.0, result, 0.01)
    }

    @Test
    fun testCardDailyLimit() {
        val result = calculateVkTransferCommission(
            "Maestro", 200000.0, 0.0
        )
        assertEquals(-2.0, result, 0.01)
    }

    @Test
    fun testCardMonthlyLimit() {
        val result = calculateVkTransferCommission(
            "Maestro", 50000.0, 600000.0
        )
        assertEquals(-2.0, result, 0.01)
    }

    @Test
    fun testInvalidCardType() {
        val result = calculateVkTransferCommission(
            "Unknown", 200.0, 0.0
        )
        assertEquals(-1.0, result, 0.01)
    }

    @Test
    fun testZeroAmount() {
        val result = calculateVkTransferCommission(
            "Visa", 0.0, 0.0
        )
        assertEquals(35.0, result, 0.01)
    }

    @Test
    fun testMaestroCard_Borderline() {
        val result = calculateVkTransferCommission(
            "Maestro", 299.0, 0.0
        )
        assertEquals(20.0 + 299.0 * 0.006, result, 0.01)
    }

    @Test
    fun testMastercardCard_Borderline() {
        val result = calculateVkTransferCommission(
            "Mastercard", 299.0, 0.0
        )
        assertEquals(20.0 + 299.0 * 0.006, result, 0.01)
    }

    @Test
    fun testVisaCard_MinCommission() {
        val result = calculateVkTransferCommission(
            "Visa", 4666.67, 0.0
        )
        assertEquals(35.0, result, 0.01)
    }
}