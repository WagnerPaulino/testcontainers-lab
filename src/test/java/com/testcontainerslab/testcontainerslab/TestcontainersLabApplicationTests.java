package com.testcontainerslab.testcontainerslab;

import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.testcontainers.containers.OracleContainer;

import com.testcontainerslab.testcontainerslab.domain.Usuario;
import com.testcontainerslab.testcontainerslab.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(initializers = { TestcontainersLabApplicationTests.Initializer.class })
class TestcontainersLabApplicationTests {

	@ClassRule
	public static OracleContainer oracle = new OracleContainer("wnameless/oracle-xe-11g-r2");

	static {
		oracle.start();
	}

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Test
	void shoudSaveUsuario() {
		Usuario u = new Usuario();
		u.setLogin("wagnersantos");
		u.setNome("wagner santos");
		u = this.usuarioRepository.save(u);
		Assertions.assertThat(u.getId()).isNotNull();
	}

	@Test
	void shoudSaveAndReceiveUsuarios() {
		Usuario u = new Usuario();
		u.setLogin("wagnersantos");
		u.setNome("wagner santos");
		u = this.usuarioRepository.save(u);
		Assert.isTrue(this.usuarioRepository.findAll().size() > 0, "Não existe nenhum usuario cadastrado");
	}

	static final class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues
					.of("spring.datasource.url=" + oracle.getJdbcUrl(),
							"spring.datasource.username=" + oracle.getUsername(),
							"spring.datasource.password=" + oracle.getPassword(),
							"spring.datasource.driverClassName=" + oracle.getDriverClassName())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}

}
