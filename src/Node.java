public class Node {
    public int index;
    public int uniqueID;
    public int sendID;
    public int sendIDNextRound;
    public int awakeRound;
    public int known_elected_id;
    public Ring linkedRing;
    public Node next;
    Status status;
    Progress progress;

    enum Status {
        unknown, LEADER;
    }

    enum Progress {
        awake, sleeping;
    }

    public Node(int index, int uniqueID) {
        this.index = index;
        this.uniqueID = uniqueID;
        this.sendID = uniqueID;
        this.sendIDNextRound = uniqueID;
        this.awakeRound = 1;
        this.status = Status.unknown;
        this.progress = Progress.awake;
    }

    public void assignValue(int num) {
        this.uniqueID = num;
        this.sendID = num;
        this.sendIDNextRound = num;
    }

    public void isAwake(int round) {
        if (awakeRound == round) {
            progress = Progress.awake;
        }
    }

    public String toString() {
        return "Processor [index = " + index + " , uniqueID = " + uniqueID + " , sendID = " + sendID + " , status = " + status + " , progress = " + progress + "]";
        //return "Processor [index = " + index + " , uniqueID = " + uniqueID + " , sendID = " + sendID + " , sendIDNextRound = " + sendIDNextRound + " , status = " + status + " , progress = " + progress + "]";
    }
}

