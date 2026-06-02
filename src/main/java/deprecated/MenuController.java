//    package deprecated;
//
//    import com.logistics.model.*;
//    import com.logistics.service.*;
//    import org.jspecify.annotations.NonNull;
//    import org.springframework.boot.CommandLineRunner;
//    import org.springframework.context.annotation.Profile;
//    import org.springframework.stereotype.Component;
//
//    import java.util.Comparator;
//    import java.util.List;
//    import java.util.Scanner;
//
//    @Component
//    @Profile("!test")
//    public class MenuController implements CommandLineRunner {
//
//        private final CargoManager cargoManager;
//        private final VehicleManager vehicleManager;
//        private final FinancesManager financesManager;
//        private final InputValidator validator;
//        private final Displayer display;
//        private final Scanner scan;
//        private final Comparator<Vehicle> fleetSorter;
//        private final CargoLoadingService cargoLoadingService;
//        private final CargoDeliveringService  cargoDeliveringService;
//        private final VehicleCreator  vehicleCreator;
//
//        private final UserService userService;
//
//        public MenuController(VehicleManager vehicleManager, CargoManager cargoManager, FinancesManager financesManager, InputValidator validator, Displayer display, Scanner scan, Comparator<Vehicle> fleetSorter, CargoLoadingService cargoLoadingService, CargoDeliveringService cargoDeliveringService, VehicleCreator vehicleCreator, UserService userService) {
//            this.vehicleManager = vehicleManager;
//            this.cargoManager = cargoManager;
//            this.financesManager = financesManager;
//            this.validator = validator;
//            this.display = display;
//            this.scan = scan;
//            this.fleetSorter = fleetSorter;
//            this.cargoLoadingService = cargoLoadingService;
//            this.cargoDeliveringService = cargoDeliveringService;
//            this.vehicleCreator = vehicleCreator;
//            this.userService = userService;
//        }
//
//        @Override
//        public void run(String @NonNull [] args) {
//            System.out.println("""
//                    Welcome in LvivLogistincs.inc™! Here you can manage your logistics company:
//                    create a cargo, choose a vehicle and deliver your parcels all over the world!
//                    Also you can track your expenses and income.
//                    \s""");
//
//            System.out.println("Hello! Please choose an existing user or create a new one:");
//            System.out.println("1. Create a new user.");
//            System.out.println("2. Choose an existing one.");
//
//            int login = validator.inputChoice(1, 2);
//
//            switch(login) {
//                case 1 -> handleUserCreation();
//                case 2 -> handleUserSelection();
//            }
//
//            User activeUser = userService.getCurrentUser();
//            vehicleManager.setFleet(activeUser.getFleet());
//            cargoManager.setWarehouse(activeUser.getWarehouse());
//            financesManager.setFinances(activeUser.getFinances());
//
//            boolean isLasting = true;
//
//            while (isLasting) {
//                System.out.println("""
//                        1. Manage/Edit your garage/fleet;
//                        2. Create a cargo/see cargos in the warehouse;
//                        3. Load a cargo;
//                        4. Deliver a cargo;
//                        5. Finish a delivery;
//                        6. Monitor your finances;
//                        7. Exit;
//                        \s""");
//
//                int option = validator.inputChoice(1, 7);
//                switch (option) {
//                    case 1 -> handleGarageMenu();
//                    case 2 -> handleCargoMenu();
//                    case 3 -> handleLoading();
//                    case 4 -> handleDelivery();
//                    case 5 -> handleFinishDelivery();
//                    case 6 -> handleStatistics();
//                    case 7 -> isLasting = handleProgramEnding();
//                }
//            }
//        }
//
//        protected void handleUserCreation() {
//            System.out.println("\n--- New user ---");
//
//            System.out.println("Enter your name:");
//            String name = scan.next();
//
//            System.out.println("Enter your surname:");
//            String surname = scan.next();
//
//            User newUser = userService.CreateAndSaveUser(name, surname);
//
//            userService.setCurrentUser(newUser);
//
//            System.out.println("The user " + name + " " + surname + " was successfully created!");
//        }
//
//        private void handleUserSelection()
//        {
//            List<User> users = userService.getAllUsers();
//            if(users.isEmpty())
//            {
//                System.out.println("The user database is empty! Please create a new one!");
//                handleUserCreation();
//                return;
//            }
//            else
//            {
//                System.out.println("\n--- Existing users ---");
//                for (int i = 0; i < users.size(); i++) {
//                    User u = users.get(i);
//                    System.out.println((i + 1) + ". " + u.getName() + " " + u.getSurname());
//                }
//
//                System.out.println("Choose the user(1 - " + users.size() + "):");
//                int choice = validator.inputChoice(1, users.size());
//                User selectedUser = users.get(choice - 1);
//
//                userService.setCurrentUser(selectedUser);
//
//                System.out.println("The logging in was executed successfully! Welcome back, " + selectedUser.getName() + "!");
//            }
//        }
//
//        private void handleGarageMenu() {
//            System.out.println("""
//                    1. Add a new transport;
//                    2. Display your fleet;
//                    3. Repair a particular vehicle;
//                    4. Delete all the garage;
//                    5. Delete a particular vehicle;
//                    0. Back;
//                    \s""");
//
//            int option = validator.inputChoice(0, 5);
//            switch (option) {
//                case 1 -> {
//                    System.out.println("1. Tanker Truck; 2. Container Truck; 3. Plane; 4. Train;");
//                    int type = validator.inputChoice(1, 4);
//                    System.out.println("Enter an initial mileage for your vehicle: ");
//                    double mileage = validator.inputChoice(40000, 100000);
//                    vehicleManager.addVehicle(vehicleCreator.create(type, mileage, vehicleManager.getType(type)));
//                    System.out.println("Your vehicle has been successfully added!");
//                }
//                case 2 -> {
//                    if(!vehicleManager.getFleet().isEmpty())
//                    {
//                        vehicleManager.sortFleet(fleetSorter);
//                        display.displayVehicles(vehicleManager.getFleet(), 0, vehicleManager.getFleet().size());
//                    }
//                    else System.out.println("There is nothing to display!");
//                }
//                case 3 -> {
//                        var brokenVehicles = vehicleManager.getFleet().stream()
//                        .filter(Vehicle::isBroken)
//                        .toList();
//
//                        if (!brokenVehicles.isEmpty()) {
//                            int choice = validator.inputChoice(0, brokenVehicles.size() - 1);
//                            Vehicle v = brokenVehicles.get(choice);
//                            Repairing.repair(v);
//                        }
//                        else System.out.println("There is nothing to repair!");
//                    }
//                case 4 ->
//                        {
//                            vehicleManager.getFleet().clear();
//                            System.out.println("The fleet was successfully emptied!");
//                        }
//                case 5 -> {
//                    if(!vehicleManager.getFleet().isEmpty()){
//                        System.out.println("Delete a particular vehicle");
//                        vehicleManager.sortFleet(fleetSorter);
//                        int choice = validator.inputChoice(0, vehicleManager.getFleet().size() - 1);
//                        vehicleManager.getFleet().remove(choice);
//                    }
//                    else System.out.println("There is nothing to delete!");
//                }
//            }
//        }
//
//        private void handleCargoMenu() {
//            System.out.println("1. Create; 2. Display; 3. Clear; 4. Delete; 0. Back;");
//            int option = validator.inputChoice(0, 4);
//            switch (option) {
//                case 1 -> {
//                    System.out.println("Enter length, width, height (in cm):");
//                    Cargo newCargo = new Cargo.Builder()
//                            .setLength(validator.inputForDouble(0.01, 5000))
//                            .setWidth(validator.inputForDouble(0.01, 5000))
//                            .setHeight(validator.inputForDouble(0.01, 5000)).build();
//                    cargoManager.addCargo(newCargo);
//                }
//                case 2 -> {
//                    if(cargoManager.getWarehouse().isEmpty())
//                    {
//                        System.out.println("There is nothing to display!");
//                    }
//                    else display.displayCargos(cargoManager.getWarehouse());
//                }
//                case 3 ->
//                        {
//                            if(!cargoManager.getWarehouse().isEmpty())
//                            {
//                                cargoManager.getWarehouse().clear();
//                                System.out.println("The warehouse was successfully emptied!");
//                            }
//                            else System.out.println("There is nothing to empty out!");
//                        }
//                case 4 -> {
//                    if(cargoManager.getWarehouse().isEmpty()) System.out.println("There is nothing to display!");
//                    else{
//                        int choice = validator.inputChoice(0, cargoManager.getWarehouse().size() - 1);
//                        cargoManager.getWarehouse().remove(choice);
//                    }
//                }
//            }
//        }
//
//        private void handleLoading() {
//            if(vehicleManager.getFleet().isEmpty() && cargoManager.getWarehouse().isEmpty()) System.out.println("Your fleet is empty and you don't have anything in your warehouse!");
//            else if(vehicleManager.getFleet().isEmpty()) System.out.println("Your fleet is empty!");
//            else if( cargoManager.getWarehouse().isEmpty()) System.out.println("You don't have anything in your warehouse!");
//            else{
//                vehicleManager.sortFleet(fleetSorter);
//                int finalIndex = vehicleManager.getFleet().size();
//                display.displayVehicles(vehicleManager.getFleet(), 0, finalIndex);
//                System.out.println("-1. Back");
//                int carChoice = validator.inputChoice(-1, finalIndex - 1);
//                if (carChoice != -1) {
//                    display.displayCargos(cargoManager.getWarehouse());
//                    System.out.println("-1. Back");
//                    int cargoChoice = validator.inputChoice(-1, cargoManager.getWarehouse().size() - 1);
//                    if (cargoChoice != -1) {
//                        Vehicle car = vehicleManager.getFleet().get(carChoice);
//                        Cargo cargo = cargoManager.getWarehouse().get(cargoChoice);
//                        if (cargoLoadingService.canFit(car, cargo.getVolume())) {
//                            cargoLoadingService.load(car, cargo.getVolume());
//                            cargoManager.getWarehouse().remove(cargo);
//                        }
//                        else System.out.println("""
//                                Sorry, your cargo can't be loaded because it's
//                                too large or because your vehicle is too small!
//                                \s""");
//                    }
//                }
//            }
//        }
//
//        private void handleDelivery() {
//            vehicleManager.sortFleet(fleetSorter);
//            List<Vehicle> loadedVehicles = vehicleManager.getFleet().stream()
//                    .filter(Vehicle::isLoaded)
//                    .toList();
//            if(!loadedVehicles.isEmpty())
//            {
//                display.displayVehicles(loadedVehicles, 0, loadedVehicles.size());
//                System.out.println("-1. Back");
//                int carChoice = validator.inputChoice(-1, loadedVehicles.size() - 1);
//                if(carChoice != -1) {
//                    var car =  vehicleManager.getFleet().get(carChoice);
//                    System.out.println("Enter the distance of delivery");
//                    double dist = validator.inputForDouble(1.0d, 12000.0d);
//                if(cargoDeliveringService.canMakeDelivery(car, dist))
//                {
//                    cargoDeliveringService.makeDelivery(car, dist);
//                    financesManager.getFinances().addToMoneyToBeEarned(dist, car.getCurrentlyLoaded(), car.getId());
//                    System.out.println("The delivery was successfully made!");
//                }
//                else System.out.println("The car can't make a delivery!");
//                }
//            }
//            else System.out.println("Sorry, you don't have any loaded vehicle!");
//        }
//
//        private void handleFinishDelivery() {
//
//            List<Vehicle> busyVehicles = vehicleManager.getFleet().stream()
//                    .filter(Vehicle::isBusy)
//                    .toList();
//
//            if (busyVehicles.isEmpty()) {
//                System.out.println("There is no active deliveries!");
//                return;
//            }
//
//            display.displayVehicles(busyVehicles, 0, busyVehicles.size());
//            System.out.println("-1. Back");
//            int choice = validator.inputChoice(-1, busyVehicles.size() - 1);
//
//            if (choice != -1) {
//                Vehicle v = busyVehicles.get(choice);
//                cargoDeliveringService.finishDelivery(v);
//                financesManager.getFinances().addToEarnings(v.getId());
//                financesManager.getFinances().addSuccessfulDelivery();
//
//                System.out.println("Delivery finished successfully!");
//            }
//        }
//
//        private void handleStatistics()
//        {
//            System.out.println("Your total income: " + financesManager.getFinances().getEarnings() +
//                    "\nNumber of successful deliveries: " + financesManager.getFinances().getNumberOfSuccessfulDeliveries() + "\n");
//        }
//
//        private boolean handleProgramEnding()
//        {
//            System.out.println("Exiting...");
//            userService.saveProgress();
//            return false;
//        }
//    }