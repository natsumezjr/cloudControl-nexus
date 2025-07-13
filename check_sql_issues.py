#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
SQL问题检查脚本 - 通用可扩展版本
自动检查所有SQL文件与实体类的匹配问题
"""

import os
import re
import glob
from pathlib import Path

def find_all_sql_files():
    """自动发现所有SQL迁移文件"""
    sql_files = []
    migration_dir = "backend/src/main/resources/db/migration"
    
    if os.path.exists(migration_dir):
        for file in glob.glob(f"{migration_dir}/V*__*.sql"):
            sql_files.append(file)
    
    return sorted(sql_files)

def find_all_entity_files():
    """自动发现所有实体类文件"""
    entity_files = []
    entity_dir = "backend/src/main/java/com/cloudcontrol/entity"
    
    if os.path.exists(entity_dir):
        for root, dirs, files in os.walk(entity_dir):
            for file in files:
                if file.endswith('.java'):
                    entity_files.append(os.path.join(root, file))
    
    return sorted(entity_files)

def extract_table_name_from_sql(sql_content):
    """从SQL内容中提取表名"""
    # 匹配CREATE TABLE语句
    match = re.search(r'CREATE\s+TABLE\s+(\w+)', sql_content, re.IGNORECASE)
    if match:
        return match.group(1)
    return None

def extract_entity_name_from_java(java_content):
    """从Java实体类中提取实体名和表名"""
    # 匹配@Entity注解和@Table注解
    entity_match = re.search(r'@Entity\s+.*?class\s+(\w+)', java_content, re.DOTALL)
    table_match = re.search(r'@Table\s*\(\s*name\s*=\s*["\']([^"\']+)["\']', java_content)
    
    entity_name = entity_match.group(1) if entity_match else None
    table_name = table_match.group(1) if table_match else None
    
    return entity_name, table_name

def extract_sql_fields_improved(sql_content):
    """改进的SQL字段提取"""
    fields = {}
    lines = sql_content.split('\n')
    
    # 跳过SQL关键字
    sql_keywords = {
        'CREATE', 'TABLE', 'PRIMARY', 'KEY', 'FOREIGN', 'CONSTRAINT',
        'REFERENCES', 'ON', 'DELETE', 'CASCADE', 'SET', 'NULL',
        'NOT', 'DEFAULT', 'UNIQUE', 'CHECK', 'INDEX'
    }
    
    for line in lines:
        line = line.strip()
        if not line or line.startswith('--') or line.startswith('/*'):
            continue
            
        # 匹配字段定义行，支持多种格式
        patterns = [
            # 标准格式: field_name TYPE constraints, -- comment
            r'^\s*(\w+)\s+([A-Z]+(?:\([^)]*\))?)\s*([^,]*),?\s*--\s*(.+)',
            # 无注释格式: field_name TYPE constraints,
            r'^\s*(\w+)\s+([A-Z]+(?:\([^)]*\))?)\s*([^,]*),?$',
            # 简单格式: field_name TYPE,
            r'^\s*(\w+)\s+([A-Z]+(?:\([^)]*\))?)\s*,?$'
        ]
        
        for pattern in patterns:
            match = re.match(pattern, line)
            if match:
                field_name = match.group(1)
                field_type = match.group(2)
                constraints = match.group(3).strip() if len(match.groups()) > 2 else ""
                comment = match.group(4).strip() if len(match.groups()) > 3 else ""
                
                # 跳过SQL关键字和约束行
                if field_name.upper() in sql_keywords:
                    continue
                
                # 跳过FOREIGN KEY等约束行
                if 'FOREIGN KEY' in line or 'PRIMARY KEY' in line or 'CONSTRAINT' in line:
                    continue
                
                fields[field_name] = {
                    'type': field_type,
                    'constraints': constraints,
                    'comment': comment
                }
                break
    
    return fields

def extract_entity_fields_improved(java_content):
    """改进的实体字段提取"""
    fields = {}
    
    # 匹配@Id字段
    id_pattern = r'@Id\s*private\s+([A-Za-z<>]+)\s+(\w+);'
    id_matches = re.findall(id_pattern, java_content)
    for field_type, field_name in id_matches:
        fields['id'] = {
            'type': field_type,
            'field_name': field_name,
            'annotation': '@Id'
        }
    
    # 匹配@Column注解的字段
    column_pattern = r'@Column\s*\(\s*name\s*=\s*["\']([^"\']+)["\'][^)]*\)\s*private\s+([A-Za-z<>]+)\s+(\w+);'
    column_matches = re.findall(column_pattern, java_content)
    for column_name, field_type, field_name in column_matches:
        fields[column_name] = {
            'type': field_type,
            'field_name': field_name,
            'annotation': '@Column'
        }
    
    # 匹配@ManyToOne关联字段
    many_to_one_pattern = r'@ManyToOne\s*@JoinColumn\s*\(\s*name\s*=\s*["\']([^"\']+)["\'][^)]*\)\s*private\s+([A-Za-z<>]+)\s+(\w+);'
    many_to_one_matches = re.findall(many_to_one_pattern, java_content)
    for column_name, field_type, field_name in many_to_one_matches:
        fields[column_name] = {
            'type': field_type,
            'field_name': field_name,
            'annotation': '@ManyToOne'
        }
    
    # 匹配@OneToMany关联字段
    one_to_many_pattern = r'@OneToMany[^)]*\)\s*private\s+([A-Za-z<>]+)\s+(\w+);'
    one_to_many_matches = re.findall(one_to_many_pattern, java_content)
    for field_type, field_name in one_to_many_matches:
        # @OneToMany字段通常不直接映射到数据库列
        fields[f'_{field_name}_collection'] = {
            'type': field_type,
            'field_name': field_name,
            'annotation': '@OneToMany'
        }
    
    # 匹配@EmbeddedId复合主键
    embedded_id_pattern = r'@EmbeddedId\s*private\s+([A-Za-z<>]+)\s+(\w+);'
    embedded_id_matches = re.findall(embedded_id_pattern, java_content)
    for field_type, field_name in embedded_id_matches:
        fields['embedded_id'] = {
            'type': field_type,
            'field_name': field_name,
            'annotation': '@EmbeddedId'
        }
    
    return fields

def auto_match_sql_entities():
    """自动匹配SQL文件和实体类"""
    sql_files = find_all_sql_files()
    entity_files = find_all_entity_files()
    
    matches = []
    
    print("🔍 自动发现SQL文件和实体类...")
    print(f"发现 {len(sql_files)} 个SQL文件")
    print(f"发现 {len(entity_files)} 个实体类文件")
    
    for sql_file in sql_files:
        sql_content = read_file(sql_file)
        if not sql_content:
            continue
        
        table_name = extract_table_name_from_sql(sql_content)
        if not table_name:
            continue
        
        # 查找对应的实体类
        for entity_file in entity_files:
            entity_content = read_file(entity_file)
            if not entity_content:
                continue
            
            entity_name, entity_table_name = extract_entity_name_from_java(entity_content)
            if not entity_name:
                continue
            
            # 匹配条件：表名相同或实体名与表名相关
            if (entity_table_name and entity_table_name == table_name) or \
               (not entity_table_name and entity_name.lower().replace('entity', '') in table_name.lower()):
                matches.append({
                    'sql_file': sql_file,
                    'entity_file': entity_file,
                    'table_name': table_name,
                    'entity_name': entity_name,
                    'sql_content': sql_content,
                    'entity_content': entity_content
                })
                print(f"✅ 匹配: {table_name} <-> {entity_name}")
                break
    
    return matches

def read_file(file_path):
    """读取文件内容"""
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            return f.read()
    except Exception as e:
        print(f"❌ 读取文件失败 {file_path}: {e}")
        return None

def check_sql_entity_mapping_auto():
    """自动检查SQL与实体类的映射关系"""
    print("\n🔍 检查SQL与实体类映射关系...")
    print("=" * 60)
    
    matches = auto_match_sql_entities()
    issues = []
    
    for match in matches:
        print(f"\n📋 检查 {match['entity_name']} ({match['table_name']})...")
        
        sql_fields = extract_sql_fields_improved(match['sql_content'])
        entity_fields = extract_entity_fields_improved(match['entity_content'])
        
        print(f"SQL字段数量: {len(sql_fields)}")
        print(f"实体字段数量: {len(entity_fields)}")
        
        # 检查字段匹配
        for sql_field, sql_info in sql_fields.items():
            if sql_field not in entity_fields:
                issues.append({
                    'entity': match['entity_name'],
                    'table': match['table_name'],
                    'type': 'missing_entity_field',
                    'field': sql_field,
                    'description': f"SQL中有字段 {sql_field}，但实体类中没有对应字段"
                })
        
        for entity_field, entity_info in entity_fields.items():
            if entity_field not in sql_fields and not entity_field.startswith('_'):
                issues.append({
                    'entity': match['entity_name'],
                    'table': match['table_name'],
                    'type': 'missing_sql_field',
                    'field': entity_field,
                    'description': f"实体类中有字段 {entity_field}，但SQL中没有对应字段"
                })
    
    return issues

def check_foreign_key_constraints_auto():
    """自动检查外键约束"""
    print("\n🔗 检查外键约束...")
    print("=" * 60)
    
    issues = []
    sql_files = find_all_sql_files()
    
    for sql_file in sql_files:
        content = read_file(sql_file)
        if not content:
            continue
        
        table_name = extract_table_name_from_sql(content)
        if not table_name:
            continue
        
        # 检查是否有FOREIGN KEY约束
        if 'FOREIGN KEY' not in content:
            # 检查是否有外键字段（通过字段名判断）
            has_fk_fields = re.search(r'\w+_id\s+[A-Z]+', content)
            if has_fk_fields:
                issues.append({
                    'file': sql_file,
                    'table': table_name,
                    'type': 'missing_foreign_key',
                    'description': f"表 {table_name} 有外键字段但缺少FOREIGN KEY约束"
                })
    
    return issues

def check_entity_relationships_auto():
    """自动检查实体关系映射"""
    print("\n🔗 检查实体关系映射...")
    print("=" * 60)
    
    issues = []
    entity_files = find_all_entity_files()
    
    for entity_file in entity_files:
        content = read_file(entity_file)
        if not content:
            continue
        
        entity_name, table_name = extract_entity_name_from_java(content)
        if not entity_name:
            continue
        
        # 检查是否有关系映射注解
        has_relationships = False
        relationship_types = []
        
        if '@ManyToOne' in content:
            has_relationships = True
            relationship_types.append('@ManyToOne')
        
        if '@OneToMany' in content:
            has_relationships = True
            relationship_types.append('@OneToMany')
        
        if '@OneToOne' in content:
            has_relationships = True
            relationship_types.append('@OneToOne')
        
        if '@ManyToMany' in content:
            has_relationships = True
            relationship_types.append('@ManyToMany')
        
        if has_relationships:
            print(f"✅ {entity_name} 正确使用了关系映射: {', '.join(relationship_types)}")
        else:
            # 检查是否有外键字段但没有关系映射
            if re.search(r'\w+_id\s+[A-Z]+', content):
                issues.append({
                    'entity': entity_name,
                    'file': entity_file,
                    'type': 'missing_relationship',
                    'description': f"{entity_name} 实体有外键字段但缺少关系映射注解"
                })
    
    return issues

def generate_fix_suggestions_improved(issues):
    """生成改进的修复建议"""
    print("\n🔧 生成修复建议...")
    print("=" * 60)
    
    if not issues:
        print("✅ 未发现需要修复的问题")
        return
    
    # 按类型分组问题
    issues_by_type = {}
    for issue in issues:
        issue_type = issue['type']
        if issue_type not in issues_by_type:
            issues_by_type[issue_type] = []
        issues_by_type[issue_type].append(issue)
    
    print(f"发现 {len(issues)} 个问题需要修复:")
    
    for issue_type, type_issues in issues_by_type.items():
        print(f"\n📋 {issue_type} 类型问题 ({len(type_issues)} 个):")
        for i, issue in enumerate(type_issues, 1):
            print(f"  {i}. {issue.get('description', '未知问题')}")
            if 'file' in issue:
                print(f"     文件: {issue['file']}")
            if 'entity' in issue:
                print(f"     实体: {issue['entity']}")
            if 'table' in issue:
                print(f"     表: {issue['table']}")
            if 'field' in issue:
                print(f"     字段: {issue['field']}")

def main():
    """主函数"""
    print("🚀 SQL问题检查开始 (通用可扩展版本)")
    print("=" * 60)
    
    # 检查SQL与实体类映射
    mapping_issues = check_sql_entity_mapping_auto()
    
    # 检查外键约束
    fk_issues = check_foreign_key_constraints_auto()
    
    # 检查实体关系
    relationship_issues = check_entity_relationships_auto()
    
    # 合并所有问题
    all_issues = mapping_issues + fk_issues + relationship_issues
    
    # 生成修复建议
    generate_fix_suggestions_improved(all_issues)
    
    print("\n" + "=" * 60)
    if all_issues:
        print(f"❌ 发现 {len(all_issues)} 个问题需要修复")
        print("💡 请根据上述建议修复问题后重新运行检查")
    else:
        print("🎉 所有SQL文件检查通过！")
        print("✅ 可以运行三合一测试脚本进行完整测试")

if __name__ == "__main__":
    main() 