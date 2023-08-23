import java.util.*;

public class Main {
    public static final HashMap<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        Runnable threadLogic = () -> {
            String str = generateRoute("RLRFR", 100);
            int countR = countR(str);
            System.out.println("Количество R: " + countR);

            synchronized (sizeToFreq) {
                if (sizeToFreq.containsKey(countR)) {
                    sizeToFreq.put(countR, sizeToFreq.get(countR) + 1);
                } else {
                    sizeToFreq.put(countR, 1);
                }
            }
        };

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(threadLogic);
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }
        mapResult(sizeToFreq);
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countR(String str) {
        int tempCountR = 0;
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) == 'R') {
                tempCountR++;
            }
        }
        return tempCountR;
    }

    public static void mapResult(HashMap map) {
        int maxValue = 0;
        int maxKey = 0;
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        System.out.println("Самое частое количество повторений - " + maxKey + " (встретилось " + maxValue + " раз)");
        System.out.println("Другие размеры: ");

        Iterator<Map.Entry<Integer, Integer>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator2.next();
            if (entry.getKey() == maxKey) {
                iterator2.next();
            } else {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            }
        }
    }
}