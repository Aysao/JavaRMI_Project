public class Task implements ITask{
    private boolean result;
    private int number;

    public Task(int number) 
    {
        this.number = number;
    }

    public boolean isPrimary() { return this.result;}
    
    public int getNumber() { return this.number;}

    public void run()
    {
        boolean running = true;
        int divider = 2;
        while (running)
        {
            if (this.number % divider == 0)
            {
                this.result = false;
                running = false;
            }
            else
            {
                if (divider == 9)
                {
                    divider++;
                }
                else
                {
                    running = false;
                }
            }
        }
    }
}
