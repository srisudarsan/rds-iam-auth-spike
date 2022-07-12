CREATE DATABASE rds_iam_auth_spike;
CREATE USER app_user WITH PASSWORD 'app-password-123';
CREATE USER migration_user WITH PASSWORD 'migration-password-123';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO app_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO migration_user;
GRANT rds_iam TO app_user;
GRANT rds_iam TO migration_user;