package board;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// RunWith는 addJunit 눌러주기, 나머지는 다 import 해주기
@RunWith(SpringRunner.class)
@SpringBootTest
class Board1ApplicationTests {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void testSqlSession() throws Exception {
		System.out.println(sqlSession.toString());
	}
}
