package com.cloudcontrol.repository.terminal;

import com.cloudcontrol.entity.terminal.Terminal;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;
import java.sql.Timestamp;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TerminalRepositoryOnlyTest {

    @Autowired
    private TerminalRepository terminalRepository;

    @BeforeEach
    public void setUp() {
        terminalRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testInsertAndFindBySerialNo() {
        Terminal terminal = new Terminal();
        terminal.setDeviceName("终端一号");
        terminal.setPassword("testpass");
        terminal.setAccountName("accountA");
        terminal.setLedDescription("测试终端");
        terminal.setLastHeartbeat(new Timestamp(System.currentTimeMillis()));
        terminal.setStatus("online");
        terminal.setPowerStatus(1);
        terminal.setBrightness(80);
        terminal.setColortemp(6500);
        terminal.setVolume(10);
        terminal.setInputMode("HDMI");
        terminal.setLanguage("zh-CN");
        terminal.setCountry("CN");
        terminal.setTimezoneId("Asia/Shanghai");
        terminal.setTimezone(8.0f);
        terminal.setIsAutoTime(1);
        terminal.setProgram("节目A");
        terminal.setProgramType("本地");
        terminal.setGpsReportInterval(60);
        terminal.setSensorReportInterval(120);
        terminal.setContentReportStatus(1);
        terminal.setLogReport("on");
        terminal.setRotateProgramVsnsReport("on");
        terminal.setExtra("{\"test\":true}");

        Terminal savedTerminal = terminalRepository.save(terminal);

        Optional<Terminal> found = terminalRepository.findBySerialNo(savedTerminal.getSerialNo());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("终端一号", found.get().getDeviceName());
        Assertions.assertEquals(savedTerminal.getSerialNo(), found.get().getSerialNo());
    }

    @Test
    @Order(2)
    public void testFindByDeviceName() {
        Terminal terminal = new Terminal();
        terminal.setDeviceName("终端二号");
        terminal.setPassword("testpass2");
        terminalRepository.save(terminal);

        Optional<Terminal> found = terminalRepository.findByDeviceName("终端二号");
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("终端二号", found.get().getDeviceName());
    }

    @Test
    @Order(3)
    public void testFindByAccountNameAndPassword() {
        Terminal terminal = new Terminal();
        terminal.setDeviceName("终端三号");
        terminal.setPassword("pw3");
        terminal.setAccountName("acc3");
        terminalRepository.save(terminal);

        Optional<Terminal> found = terminalRepository.findByAccountNameAndPassword("acc3", "pw3");
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("终端三号", found.get().getDeviceName());
    }
} 