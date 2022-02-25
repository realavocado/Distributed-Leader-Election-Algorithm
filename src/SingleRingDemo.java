import java.util.Scanner;

public class SingleRingDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            Ring.totalRing.clear();
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("                   Welcome to Single Ring Asynchronous LCR-Algorithm Demo.");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("To start this demo, you have to construct a Ring model.");
            System.out.println("In this demo, some processors in the ring will be asleep until some certain rounds.");
            System.out.println("The wake-up round for those processors are randomly decided.");
            System.out.println("What you have to do is to decide the amount of processors in the ring..");
            System.out.println();
            System.out.println("Now enter the total amount of processors.");
            System.out.println("(If want to exit, just enter 0.)");
            int num = 0;
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

            Ring ring = new Ring(num);
            Ring.giveIdOverRings();
            LCR_Algorithm.decideAsyncNodesAndRounds(ring);
            System.out.println();
            System.out.println("The ring initialized will be showed below (press 'Enter' to continue)");
            scanner.nextLine();
            ring.list();
            System.out.println();
            System.out.println("Press 'Enter' to continue");
            scanner.nextLine();

            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("Now select which type of result of LCR-Algorithm you want to see:");
            System.out.println("Enter 1: show the whole detailed procedure of every round.");
            System.out.println("Enter 2: show the final result directly.");
            int key = -1;
            while (true) {
                Scanner sc2 = new Scanner(System.in);
                if (sc2.hasNextInt()) {
                    key = sc2.nextInt();
                    if (key > 0 && key < 3) {
                        break;
                    }else {
                        System.out.println("Invalid input. Please enter a correct number.");
                    }
                }
            }
            System.out.println();
            switch (key) {
                case 1:
                    LCR_Algorithm.asyncLCR(ring,1);
                    break;
                case 2:
                    LCR_Algorithm.asyncLCR(ring,0);
                    break;
                default:
                    break;
            }
            System.out.println();
            System.out.println("Total number of rounds:");
            System.out.println(ring.getNumOfRounds());
            System.out.println("Total number of messages:");
            System.out.println(ring.getNumOfMessages());
            System.out.println();
            System.out.println("----------------------------------");
            System.out.println("Demo finished.");
            System.out.println("----------------------------------");
            System.out.println();

        }
    }
}
