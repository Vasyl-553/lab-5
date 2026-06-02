//package deprecated;
//
//import java.util.List;
//import com.logistics.model.*;
//import org.springframework.stereotype.Service;
//
//@Service
//public class Displayer {
//    public void displayVehicles(List<Vehicle> vehicles, int indexStart, int indexEnd)
//    {
//        if(vehicles.isEmpty()) {
//            System.out.println("There is nothing to display!");
//            return;
//        }
//        String format = "%-5s %-15s %-10s %-10s %-10s %-10s %-10s%n";
//        String format_1 = "%-15s %-10s %-10s %-10s %-10s %-10s%n";
//        System.out.printf(format, "№", "Type", "isLoaded",  "isBroken", "isBusy", "Mileage", "FreeSpace");
//        for (int i = indexStart; i < indexEnd; i++) {
//            String[] info = vehicles.get(i).getInfo();
//            System.out.printf("%-5s ", i + ". ");
//            System.out.printf(format_1, (Object[]) info);
//        }
//    }
//
//    public void displayCargos(List<Cargo> warehouse)
//    {
//        if(warehouse.isEmpty()) {
//            System.out.println("There is nothing to display!");
//            return;
//        }
//        int index = 0;
//        System.out.printf("%-5s %-10s%n", "№", "Volume");
//        for (Cargo crg : warehouse) {
//            System.out.printf("%-5s %-10s%n", index + ". ", crg.getVolume());
//            index++;
//        }
//    }
//}
