package com.interview.test.service;

import com.interview.test.objects.RentalAgreement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RentalServiceTest {

	private static RentalService rentalService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		rentalService = new RentalService();
	}


	/**
	 * Test 1:
	 * Tests the case where the discount percent is invalid (greater than 100) using tool code "JAKR" with a checkout date of 09/03/2015, rental days of 5, and a discount of 101%.
	 */
	@Test
	public void testInvalidDiscountPercent() {
		LocalDate checkOutDate = LocalDate.of(2015, 9, 3);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			rentalService.checkout("JAKR", 5, 101, checkOutDate);
		});

		assertEquals("Discount percent must be between 0 and 100.", exception.getMessage());
	}

	/**
	 * Test 2:
	 * Tests the rental of a ladder with tool code "LADW" with a checkout date of 07/02/2020, rental days of 3, and a discount of 10%.
	 */
	@Test
	public void testLadderRental() {
		LocalDate checkOutDate = LocalDate.of(2020, 7, 2);
		RentalAgreement agreement = rentalService.checkout("LADW", 3, 10, checkOutDate);

		assertEquals("LADW", agreement.getToolCode());
		assertEquals("Ladder", agreement.getToolType());
		assertEquals("Werner", agreement.getBrand());
		assertEquals(3, agreement.getRentalDays());
		assertEquals(checkOutDate, agreement.getCheckOutDate());
		assertEquals(checkOutDate.plusDays(3), agreement.getDueDate());
		assertEquals(1.99, agreement.getDailyCharge().doubleValue());
		assertEquals(2, agreement.getChargeDays());
		assertEquals(3.98, agreement.getPreDiscountCharge().doubleValue(), 0.01);
		assertEquals(10, agreement.getDiscountPercent());
		assertEquals(0.40, agreement.getDiscountAmount().doubleValue(), 0.01);
		assertEquals(3.59, agreement.getFinalCharge().doubleValue(), 0.01);
	}

	/**
	 * Test 3:
	 * Tests the rental of a chainsaw with tool code "CHNS" with a checkout date of 07/02/2015, rental days of 5, and a discount of 25%.
	 */
	@Test
	public void testChainsawRental() {
		LocalDate checkOutDate = LocalDate.of(2015, 7, 2);
		RentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, checkOutDate);

		assertEquals("CHNS", agreement.getToolCode());
		assertEquals("Chainsaw", agreement.getToolType());
		assertEquals("Stihl", agreement.getBrand());
		assertEquals(5, agreement.getRentalDays());
		assertEquals(checkOutDate, agreement.getCheckOutDate());
		assertEquals(checkOutDate.plusDays(5), agreement.getDueDate());
		assertEquals(1.49, agreement.getDailyCharge().doubleValue());
		assertEquals(3, agreement.getChargeDays()); // 3 chargeable days
		assertEquals(4.47, agreement.getPreDiscountCharge().doubleValue(), 0.01);
		assertEquals(25, agreement.getDiscountPercent());
		assertEquals(1.12, agreement.getDiscountAmount().doubleValue(), 0.01);
		assertEquals(3.35, agreement.getFinalCharge().doubleValue(), 0.01);
	}

	/**
	 * Test 4:
	 * Tests the rental of a jackhammer with tool code "JAKD" with a checkout date of 09/03/2015, rental days of 6, and no discount.
	 */
	@Test
	public void testJackhammerRentalWithNoDiscount() {
		LocalDate checkOutDate = LocalDate.of(2015, 9, 3);
		RentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, checkOutDate);

		assertEquals("JAKD", agreement.getToolCode());
		assertEquals("Jackhammer", agreement.getToolType());
		assertEquals("DeWalt", agreement.getBrand());
		assertEquals(6, agreement.getRentalDays());
		assertEquals(checkOutDate, agreement.getCheckOutDate());
		assertEquals(checkOutDate.plusDays(6), agreement.getDueDate());
		assertEquals(2.99, agreement.getDailyCharge().doubleValue());
		assertEquals(3, agreement.getChargeDays()); // 3 chargeable days
		assertEquals(8.97, agreement.getPreDiscountCharge().doubleValue(), 0.01);
		assertEquals(0, agreement.getDiscountPercent());
		assertEquals(0, agreement.getDiscountAmount().doubleValue(), 0.01);
		assertEquals(8.97, agreement.getFinalCharge().doubleValue(), 0.01);
	}

	/**
	 * Test 5:
	 * Tests the rental of a jackhammer with tool code "JAKR" with a checkout date of 07/02/2015, rental days of 9, and no discount.
	 */
	@Test
	public void testJackhammerRentalWithNoDiscountLong() {
		LocalDate checkOutDate = LocalDate.of(2015, 7, 2);
		RentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, checkOutDate);

		assertEquals("JAKR", agreement.getToolCode());
		assertEquals("Jackhammer", agreement.getToolType());
		assertEquals("Ridgid", agreement.getBrand());
		assertEquals(9, agreement.getRentalDays());
		assertEquals(checkOutDate, agreement.getCheckOutDate());
		assertEquals(checkOutDate.plusDays(9), agreement.getDueDate());
		assertEquals(2.99, agreement.getDailyCharge().doubleValue());
		assertEquals(5, agreement.getChargeDays()); // 5 chargeable days
		assertEquals(14.95, agreement.getPreDiscountCharge().doubleValue(), 0.01);
		assertEquals(0, agreement.getDiscountPercent());
		assertEquals(0, agreement.getDiscountAmount().doubleValue(), 0.01);
		assertEquals(14.95, agreement.getFinalCharge().doubleValue(), 0.01);
	}

	/**
	 * Test 6:
	 * Tests the rental of a jackhammer with tool code "JAKR" with a checkout date of 07/02/2020, rental days of 4, and a discount of 50%.
	 */
	@Test
	public void testJackhammerRentalWith50PercentDiscount() {
		LocalDate checkOutDate = LocalDate.of(2020, 7, 2);
		RentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, checkOutDate);

		assertEquals("JAKR", agreement.getToolCode());
		assertEquals("Jackhammer", agreement.getToolType());
		assertEquals("Ridgid", agreement.getBrand());
		assertEquals(4, agreement.getRentalDays());
		assertEquals(checkOutDate, agreement.getCheckOutDate());
		assertEquals(checkOutDate.plusDays(4), agreement.getDueDate());
		assertEquals(2.99, agreement.getDailyCharge().doubleValue());
		assertEquals(1, agreement.getChargeDays()); // 1 chargeable days
		assertEquals(2.99, agreement.getPreDiscountCharge().doubleValue(), 0.01);
		assertEquals(50, agreement.getDiscountPercent());
		assertEquals(1.50, agreement.getDiscountAmount().doubleValue(), 0.01);
		assertEquals(1.50, agreement.getFinalCharge().doubleValue(), 0.01);
	}
}
