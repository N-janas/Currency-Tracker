# Currency-Tracker
Currency converter with tracking functionality

## Funkcjonalność :
- Konwersja walut
- Historia kursów walut
- Pozwala na tworzenie wykresów z porównaniem ostatnich kursów wybranych walut

## Założenia :
- Użycie otwartego API https://exchangeratesapi.io/
- Baza przechowuje 10 wybranych walut,
    każda tabela przechowuje przeliczniki na daną walutę z pozostałych walut oraz date
- Aktualizowana jest codziennie raz przy użyciu WorkManager
- Przeliczanie z waluty realizowane jest za pomocą współczynnika w bazie lub zapytaniem do API
- Aplikacja pozwala sprawdzić kursy na daną walute w wybranym przez użytkownika dniu
- Tworzenie wykresów za pomocą mpchart

## Wygląd :
- Fragment odpowiadający za konwersję walut
- Fragment odpowiadający za wybranie walut oraz utworzenie z nich wykresu
- Wyświetlanie walut ulubionych/najczęściej konwertowanych
- Wizualizacja spisu współczynników przeliczania odpowiedniej waluty w wybranym dniu
