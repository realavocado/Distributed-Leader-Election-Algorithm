import java.util.Scanner;

public class RingOfRingsDemo {
    public static void main(String[] args) {
        while (true) {
            Ring.totalRing.clear();
            Ring.interfaceList.clear();
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("                       Welcome to Ring of rings LCR-Algorithm Demo.");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("To start this demo, you have to construct a Ring-of-rings model.");
            System.out.println("It means you have to decide the number of rings and the number of processors in each ring.");
            System.out.println("Notice：The amount of interface processors is the amount of total rings minus 1.");
            System.out.println();
            System.out.println("Now enter the total amount of rings (include main ring).");
            System.out.println("(If want to exit, just enter 0.)");
            int num = -1;
            while (true) {
                Scanner sc = new Scanner(System.in);
                if (sc.hasNextInt()) {
                    num = sc.nextInt();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a correct number.");
                }
            }

            if (num == 0) {
                break;
            }
            for (int i = 0; i < num; i++) {
                System.out.println();
                if (i == 0) {
                    System.out.println("Enter the amount of processors in the main ring.");
                } else {
                    ;
                    System.out.println("Enter the amount of processors in the sub-ring " + i + ":");
                }
                int amount = -1;
                while (true) {
                    Scanner sc2 = new Scanner(System.in);
                    if (i == 0) {
                        if (sc2.hasNextInt()) {
                            amount = sc2.nextInt();
                            if (amount > num - 1) {
                                break;
                            } else {
                                System.out.println("The size of main ring must be larger than " + (num - 1) + ". Please enter correctly.");
                            }
                        }else {
                            System.out.println("Invalid input. Please enter a correct number.");
                        }
                    } else {
                        if (sc2.hasNextInt()) {
                            amount = sc2.nextInt();
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter a correct number.");
                        }
                    }
                }
                Ring ring = new Ring(amount);
            }
            System.out.println();
            System.out.println("Now decide the type of ID distribution over rings");
            System.out.println("(The message transmitting direction is clockwise)");
            System.out.println("Enter 1: random distribution");
            System.out.println("Enter 2: Ascend clockwise distribution");
            System.out.println("Enter 3: Ascend counterclockwise distribution");

            int select = -1;
            while (true) {
                Scanner sc3 = new Scanner(System.in);
                if (sc3.hasNextInt()) {
                    select = sc3.nextInt();
                    if (select > 0 && select < 4) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a correct number.");
                    }
                }
            }
            switch (select) {
                case 1:
                    Ring.giveIdOverRings();
                    System.out.println();
                    System.out.println("Random distribution finished");
                    break;
                case 2:
                    Ring.giveIdAscendClockwise();
                    System.out.println();
                    System.out.println("Ascend clockwise distribution finished");
                    break;
                case 3:
                    Ring.giveIdAscendCounterClockwise();
                    System.out.println();
                    System.out.println("Ascend counterclockwise distribution finished");
                    break;
                default:
                    break;
            }
            System.out.println("All rings you built will be showed below: press 'Enter' to continue");
            Scanner sc3 = new Scanner(System.in);
            sc3.nextLine();

            Ring.generateInterfaceProcessor();
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------------");

            for (int i = 0; i < Ring.totalRing.size(); i++) {
                Ring ring = (Ring) Ring.totalRing.get(i);
                if (i == 0) {
                    System.out.println();
                    System.out.println("Main ring:");
                } else {
                    System.out.println("");
                    System.out.println("Sub-ring" + i + ":");
                }
                System.out.println();
                ring.list();
                System.out.println();
            }
            System.out.println();
            System.out.println("Press 'Enter' to continue'");
            Scanner sc4 = new Scanner(System.in);
            sc4.nextLine();

            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("Now select which type of result of LCR-Algorithm you want to see:");
            System.out.println("Enter 1: show the whole detailed procedure of every round.");
            System.out.println("Enter 2: show the final result directly.");
            //int key = scanner.nextInt();
            int key = -1;
            while (true) {
                Scanner sc5 = new Scanner(System.in);
                if (sc5.hasNextInt()) {
                    key = sc5.nextInt();
                    if (key > 0 && key < 3) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a correct number.");
                    }
                }
            }
            System.out.println();
            switch (key) {
                case 1:
                    LCR_Algorithm.globalLCR(1);
                    break;
                case 2:
                    LCR_Algorithm.globalLCR(0);
                    break;
                default:
                    break;
            }
            System.out.println();
            System.out.println("Total number of rounds:");
            System.out.println(Ring.getTotalNumOfRounds());
            System.out.println("Total number of messages:");
            System.out.println(Ring.getTotalNumOfMessages());
            System.out.println();
            System.out.println("----------------------------------");
            System.out.println("Demo finished.");
            System.out.println("----------------------------------");
            System.out.println();
        }


    }
}
