About
===

Kalendarz z geolokacją, zintegrowany z facebookiem, Dostępny na: https://calendarplus.herokuapp.com/

Getting Started
===

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
