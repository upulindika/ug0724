package com.interview.test.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentalAgreementTest {
	private RentalAgreement rentalAgreement;

	@BeforeEach
	public void setup() {
		rentalAgreement = new RentalAgreement(
				"LADW", "Ladder", "Werner", 3, LocalDate.of(2020, 7, 2), LocalDate.of(2020, 7, 5),
				BigDecimal.valueOf(1.99), 3, BigDecimal.valueOf(5.97), 10, BigDecimal.valueOf(0.60), BigDecimal.valueOf(5.37)
		);
	}

	@Test
	public void testGetToolCode() {
		assertEquals("LADW", rentalAgreement.getToolCode());
	}

	@Test
	public void testGetToolType() {
		assertEquals("Ladder", rentalAgreement.getToolType());
	}

	@Test
	public void testGetBrand() {
		assertEquals("Werner", rentalAgreement.getBrand());
	}

	@Test
	public void testGetRentalDays() {
		assertEquals(3, rentalAgreement.getRentalDays());
	}

	@Test
	public void testGetCheckOutDate() {
		assertEquals(LocalDate.of(2020, 7, 2), rentalAgreement.getCheckOutDate());
	}

	@Test
	public void testGetDueDate() {
		assertEquals(LocalDate.of(2020, 7, 5), rentalAgreement.getDueDate());
	}

	@Test
	public void testGetDailyCharge() {
		assertEquals(1.99, rentalAgreement.getDailyCharge().doubleValue());
	}

	@Test
	public void testGetChargeDays() {
		assertEquals(3, rentalAgreement.getChargeDays());
	}

	@Test
	public void testGetPreDiscountCharge() {
		assertEquals(5.97, rentalAgreement.getPreDiscountCharge().doubleValue(), 0.01);
	}

	@Test
	public void testGetDiscountPercent() {
		assertEquals(10, rentalAgreement.getDiscountPercent());
	}

	@Test
	public void testGetDiscountAmount() {
		assertEquals(0.60, rentalAgreement.getDiscountAmount().doubleValue(), 0.01);
	}

	@Test
	public void testGetFinalCharge() {
		assertEquals(5.37, rentalAgreement.getFinalCharge().doubleValue(), 0.01);
	}

	@Test
	public void testPrintAgreement() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy", Locale.US);

		String expectedOutput = String.format("Tool code: LADW\n" +
						"Tool type: Ladder\n" +
						"Tool brand: Werner\n" +
						"Rental days: 3\n" +
						"Check out date: %s\n" +
						"Due date: %s\n" +
						"Daily rental charge: $1.99\n" +
						"Charge days: 3\n" +
						"Pre-discount charge: $5.97\n" +
						"Discount percent: 10%%\n" +
						"Discount amount: $0.60\n" +
						"Final charge: $5.37\n",
				rentalAgreement.getCheckOutDate().format(dateFormatter),
				rentalAgreement.getDueDate().format(dateFormatter));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
		rentalAgreement.printAgreement();
		//Removing all carriage return (\r) characters
		String actual = outputStream.toString().replaceAll("(\\r)", "");
		assertEquals(expectedOutput, actual);
	}
}
