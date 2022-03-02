import java.util.Random;

public class LCR_Algorithm {

    //select asynchronous processors and their awake rounds
    public static void decideAsyncNodesAndRounds(Ring ring) {
        int amount = ring.processorList.size();
        Random r = new Random();
        int num = r.nextInt(amount - 1) + 1;
        ring.generateRandomNumber(num, 1, amount); //randomly attribute
        for (int i = 0; i < ring.processorList.size(); i++) {
            Node processor = (Node) ring.processorList.get(i);
            if (ring.randomNumberList.contains(processor.index)) {
                processor.progress = Node.Progress.sleeping;
                int round = r.nextInt(3 * amount / 4) + 1;
                processor.awakeRound = round;
            }
        }
    }


    /**
     * LCR algorithm. Applicable to both synchronous and asynchronous condition.
     *
     * @param ring the ring goes through LCR
     * @param num  num == 1: show the whole procedure; num == 0: just show the final result
     */
    public static void LCR(Ring ring, int num) {
        //LCR_Algorithm.decideAsyncNodesAndRounds(ring);
        boolean flag = true;
        int numOfRounds = ring.getNumOfRounds();
        int numOfMessages = ring.getNumOfMessages();
        //every processor will transmit message at least one time
        numOfMessages = numOfMessages + ring.getProcessorAmount(ring.getInitNode()) - 1;
        ring.setNumOfMessages(numOfMessages);
        while (flag) {
            //show process of every round
            if (num == 1) {
                if (numOfRounds == 0) {
                    System.out.println("Initial state:");
                } else {
                    System.out.println("Round " + numOfRounds);
                }
                for (int i = 0; i < Ring.interfaceList.size(); i++) {
                    Node interfaceProcessor = (Node) Ring.interfaceList.get(i);
                    //if this ring is the main ring and an interface node wakes up at this round
                    if (numOfRounds == interfaceProcessor.awakeRound && ring.processorList.contains(interfaceProcessor)) {
                        //at the waking up round of interface, give it the uniqueID of its sub-ring
                        interfaceProcessor.assignValue(interfaceProcessor.linkedRing.getLeader().uniqueID);
                        System.out.println();
                        //tell the user which interface wakes up
                        System.out.println("Interface processor with index " + interfaceProcessor.index + " becomes AWAKE at this round.");
                        System.out.println("The maximum ID it receives from its sub-ring is " + interfaceProcessor.uniqueID + ", which is its unique ID now.");
                        System.out.println();
                    }
                }
                //show the result after this round
                ring.list();
                System.out.println();
            } /*else {
                for (int i = 0; i < Ring.interfaceList.size(); i++) {
                    Node interfaceProcessor = (Node) Ring.interfaceList.get(i);
                    //if this ring is the main ring and an interface node wakes up at this round
                    if (numOfRounds == interfaceProcessor.awakeRound && ring.processorList.contains(interfaceProcessor)) {
                        //at the waking up round of interface, give it the uniqueID of its sub-ring
                        interfaceProcessor.assignValue(interfaceProcessor.linkedRing.getLeader().uniqueID);
                    }
                }
            }*/
            //a new round starts
            numOfRounds++;
            ring.setNumOfRounds(numOfRounds);
            for (int i = 0; i < ring.processorList.size(); i++) {
                Node processor = (Node) ring.processorList.get(i);
                processor.isAwake(numOfRounds); //check in the new round whether there are any new processors waking up
                processor.sendID = processor.sendIDNextRound; //update the sendID
            }
            for (int i = 0; i < Ring.interfaceList.size(); i++) {
                Node interfaceProcessor = (Node) Ring.interfaceList.get(i);
                //if this ring is the main ring and an interface node wakes up at this round
                if (numOfRounds == interfaceProcessor.awakeRound && ring.processorList.contains(interfaceProcessor)) {
                    //at the waking up round of interface, give it the uniqueID of its sub-ring
                    interfaceProcessor.assignValue(interfaceProcessor.linkedRing.getLeader().uniqueID);
                }
            }
            //procedure of sending messages in a round
            for (int i = 0; i < ring.processorList.size(); i++) {
                Node processor = (Node) ring.processorList.get(i);
                //if there's a processor asleep, then no message via this processor, it receives a message but the message is lost
                if (processor.progress == Node.Progress.awake && processor.next.progress == Node.Progress.sleeping) {
                    numOfMessages++;
                    ring.setNumOfMessages(numOfMessages);
                }
                //if current processor and the next processor are both awake, then transmit messages normally
                if (processor.progress == Node.Progress.awake && processor.next.progress == Node.Progress.awake) {
                    if (processor.sendID > processor.next.sendID) {
                        //if sendID can pass, then there's a transmission of message
                        numOfMessages++;
                        ring.setNumOfMessages(numOfMessages);
                        processor.next.sendIDNextRound = processor.sendID;
                    }
                    if (processor.sendID == processor.next.uniqueID) {
                        numOfMessages++;
                        ring.setNumOfMessages(numOfMessages);
                        ring.setLeader(processor.next);
                        ring.updateStatus();
                        //got a leader in the subRing, then told interface processor
                        if (ring.getLinkNode() != null) {
                            ring.getLinkNode().awakeRound = numOfRounds + 1;
                            //share leaderID with interface processor
                        }
                        //show result of final round
                        System.out.println("Round " + numOfRounds);
                        System.out.println();
                        System.out.println("The processor with unique ID " + ring.getLeader().uniqueID + " has claimed itself leader!");
                        System.out.println();
                        ring.list();
                        System.out.println();
                        flag = false;
                    }
                }
            }
        }
    }

    //Leader-Election for Rings of Rings
    public static void globalLCR(int num) {
        for (int i = 1; i < Ring.totalRing.size(); i++) {
            Ring ring = (Ring) Ring.totalRing.get(i);
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("[ The Procedure of sub ring " + i + "]");
            System.out.println();
            LCR_Algorithm.LCR(ring, num);
            //ring.getLinkNode().assignValue(ring.getLeader().uniqueID);
        }
        Ring mainRing = (Ring) Ring.totalRing.get(0);
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("[ The Procedure of main ring. ]");
        LCR_Algorithm.LCR(mainRing, num);
        System.out.println("======== The 'Leader' ID of all rings is " + mainRing.getLeader().uniqueID +" ========");
    }

    //LCR algorithm in a ring (synchronous)
    //temporarily useless
    public static void LCR(Ring ring) {
        boolean flag = true;
        int numOfRounds = ring.getNumOfRounds();
        int numOfMessages = ring.getNumOfMessages();
        while (flag) {
            //a new round starts
            numOfRounds++;
            ring.setNumOfRounds(numOfRounds);
            for (int i = 0; i < ring.processorList.size(); i++) {
                Node processor = (Node) ring.processorList.get(i);
                processor.sendID = processor.sendIDNextRound;
            }
            for (int i = 0; i < ring.processorList.size(); i++) {
                Node processor = (Node) ring.processorList.get(i);
                if (processor.sendID > processor.next.uniqueID) {
                    //if sendID can pass, then there's a transmission of message
                    numOfMessages++;
                    ring.setNumOfMessages(numOfMessages);
                    processor.next.sendIDNextRound = processor.sendID;
                }
                if (processor.sendID == processor.next.uniqueID) {
                    ring.setLeader(processor.next);
                    flag = false;
                }
            }
        }
    }
}
