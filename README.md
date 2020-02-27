# Bigdata

L’obiettivo del progetto é quello analizzare funzionalitá e prestazioni di un database
SQL, NoSQL e NewSql. L’ intento é quello di implementare un applicativo Web
di analisi e reporting che illustra con opportune dashboard l'efficenza in termini di tempo dei
diversi database :
1. In ambito OLTP : Operazioni ACID
2. In ambito OLAP : Operazioni di aggregation e slicing

I db utilizzati dall'applicativo sono :

• MySql 

• MongoDB

• CockroachDB

Nel progetto oltre alla implementazione delle interfacce web e servizi implementati per effettuare le query 
sono presenti i file utili per fare lo start dei servizi utilizzati da CockroachDB e i file di configurazione del application server Wildfly in cui sono stati inserti le librerie e configurati i datasource dei db.
