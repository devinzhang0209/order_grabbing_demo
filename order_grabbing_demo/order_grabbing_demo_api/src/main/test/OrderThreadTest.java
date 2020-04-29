
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Devin Zhang
 * @className Mythread
 * @description TODO
 * @date 2020/4/20 14:02
 */

public class OrderThreadTest implements Runnable {
    @Override
    public void run() {
        try {
            httpURLGETCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void httpURLGETCase() {
        String userId = UUID.randomUUID().toString().replaceAll("-", "");
        String methodUrl = "http://192.168.0.91:7999/order/addOrder?userId=" + userId;

        System.out.println("开始访问:" + methodUrl);

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String line = null;
        try {
            URL url = new URL(methodUrl);
            connection = (HttpURLConnection) url.openConnection();
            // 根据URL生成HttpURLConnection
            connection.setRequestMethod("GET");
            // 默认GET请求
            connection.connect();
            // 建立TCP连接
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                // 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));
                    // "\n"
                }
                System.out.println("结果" + result.toString());
                if (result.toString().contains("没货了")) {
                    long endTine = System.currentTimeMillis();
                    long useTime = endTine - beginTime;
                    //共耗时:102041毫秒
                    //共耗时:82159毫秒
                    System.out.println("共耗时:" + useTime + "毫秒");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
    }

    static long beginTime;

    public static void main(String[] args) {

        beginTime = System.currentTimeMillis();

        ExecutorService es = Executors.newFixedThreadPool(10000);
        OrderThreadTest mythread = new OrderThreadTest();
        Thread thread = new Thread(mythread);
        for (int i = 0; i < 1000001; i++) {
            es.execute(thread);
        }
    }
}
