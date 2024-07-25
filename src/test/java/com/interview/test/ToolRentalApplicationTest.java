package com.interview.test;

import com.interview.test.objects.RentalAgreement;
import com.interview.test.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ToolRentalApplicationTest {

	@Mock
	private RentalService rentalService;
	private ByteArrayOutputStream outputStream;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));
	}

	@Test
	public void testRunSuccessfulCheckout() {
		String input = "LADW\n3\n10\n07/02/2020\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		RentalAgreement mockAgreement = mock(RentalAgreement.class);
		when(rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2))).thenReturn(mockAgreement);

		ToolRentalApplication app = new ToolRentalApplication(rentalService, scanner);
		app.run();

		verify(rentalService).checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
		verify(mockAgreement).printAgreement();
	}

	@Test
	public void testRunInvalidRentalDays() {
		String input = "LADW\n-1\n10\n07/02/2020\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		when(rentalService.checkout(anyString(), anyInt(), anyInt(), any(LocalDate.class)))
				.thenThrow(new IllegalArgumentException("Rental day count must be 1 or greater."));

		ToolRentalApplication app = new ToolRentalApplication(rentalService, scanner);
		app.run();

		assertTrue(outputStream.toString().contains("Error: Rental day count must be 1 or greater."));
	}

	@Test
	public void testRunInvalidDiscount() {
		String input = "LADW\n3\n110\n07/02/2020\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		when(rentalService.checkout(anyString(), anyInt(), anyInt(), any(LocalDate.class)))
				.thenThrow(new IllegalArgumentException("Discount percent must be between 0 and 100."));

		ToolRentalApplication app = new ToolRentalApplication(rentalService, scanner);
		app.run();

		assertTrue(outputStream.toString().contains("Error: Discount percent must be between 0 and 100."));
	}

	@Test
	public void testRunInvalidDate() {
		String input = "LADW\n3\n10\ninvalid_date\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		Scanner scanner = new Scanner(System.in);

		ToolRentalApplication app = new ToolRentalApplication(rentalService, scanner);
		app.run();

		assertTrue(outputStream.toString().contains("Error: Invalid date format. Please use mm/dd/yyyy."));
	}
}

