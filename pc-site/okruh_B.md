## Oblast B

### Techniky přenosu dat

#### Fyzická přenosová média

- kovové (kroucená dvojlinka, koaxiální kabely)
- optické (optická vlákna)
- bezdrátové (radio, infračervené přenosy)
- přenos elektromagnetického vlnění s různými charakteristikami (elektrické signály - proud a napětí, světlo - intenzita, radiové vlny - frekvence, intenzita, fáze)
- nechtěné změny signálu:
	- útlum (zeslabení)
	- zkreslení
	- rušení
- teoreticky jsou objektivní - přijmou stejný signál jako byl vyslán, v reálu neoptimální cesty
- potenciál přenosu je limitován, schopnost přenosu závisí na frekvenci a způsobu přenosu, také vzdálenosti

#### Analogové a digitální přenosy

- analogové - přímo měříme velikost veličin
- digitální - jednotlivým hodnotám přiřazeny intervaly
- fyzické cesty mohou přenést jen určitou analogovou kvalitu - mění se jen interpretace
	- analogový - nikdy nebude optimální, informace se vždy poškodí, propojováním cesty a prvků se to zhorší
	- digitální - může být optimální, vyšší efektivita, signál může být znovuobnoven - nevadí propojování cest

#### Tvary a vlastnosti křivek

- tvary křivek - základní tvar grafu jako funkce v čase
	- čtvercový
	- trojúhelníkový
	- sinusový $y=A\cdot\sin(\omega\cdot t+\varphi)$
- furierova transformace - vlna může být rozložena na součet sinusovek
- v reálu omezené pásmo frekvencí, ostré přechody jsou komplikované kvůli useknutí vyšších frekvencí

#### Přenosy v základním pásmu

- nemodulované
- sekvence elektrických/světelných pulzů přímo s kódovanými daty (frekvence blízká nule, čtvercová vlna, celé pásmo nese jen 1 datový signál)
- jednodušší implementace, kratší vzdálenosti (většinou drátová média)
- frekvence změny signálu - frekvence změny dat
	- polarita - počet úrovní rozeznání (unipolární jen low-high, bipolární je negative-positive-zero)
	- NRZ - non return to zero, RZ - return to zero
	- bifázové - alespoň jedna změna za periodu
	- kódování - buď stabilní hladina při tiku, nebo směr změny signálu

#### Principy linkových kódů

- informace reprezentována různými výškami signálu nebo hranami, signál vysílán po určitou dobu
	- unipolární (nahoře 1V - 1, dole 0 V - 0, drží se po celý interval)
	- bipolární RZ (polovinu intervalu v -1V = 0, pak v 0V, polovinu intervalu v 1V = 1, pak 0 V)
	- bipolární NRZ (stejné jako uni, ale 1 = 1 V, 0 = -1 V)
	- manchester (v polovině intervalu je buď rostoucí hrana = 1, nebo klesající hrana = 0, mezi 1 V a -1 V)
- většinou kombinace technik, high lvl (sekvence logicky uspořádána a převedena)

#### Problém synchronizace

- bitová perioda = interval na poslání 1 bitu
- odesílatel musí být synchronizován s příjemcem, aby mohly být signály korektně přijaty
- jinak může dojít k posunům dat, ztrátě bitů

#### Techniky synchronizace

- separátní clock signál - neužitečné
- vlastní-hodiny (hodinový signál je v tom přenosu zahrnut, nutnost dostatečného počtu přenosů v signálu)
	- clock recovery - zjištění času
- isosynchronní techniky (čas i data najednou) přímá recovery (tiky jsou dány přenosem, redundantní kódování), nepřímá recovery (tiky odvozeny od sekvence dat, nesmí být hodně stejných bitů za sebou, bit stuffing a blokové kódování)
	- redundantní - každý bit alespoň 1 změnu (manchester - ethernet 10 Mb, 100% overhead - dvojitá šířka pásma, bipolar RZ)
	- bit stuffing - uměle daný opačný bit po každém dlouhém přenosu stejných symbolů, 0% overhead
	- scrambling - bity mixovány s pseudorandom bity, příjemce to musí být schopný regenerovat
- anisynchronní - čas a data v rozdílné časy

#### DC komponenta a disparita

- průměrná amplituda vlny
- na velké vzdálenosti je přenos DC komponenty špatný, chceme balancování DC - žádná průměrná amplituda
	- konstantně vážený kód - symbol je vybalancovaný
	- párovaný disparitní kód - balancování mezi jednotlivými symboly
- průběžná disparita - rozdíl počtu 0 a 1, chceme vyváženou disparitu

#### Blokové kódování

- ntice bitů přeloženy do ktic, fixovaný slovníkový překlad
- ktice s nejvíce střídáním jsou preferovány, existují alternativy k jedné vstupní ntici, několik ktic nevyužitých (kontrolní signály, erory)
- nezanedbatelný overhead
- 4B5B - 4 bits na 5 bits, fast ethernet (100 Mb), 25% overhead
- 8b/10b - skupiny 5+3 na 6+4, limit 5 stejných bitů, garance DC komponenty i disparity max +-2, gigabit ethernet (1 Gb), HDMI, SATA, USB, 25% overhead

#### Přenosy v přeloženém pásmu

- modulované přenosy
- harmonická nosná vlna (vyšší frekvence, využívány jenom frekvence okolo nosné vlny, abychom zůstali v pásu)
- na delší vzdálenosti, vyšší využití (bezdrátová média a optika)

#### Kvadraturní amplitudová modulace

- informace reprezentována změnami v parametrech vlny

#### Zajištění transparence
#### Principy a cíle framingu
#### Techniky stuffingu
#### Znakově orientované protokoly
#### Bitově orientované protokoly
#### Bytově orientované protokoly



### Síťová vrstva a směrování
​    (B18) Routing a forwarding
​    (B19) Směrovací a forwardovací tabulky
​    (B20) Obvyklé přístupy směrování
​    (B21) Klasifikace směrovacích přístupů
​    (B22) Adaptivní a neadaptivní směrování
​    (B23) Statické (fixní) směrování
​    (B24) Záplavové směrování
​    (B25) Techniky řízené záplavy
​    (B26) Centralizované směrování
​    (B27) Distribuované izolované směrování
​    (B28) Metoda zpětného učení
​    (B29) Metoda zdrojového směrování
​    (B30) Distribuované neizolované směrování
​    (B31) Směrování distance-vector
​    (B32) Problém count to infinity
​    (B33) Vlastnosti protokolu RIP
​    (B34) Směrování link-state
​    (B35) Srovnání principů RIP a OSPF
​    (B36) Velikost tabulek a aktualizací
​    (B37) Směrovací domény
​    (B38) Techniky směrování na L2

Transportní vrstva a protokoly
    (B39) End-to-end komunikace
    (B40) Transportní spojení
    (B41) Srovnání protokolů TCP a UDP
    (B42) Bytový stream TCP
    (B43) Navazování spojení
    (B44) Zajištění spolehlivosti
    (B45) Detekce poškozených bloků
    (B46) Kontrola parity
    (B47) Kontrolní součty
    (B48) Cyklické redundantní součty
    (B49) Potvrzovací strategie
    (B50) Jednotlivé potvrzování
    (B51) Kontinuální potvrzování
    (B52) Potvrzování s návratem
    (B53) Selektivní opakování
    (B54) Metoda posuvného okénka
    (B55) Problém řízení toku
    (B56) Předcházení zahlcení sítě
    (B57) Spolehlivost v TCP
    (B58) Možnosti zajištění QoS
    (B59) Principy řešení DiffServ
    (B60) Principy řešení IntServ
    (B61) Opatření client buffering