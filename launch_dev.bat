start C:\"Program Files"\MySQL\"MySQL Server 8.0"\bin\mysqld.exe --console
start java -jar .\target\eav-app-1.0.jar
cd .\client\ && npm run dev
start http://localhost:3000