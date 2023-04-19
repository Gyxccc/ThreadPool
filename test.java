import java.util.Scanner;

public class test {
    static class MyRunnable implements Runnable {
        private int num;
        MyRunnable(int num) {
            this.num = num;
        }
        @Override
        public void run() {
//            System.out.println("正在执行任务: " + num);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        MyThreadpool myThreadPool = new MyThreadpool();
        while (true){
            Scanner scan = new Scanner(System.in);
            String tkn = scan.nextLine();
            if ("".equals(tkn)) {
                for (int j=0;j<5;j++) {
                    myThreadPool.execute(new MyRunnable(i));
                    i++;
//                    Thread.sleep(200);
                }
            }else {
                break;
            }
        }
//        Thread.sleep(2000);			//主线程休眠2s
        myThreadPool.shutDown();		//销毁所有工作线程
        System.out.println("线程池已经被销毁了");
    }
}
