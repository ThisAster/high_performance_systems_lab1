set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER clinic WITH PASSWORD 'clinic';
  CREATE DATABASE clinic_db WITH OWNER clinic;
  GRANT ALL PRIVILEGES ON DATABASE clinic_db TO clinic;
	CREATE USER analysis WITH PASSWORD 'analysis';
	CREATE DATABASE analysis_db WITH OWNER analysis;
	GRANT ALL PRIVILEGES ON DATABASE analysis_db TO analysis;
	CREATE USER appointment WITH PASSWORD 'appointment';
	CREATE DATABASE appointment_db WITH OWNER appointment;
	GRANT ALL PRIVILEGES ON DATABASE appointment_db TO appointment;
	CREATE USER auth WITH PASSWORD 'auth';
	CREATE DATABASE auth_db WITH OWNER auth;
	GRANT ALL PRIVILEGES ON DATABASE auth_db TO auth;
	CREATE USER doctor WITH PASSWORD 'doctor';
	CREATE DATABASE doctor_db WITH OWNER doctor;
	GRANT ALL PRIVILEGES ON DATABASE doctor_db TO doctor;
	CREATE USER document WITH PASSWORD 'document';
	CREATE DATABASE document_db WITH OWNER document;
	GRANT ALL PRIVILEGES ON DATABASE document_db TO document;
	CREATE USER patient WITH PASSWORD 'patient';
	CREATE DATABASE patient_db WITH OWNER patient;
	GRANT ALL PRIVILEGES ON DATABASE patient_db TO patient;
	CREATE USER recipe WITH PASSWORD 'recipe';
	CREATE DATABASE recipe_db WITH OWNER recipe;
	GRANT ALL PRIVILEGES ON DATABASE recipe_db TO recipe;
EOSQL