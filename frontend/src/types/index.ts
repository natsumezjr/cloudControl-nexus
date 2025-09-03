// 基础类型定义
export interface BaseEntity {
  id?: number;
  created_at?: string;
  updated_at?: string;
}

// 用户相关类型
export interface User extends BaseEntity {
  username: string;
  email: string;
  role: UserRole;
  status: UserStatus;
}

export enum UserRole {
  ADMIN = 'ADMIN',
  USER = 'USER',
  OPERATOR = 'OPERATOR'
}

export enum UserStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  SUSPENDED = 'SUSPENDED'
}

export interface UserCreateRequest {
  username: string;
  email: string;
  password: string;
  role: UserRole;
}

export interface UserUpdateRequest {
  username?: string;
  email?: string;
  role?: UserRole;
  status?: UserStatus;
}

export interface UserLoginRequest {
  username: string;
  password: string;
}

export interface UserLoginResponse {
  token: string;
  user: User;
  expires_in: number;
}

// 终端相关类型
export interface Terminal extends BaseEntity {
  serial_no: number;
  device_name: string;
  terminal_group_id?: number;
  led_description?: string;
  last_heartbeat?: string;
  status: TerminalStatus;
  power_status?: number;
  brightness?: number;
  colortemp?: number;
  volume?: number;
  input_mode?: string;
  language?: string;
  country?: string;
  timezone_id?: string;
  timezone?: number;
  is_auto_time?: number;
  program?: string;
  program_type?: string;
  gps_report_interval?: number;
  sensor_report_interval?: number;
  content_report_status?: number;
  log_report?: string;
  rotate_program_vsns_report?: string;
  extra?: string;
  lat?: number;
  lng?: number;
}

export enum TerminalStatus {
  ONLINE = 'ONLINE',
  OFFLINE = 'OFFLINE',
  ERROR = 'ERROR',
  MAINTENANCE = 'MAINTENANCE'
}

export interface TerminalCreateRequest {
  device_name: string;
  password: string;
  account_name?: string;
  terminal_group_id?: number;
  led_description?: string;
}

export interface TerminalUpdateRequest {
  device_name?: string;
  terminal_group_id?: number;
  led_description?: string;
  status?: TerminalStatus;
}

// 终端组相关类型
export interface TerminalGroup extends BaseEntity {
  name: string;
  description?: string;
}

export interface TerminalGroupCreateRequest {
  name: string;
  description?: string;
}

// 终端标签相关类型
export interface TerminalTag extends BaseEntity {
  tag_id: number;
  tag_name: string;
}

export interface TerminalTagRelation extends BaseEntity {
  relation_id: number;
  terminal_id: number;
  tag_id: number;
}

// 节目相关类型
export interface Program extends BaseEntity {
  title: string;
  status: ProgramStatus;
  program_info?: string;
  programs_data?: string;
  author?: number;
  slug?: string;
  vsn_name?: string;
}

export enum ProgramStatus {
  DRAFT = 'DRAFT',
  PUBLISHED = 'PUBLISHED',
  ARCHIVED = 'ARCHIVED'
}

export interface ProgramCreateRequest {
  title: string;
  program_info?: string;
  programs_data?: string;
}

export interface ProgramUpdateRequest {
  title?: string;
  status?: ProgramStatus;
  program_info?: string;
  programs_data?: string;
}

export interface ProgramTerminalRelation extends BaseEntity {
  relation_id: number;
  program_id: number;
  terminal_id: number;
  publish_status: string;
  publish_time?: string;
}

// 媒体相关类型
export interface Media extends BaseEntity {
  title_raw?: string;
  description?: string;
  file_type?: string;
  mime_type?: string;
  media_type?: string;
  attachment_filesize?: number;
  file_path?: string;
  thumbnail_path?: string;
  width?: number;
  height?: number;
  duration?: number;
  author?: number;
  guid?: string;
  slug?: string;
  source_url?: string;
  src?: string;
  post_status?: string;
  comment_status?: string;
  ping_status?: string;
  parent?: number;
}

export interface MediaUploadRequest {
  file: File;
  title?: string;
  description?: string;
}

// 排程相关类型
export interface Schedule extends BaseEntity {
  terminal_id: number;
  program_id: number;
  start_time: string;
  end_time: string;
  status: ScheduleStatus;
  priority: number;
}

export enum ScheduleStatus {
  PENDING = 'PENDING',
  ACTIVE = 'ACTIVE',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED'
}

export interface ScheduleCreateRequest {
  terminal_id: number;
  program_id: number;
  start_time: string;
  end_time: string;
  priority?: number;
}

// 命令相关类型
export interface Command extends BaseEntity {
  terminal_id: number;
  cmd_num: number;
  command_type: CommandType;
  command_method: CommandMethod;
  status: CommandStatus;
  payload?: string;
}

export enum CommandType {
  WAKEUP = 'WAKEUP',
  SLEEP = 'SLEEP',
  RESTART = 'RESTART',
  SCREENSHOT = 'SCREENSHOT',
  CLEAR_CACHE = 'CLEAR_CACHE',
  BRIGHTNESS = 'BRIGHTNESS',
  COLORTEMP = 'COLORTEMP',
  VOLUME = 'VOLUME',
  INPUT_MODE = 'INPUT_MODE',
  RELAY_CONTROL = 'RELAY_CONTROL',
  SYSTEM_UPGRADE = 'SYSTEM_UPGRADE',
  GPS_REPORT = 'GPS_REPORT',
  SENSOR_REPORT = 'SENSOR_REPORT',
  PROGRAM_UPDATE = 'PROGRAM_UPDATE',
  PROGRAM_SWITCH = 'PROGRAM_SWITCH',
  PROGRAM_CLEAR = 'PROGRAM_CLEAR',
  PROGRAM_DELETE = 'PROGRAM_DELETE',
  LOG_REPORT = 'LOG_REPORT',
  NETWORK_INFO = 'NETWORK_INFO'
}

export enum CommandMethod {
  GET = 'GET',
  POST = 'POST'
}

export enum CommandStatus {
  PENDING = 'PENDING',
  SENT = 'SENT',
  EXECUTING = 'EXECUTING',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED',
  TIMEOUT = 'TIMEOUT'
}

export interface CommandResult extends BaseEntity {
  command_id: number;
  exit_code?: number;
  stdout?: string;
  stderr?: string;
  started_at?: string;
  finished_at?: string;
  duration_ms?: number;
  error_message?: string;
  environment?: string;
}

export interface CommandCreateRequest {
  terminal_id: number;
  command_type: CommandType;
  command_method: CommandMethod;
  payload?: string;
}

// TUS上传相关类型
export interface TusUpload extends BaseEntity {
  upload_id: string;
  filename?: string;
  file_type?: string;
  mime_type?: string;
  file_size?: number;
  upload_offset: number;
  checksum?: string;
  file_path?: string;
  status: TusUploadStatus;
}

export enum TusUploadStatus {
  PENDING = 'PENDING',
  UPLOADING = 'UPLOADING',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED'
}

// API响应类型
export interface ApiResponse<T = any> {
  success: boolean;
  data?: T;
  message?: string;
  error?: string;
  code?: number;
}

export interface PaginatedResponse<T> {
  content: T[];
  total_elements: number;
  total_pages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

// 分页请求参数
export interface PageRequest {
  page?: number;
  size?: number;
  sort?: string;
  direction?: 'ASC' | 'DESC';
}

// 搜索请求参数
export interface SearchRequest extends PageRequest {
  keyword?: string;
  filters?: Record<string, any>;
}

// 认证相关类型
export interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  loading: boolean;
}

// WebSocket消息类型
export interface WebSocketMessage {
  type: string;
  data: any;
  timestamp: string;
}

export interface TerminalStatusMessage {
  terminal_id: number;
  status: TerminalStatus;
  last_heartbeat: string;
  power_status?: number;
  brightness?: number;
  volume?: number;
}

export interface CommandResponseMessage {
  command_id: number;
  terminal_id: number;
  status: CommandStatus;
  result?: CommandResult;
} 