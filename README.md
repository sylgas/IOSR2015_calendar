About
===

Kalendarz z geolokacją, zintegrowany z facebookiem, Dostępny na: https://calendarplus.herokuapp.com/

Getting Started
===

Lokalna baza danych
======

1. Pobierz MongoDB ze strony:
https://www.mongodb.org/downloads

2. Stwórz katalog na dane /data/db lub C:\data\db (Windows) - sciezka może być zmieniona za pomoca opcji --dbpath

3. Dodaj mongo/bin do PATH

4. Zmień w pliku src/main/resources/config.properties wartosci:
```bash
   mongodb.host = localhost
   mongodb.port = 27017
```

5. Uruchom mongo za pomoca komendy:
```bash
   $ mongod
```

6. Polacz sie z baza i utworz uzytkownika:
```bash
   $ mongo heroku_app35228146
   $ db.createUser(
     {
       user: "admin",
       pwd: "admin",
       roles: [ { role: "userAdmin", db: "heroku_app35228146" } ]
     }
   )
```

Serwer
======

Aplikacja integruje się z IDE Intellij jako projekt typu SpringMVC.

W celu uruchomienia serwera lokalnie należy wykonać:

```bash
   $ maven package
   $ java -jar target/dependency/webapp-runner.jar target/*.war
```
Dokładniejsze instrukcje znajdują się na stronie:
https://devcenter.heroku.com/articles/java-webapp-runner

Na heroku aplikacja deply'uje się automatycznie po zmianie brancha master.

Aplikacja webowa
======

Należy pobrać i zainstalować NodeJS (http://nodejs.org/).
Następnie należy zainstalować transpiler CoffeeScript komendą:

```bash
   $ npm install -g bower coffee-script
```

Należy pobrać i zainstalować IntelliJ (https://www.jetbrains.com/idea/download/) oraz zainstalować wtyczki:
* AngularJS
* File Watchers
* NodeJS

W celu automatycznej kompilacji plików .coffee należy wymagane jest skonfigurowanie watchera CoffeeScript. W tym celu należy:
- wybrać z menu File/Settings/File Watchers
- kliknąć '+' i wybrać 'CoffeeScript'
- pole 'Program' powinno zawierać ścieżkę do zainstalowanego wcześniej transpilera. Jeżeli nie - należy podać odpowiednią ścieżkę.
