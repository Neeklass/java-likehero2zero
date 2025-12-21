# java-likehero2zero

# Aufgabe
- Web-Frontend für Projekt "Like Hero To Zero"
=> Darstellung der weltweiten CO2-Emmisionen (ohne Login) 
- Backend-Oberfläche mit Login für registrierte Wissenschaftler:innen (R,W) -> Daten ändern
- optional: Datensatzänderungen, -ergänzungen müssen erst freigegeben werden

## User Stories
1. MUST: Als umweltpolitisch interessierte:r Bürger:in will ich den aktuellsten im Datensatz verfügbaren 
CO2-Ausstoß des Landes nachlesen können, über dessen Staatsbürgerschaft ich verfüge.
2. MUST: Als registriere:r, Daten beitragende:r Wissenschaftler:in will ich die jüngsten Daten aus meiner 
Klimaforschung in dem Datensatz hinterlegen.
3. COULD: Als Herausgeber:in des Datensatzes will ich sicherstellen, dass Ergänzungen oder Änderungen an 
den Daten erst freigegeben werden müssen.

## Notizen

- öffentlich verfügbaren Datensatz nutzen
- github repo

## Dokumentation
Dokumentiere die Entwicklung der Webanwendung im Fallstudienbericht. Im Hauptteil sollten das Design und 
die Umsetzung beschrieben werden. Dabei soll jeweils im Entwurf und anhand von Screenshots in der 
tatsächlichen Umsetzung gezeigt werden, wie …
- … die einzelnen Webseiten gestaltet sind, und wie man zwischen den Webseiten hin- und herspringen kann,
- wie die Datenbankstruktur beschaffen ist und 
- wie die softwaretechnische Architektur gestaltet ist (mind. ein UML-Strukturdiagramm inkl. aller verwendeter Beans).


## Techstack
- JSF und CDI/Beans, optional dazu: andere Komponentenbibliotheken (z. B. PrimeFaces)
- JPA mit einem Persistenz-Provider (z. B. Hibernate)
- eine relationale Datenbank (z. B. MySQL)


# Setup

wenn mit devconatiner verbunden. bash öffnen das eingeben:

mvn archetype:generate -DgroupId=de.niklas -DartifactId=herotozero -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false


pom.xml erstellen

create folders

mkdir -p src/main/java/de/niklas (Hier kommt dein Java-Code hin)

mkdir -p src/main/resources/META-INF (Hier kommt die Datenbank-Konfiguration hin)

mkdir -p src/main/webapp/WEB-INF (Hier kommen die JSF-Konfigurationsdateien hin)

# dev

- docker desktop starten
- compose.yaml starten für mysql
- devcontainer.json in vscode starten für java