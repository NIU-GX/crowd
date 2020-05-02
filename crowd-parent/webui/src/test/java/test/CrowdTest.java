package test;



import com.example.service.AdminService;
import com.example.entity.Admin;
import com.example.util.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author NGX
 * @Date 2020/4/28 18:33
 * @Description
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CrowdTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AdminService adminService;
    @Test
    public void test() throws SQLException {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void test1() {
        Admin admin = new Admin(null,"niu","niu","牛","niu@qq.com","2020/4/29");
        adminService.saveAdmin(admin);
    }

    @Test
    public void test2() {
        String s = MD5Util.md5("admin");
        System.out.println(s);
    }
}
