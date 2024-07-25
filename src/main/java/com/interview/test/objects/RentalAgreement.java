package com.interview.test.objects;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a rental agreement for a tool.
 */
@Getter
public class RentalAgreement {

	private String toolCode;
	private String toolType;
	private String brand;
	private int rentalDays;
	private LocalDate checkOutDate;
	private LocalDate dueDate;
	private BigDecimal dailyCharge;
	private int chargeDays;
	private BigDecimal preDiscountCharge;
	private int discountPercent;
	private BigDecimal discountAmount;
	private BigDecimal finalCharge;

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy");

	/**
	 * Constructs a RentalAgreement with the specified details.
	 *
	 * @param toolCode          the code of the rented tool
	 * @param toolType          the type of the rented tool
	 * @param brand             the brand of the rented tool
	 * @param rentalDays        the number of days the tool is rented
	 * @param checkOutDate      the date the tool is checked out
	 * @param dueDate           the date the tool is due back
	 * @param dailyCharge       the daily rental charge for the tool
	 * @param chargeDays        the number of chargeable days
	 * @param preDiscountCharge the total charge before applying any discount
	 * @param discountPercent   the discount percentage applied to the rental
	 * @param discountAmount    the amount of discount applied
	 * @param finalCharge       the final charge after applying the discount
	 */
	public RentalAgreement(String toolCode, String toolType, String brand, int rentalDays, LocalDate checkOutDate,
						   LocalDate dueDate, BigDecimal dailyCharge, int chargeDays, BigDecimal preDiscountCharge,
						   int discountPercent, BigDecimal discountAmount, BigDecimal finalCharge) {
		this.toolCode = toolCode;
		this.toolType = toolType;
		this.brand = brand;
		this.rentalDays = rentalDays;
		this.checkOutDate = checkOutDate;
		this.dueDate = dueDate;
		this.dailyCharge = dailyCharge;
		this.chargeDays = chargeDays;
		this.preDiscountCharge = preDiscountCharge;
		this.discountPercent = discountPercent;
		this.discountAmount = discountAmount;
		this.finalCharge = finalCharge;
	}

	/**
	 * Prints the rental agreement details to the console.
	 */
	public void printAgreement() {
		System.out.println("Tool code: " + toolCode);
		System.out.println("Tool type: " + toolType);
		System.out.println("Tool brand: " + brand);
		System.out.println("Rental days: " + rentalDays);
		System.out.println("Check out date: " + checkOutDate.format(DATE_FORMAT));
		System.out.println("Due date: " + dueDate.format(DATE_FORMAT));
		System.out.printf("Daily rental charge: $%.2f%n", dailyCharge);
		System.out.println("Charge days: " + chargeDays);
		System.out.printf("Pre-discount charge: $%.2f%n", preDiscountCharge);
		System.out.printf("Discount percent: %d%%%n", discountPercent);
		System.out.printf("Discount amount: $%.2f%n", discountAmount);
		System.out.printf("Final charge: $%.2f%n", finalCharge);
	}
}
