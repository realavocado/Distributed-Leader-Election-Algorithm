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
                //Random r = new Random();
                int round = r.nextInt(amount / 3) + 1;
                processor.awakeRound = round;
            }
        }
    }

    //async-LCR  Choice A: display the final result. Choice B: display the process of every round
    //num == 1: show the whole procedure; num == 0: just show the final result
    public static void asyncLCR(Ring ring, int num) {
        //LCR_Algorithm.decideAsyncNodesAndRounds(ring);
        boolean flag = true;
        int numOfRounds = ring.getNumOfRounds();
        int numOfMessages = ring.getNumOfMessages();
        while (flag) {
            //a new round starts
            for (int i = 0; i < ring.processorList.size(); i++) {
                Node processor = (Node) ring.processorList.get(i);
                processor.isAwake(numOfRounds); //check in the new round whether there are any new processors waking up
                processor.sendID = processor.sendIDNextRound; //update the sendID
            }
            //show process of every round
            if (num == 1) {
                System.out.println("Round " + numOfRounds);
                for (int i = 0; i < Ring.interfaceList.size(); i++) {
                    Node interfaceProcessor = (Node) Ring.interfaceList.get(i);
                    //if this ring is the main ring and an interface node wakes up at this round
                    //tell the user it wakes up
                    if (numOfRounds == interfaceProcessor.awakeRound && ring.processorList.contains(interfaceProcessor)) {
                        //at the waking up round of interface, give it the uniqueID of its sub-ring
                        interfaceProcessor.assignValue(interfaceProcessor.linkedRing.getLeader().uniqueID);
                        System.out.println();
                        System.out.println("Interface processor with uniqueID " + interfaceProcessor.uniqueID + " becomes AWAKE at this round.");
                        System.out.println();
                    }
                }
                //show the result after this round
                ring.list();
                System.out.println();
            }
            numOfRounds++;
            ring.setNumOfRounds(numOfRounds);
            //procedure of sending messages in a round
            for (int i = 0; i < ring.processorList.size(); i++) {
                Node processor = (Node) ring.processorList.get(i);
                //if there's a processor asleep, then no message via this processor, it receives a message but the message is lost
                if (processor.progress == Node.Progress.awake && processor.next.progress == Node.Progress.sleeping && processor.sendID > processor.next.sendID) {
                    numOfMessages++;
                    ring.setNumOfMessages(numOfMessages);
                }
                //if current processor and the next processor are both awake, then transmit messages normally
                if (processor.progress == Node.Progress.awake && processor.next.progress == Node.Progress.awake) {
                    if (processor.sendID > processor.next.uniqueID) {
                        //if sendID can pass, then there's a transmission of message
                        numOfMessages++;
                        ring.setNumOfMessages(numOfMessages);
                        processor.next.sendIDNextRound = processor.sendID;
                    }
                    if (processor.sendID == processor.next.uniqueID) {
                        ring.setLeader(processor.next);
                        //got a leader in the subRing, then told interface processor
                        if (ring.getLinkNode() != null) {
                            ring.getLinkNode().awakeRound = numOfRounds + 1;
                            //share leaderID with interface processor
                            //ring.getLinkNode().assignValue(ring.getLeader().uniqueID);
                        }
                        //show result of final round
                        //if (num == 1) {
                        System.out.println("Round " + numOfRounds);
                        System.out.println();
                        System.out.println("The processor with unique ID " + ring.getLeader().uniqueID + " has claimed itself leader!");
                        System.out.println();
                        ring.list();
                        System.out.println();
                        //}
                        flag = false;
                    }
                }
            }
        }
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

    //Leader-Election for Rings of Rings
    public static void globalLCR(int num) {
        for (int i = 1; i < Ring.totalRing.size(); i++) {
            Ring ring = (Ring) Ring.totalRing.get(i);
            //ring.LCR();
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            int index = i + 1;
            System.out.println("[ The Procedure of sub ring " + index + "]");
            System.out.println();
            LCR_Algorithm.asyncLCR(ring, num);
            //ring.getLinkNode().assignValue(ring.getLeader().uniqueID);
        }
        Ring mainRing = (Ring) Ring.totalRing.get(0);
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("[ The Procedure of main ring. ]");
        LCR_Algorithm.asyncLCR(mainRing, num);
    }

    public static void main(String[] args) {
        Ring ring = new Ring(15);
        Ring.giveIdAscendClockwise();
        decideAsyncNodesAndRounds(ring);
        asyncLCR(ring, 1);
    }
}
