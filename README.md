About
=====

Kalendarz z geolokacją, zintegrowany z facebookiem.

Getting Started
---------------

Aplikacja integruje się z IDE Intellij jako projekt typu SpringMVC.

W celu uruchomienia serwera lokalnie należy wykonać:

#+BEGIN_EXAMPLE
   $ maven package
   $ java -jar target/dependency/webapp-runner.jar target/*.war
#+END_EXAMPLE

Dokładniejsze instrukcje znajdują się na stronie:
https://devcenter.heroku.com/articles/java-webapp-runner

Na heroku aplikacja deply'uje się automatycznie po zmianie brancha master.