// docs/architecture/细节设计思路.md

# Command-Terminal 关系设计细节

## 设计场景
- 前端/接口只传 terminalId，后端不直接用对象导航。
- Service 层根据 terminalId 查询 Terminal 实体，组装 Command 实体。

## 推荐做法
- DTO、API、数据库表均只出现 terminalId 字段。
- Service 层用 terminalRepository.findById(terminalId) 查出 Terminal。
- 组装 Command 实体，保证外键约束和业务一致性。

## 典型代码片段
```java
// Controller 层
@PostMapping("/command")
public ResponseEntity<?> createCommand(@RequestBody CommandDTO dto) {
    commandService.createCommand(dto);
}

// Service 层
public void createCommand(CommandDTO dto) {
    Terminal terminal = terminalRepository.findById(dto.getTerminalId())
        .orElseThrow(() -> new NotFoundException("终端不存在"));
    Command command = new Command();
    command.setTerminalId(dto.getTerminalId());
    // 其它字段赋值
    commandRepository.save(command);
}
```

## 优势
- 保持接口简洁，前后端解耦。
- 规避 JPA 导航对象带来的复杂性和类型错误。
- 便于测试、Mock、数据校验，易于维护和扩展。

## 适用范围
- 适用于所有 Command 与 Terminal 相关的接口、DTO、Entity、SQL 设计。
- 后续如需扩展为对象导航，可在 Service 层灵活调整。

---