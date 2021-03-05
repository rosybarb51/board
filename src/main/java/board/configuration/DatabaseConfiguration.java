package board.configuration;

// 아래의 것들 import 해주기
import javax.sql.DataSource;

// ibatis와 mybatis는 사실 똑같은 것이다, 신경 안 써도 된다. 오픈 소스라서 중간에 이름이 바꼈다가 다시 돌아와서... 동일한 것을 뜻하는 것이기 때문에 크게 신경 안 써도 됨.
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// 어노테이션 선언 후 오류 밑 줄 눌러서 import 해주기
@Configuration
// 아래는 데이터베이스 정보 넣어놓은 파일
// 설정파일의 위치를 설정하는 어노테이션
// @PropertySource 어노테이션을 여러 개 사용 시 설정파일을 여러 개 지정할 수 있음. 우리는 지금 하나밖에 없어서 하나만 넣은 것임.
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {
	
//	마이바티스를 사용하기 위함
	@Autowired
	private ApplicationContext applicationContext;
	
//	히카리 CP를 사용하여 데이터 베이스와 연결하기 위한 부분
	
//	자바빈을 의미함. 자바 클래스이다. 말하는 것임
	@Bean
//	@ConfigurationProperties 어노테이션의 prefix가 지정하고 있는 datasource를 사용한다는 의미. (dbConn.jsp 파일에서..?) 아래는 spring.datasource.hikari 라고 되어 있는 놈을 쓰겠다는 의미임.
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
//	DataSource 는 import 할 때 javax.sql 로 임폴트 해주기
	public DataSource dataSource() throws Exception {
//		앞에서 만든 히카리CP 설정을 이용하여 데이터베이스와 연결하는 데이터 소스를 생성
//		위에서 만든 hikariConfig() 메소드를 실행해서 결과를 dataSource 라는 곳에 저장해서 실제로 정상적으로 동작하는지 확인하기 위해서 toString() 이라는 곳에 저장해서 화면에 출력해서 확인한다. 
//		jsp의 connection 객체를 생성한 것과 같음(DataSource)
		DataSource dataSource = new HikariDataSource(hikariConfig());
//		히카리CP 설정으로 데이터베이스와 정상적으로 연결되었는지 화면에 출력
		System.out.println(dataSource.toString());
		return dataSource;
	}

//	mybati 를 사용하기 위한 설정
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		sqlSessionFactoryBean : 스프링부트에서 마이바티스 사용 시 sqlSessionFactoryBean 클래스를 사용함. 스프링부트에서 데이터베이스 연결해서 sql 문을 날리려고 하면 sqlSessionFactory를 사용한다고 생각하면된다, 그런데 거기에서 마이바티스는 sqlSessionFactoryBean을 사용해서 sql 명령어를 날린다고 생각하면 된다. sql 명령문을 날리기위한 세트라고 생각하자. 
//		앞에는 클래스, 뒤에는 객체임. 클래스 이용해서 객체만드는 공식. 이름 그냥 똑같이 만들어줬음. 헷갈리지 말 것. 
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		
//		(위에서 만들어 놓은 dataSource 실행한 것을 가지고 와서 연결)
//		히카리CP로 생성한 데이터 소스를 연결
		sqlSessionFactoryBean.setDataSource(dataSource);
//		(applicationContext을 사용해서 classpath:/mapper/**/sql-*.xml(*은 우리가 원하는 이름 아무거나, 실제 애플리케이션 개발할 때 아래와 같이 sql 파일이 굉장히 여러 개이기 때문에 아래와 같이 패턴 형식으로 만들어 놓은 것임.) 위치의 맵퍼파일 위치를 지정해준다.) 
//		마이바티스 xml 파일의 위치 및 이름을 설정
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		
//		럼복 사용하기 하면서 추가함
//		제일 밑에 럼복사용하기에서 정했던 내용이 sqlSessionFactoryBean에다가  제일 아래에서 저장한..? mybatisConfig 을 설정한 것임... 
//		이제 진자 여기까지 하면 
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		
//		마지막에 맵퍼 파일을 sqlSessionFactory 타입으로 만들어서 반환함.
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
//	위에서 만든 맴퍼 파일을...최종적으로...
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
//	럼복 사용하기,, BoardDto.java에서 카멜명명법 지정하고 와서 작업함.
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();
	}
}

















