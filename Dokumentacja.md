Klasa: CurrencyInfoLoader  - klasa typu singleton, używana do pobierania pól danej waluty.


public static CurrencyInfoLoader getInstance(
tworzy obiekt klasy, lub jeśli już został już stworzony kopie jego referencjii. 


public CurrencyModel getCurrencyModelForLastDays(String tableType, String currency, long days)
zwraca model waluty, dla danego typu tablicy, dla danej waluty, i ilości dni wstecz od daty bierzącej.


public CurrencyModel getCurrencyModelForPeriod(String tableType, String currency, Period period)


Zwraca model waluty, dla danego typu tablicy, dla danej wality oraz okresu czasu od daty bierzącej. Bierze tylko pod uwage dni robocze(poniedziałęk - piątek, bez świąt).
 


Klasa: RateModel
Klasa typu encji, zawiera date, średnią cene, cene kupna, cene sprzedaży. Klasa posiada adnotacje @JsonIgnoreProperties(ignoreUnknown = true)
dzięki czemu podczas pobierania jsona, nie wszystkie pola są wymagane


 
private String no;                        numer seryjny
private String effectiveDate;        data
private Double mid;                        cena średnia
private Double bid;                        cena sprzedaży
private Double ask;                        cena kupna


public RateModel(String effectiveDate, Double mid)
public RateModel(String no, String effectiveDate, Double mid, Double bid, Double ask)


Klasa posiada dwa konstruktory.
Dodatkowo getery oraz settery.


Klasa: StatisticsController
Klasa do obliczania wartości statystycznych dla przekazanego zestawu danych


public StatisticsController() - konsturktor tworzący nowy obiekt DescriptiveStatistics


public void setCurrency(String currency) 
metoda ustawia typ waluty, dla której mają zostać policzone statystyki.


public void setTableType(String tableType) 
metoda ustawiająca typ tabeli podczas zapytania do api.




public void setPeriodAndCalculate(String s)
główna metoda klasy, przekazywany przez argument s okres czasu w postaci: “rok, pół roku, itd…”. po wywołaniu metody, do obiektu klasy DescriptiveStatistics
zapisywane są wszystkie statystyki.


public DescriptiveStatistics getStats()
metoda zwracająca wszystkie statystki.


Klasa: SessionCountController
Klasa obliczająca ilość sesji wzrostowych, bez zmian, malejących.


public SessionCountController(CurrencyModel currencyModel)
konstruktor 


public CountSessionStateModel calculateSessionCount()
metoda obliczająca i zwracająća ilość sesji wzrostowych, bez zmian, malejących.




Klasa: CurrencyModel 
Klasa do obliczania par walutowych




public CurrencyModel ()
konstruktor domyślny


public void CurrencyModel (String s)
ustawia okres czasu na podstawie s, (dostępne są tylko 2 opcje: “kwartał” oraz “1 miesiąć”)


public void CurrencyModel (String chosedCurrencyPair)
metoda ustawiająca pare walutową np “EUR/USD”


public CurrencyModel CurrencyModel ()
Metoda obliczająca rozkład zmian par walutowych. wynik zwracany do nowego CurrencyModel. do wyniku odwołujemy się poprzez .getRates().getMid()
po date analogicznie.        


Klasa: Controller
Klasa odpowiedzialna za działanie frontEndu, oraz za wykonywanie odpowiednich akcji(na backEndzie) podczas aktywności na GUI.


Plik: sample.fxml
Plik opisujący wygląd frontEndu, wykorzystywany w JavaFX.


Plik: pom.xml
Plik dla zarządzania zależnościami w projekcie, służy dla dodania i kontrolu wersji bibliotek zewnętrznych. Cześć narzędzia do zarządzania projektami Maven.