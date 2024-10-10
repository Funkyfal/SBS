package Test.sbs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
public class ConnectionTest {
    @Autowired
    private DataSource dataSource;
    @Test
    void FirstTest(){
        try{
            dataSource.getConnection();
            System.out.println("yes");
        }
        catch (Exception e)
        {
                System.out.println("no");
        }
    }
}
