import java.util.ArrayDeque;
import java.util.Queue;
import java.rmi.*;

public class BagOfTask{
    private Queue<ITask> queue;
    private int currentN = 0;
    private boolean[] tab;

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
    }

    public int getSize()
    {
        return tab.length;
    }

    public boolean[] getTab(){
        return tab;
    }
}