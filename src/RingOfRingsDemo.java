public class RingOfRingsDemo {
    public static void main(String[] args) {
        Ring ring1 = new Ring(20);
        //ring1.buildRing(20);
        Ring ring2 = new Ring(23);
        //ring2.buildRing(5);
        Ring ring3 = new Ring(8);
        //ring3.buildRing(8);
        Ring ring4 = new Ring(12);
        //ring4.buildRing(7);
        Ring.giveIdOverRings();
        Ring.generateInterfaceProcessor();
        System.out.println("Total number of rings:");
        System.out.println(Ring.totalRing.size());
        System.out.println("Total number of processors of all rings");
        System.out.println(Ring.getTotalNumOfProcessor());
        System.out.println("After getting unique ID, show all:");
        System.out.println();
        System.out.println("Main ring:");
        ring1.list();
        System.out.println();
        //Ring.globalLCR();
        System.out.println("Ring2:");
        ring2.list();
        System.out.println();
        System.out.println("Ring3:");
        ring3.list();
        System.out.println();
        System.out.println("Ring4");
        ring4.list();
        //LCR
        System.out.println();
        LCR_Algorithm.globalLCR();
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("The main ring after LCR:");
        ring1.list();
        /*System.out.println();
        System.out.println("Sub-networks:");
        System.out.println("Ring2:");
        ring2.list();
        System.out.println("Ring3:");
        ring3.list();
        System.out.println("Ring4");
        ring4.list();
        System.out.println();
        System.out.println("Main ring leader:");
        System.out.println(ring1.getLeader());
        System.out.println();
        System.out.println("Total number of rounds:");
        System.out.println(Ring.getTotalNumOfRounds());
        System.out.println("Total number of messages:");
        System.out.println(Ring.getTotalNumOfMessages());*/
    }
}
