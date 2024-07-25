package com.interview.test.objects;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a tool available for rental.
 */
@Getter
public class Tool {

	private String toolCode;
	private String toolType;
	private String brand;
	private BigDecimal dailyCharge;
	private boolean weekdayCharge;
	private boolean weekendCharge;
	private boolean holidayCharge;

	private static final Map<String, Tool> toolCatalog = new HashMap<>();

	static {
		toolCatalog.put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl", BigDecimal.valueOf(1.49), true, false, true));
		toolCatalog.put("LADW", new Tool("LADW", "Ladder", "Werner", BigDecimal.valueOf(1.99), true, true, false));
		toolCatalog.put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt", BigDecimal.valueOf(2.99), true, false, false));
		toolCatalog.put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid", BigDecimal.valueOf(2.99), true, false, false));
	}

	/**
	 * Constructs a Tool with the specified details.
	 *
	 * @param toolCode      the code of the tool
	 * @param toolType      the type of the tool
	 * @param brand         the brand of the tool
	 * @param dailyCharge   the daily rental charge for the tool
	 * @param weekdayCharge indicates if the tool has a weekday charge
	 * @param weekendCharge indicates if the tool has a weekend charge
	 * @param holidayCharge indicates if the tool has a holiday charge
	 */
	public Tool(String toolCode, String toolType, String brand, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
		this.toolCode = toolCode;
		this.toolType = toolType;
		this.brand = brand;
		this.dailyCharge = dailyCharge;
		this.weekdayCharge = weekdayCharge;
		this.weekendCharge = weekendCharge;
		this.holidayCharge = holidayCharge;
	}

	/**
	 * Retrieves a Tool object by its code.
	 *
	 * @param toolCode the code of the tool to retrieve
	 * @return the Tool object corresponding to the specified code, or null if no such tool exists
	 */
	public static Tool getToolByCode(String toolCode) {
		return toolCatalog.get(toolCode);
	}
}

