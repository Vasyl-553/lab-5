//package deprecated;
//
//import org.springframework.stereotype.Service;
//
//import java.util.Scanner;
//
//@Service
//public class InputValidator {
//
//    private final Scanner scan;
//    public InputValidator(Scanner scan)
//    {
//        this.scan = scan;
//    }
//
//    public int inputChoice(int min, int max)
//    {
//        boolean flag = true;
//        int choice = 0;
//        while(flag)
//        {
//            System.out.println("Enter your choice in range of [" + min + ", " + max + "]: ");
//
//            if(scan.hasNextInt())
//            {
//                choice = scan.nextInt();
//                if(choice >= min && choice <= max)
//                {
//                    flag = false;
//                }
//                else
//                {
//                    System.out.println("Enter a number in a given range!");
//                }
//            }
//            else
//            {
//                System.out.println("Enter a number!");
//                scan.next();
//                continue;
//            }
//        }
//        return choice;
//    }
//
//    public double setMileage(double maxLimit, String transportName)
//    {
//        double initMileage;
//        while(true)
//        {
//            System.out.println("Enter the initial mileage of your " + transportName + ": ");
//            if(scan.hasNextDouble())
//            {
//                initMileage = scan.nextDouble();
//                if(initMileage >= 0 && initMileage <= maxLimit)
//                {
//                    System.out.println("The initial mileage for your \"" + transportName + "\" was added successfully!\n");
//                    break;
//                }
//                else
//                {
//                    System.out.println("Enter a number in range of [0 - " + maxLimit + "]\n");
//                }
//            }
//            else
//            {
//                System.out.println("Enter a number, not a word or smth else!\n");
//                scan.next();
//            }
//        }
//
//        return initMileage;
//    }
//
//
//    public double inputForDouble(double min, double max)
//    {
//        boolean flag = true;
//        double choice = 0;
//        while(flag)
//        {
//            System.out.println("Enter a number in range of [" + min + ", " + max + "]: ");
//
//            if(scan.hasNextDouble())
//            {
//                choice = scan.nextDouble();
//                if(choice >= min && choice <= max)
//                {
//                    flag = false;
//                }
//                else
//                {
//                    System.out.println("Enter a number in a given range!");
//                }
//            }
//            else
//            {
//                System.out.println("Enter a number!");
//                scan.next();
//                continue;
//            }
//        }
//        return choice;
//    }
//}
//
