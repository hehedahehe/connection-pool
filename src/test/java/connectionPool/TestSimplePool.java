package connectionPool;

import com.cesuokanc.connectionPool.pool.IConnectionPool;
import com.cesuokanc.connectionPool.pool.SimpleConnectionPool;
import com.cesuokanc.connectionPool.util.ConnectionProvider;
import org.junit.Test;


public class TestSimplePool {
    private int poolSize = 100;
    private int clientNum = 1000;

    private IConnectionPool connectionPool = new SimpleConnectionPool(poolSize);
    private ConnectionProvider provider = new ConnectionProvider();


    @Test
    public void basicTest(){

        Client[] clients = new Client[clientNum];
        long beginTime = System.currentTimeMillis();
        for(int i=0;i<clientNum;i++){
            clients[i]  = new Client(connectionPool);
            clients[i] .start();
        }
        for(int i=0;i<clientNum;i++){
            try {
                clients[i].join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        connectionPool.close();
        long endTime = System.currentTimeMillis();
        System.out.println("【使用】连接池总耗时："+(endTime-beginTime)+"ms");


    }


    @Test
    public void basicTestWithoutPool(){
        long beginTime = System.currentTimeMillis();
        Client[] clients1 = new Client[clientNum];
        for(int i=0;i<clientNum;i++){
            clients1[i]  = new Client(provider);
            clients1[i] .start();
        }
        for(int i=0;i<clientNum;i++){
            try {
                clients1[i].join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("【未使用】连接池总耗时："+(endTime-beginTime)+"ms");
    }


}
