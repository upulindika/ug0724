package com.interview.test.service;

import com.interview.test.objects.RentalAgreement;
import com.interview.test.objects.Tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * Service class for handling rental operations.
 */
public class RentalService {

	/**
	 * Creates a rental agreement based on the provided parameters.
	 *
	 * @param toolCode        the code of the tool to be rented
	 * @param rentalDays      the number of days the tool will be rented
	 * @param discountPercent the discount percentage to be applied
	 * @param checkOutDate    the date when the tool is checked out
	 * @return a RentalAgreement object containing all rental details
	 * @throws IllegalArgumentException if rentalDays is less than 1, discountPercent is not between 0 and 100, or toolCode is invalid
	 */
	public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkOutDate) {
		if (rentalDays < 1) {
			throw new IllegalArgumentException("Rental day count must be 1 or greater.");
		}

		if (discountPercent < 0 || discountPercent > 100) {
			throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
		}

		Tool tool = Tool.getToolByCode(toolCode);
		if (tool == null) {
			throw new IllegalArgumentException("Invalid tool code.");
		}

		LocalDate dueDate = checkOutDate.plusDays(rentalDays);
		int chargeDays = calculateChargeDays(tool, checkOutDate, dueDate);
		BigDecimal preDiscountCharge = tool.getDailyCharge().multiply(BigDecimal.valueOf(chargeDays));
		BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf((discountPercent / 100.0)));
		BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

		return new RentalAgreement(toolCode, tool.getToolType(), tool.getBrand(), rentalDays, checkOutDate,
				dueDate, tool.getDailyCharge(), chargeDays, preDiscountCharge, discountPercent,
				round(discountAmount), round(finalCharge));
	}

	/**
	 * Calculates the number of chargeable days for a rental period.
	 *
	 * @param tool         the tool being rented
	 * @param checkOutDate the date when the tool is checked out
	 * @param dueDate      the date when the tool is due
	 * @return the number of chargeable days
	 */
	private int calculateChargeDays(Tool tool, LocalDate checkOutDate, LocalDate dueDate) {
		int chargeDays = 0;
		LocalDate date = checkOutDate.plusDays(1);
		while (!date.isAfter(dueDate)) {
			DayOfWeek dayOfWeek = date.getDayOfWeek();
			if (tool.isWeekdayCharge() && isWeekday(dayOfWeek) ||
					tool.isWeekendCharge() && isWeekend(dayOfWeek) ||
					tool.isHolidayCharge() && isHoliday(date)) {
				chargeDays++;
			}
			if (!tool.isHolidayCharge() && isHoliday(date)) {
				chargeDays--;
			}
			date = date.plusDays(1);
		}
		return chargeDays;
	}

	/**
	 * Checks if a given day is a weekday.
	 *
	 * @param dayOfWeek the day of the week to check
	 * @return true if the day is a weekday, false otherwise
	 */
	private boolean isWeekday(DayOfWeek dayOfWeek) {
		return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
	}

	/**
	 * Checks if a given day is a weekend.
	 *
	 * @param dayOfWeek the day of the week to check
	 * @return true if the day is a weekend, false otherwise
	 */
	private boolean isWeekend(DayOfWeek dayOfWeek) {
		return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
	}

	/**
	 * Checks if a given date is a holiday.
	 *
	 * @param date the date to check
	 * @return true if the date is a holiday, false otherwise
	 */
	private boolean isHoliday(LocalDate date) {
		LocalDate independenceDay = getIndependenceDay(date.getYear());
		LocalDate laborDay = getLaborDay(date.getYear());
		return date.isEqual(laborDay) || date.isEqual(independenceDay);
	}

	/**
	 * Gets the date of Independence Day for a given year, adjusting for weekends.
	 *
	 * @param year the year to check
	 * @return the date of Independence Day
	 */
	private static LocalDate getIndependenceDay(int year) {
		LocalDate july4th = LocalDate.of(year, Month.JULY, 4);
		if (july4th.getDayOfWeek() == DayOfWeek.SATURDAY) {
			return july4th.minusDays(1);
		} else if (july4th.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return july4th.plusDays(1);
		}
		return july4th;
	}

	/**
	 * Gets the date of Labor Day for a given year.
	 *
	 * @param year the year to check
	 * @return the date of Labor Day
	 */
	private static LocalDate getLaborDay(int year) {
		LocalDate firstMondayInSeptember = LocalDate.of(year, Month.SEPTEMBER, 1);
		while (firstMondayInSeptember.getDayOfWeek() != DayOfWeek.MONDAY) {
			firstMondayInSeptember = firstMondayInSeptember.plusDays(1);
		}
		return firstMondayInSeptember;
	}

	/**
	 * Rounds a BigDecimal value to two decimal places.
	 *
	 * @param value the value to round
	 * @return the rounded value
	 */
	private BigDecimal round(BigDecimal value) {
		return value.setScale(2, RoundingMode.CEILING);
	}
}
