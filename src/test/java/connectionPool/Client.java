package connectionPool;

import com.cesuokanc.connectionPool.pool.IConnectionPool;
import com.cesuokanc.connectionPool.pool.SimpleConnectionPool;
import com.cesuokanc.connectionPool.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * @desc
 * @author lirb
 * @datetime 2017/11/26,17:41
 */
public class Client extends Thread {

    private IConnectionPool pool;
    private Connection connection;

    private ConnectionProvider provider;


    public Client(IConnectionPool connectionPool){
        this.pool = connectionPool;
    }

    public Client(ConnectionProvider provider){
        this.provider = provider;
    }

    @Override
    public void run() {
        try{
            String sql = "insert into tb_test values('小明',20)";
            if(pool!=null){
                connection = pool.getConnection(); //使用池
                Statement statement = connection.createStatement();
                statement.execute(sql);
//                System.out.println(Thread.currentThread().getName()+"插入一条数据");
                pool.releaseConnection(connection);
            }else{
                connection = provider.getConnection();//不使用池
                Statement statement = connection.createStatement();
                statement.execute(sql);
//                System.out.println(Thread.currentThread().getName()+"插入一条数据");
                connection.close();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
