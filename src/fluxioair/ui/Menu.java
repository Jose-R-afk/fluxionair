package fluxioair.ui;

import fluxioair.exception.*;
import fluxioair.model.Booking;
import fluxioair.model.Flight;
import fluxioair.service.BookingService;
import fluxioair.service.FlightService;
import fluxioair.service.PassengerService;

import java.util.Scanner;

/**
 * Console user interface for the FluxioAir system.
 *
 * All user-facing text is in Spanish.
 * All code (variables, methods, classes) is in English.
 * Every operation is wrapped in try-catch blocks catching specific exceptions.
 */
public class Menu {

    private final Scanner          scanner;
    private final PassengerService passengerService;
    private final FlightService    flightService;
    private final BookingService   bookingService;

    public Menu(PassengerService passengerService, FlightService flightService,
                BookingService bookingService) {
        this.scanner          = new Scanner(System.in);
        this.passengerService = passengerService;
        this.flightService    = flightService;
        this.bookingService   = bookingService;
    }

    // ======================== ENTRY POINT ========================

    public void start() {
        showWelcome();
        int option;
        do {
            showMainMenu();
            option = readInt();
            handleMainOption(option);
        } while (option != 0);
    }

    private void showWelcome() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║         ✈  BIENVENIDO A FLUXIOAIR  ✈                ║");
        System.out.println("║     Sistema de Gestión de Aerolínea v2.0            ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    private void showMainMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         MENÚ PRINCIPAL               ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Gestión de Pasajeros             ║");
        System.out.println("║  2. Gestión de Vuelos                ║");
        System.out.println("║  3. Gestión de Reservas              ║");
        System.out.println("║  4. Estadísticas y Reportes          ║");
        System.out.println("║  0. Salir del Sistema                ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    private void handleMainOption(int option) {
        switch (option) {
            case 1: showPassengerMenu(); break;
            case 2: showFlightMenu();    break;
            case 3: showBookingMenu();   break;
            case 4: showReportsMenu();   break;
            case 0: System.out.println("\n✈  Gracias por usar FluxioAir. ¡Hasta pronto!  ✈\n"); break;
            default: System.out.println("Opción no válida.");
        }
    }

    // ======================== PASSENGER MENU ========================

    private void showPassengerMenu() {
        int option;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║       GESTIÓN DE PASAJEROS           ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Registrar nuevo pasajero         ║");
            System.out.println("║  2. Buscar pasajero por cédula       ║");
            System.out.println("║  3. Listar todos los pasajeros       ║");
            System.out.println("║  0. Volver                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seleccione: ");
            option = readInt();
            switch (option) {
                case 1: registerPassenger(); break;
                case 2: searchPassenger();   break;
                case 3: System.out.println(passengerService.listPassengers()); break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        } while (option != 0);
    }

    private void registerPassenger() {
        System.out.println("\n--- REGISTRO DE NUEVO PASAJERO ---");
        System.out.print("Cédula        : "); String idNumber      = scanner.nextLine().trim();
        System.out.print("Nombres       : "); String firstName     = scanner.nextLine().trim();
        System.out.print("Apellidos     : "); String lastName      = scanner.nextLine().trim();
        System.out.print("Edad          : "); int    age           = readInt();
        System.out.print("Email         : "); String email         = scanner.nextLine().trim();
        System.out.print("Teléfono      : "); String phoneNumber   = scanner.nextLine().trim();
        System.out.print("Pasaporte     : "); String passportNum   = scanner.nextLine().trim();
        System.out.print("Nacionalidad  : "); String nationality   = scanner.nextLine().trim();

        try {
            passengerService.registerPassenger(idNumber, firstName, lastName, age,
                    email, phoneNumber, passportNum, nationality);
            System.out.println("\nEXITO: Pasajero " + firstName + " " + lastName + " registrado correctamente.");

        } catch (InvalidEmailException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (DuplicateIdException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (DuplicatePassportException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    private void searchPassenger() {
        System.out.print("\nIngrese el número de cédula: ");
        String idNumber = scanner.nextLine().trim();
        var passenger = passengerService.findByIdNumber(idNumber);
        if (passenger != null) System.out.println(passenger.generateDetail());
        else                    System.out.println("No se encontró el pasajero con esa cédula.");
    }

    // ======================== FLIGHT MENU ========================

    private void showFlightMenu() {
        int option;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║         GESTIÓN DE VUELOS            ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Agregar vuelo nacional           ║");
            System.out.println("║  2. Agregar vuelo internacional      ║");
            System.out.println("║  3. Buscar vuelo por código          ║");
            System.out.println("║  4. Listar todos los vuelos          ║");
            System.out.println("║  5. Listar vuelos programados        ║");
            System.out.println("║  6. Cambiar estado de un vuelo       ║");
            System.out.println("║  0. Volver                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seleccione: ");
            option = readInt();
            switch (option) {
                case 1: addDomesticFlight();       break;
                case 2: addInternationalFlight();  break;
                case 3: searchFlight();            break;
                case 4: System.out.println(flightService.listAllFlights());       break;
                case 5: System.out.println(flightService.listScheduledFlights()); break;
                case 6: changeFlightStatus();      break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        } while (option != 0);
    }

    private void addDomesticFlight() {
        System.out.println("\n--- AGREGAR VUELO NACIONAL ---");
        System.out.print("Código de vuelo     : "); String code          = scanner.nextLine().trim();
        System.out.print("Ciudad origen       : "); String origin        = scanner.nextLine().trim();
        System.out.print("Ciudad destino      : "); String destination   = scanner.nextLine().trim();
        System.out.print("Fecha (DD/MM/YYYY)  : "); String date          = scanner.nextLine().trim();
        System.out.print("Hora salida (HH:MM) : "); String departure     = scanner.nextLine().trim();
        System.out.print("Hora llegada (HH:MM): "); String arrival       = scanner.nextLine().trim();
        System.out.print("Capacidad total     : "); int    capacity      = readInt();
        System.out.print("Precio base ($)     : "); double basePrice     = readDouble();
        System.out.print("Duración (horas)    : "); double durationHours = readDouble();
        System.out.print("¿Incluye alimentación? (s/n): ");
        boolean includesMeal = scanner.nextLine().trim().equalsIgnoreCase("s");

        try {
            flightService.addDomesticFlight(code, origin, destination, date,
                    departure, arrival, capacity, basePrice, durationHours, includesMeal);
            System.out.println("\nEXITO: Vuelo nacional " + code + " registrado correctamente.");
        } catch (FlightNotAvailableException | IllegalArgumentException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    private void addInternationalFlight() {
        System.out.println("\n--- AGREGAR VUELO INTERNACIONAL ---");
        System.out.print("Código de vuelo      : "); String code        = scanner.nextLine().trim();
        System.out.print("Ciudad origen        : "); String origin      = scanner.nextLine().trim();
        System.out.print("Ciudad destino       : "); String destination = scanner.nextLine().trim();
        System.out.print("Fecha (DD/MM/YYYY)   : "); String date        = scanner.nextLine().trim();
        System.out.print("Hora salida (HH:MM)  : "); String departure   = scanner.nextLine().trim();
        System.out.print("Hora llegada (HH:MM) : "); String arrival     = scanner.nextLine().trim();
        System.out.print("Capacidad total      : "); int    capacity    = readInt();
        System.out.print("Precio base ($)      : "); double basePrice   = readDouble();
        System.out.print("País destino         : "); String country     = scanner.nextLine().trim();
        System.out.print("¿Requiere visa? (s/n): ");
        boolean requiresVisa = scanner.nextLine().trim().equalsIgnoreCase("s");
        System.out.print("Cargo internacional ($): "); double intlFee   = readDouble();

        try {
            flightService.addInternationalFlight(code, origin, destination, date,
                    departure, arrival, capacity, basePrice, country, requiresVisa, intlFee);
            System.out.println("\nEXITO: Vuelo internacional " + code + " registrado correctamente.");
        } catch (FlightNotAvailableException | IllegalArgumentException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    private void searchFlight() {
        System.out.print("\nIngrese el código del vuelo: ");
        String code = scanner.nextLine().trim();
        Flight flight = flightService.findByCode(code);
        // POLYMORPHISM: generateDetail() executes the real subclass version
        if (flight != null) System.out.println(flight.generateDetail());
        else                System.out.println("No se encontró ningún vuelo con el código: " + code);
    }

    private void changeFlightStatus() {
        System.out.print("\nCódigo del vuelo: ");
        String code = scanner.nextLine().trim();
        System.out.println("Estados: Programado | En Vuelo | Aterrizado | Cancelado");
        System.out.print("Nuevo estado: ");
        String status = scanner.nextLine().trim();
        try {
            System.out.println(flightService.changeFlightStatus(code, status));
        } catch (FlightNotAvailableException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    // ======================== BOOKING MENU ========================

    private void showBookingMenu() {
        int option;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║       GESTIÓN DE RESERVAS            ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Crear nueva reserva              ║");
            System.out.println("║  2. Cancelar reserva                 ║");
            System.out.println("║  3. Buscar reserva por código        ║");
            System.out.println("║  4. Listar todas las reservas        ║");
            System.out.println("║  0. Volver                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seleccione: ");
            option = readInt();
            switch (option) {
                case 1: createBooking();  break;
                case 2: cancelBooking();  break;
                case 3: searchBooking();  break;
                case 4: System.out.println(bookingService.listAllBookings()); break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        } while (option != 0);
    }

    private void createBooking() {
        System.out.println("\n--- CREAR NUEVA RESERVA ---");
        System.out.print("Cédula del pasajero         : "); String passengerId  = scanner.nextLine().trim();
        System.out.print("Código del vuelo            : "); String flightCode   = scanner.nextLine().trim();
        System.out.print("Cantidad de asientos (1-5)  : "); int    seats        = readInt();
        System.out.print("Fecha de reserva (DD/MM/YYYY): "); String bookingDate = scanner.nextLine().trim();

        try {
            Booking booking = bookingService.createBooking(passengerId, flightCode, seats, bookingDate);
            System.out.println("\nEXITO: Reserva creada correctamente.");
            System.out.println(booking.generateDetail());

        } catch (IllegalArgumentException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (FlightNotAvailableException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (InsufficientSeatsException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    private void cancelBooking() {
        System.out.print("\nCódigo de la reserva a cancelar: ");
        String code = scanner.nextLine().trim();
        try {
            Booking booking = bookingService.cancelBooking(code);
            System.out.println("\nEXITO: Reserva " + code + " cancelada correctamente.");
            System.out.println("Asientos devueltos al vuelo " + booking.getFlight().getFlightCode()
                    + ": " + booking.getSeatsBooked());
            System.out.println("Asientos disponibles ahora: " + booking.getFlight().getAvailableSeats());

        } catch (BookingNotFoundException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    private void searchBooking() {
        System.out.print("\nCódigo de la reserva: ");
        String code = scanner.nextLine().trim();
        try {
            Booking booking = bookingService.getBookingByCode(code);
            System.out.println(booking.generateDetail());
        } catch (BookingNotFoundException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    // ======================== REPORTS MENU ========================

    private void showReportsMenu() {
        int option;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║      ESTADÍSTICAS Y REPORTES         ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Buscar reserva por código        ║");
            System.out.println("║  2. Total de pasajeros registrados   ║");
            System.out.println("║  3. Reservas por pasajero            ║");
            System.out.println("║  0. Volver                           ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seleccione: ");
            option = readInt();
            switch (option) {
                case 1:
                    System.out.print("Código de la reserva: ");
                    String code = scanner.nextLine().trim();
                    try {
                        System.out.println(bookingService.getBookingByCode(code).generateDetail());
                    } catch (BookingNotFoundException e) {
                        System.out.println("\nERROR: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("\n Total de pasajeros registrados: "
                            + bookingService.getTotalPassengers());
                    break;
                case 3:
                    System.out.print("Cédula del pasajero: ");
                    String id = scanner.nextLine().trim();
                    try {
                        System.out.println(bookingService.getBookingsByPassenger(id));
                    } catch (IllegalArgumentException e) {
                        System.out.println("\nERROR: " + e.getMessage());
                    }
                    break;
                case 0: break;
                default: System.out.println("Opción no válida.");
            }
        } while (option != 0);
    }

    // ======================== INPUT HELPERS ========================

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número entero válido: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido (use punto para decimales): ");
            }
        }
    }
}
