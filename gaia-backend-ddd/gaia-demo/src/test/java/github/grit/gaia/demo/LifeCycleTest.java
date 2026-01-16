package github.grit.gaia.demo;

import github.grit.gaia.demo.config.LifeCycleBean;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LifeCycleTest {

	@Autowired
	private LifeCycleBean lifeCycleBean;


	@Test
	public void test() {
		System.out.println("LifeCycleTest");
	}
}
