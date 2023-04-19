import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadpool {
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100);
    //线程管理列表
    private List<Worker> Wokers = new ArrayList<>();
    private final static int maxWorkerCount = 5;   //线程池最大允许的个数

    //execute方法
    public void execute(Runnable command) throws InterruptedException {
        if(Wokers.size() < maxWorkerCount) {
            //创建一个新的工作线程
            Worker worker = new Worker(queue, Wokers.size());
            worker.start();
            Wokers.add(worker);
        }
        queue.put(command); //添加任务到线程安全的队列中
    }

    //销毁所有线程
    public void shutDown() throws InterruptedException {
        for(Worker worker : Wokers) {
            worker.interrupt();
        }
        //并且让主线程join阻塞等待所有工作线程
        for(Worker worker : Wokers) {
            worker.join();                       //join方法可以让调用的线程处于阻塞状态， 知道等待的线程结束完毕之后就会恢复
        }
        System.out.println("所有工作线程销毁完毕!");
    }
    class Worker extends Thread {
        private BlockingQueue<Runnable> queue = null;
        public Worker(BlockingQueue<Runnable> queue, int id) {
            this.queue = queue;
            Thread.currentThread().setName(id + "号工作线程");
            System.out.println(Thread.currentThread().getName());
        }

        //工作线程执行内容
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable command = queue.take();
                    String dateStr = Long.toString(System.currentTimeMillis() / 1000L);
                    System.out.println(Thread.currentThread().getName() + ":" + dateStr);
                    command.run();
                    Thread.sleep(2000);
                }
            }
            catch(InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "被中止了");
            }
        }
    }
}
