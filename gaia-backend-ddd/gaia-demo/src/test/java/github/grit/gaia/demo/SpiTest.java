package github.grit.gaia.demo;


import java.util.ServiceLoader;

import github.grit.gaia.demo.spi.SpiService;
import org.junit.jupiter.api.Test;

public class SpiTest {

	@Test
	public void testSpi(){
		ServiceLoader<SpiService> load = ServiceLoader.load(SpiService.class);
		for (SpiService spiService : load) {
			System.out.println(spiService.show());
		}
	}
}

