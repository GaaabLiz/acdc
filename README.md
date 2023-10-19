# Compilatore da AC a DC

### Introduzione
Questo compilatore è stato sviluppato come progetto per il corso di Fondamenti, Linguaggi e traduttori dell'università Del Piemonte Orientale.

### Linguaggio AC
In ac ci sono:
- 2 tipi di dato interi e floating point. Un letterale intero è una sequenza di cifre; floating point è una sequenza (non vuota) di cifre seguita da “.” seguita da almeno 1 cifra e al più 5 cifre; se volete usare usare una definizione più restrittiva fatelo pure!
- variabili che sono stringhe contenenti solo i 26 caratteri dell’alfabeto inglese minuscoli. Una variabile deve essere dichiarata prima di poter essere usata (in una espressione);
- dichiarazioni: float o int seguiti da una variabile
- 2 istruzioni: assegnamento e stampa. sintassi dell’assegnamento: variabile “=” espressione. sintassi della stampa: print variabile.
- Le espressioni possono essere letterali (interi o floating point), variabili oppure somma, sottrazione, moltiplicazione o divisione di espressioni.
- Una espressione di tipo int può essere convertita automaticamente a float (se necessario) e nessun altra conversione è possibile.

### Linguaggio DC
Il linguaggio target dc è un calcolatore “stack based” (cioè usa la notazione polacca inversa) cross-platform. È una delle prime applicazioni scritte per Unix.
Si usa, da terminale, digitando dc e poi a capo. A questo punto si scrivono le espressioni da valutare. Per stampare si digita p (e q per uscire). Gli operatori sono: + (addizione), - (sottrazione), * (moltiplicazione), / (divisione), ecc.. Si può specificare la precisione delle operazioni, dicendo quante cifre decimali considerare. Il default e’ 0. Per cui, 5 4 / valuta a 0.