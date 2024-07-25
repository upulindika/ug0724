package com.interview.test;

import com.interview.test.objects.RentalAgreement;
import com.interview.test.service.RentalService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * The main application class for the tool rental POS system.
 */
public class ToolRentalApplication {
	private RentalService rentalService;
	private Scanner scanner;

	/**
	 * Constructs a ToolRentalApplication with the given rental service and scanner.
	 *
	 * @param rentalService the rental service to use for processing rentals
	 * @param scanner       the scanner to use for reading user input
	 */
	public ToolRentalApplication(RentalService rentalService, Scanner scanner) {
		this.rentalService = rentalService;
		this.scanner = scanner;
	}

	/**
	 * The main method that starts the tool rental application.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		RentalService rentalService = new RentalService();
		Scanner scanner = new Scanner(System.in);
		ToolRentalApplication app = new ToolRentalApplication(rentalService, scanner);
		app.run();
		scanner.close();
	}

	/**
	 * Runs the tool rental application, interacting with the user to collect rental details and print the rental agreement.
	 */
	public void run() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		try {
			System.out.print("Enter tool code (e.g., LADW, CHNS, JAKD, JAKR): ");
			String toolCode = scanner.nextLine().toUpperCase();

			System.out.print("Enter rental day count: ");
			int rentalDays = Integer.parseInt(scanner.nextLine());

			System.out.print("Enter discount percent (0-100): ");
			int discountPercent = Integer.parseInt(scanner.nextLine());

			System.out.print("Enter checkout date (mm/dd/yyyy): ");

			LocalDate checkOutDate;
			try {
				checkOutDate = LocalDate.parse(scanner.nextLine(), formatter);
			} catch (DateTimeParseException e) {
				System.out.println("Error: Invalid date format. Please use mm/dd/yyyy.");
				return;
			}

			RentalAgreement agreement = rentalService.checkout(toolCode, rentalDays, discountPercent, checkOutDate);

			System.out.println("\nRental Agreement:");
			agreement.printAgreement();

		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
