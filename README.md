About
=====

Kalendarz z geolokacją, zintegrowany z facebookiem, Dostępny na: https://calendarplus.herokuapp.com/

Getting Started
---------------

Aplikacja integruje się z IDE Intellij jako projekt typu SpringMVC.

W celu uruchomienia serwera lokalnie należy wykonać:

```bash
   $ maven package
   $ java -jar target/dependency/webapp-runner.jar target/*.war
```
Dokładniejsze instrukcje znajdują się na stronie:
https://devcenter.heroku.com/articles/java-webapp-runner

Na heroku aplikacja deply'uje się automatycznie po zmianie brancha master.