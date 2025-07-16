package com.cloudcontrol.repository.user;

import com.cloudcontrol.entity.user.User;
import com.cloudcontrol.enums.user.UserRole;
import com.cloudcontrol.enums.user.UserStatus;
import com.cloudcontrol.dto.user.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/cloudcontrol_test",
    "spring.datasource.username=postgres",
    "spring.datasource.password=bupt_test",
    "spring.jpa.hibernate.ddl-auto=validate",
    "spring.flyway.enabled=true"
})
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    // DTO -> Entity 转换
    private User toEntity(UserRegisterRequest dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setNickname(dto.getNickname());
        user.setTermId(dto.getTermId());
        user.setAssignedSize(dto.getAssignedSize());
        // 只取第一个角色映射到User.role
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            try {
                user.setRole(UserRole.valueOf(dto.getRoles().get(0)));
            } catch (IllegalArgumentException e) {
                user.setRole(UserRole.MANAGER); // 默认角色
            }
        } else {
            user.setRole(UserRole.MANAGER); // 默认角色
        }
        user.setStatus(UserStatus.ACTIVE);
        return user;
    }

    @Test
    void testUserRepositoryAndDtoEntityFlow() {
        // 1. 构造注册DTO
        UserRegisterRequest dto = new UserRegisterRequest();
        dto.setUsername("integrationUser");
        dto.setEmail("integration@example.com");
        dto.setPassword("integrationPass");
        dto.setNickname("集成测试用户");
        dto.setTermId(100);
        dto.setAssignedSize(1024L);
        dto.setRoles(Collections.singletonList("MANAGER"));

        // 2. DTO -> Entity
        User user = toEntity(dto);

        // 3. Repository 保存
        userRepository.save(user);

        // 4. Repository 查询
        Optional<User> found = userRepository.findByUsername("integrationUser");
        assertThat(found).isPresent();
        User foundUser = found.get();
        assertThat(foundUser.getEmail()).isEqualTo("integration@example.com");
        assertThat(foundUser.getRole()).isEqualTo(UserRole.MANAGER);
        assertThat(foundUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(foundUser.getNickname()).isEqualTo("集成测试用户");
        assertThat(foundUser.getTermId()).isEqualTo(100);
        assertThat(foundUser.getAssignedSize()).isEqualTo(1024L);

        // 5. 修改状态并保存
        foundUser.setStatus(UserStatus.BANNED);
        userRepository.save(foundUser);
        Optional<User> bannedUser = userRepository.findByUsername("integrationUser");
        assertThat(bannedUser).isPresent();
        assertThat(bannedUser.get().getStatus()).isEqualTo(UserStatus.BANNED);

        // 6. 删除
        userRepository.delete(foundUser);
        assertThat(userRepository.findByUsername("integrationUser")).isNotPresent();
    }
} 