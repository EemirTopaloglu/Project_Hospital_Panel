🏥 Hospital Management System

This project is a simple Hospital Management System application developed using Java Swing and MySQL.
It follows a Layered Architecture (Model-DAO-UI) to manage hospital staff, patient, and report data.

🚀 Features

Login System: User authentication and role-based access (Admin, Staff).

Patient Management: Add, delete, update, and list patients.

Report Management: Add and view patient reports.

User Interface: Simple and functional UI built with Java Swing.

Database Connection: MySQL integration via JDBC.

📂 Project Structure

/src/database → Database connection (DBConnection.java)

/src/model → Model classes (Patient.java, Report.java)

/src/dao → Data Access Layer (PatientDAO.java, ReportDAO.java)

/src/ui → User interface (LoginFrame.java, MainFrame.java, panels)

⚙️ How to Run

Create a MySQL database named hospital.

Import required tables (User, Patient, Report, etc.).

Update your database username & password in DBConnection.java.

Run the project (LoginFrame.java is the entry point).
