package com.interview.test.objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ToolTest {
	private Tool toolLadder;
	private Tool toolChainsaw;
	private Tool toolJackhammer;

	@BeforeEach
	public void setup() {
		toolLadder = new Tool("LADW", "Ladder", "Werner", BigDecimal.valueOf(1.99), true, true, false);
		toolChainsaw = new Tool("CHNS", "Chainsaw", "Stihl", BigDecimal.valueOf(1.49), true, false, true);
		toolJackhammer = new Tool("JAKD", "Jackhammer", "DeWalt", BigDecimal.valueOf(2.99), true, false, false);
	}

	@Test
	public void testGetCode() {
		assertEquals("LADW", toolLadder.getToolCode());
		assertEquals("CHNS", toolChainsaw.getToolCode());
		assertEquals("JAKD", toolJackhammer.getToolCode());
	}

	@Test
	public void testGetType() {
		assertEquals("Ladder", toolLadder.getToolType());
		assertEquals("Chainsaw", toolChainsaw.getToolType());
		assertEquals("Jackhammer", toolJackhammer.getToolType());
	}

	@Test
	public void testGetBrand() {
		assertEquals("Werner", toolLadder.getBrand());
		assertEquals("Stihl", toolChainsaw.getBrand());
		assertEquals("DeWalt", toolJackhammer.getBrand());
	}

	@Test
	public void testGetDailyCharge() {
		assertEquals(1.99, toolLadder.getDailyCharge().doubleValue(), 0.01);
		assertEquals(1.49, toolChainsaw.getDailyCharge().doubleValue(), 0.01);
		assertEquals(2.99, toolJackhammer.getDailyCharge().doubleValue(), 0.01);
	}

	@Test
	public void testIsWeekdayCharge() {
		assertTrue(toolLadder.isWeekdayCharge());
		assertTrue(toolChainsaw.isWeekdayCharge());
		assertTrue(toolJackhammer.isWeekdayCharge());
	}

	@Test
	public void testIsWeekendCharge() {
		assertTrue(toolLadder.isWeekendCharge());
		assertFalse(toolChainsaw.isWeekendCharge());
		assertFalse(toolJackhammer.isWeekendCharge());
	}

	@Test
	public void testIsHolidayCharge() {
		assertFalse(toolLadder.isHolidayCharge());
		assertTrue(toolChainsaw.isHolidayCharge());
		assertFalse(toolJackhammer.isHolidayCharge());
	}
}
