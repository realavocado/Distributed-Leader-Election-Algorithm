public class test {
    public static void main(String[] args) {
        Ring mainRing = new Ring(100);
        for (int i = 0; i < 10; i++) {
            Ring ring = new Ring(20);
        }
        Ring.giveIdAscendCounterClockwise();
        Ring.generateInterfaceProcessor();
        LCR_Algorithm.globalLCR(0);
        System.out.println();
        System.out.println("Total number of rounds:");
        System.out.println(Ring.getTotalNumOfRounds());
        System.out.println("Total number of messages:");
        System.out.println(Ring.getTotalNumOfMessages());

    }
}
