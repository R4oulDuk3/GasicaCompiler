# MJProjekat

## Naslovna Strana
- **Naziv Projekta:** Realizacija Kompajlera za Mikrojavu
- **Datum:** 24.08.2023.
- **Izradio:** Gavrilo Vojteški

---

## Kratak Opis Postavke Zadatka
Cilj projektnog zadatka je razvijanje kompajlera za Mikrojavu koji omogućava prevodjenje sintaksno i semantički ispravnih Mikrojava programa u Mikrojava bajtkod.

---

## Opis Komandi za Rad sa Projekatom

1. **Generisanje Java koda pomoću alata:**
   - Leksička analiza: `ant lexerGen`
   - Sintaksna analiza: `ant parserGen`
2. **Prevođenje Koda Kompajlerom:** `ant compile`
3. **Pokretanje i Testiranje Rešenja:** 
   - Pokretanje sa disassembler: `ant disasm`
   - Pokretanje objekta: `ant runObj`
   - Debugiranje objekta: `ant debugObj`

---

## Novouvedene Klase i Njihov Opis

1. **CodeGenerator:** 
   - **Opis:** Ova klasa je zadužena za generisanje koda. Prevodi sintaksno i semantički ispravne programe u izvršni oblik za Mikrojava VM. 
2. **SemanticAnalyzer:** 
   - **Opis:** Klasa obavlja semantičku analizu koda. Formira se na osnovu apstraktnog sintaksnog stabla koje je nastalo kao rezultat sintaksne analize. Ukoliko izvorni kod ima semantičke greške, prikazuje se adekvatna poruka.

