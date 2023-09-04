
public class VotingPoll {
   private String Car;
   private int Votes;

    public VotingPoll() {
    }

    public VotingPoll(String Car, int Votes) {
        this.Car = Car;
        this.Votes = Votes;
    }

    public String getCar() {
        return Car;
    }

    public void setCar(String Car) {
        this.Car = Car;
    }

    public int getVotes() {
        return Votes;
    }

    public void setVotes(int Votes) {
        this.Votes = Votes;
    }
    public void VotesIncrease(){
    this.Votes++;
    }

    @Override
    public String toString() {
        return "VotingPoll{" + "Car=" + Car + ", Votes=" + Votes + '}';
    }
    
}
