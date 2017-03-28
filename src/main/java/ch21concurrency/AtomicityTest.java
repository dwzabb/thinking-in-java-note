package ch21concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTest implements Runnable{
    private int i = 0;
    public int getValue() {
        return i;
    }

    private synchronized void increment() {
        ++i;
        ++i;
    }

    @Override
    public void run() {
        while(true) {
            increment();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);

        //Thread.sleep(1);
        while(true) {
            int val = at.getValue();
            System.out.println(val);
            if (val % 2 != 0) {
                System.out.println("Not even! " + val);
                System.exit(0);
            }
        }
    }
}/* Output
0
2402
2864
3476
4804
5118
6264
8276
9798
11118
12338
14188
15574
16938
18432
20562
22504
24040
25552
26824
28048
29240
30444
31742
33008
34216
35610
37566
39504
41166
42914
44422
46678
48438
50104
51788
53852
56362
58310
59750
61290
62942
64684
67306
69464
71206
73144
74898
76653
Not even! 76653
*/