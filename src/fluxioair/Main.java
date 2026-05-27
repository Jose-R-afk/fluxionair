package fluxioair;

import fluxioair.service.BookingService;
import fluxioair.service.FlightService;
import fluxioair.service.PassengerService;
import fluxioair.ui.Menu;

/**
 * Application entry point for the FluxioAir system.
 *
 * Instantiates services, loads test data based on the exam use cases,
 * and launches the console menu.
 */
public class Main {

    public static void main(String[] args) {

        // ======================== INSTANTIATE SERVICES ========================
        PassengerService passengerService = new PassengerService();
        FlightService    flightService    = new FlightService();
        BookingService   bookingService   = new BookingService(passengerService, flightService);

        // ======================== LOAD TEST DATA ========================
        loadTestData(passengerService, flightService);

        // ======================== START MENU ========================
        Menu menu = new Menu(passengerService, flightService, bookingService);
        menu.start();
    }

    /**
     * Loads test data based on the exam use cases.
     * Allows testing the system without entering data manually.
     */
    private static void loadTestData(PassengerService passengerService,
                                      FlightService flightService) {
        System.out.println("Cargando datos de prueba del sistema...");

        // ---- TEST PASSENGERS (Exam Case 1) ----
        passengerService.registerPassenger(
                "1234567890", "Juan", "Pérez", 35,
                "juan.perez@email.com", "3001234567",
                "P12345678", "Colombiana");

        passengerService.registerPassenger(
                "9876543210", "María", "González", 28,
                "maria.gonzalez@email.com", "3109876543",
                "P98765432", "Colombiana");

        passengerService.registerPassenger(
                "1122334455", "Carlos", "Rodríguez", 42,
                "carlos.rodriguez@email.com", "3201122334",
                "P11223344", "Venezolana");

        // ---- TEST DOMESTIC FLIGHTS ----
        flightService.addDomesticFlight(
                "VN001", "Bogotá", "Cartagena",
                "20/06/2025", "08:00", "09:30",
                150, 250000, 1.5, true);

        flightService.addDomesticFlight(
                "VN002", "Medellín", "Cali",
                "21/06/2025", "10:00", "11:00",
                120, 180000, 1.0, false);

        flightService.addDomesticFlight(
                "VN003", "Barranquilla", "San Andrés",
                "22/06/2025", "07:00", "08:30",
                100, 320000, 1.5, true);

        // ---- TEST INTERNATIONAL FLIGHTS (Exam Case 2) ----
        flightService.addInternationalFlight(
                "GA100", "Bogotá", "Miami",
                "15/12/2024", "08:00", "13:00",
                180, 800000,
                "Estados Unidos", true, 100000);

        flightService.addInternationalFlight(
                "VI002", "Cartagena", "Panamá",
                "18/06/2025", "06:00", "08:00",
                160, 600000,
                "Panamá", false, 80000);

        flightService.addInternationalFlight(
                "VI003", "Medellín", "Madrid",
                "25/06/2025", "23:00", "15:00",
                200, 1500000,
                "España", true, 150000);

        System.out.println("¡Datos de prueba cargados exitosamente!\n");
    }
}
