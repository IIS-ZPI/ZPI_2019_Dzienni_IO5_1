# Dokumentacja NBP API

## Ogólne informacje
Odpowiedź serwisu zwracana jest albo w formacie JSON albo XML, zależnie od wymagań klienta. 
Format odpowiedzi można wskazać na dwa sposoby – za pomocą parametru zapytania ?format albo przy pomocy nagłówka HTTP Accept:
- format JSON: nagłówek Accept: application/json lub parameter ?format=json
- format XML: nagłówek Accept: application/xml lub parameter ?format=xml

Jeśli nie określono formatu zwracany jest JSON.

#### Dane archiwalne dostępne są odpowiednio:

- dla kursów walut – od 2 stycznia 2002 r.,
- dla cen złota – od 2 stycznia 2013 r.

przy czym pojedyncze zapytanie nie może obejmować przedziału dłuższego, niż 93 dni.

#### Zakres czasowy dla wyników zapytania można określić na jeden z poniższych sposobów:
- aktualnie obowiązujące dane - ostatnia opublikowana wartość obowiązująca w momencie wykonania zapytania,
- dane opublikowane w dniu dzisiejszym - wartość opublikowana bieżącego dnia,
- seria danych z ostatnich N notowań,
- dane dla konkretnej daty publikacji,
- seria danych z zadanego przedziału dat.

## Parametry zapytań
- **{table}** – typ tabeli (A, B, lub C)
- **{code}** – trzyliterowy kod waluty (standard [ISO 4217](https://pl.wikipedia.org/wiki/ISO_4217))
- **{topCount}** – liczba określająca maksymalną liczność zwracanej serii danych
- **{date}**, **{startDate}**, **{endDate}** – data w formacie RRRR-MM-DD (standard ISO 8601)

## Zapytania o pojedynczą walutę
- Aktualnie obowiązujący kurs waluty {code} z tabeli kursów typu {table}
<pre>
http://api.nbp.pl/api/exchangerates/rates/<b>{table}</b>/<b>{code}</b>/
</pre>
- Seria ostatnich **{topCount}** kursów waluty **{code}** z tabeli kursów typu **{table}**
<pre>
http://api.nbp.pl/api/exchangerates/rates/<b>{table}</b>/<b>{code}</b>/last/<b>{topCount}</b>/
</pre>
- Kurs waluty **{code}** z tabeli kursów typu **{table}** opublikowany w dniu dzisiejszym (albo brak danych)
<pre>
http://api.nbp.pl/api/exchangerates/rates/<b>{table}</b>/<b>{code}</b>/today/
</pre>
- Kurs waluty **{code}** z tabeli kursów typu **{table}** opublikowany w dniu **{date}** (albo brak danych)
<pre>
http://api.nbp.pl/api/exchangerates/rates/<b>{table}</b>/<b>{code}</b>/<b>{date}</b>/
</pre>
- Seria kursów waluty **{code}** z tabeli kursów typu **{table}** opublikowanych w zakresie dat od **{startDate}** do **{endDate}** (albo brak danych)
<pre>
http://api.nbp.pl/api/exchangerates/rates/<b>{table}</b>/<b>{code}</b>/<b>{startDate}</b>/<b>{endDate}</b>/
</pre>

## Dodatkowe informacje

#### Rodzaje tabel
- A - tabela kursów średnich walut obcych;
- B - tabela kursów średnich walut niewymienialnych;
- C - tabela kursów kupna i sprzedaży;
