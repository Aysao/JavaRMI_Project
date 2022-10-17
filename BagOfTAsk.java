import java.util.ArrayDeque;
import java.util.Queue;

public interface BagOfTAsk extends Remote{
    private Queue<ITask> queue;

    public BagOfTask(int N)
    {
        queue = new ArrayDeque<ITask>();
    }

    public ITask getNext()
    {
        currentN++;
        return new Task(currentN);
    }

    public void sendResult(ITask t)
    {
        Task task = (Task)t;
        tab[task.getNumber()] = task.isPrimary();
    }

    public int getSize()
    {
        return tab.length;
    }

    public boolean[] getTab(){
        return tab;
    }
}