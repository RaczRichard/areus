#   a r e u s 

Ez a projekt teljes mértékben Dockerben futtatható, nincs szükség külön telepítésre vagy környezetbeállításra.

## Indítás

A projekt indításához egyszerűen futtasd az alábbi parancsot a projekt gyökeréből:

```bash
 docker-compose up
```

Ez elindítja:

- a Spring Boot alkalmazást a 8080-as porton
- egy MySQL adatbázist a 3306-os porton

Győződj meg róla, hogy a 3306 és 8080 portok szabadok, különben az alkalmazás nem tud elindulni.

## Követelmények
- Docker
- Docker Compose

## Hitelesítés (Basic Auth)
Az API Basic Auth védelemmel van ellátva. A belépéshez használd az alábbi hitelesítő adatokat:

- Felhasználónév: user
- Jelszó: password

Példa curl-lel:

```curl -u user:password http://localhost:8080/api/v1/customer/get```

## API Végpontok
```GET /api/v1/customer/average-age```
Visszaadja az összes ügyfél átlagéletkorát (szám formájában).

```GET /api/v1/customer/between```
Visszaadja azoknak az ügyfeleknek a listáját, akik 18 és 40 év közöttiek.

```POST /api/v1/customer/create```
Létrehoz egy új ügyfelet.

Body (JSON): CustomerDTO

Példa:
```
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 30,
  "email": "john@example.com"
}
```
```GET /api/v1/customer/get```
Visszaadja az összes ügyfelet.

```GET /api/v1/customer/get/{id}```
Visszaadja az adott ID-val rendelkező ügyfelet.

```PUT /api/v1/customer/update/{id}```
Frissíti az adott ID-val rendelkező ügyfelet.

```DELETE /api/v1/customer/delete/{id}```
Törli az adott ID-val rendelkező ügyfelet.

## Konténerizált működés
Az alkalmazás teljes mértékben Docker-kompatibilis, nincs szükség külön Java vagy MySQL telepítésre. Az adatbázis elérhető a localhost:3306 címen, az alkalmazás pedig a http://localhost:8080 alatt fut.

## Megjegyzés
Az adatbázis inicializálás, jelszavak, és egyéb konfigurációk a docker-compose.yml és application.properties fájlban állíthatók.
