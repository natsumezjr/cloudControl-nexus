package com.cloudcontrol.repository.command;

import com.cloudcontrol.entity.command.Command;
import com.cloudcontrol.entity.terminal.Terminal;
import com.cloudcontrol.repository.terminal.TerminalRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class CommandRepositoryTest {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testInsertAndQueryCommand() {
        // 插入终端
        Terminal terminal = new Terminal();
        terminal.setDeviceName("test-device");
        terminal.setPassword("123456");
        Terminal savedTerminal = terminalRepository.save(terminal);

        // 插入指令
        Command command = new Command();
        command.setTerminalId(savedTerminal.getSerialNo());
        command.setAuthorUrl("api/test");
        Command.Content content = new Command.Content();
        content.setRaw("{\"test\":1}");
        command.setContent(content);
        command.setKarma(1);
        command.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Command savedCommand = commandRepository.save(command);

        // 查询并断言
        Optional<Command> foundOpt = commandRepository.findById(savedCommand.getId());
        assertThat(foundOpt).isPresent();
        Command found = foundOpt.get();
        assertThat(found.getTerminalId()).isEqualTo(savedTerminal.getSerialNo());
        assertThat(found.getAuthorUrl()).isEqualTo("api/test");
        assertThat(found.getContent().getRaw()).isEqualTo("{\"test\":1}");
    }

    @Test
    public void testCascadeDeleteByTerminal() {
        // 插入终端
        Terminal terminal = new Terminal();
        terminal.setDeviceName("cascade-device");
        terminal.setPassword("654321");
        Terminal savedTerminal = terminalRepository.save(terminal);

        // 插入指令
        Command command = new Command();
        command.setTerminalId(savedTerminal.getSerialNo());
        command.setAuthorUrl("api/cascade");
        Command.Content content = new Command.Content();
        content.setRaw("{\"cascade\":true}");
        command.setContent(content);
        command.setKarma(2);
        command.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        Command savedCommand = commandRepository.save(command);

        // 删除终端，检查指令是否级联删除
        terminalRepository.deleteById(savedTerminal.getSerialNo());
        entityManager.flush();
        entityManager.clear();
        Optional<Command> found = commandRepository.findById(savedCommand.getId());
        assertThat(found).isNotPresent();
    }
} 