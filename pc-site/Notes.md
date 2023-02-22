# POČÍTAČOVÉ SÍTĚ

## Přehled

1. [Základní pojmy a paradigmata](#základní-pojmy-a-paradigmata)
2. [Taxonomie počítačových sítí](#taxonomie-počítačových-sítí)
3. [Vrstvy a vrstvové modely](#vrstvy-a-vrstvové-modely)
4. [Techniky přenosu dat](#techniky-přenosu-dat)
5. [Síťová vrstva a směrování](#síťová-vrstva-a-směrování)
6. [Transportní vrstva a protokoly](#transportní-vrstva-a-protokoly)
7. [Internetworking](#internetworking)
8. [Adresy a adresování](#adresy-a-adresování)
9. [Protokoly IPv4 a IPv6](#protokoly-ipv4-a-ipv6)

## Základní pojmy a paradigmata

Zpět na [Přehled](#přehled).

### (A01) Proudové a blokové přenosy

Odpověď na otázku _*V jaké formě budou data posílaná?*_

#### Proudové přenosy

Posíláme souvislý nestrukturovaný proud (sekvenci) dat, po jednotlivých symbolech (bits, bytes, words).

Vlastnosti:

- Zachovává pořadí (princip FIFO fronty)
- Vhodnější pro dvou-bodová spojení

Příklady:

- L1: Ethernet (bits), Wi-fi (words),
- L4: TCP (bytes)

#### Blokové přenosy

Data rozdělíme do menších celků (bloků)

- fixní velikost bloků (nepkratické)
- proměnná velikost bloků (typicky s horním omezením, může být i spodní)

Blok je strukturovaný - header, body, footer

Vlastnosti:

- nemusí být zachované pořadí bloků
- bloky po sobě ani nemusí bezprostředně následovat (mohou být prodlevy)
- bloky často obsahují metadata (adresa odesílatele v případě poruchy, adresa příjemce, pořadí bloků pro zpětné sestavení, identifikace přenosu pro umožnění více přenosů zároveň)

Terminologie bolků podle vrstev:

- L2: rámce (ethernet), buňky (ATM)
- L3: datagramy (IPv4), pakety (IPv6)
- L4: segmenty (TCP), datagramy (UDP)
- L7: zprávy (HTTP)

#### Kombinace obou přístupů

- posílání bloků proudem (framing)
- posílání bloků jinými bloky (encapsulation)
- posílání proudů bloky

### (A02) Spojované a nespojované přenosy

Odpověď na otázku _*Musíme se domluvit na přenosu dopředu?*_

#### Spojované

Komunikující strany se musí dohodnout na navázání/udržení/přerušení spojení. Pořadí dat musí být zachováno.

Navázání spojení zahrnuje: obě strany existují, jsou schopny se najít, souhlasí se spojení, domluví se na parametrech přenosu (alkoace zdroj, vytyčení cesty...)

Příklady:

- L2: ATM
- L4: TCP (spojovaný, ale iluze, protože L3 není)
- L7: HTTP, SMTP, POP3 (protože používají TCP)

Přenos má stavový charakter:

- komunikuji/nekomunikuji
- je třeba se vyhnout dead lockům
- je třeba detekovat a řešit nestandartní situace

#### Nespojované

Odesílání jednotlivých zpráv (tzv. datagramů). Pořadí dat není zachované (analogie s poštou, pošlu tři dopisy, každý může dojít jindy).

Není navázáno spojení (příjemce ani nemusí existovat). Bezstavový charakter. Každý datagram cestuje nezávisle. Datagram musí obsahovat kompletní identifiakci přijemce.

Příklady:

- L4: UDP
- L3: IP, ICMP
- L2: Ethernet

### (A03) Přepojování okruhů a paketů

Odpověď na otázku _*Jak se data dostanou k příjemci*_

#### Přepojování okruhů

První vytvoříme okruh, přes který se posílají data. Najde se a vytyčí se cesta (fyzicky nebo virtuálně)

Vlastnosti:

- přenos musí být spojovaný
- Iluze přímého spojení s protistranou
- Nízká, konstantní latence (hledání cesty pouze jednou na začátku)
- zachovává pořadí
- v okruhu máme vyhrazenu exkluzivní kapacitu - můžeme poskytovat garantované přenosy
- podporuje proudové i blokové přenosy
- využití v telekomunikacích.

#### Přepojování paketů

Pro každý blok dat se najde cesta zvlášť. Po cestě prochází skrz přepojovací uzly. Uzel má buffery pro příchozí a odchozí data → přijme blok a umístí ho do příchozího bufferu → postupně zpracovává bloky z příchozího bufferu → vybere blok, vybere mu nový směr a dá ho do odpovídajícího odchozího bufferu → zpracovaný blok čeká v odchozím bufferu na odeslání

Vlastnosti:

- odolné vůči chybám
- princip fungování počítačových sítí
- přenos musí být blokový - metadat musí obsahovat adresu příjemce a odesílatele
- Kapacita cest a přepojovacích uzlů je omezená a sdílená
  - dostupná kapacita může být nedostatečná a nadbytečné bloky mohou být zničeny!
- vyšší latence (náročný proces v uzlech)
  - je proměnlivá - závisí na aktuální vytíženosti cest a uzlů

Příklady:

- L2: přepínače, switche
- L3: směrovače, routery

### (A04) Virtuální okruhy a datagramová služba

#### virtuální okruhy

Přepojování paketů (proto virtuální) pomocí spojovaného přenosu. Virtuálně se vytyčí cesta (VCI - virtual circuit identifier), jednotlivé přepojovací prvky si pamatují cestu. Bloky v sobě nesou ID okruhu a všechny jsou směrovány po tomto jednom okruhu. Takto funguje např. ATM na L2

#### Datagramová služba

Posílání paketů nespojovaným přenosem. Takto funguje např. Ethernet na L2

### (A05) Spolehlivé přenosy a nespolehlivé přenosy

Odpověď na otázku _*Jakou úroveň spolehlivosti přenosu požadujeme?*_

#### Spolehlivé

Přenos má povinnost zajistit spolehlivost.

- Detekce chybových situací: paritní bity, kontrolní součty, CRC... (nikdy to ale není 100%)
- řešení chybových situací: samoopravné kódy (ne příliš praktické), opakovaný přenos (pozitivní/negativní acknowledgment, negativní = pošli zprávu znovu)

Následky:

- narušení plynulosti (při chybách)
- větší množství zpráv (acknowledgments a nutnost znovuposílání)
- větší zprávy (kvůli zabezpečovacím údajům)
- vyžaduje vyšší přenosovou a výpočetní kapacitu
- neexistuje absolutní spolehlivost

Příklad: TCP (L4)

#### Nespolehlivé

Když dojde k chybě, nebudeme ji řešit (chyby ale nevytváříme cíleně :D)

- tam kde je důležitější pravidelnost a latence, než přesnost

Příklady: UDP (L4), IP (L3), Ethernet (L2), ATM (L2)

### (A06) Garantované a negarantované služby

Odpověď na otázku _*Bude po celou dobu dostupný dostatek zdrojů?*_

#### Garantované

Garance dostatečného možství zdrojů (přenosová a výpočetní kapacita) po celou dobu přenosu. Zdroje jsou zajištěny dopředu při navazování spojení (exkluzivní kapacita) - zabere se prostor pro maximální očekávanou zátěž - často nevyužita kapacita (nemůže být využita nikým jiným), protp drahé a neefektivní. Realizace vždy pomocí přepojování okruhů.

#### Negarantované

Negarantované - jednodušší a efketivnější (škálováno na průměrnou zátěž). Při vyšším zatížení můžou dojít zdroje - zaplní se buffery, nebo procesor - některé pakety musí být zahozeny (jaké?)

### (A07) Princip Best Effort a Quality of Service

Jaké pakety zahodit, když dojdou zdroje u negarantovaných přenosů?

- Best effort
  - pakety jsou doručovány tak dlouho, jak je to možné. Pokud doručení není možné, začnou se zahazovat náhodně
  - př. IP (L3), Ethernet (L2), ATM (L2)
- Quality of Service (QoS)
  - relativní QoS - princip prioritizace, jako první zahazujeme data s menší prioritou
  - absolutní QoS - musíme dopředu zarezerovat zdroje. Pokud není kapacita, musí se rezervace odmítnout a tudíž nemůže dojít k přenosu (podobné jako přepojování paketů)

### (A08) Svět telekomunikačních a počítačových sítí

#### Telekomunikační sítě

Starší, vznikl v době kdy byla komunikace vnímána jako strategická záležitost.
Pro hlasové hovory, nově data.
Pracují s chytrou sítí a hloupými koncovými uzly
síť - páteřní síť, telefonní ústředny, jednoduchý centrální management, ale drahá jedno-účelová záležitost
koncová zařízení - nevyžadujís skoro nic
vlastnosti - přepojování okruhů, spojované, spolehlivé a garantované přenosy (QoS)

Zdroje jsou limitované, nešlo uspokojit každého, byla snaha prodávat exkluzivní rezervaci zdrojů (báli se poskytovat nespolehlivé služby)
Regulace ze strany státu a vlád, ale postupně se liberalizuje (monopoly se stávají incumbenty)
Majiteli telekomunikačních sítí nejsou jejich koneční uživatelé.

#### Počítačové sítě

Novější, dříve data, nove multimediální přenosy.
Síť hloupá, koncová zařízení chytrá (levnější)
Síť - co nejminimalističtější, levná

přepojování paketů, nespojované, nespolehlivé a negarantované přenosy (best effort)

I zde je nedostatek zdrojů, ale vždy se předpokládalo, že jich bude dost (hybnou sylou jsou technické faktory)

Od začátku liberalizováno -> problém s kompatibilitou a sjednocením.

### (A09) Hospodaření se zdroji

- Moore's law - každé dva roky se zdvojnásobí výkon (počet tranzistorů na mm) (postupně se zpomaluje, půdobně to bylo každý rok).
  - Z moorova zákona vyplývá, že každé dva roky máme srovnatelnou výpočetní sílu za poloviční cenu
- Gilder's law - přenosová cesta(?) se každý rok ztrojnásobuje
- Disk law - uložná kapacita se každý rok přibližně zdvojnásobí

## Taxonomie počítačových sítí

Zpět na [Přehled](#přehled).

Dva základní druhy sítí:
Broadcast networks (distribuční) 1:n
chceme realizovat broadcastové přenosy (televizní/radiový program)
nemá smysl řešit směrování, všechna data patří všem
DVB (DVB-T2, DVB-C2, DVB-S2), DAB
Sítě s přepojováním 1:1
založeno na přepínání okruhů/paketů
Okruhů: spojované, garantované, přenes bloků/proudu, spolehlivý/nespolehlivý
Paketů: přenos bloků, spojované (virtual circuits - spolehlivé/nespolehliví, best effort/QoS) nespojované (datagramy, spolehlivé/nespolehlivé a Best effort/relativní QoS)

Telekomunikační sítě
distribuční (televize) nebo přepínané (telefony)
jednoúčelové
princip chytrých sítí a hloupých koncových zařízení
Datové sítě
hloupá síť, chytrá koncová zařízení
(lze vnímat jako podpřípad počítačových sítí)
vznikly a jsou provozovány telekomunikačními společnostmi
Počítačové sítě
opět přenos dat

### (A16) Telekomunikační sítě

Charakteristická vnitřní struktura:
páteřní (transportní) část - na nějakém území vybuduje základní infrastrukturu velkokapacitních cest a několika uzlů (centrálně řízené síťové prvky- telefonní ústředny), - cílem je překlenout dlouhé vzdálenosti (dnes třeba optickými kabely)
přístupová část - umožňuje připojení konkrétních koncových uživatelů do naší infrastruktury - point of presence - rozhraní páteřní a přístupové sítě, vytváří na menším území (v obci) vstupní bod pro koncové uživatele - customer premises - přípojka kde se nachází odběratel služby

### (A17) Pevná telefonní síť

SPT Praha-> (1993) SPT Telecom -> (2000) Český Telecom -> (2006) Telefónica O2 Czech Republic -> (2014) O2 Czech Republic -> 2015 CETIN (Česká telekomunikační infrastruktura)
páteř - 38 000 km optických kabelů
přístupová - 20 000 000 km kroucených dvoulinek (nejpodstatnější vývoj v 90 letech)

Páteřní síť - 2 mezinárodní ústředny - 8 tranzitních ~ na 14 krajů - 140 lokálních (místních, v čechách "řídící") ~ na 77 okresů - ~3 tisíce předsunutých ~ na 6 tisíc obcí ( s 15000 částmi)

### (A18) Mobilní síť GSM

Druhá generace
Páteřní část - network switching subsystem - Mobile Switching Centers (13) - ústředny - Brány (Gateway MSC) do jiných sítí

Přístupová část - base station subsystem - GERAN (GSM EDGE Radio Access Network) - (4500) základem jsou základnové přenosové stanice (Base Transceiver Station BTS) - vysílací věž - zajišťuje komunikaci koncových uživatelů (telefonů) se zbytkem sítě - BTS formuje buňku označující nejbližší geografické území, může mít více antén (každou pro jiný sektor s vlastní frekvencí) - (150) BSC - Base Station Controller - řídiče BTSek - 1300 sektorů

### (A19) Přístupové sítě

POP (point of presence) jsou místní telefonní ústředny, nebo předsunuté ústředny

ústředna má hlavní rozvaděč, do kterého se zapujují účastnická vedení (kroucené dvojlinky) vedoucí až na přístupová místa (mezi ústřednoua bytem je jedna dvoulinka)
místní smyčka nemusí být přímá, může projít síťovými rozvaděči, navíc jsou ve svazcích
jednotky kilometrů (1-2, <5)
přípojek je asi 8M účastnických vedení (asi 700 tisíc se aktivně využívá, maximum bylo v 2011 kdy jich bylo využíváno 3.8M) - v 90 letech na přípojdu čekalo 600 tisíc lidí (na konci 80tých bylo čekatelů méně, ale o to delší doby)

(převážně fyziského charakteru jsou) celoplošného charakteru, husté - pokrývají velké územní
Buduje se k potenciálním zákazníkům a pak se k ní mohou připojovat.
Prochází veřejnými prostory - výkopy, náročný proces (85% nákladů na infrastrukturu jsou zemní práce) - vyžaduje značné plánování a synchronizaci s kýmkoliv dalším kdo chce ve městě kopat - když už se to dělá, dělá se to pořádně, nejlepšími materiály a předimenzovaně
"poslední míle" - připojení koncových uživatelů (z pohledu operátorů, naopak první míle)

Chci vybudovat síť - vše je drahé a náročné - v dnešní dově buď bezdrátově, nebo optické kabely - historicky původní monopoly byli donuceni zpřístupnit svou infrastrukturu - NEBO PPŘEKRYVNÁ

### (A20) Překryvné přístupové sítě

Využiji existující infrastrukturu a přizpůsobím si ji tak abych nad ní mohla vybudovat vlastní infrastrukturu

- pevné linky (miliony kroucených dvoulinek) - původně slouží k posílání hlasové služby, dnes internet
- přenosy silové elektřiny
- coaxiální kabely kabelové televize

je třeba rozšířit nebo upravit původní síť
-> používáme odlišné frekvence (pak o sobě přenosy ani neví)
-> zapouzdrování dat do dat které síť už umí přenášet

### (A21) Technologie xDSL

Digital Subscriber Line - využití struktury původních telefonních linek k datovým přenosům
kroucené dvoulinky, původně měly přenášet analogové hlasové hovory (300 Hz-3.4 kHz)

Frekvenční multiplex

- využijeme stejnou dvoulinku, ale svá data budeme přenášet na jiné frekvenci
  - nad 3.4kHz (v nadhovorovém pásmu)
- dochází i k digitalizaci a je třeba řešit především vzdálenost (přenosové cesty mají rušení a útlum)

Technické řešení:
DSL modem (bere digitální data a moduluje je na analogový signál)
<-> splitter (spojuje signály na vyšších a nižších frekvencích a naopak)
<-> místní smyčka (přenáší obojí)
<-> point of presence (zase splitter a modem) - zde jsou ale tisíce zařízení - je zde třeba integrované zařízení - DSLAM (DSL Access Multiplexer) - toto řeší problematiku splitterů a modemů pro všechny linky najednou - způsobí přesměrování do jiné páteřní sítě (páteřní telekomunikační síť je tedy jiná, protože by elkový datový přenos nevydržela)

u DSL technologií vzdálenost zhoršuje kvalitu, útlum a zkreslení a snižuje přenosovou rychlost
-> proto vznikají předsunuté ústředny, aby se zkrátila vzdálenost

(1998) ADSL - Asymetric Digital Subscriber Line
(25kHz-1.1MHz) - vzdálenost několika kilometrů - maximální přenosové rychlosti za ideálních podmínech 8Mb/s downstream, 1.5Mb/s upstream

(2006) VDSL2 - Very High-Speed Digital Subscriber Line
(až 35 MHz) - do 300m - 200Mb/stahování, 100Mb/s odesílnání

### (A22) Technologie PLC

Nad rozvody 230V/50Hz elektřiny - Na vyšší frekvenci realizujeme datové přenesy - různé standardy v různých zemích - na sloupech vyskového napětí nejsou kabely stíněné a fungují jako antény - je třeba kontrolovat vyzařované radiové signály aby nerušily ostatní signály - i v konkrétní domácnosti se jedná o zašuměné prostředí - vypnutí/zapnutí spotřebiče ovlivní všechny datové přenosy - transformery datové přenosy naruší a obecně na to dráty nejsou dělané

Powerline communcations - lze využít na velké vzdálenosti, i na území celého státu (pak jde ale využívat pouze nízké frekvence, což znamená velmi nízké přenosové rychlosti) - užívá se správci elektrické soustavy pro monitorování a posílání spínacích signálů

Last mile - drahé a v praxi nepoužívané - Broadband over Powerline - Střední napětí desítky kV mezi transformátory - Na nízkých 230V jiný přístup

Last Meter
Homeplug - vnitřní rozvody za domácím elektroměrem lze využít k částečnému vybudování domácí sítě - až 500Mb/s, pod 100MHz

### (A23) Technologie DOCSIS

Rozvody kabelové televize - coaxiální kabely pro analogový jedno-směrný přenos
-> chceme digitální a obousměrné

EuroDOCSIS (Data Over Cable Service Interface Specification)

- 1997 1.0 - 40Mb/s down 1Mb/s up
- 2018 4.0 - 10 Gb/s down, 6Gb/s up

- nezáleží na vzdálenosti, jedná se o vyšší rychlosti (protože se jedná o lepší kabely)

Dnes se vše řeší kombinací optických vláken a coaxiálních kabelů - Point of presence je CMTS (Sable Modem Termination System) - v budově kde je distribuční hub, stejně jako DS hub - Optická vlákna vedoucí k optickým uzlům na půl cesty, k jednotlivým zákaznkům coaxiální kabely - customer premise u zákazníků CM Cable Modem který vše uvádí do provozu

### (A24) Optické přístupové sítě FTTx

Aktivní - v rámci infrastruktury využívají aktivní síťové prvky - rozbočování, větvení, zesilování, tvarování, vylepšován systému - vyšší přenosové kapacity na mnohem další vzdálenosti - mnohem dražší

Pasivní - zakopané pasivní elementy - možno pouze na menší vzdálenosti - levnější řešení

Fiber To The (Something) - pasivní - rodina technologií - "kam až s optikou ke koncovému zákazníkovi jdete?" - o to čím blíž to bude dražší a komplexnější - značná část od point of presence bude optikou, ale poslední čát bude řešena cloaxiálním kabelem nebo ethernetem...
FTTH - home
FTTB - building
FTTC - curb - na obdubník
FFTN - node - využívá se v DOCSIS

### (A25) Privátní datové sítě

Hlavním účelem datových sítí je přenášet data. Hloupé sítě a chytrá koncová zařízení.
Přenášejí data, ale vznikly ve světě telekomunikačních sítí

Soukromá datová síť - vlastník je uživatelem (vybudoval ji sám pro sebe)

- majitel rozhoduje o všech technologiích, protokolech, adresách, ...
- drahé -> budou si to tedy moci dovolit pouze velké subjekty
  - např ministerstvo vnitra -> přebrala česká pošta
    - zahrnuje veškerou kritickou infrastrukturu státu (policie, záchranná služba, ...) a díky předimenzovanosti i pošta (datové schánky)

### (A26) Veřejné datové sítě

Telekomunikační operátor vybuduje veřejnou datovou síť a nabízí ji zákazníkům.
Vlastník není zamýšleným uživatelem - platí se za objem přenesených dat, pořet spojení nebo jejich trvání
K připojení je třeba kvalitní a podrobná dokumentace jak se připojit

Výhoda - mohou se připojit i menší subjekty. - flexibilní, nejedná se o investici - sdílena všemi uživateli (bezpečnost), o všem rozhoduje vlastník

### (A27) Virtuální privátní datové sítě

Kombinace předchozích
Sdílená veřejná infrastruktura, navozujeme iluzi samostatných logických sítí. Tyto sítě jsou odděleny a uživatelé se vzájemně mezi různými stěmi nevidí.
Levnější a rozumný kompromis v podobě trochy rozhodování (sdresní prostor, uživateké, oprávnění)

### (A28) Sítě PAN, LAN, MAN, WAN

Hloupá síť, chytrá koncová zařízení.

PAN - Personal Area Network

LAN - Local Area Network

MAN - Metropolitan Area Network

WAN - Wide Area Network

### (A29) Personal Area Networks

1-10m - osobní prostor konkrétního uživatele

- spojení zařízení které chcem používat - připojení periferií, tiskárna, sluchátek... drátové (USB) bezdrátové (wi-fi, bluetooth, IrDA)

### (A30) Local Area Networks

10m - 1km - sítě v rámci domácnosti, firmy školy

- propojování počítačů/serverů/notebooků - pokud patří k sobě.
- v širším pojetí se zde mohou vyskytovat směrovače na 3. vrstvě -> pak spíše soustava vzájemně propojených sítí
- v užším pojetí máme pouze fyzickou a linkovou vrstvy - opakovače-repatery, přepínače-switche, mosty-bridge
- Ethernet
- dosahujeme kratší latence přenosů a vyšší spolehlivosti přenosů
- může zde být i omezená dostupnost jednotlivých zařízení

### (A31) Metropolitan Area Networks

1km -- 100km - kampus, město

- mohou se zde přippojovat i koncoví uživatelé, ale hlavní je propojovat lokální sítě
- PASNET (Prague Academic and Scientific Network) - propojuje Akademii věd, UK, ČVUT a VŠE
- MEPNET (Metropolitan Prague Network) - jednotlivé městské části, knihovny, policie
- Ethernet, WiMAX, ATM, FDDI
- vlastněny a provozovámy skupinami právnických osob nebo městy, uživatel nemusí odpovídat vlastníkovi
- překlenují veřejné prostory

### (A32) Wide Area Networks

100km+ - stát, kontinent...

- propojují jednotlivé sítě typu LAN nebo MAN
- přenost dat na větěí vzdálenosti (mezi státy a podmořskými kabely mezi kontinenty)
- vlastníky jsou nadnárodní společnosti nebo poskytovatelé připojení a operátoři
- CESNET (akademická výzkumná infrastruktura) - výpočtní datová centra
  - propojuje všechna důležitá univerzitní města
  - 100Gb/s linka do celoevropského systému G=EAN
  - 10Gb/s k Internet via Telecom italia (Tier 1 provider)
- L1 -optická vlákna, L2 - MPLP, SONET/SDH, L3 - TCP/IP
- čím delší vzdálenost, tím vyšší latence, nižší spolehlivost, nesymetrická topologie, permanentní dostupnost

### (A33) Architektura Internetu

Původně měl pouze jednu páteřní síť

ARPANET - finincováno americkým ministerstvem obrany - první síť testující mechanismus přepojování paketů - pak převedeno na civilní zdroje
NSFNET - pro výzkum a výuku

Časem existovalo sítí více, které si konkurovaly jako alternativy.

Dnes máme vrstevnatou architekturu

- Páteř - sítě všech Tier 1 Internet Service Providers
  - propojeny pomocí Internet eXchange Points
    - propojování libovolných sítí
    - NIX.cz (Neutral Internet eXchange) - 195 sítí, 10Tb/s

Tier 1

- 15 firem (Amerika - AT&T, Verizon, německo Deutsche Telekom, ...)
- i více než 800 000km optických cest
- operátor sem patří, pokud vlastní fyzickou infrastrukturu dostatečně rozsáhlou na to realizovat přímý přístup do jakékoliv sítě na světě aniž by za toto musel ostatním platit
- vzájemně si za případný peering neplatí, je to pro ně marginální záležitost

Tier 2

- část dat může vyměňovat zdarma, ale do netriviální části si musí buď kupovat tranzit od Tier 1, nebo zajišťovat peering na stejné úrovni
- státy / regiony

Tier 3

- poslední, nabízí služby koncovým uživatelům, mají nejvýše vlastní přístupovou síť, za veškerý tranzit si musí platit (i za peering)

### (A34) Peeringové a tranzitní přenosy

Peeringový přenos

- dva poskytovatelé se domluví, že si mezi sebou budou vyměňovat konkrétní data.

Tranzit

- vše co je směřováno až na Tier 1

### (A35) Intranet, extranet a darknet

Klasifikuje jakým způsobem jsou využívány služby a prostředky

Intranet - sdílené tiskárny, data a programy v rámci firmy

Extranet - nabízení služeb venkovním uživatelům

Darknet - překryvná síť nad internetem, anonymizovaný přístup, spíše využíván k nelegálním aktivitám

## Vrstvy a vrstvové modely

Zpět na [Přehled](#přehled).

### (A36) Principy vrstvových modelů

Rozdělení komplikovaného konceptu do menších celků - modely které spolu spolupracují a samy řeší dílčí problémy.
Různá úrov abstrakce (vyšší vrstva, více abstrakce)
Každá vrsstva něco nabízí bezprostředně vyšší vrstvě a využívá služeb nižší vrstvy.
Základní požadavky: - každá vrstrva definuje své veřejné rozhraní - vrsta se zavazuje k řešení konkrétních úkolů - veškerá implementace je skryta - vrstvy hsou nezávislé - nabízí alternativní řešení - flexibilnější

### (A37) Vertikální komunikace

Komunikace naskrz vrstvami
Při komunikaci každá zpráva propadne vrstvami uzlem dolů, je poslána a pak stoupá nahoru.
Odesílatel si připraví data a předá je nejbližší nižší vrstvě na odeslání-> až fyzická něco odesílá.
Příjemce je zpracuje, rozbalí a pošle nahoru.

### (A38) Horizontální komunikace

Komunikace uvnitř vrstvy
Komunikace odpovídajících si entit uvnitř jedné vrsty vyskytujícíh se v různých uzlech (servery, počítače, nebo switche routery) - L1 opačné konce drátů - L2 protější síťová rozhraní - L3 uzly - L7 běžící aplikace
V dané vrstvě zpravidla probíhá více komunikací najednou.
Je třeba domluvit ti pravidla (prostřednictvím protokolů)
Asynchronního charakteru - po odeslání je třeba řekat na reakci. Kromě fyzické vrstvy čistě virtuální charakter - pracujeme pouze s iluzí.

### (A39) Principy síťových protokolů

konkrétní fixní a předem daná pravidla zajišťující fungování horizontální komunikace
pravidla musí být nezávislá na platformě i konkrétní implementaci
Formálně definuje: - veřejné rozhraní - co dělá + technické detaily vertikální komunikace - vzájemná pravidla naskrz horizontální komunikací - stavová komunikace - formát dat - strukturu a sémantiku dat - se kterými budeme pracovat

Náleží jedné vrstvě a nelze rozkládat naskrz vrstev (ale může ve vrstvě koexistovat více protokolů)

- mohou být alternatvami (TCP a UDP na L4)
- mohou být komplementární - d2lají úplně jiné věci (SMTP, HTTP na L7)

Jedna z věcí které je třeba definovat: struktura dat
PDU - protocol data unit - jednotka dat se kterou protokol pracuje - frame, buňky, pakety - vnitřní struktura - hlavička (odesílatel, příjemnce) - tělo (užitečná data která chce vyšší vrstva přenét) - patička (kontrolní součty)
MTU - MAximum Transition Unit - největší množství nákladu, které je vrstva ochotna převést

### (A40) Síťové modely a architektury

Model: počet vrstev, účely vrstev, jaké nabízí rozhraní, specifikace charakteru chování (spojovaný/nespojovaný, spolehlivý/nespolehlivý)
Architektura: model + implementace a konkrétní protokoly

### (A41) Referenční model ISO/OSI

MODEL Mezinárodní srandardizažní organizace - Open Systemc Interconnection Model
Pochází ze světa spojů - preferuje spojované přenosy, spolehlivost, garantovné přenosy
7 vrstev

Megalomanské ambice, představa standardizace jednotlivých koncových uzlů ( i aplikací)

prokázal se být neimplementovatelným
-> Referenční model, terminologie, hlavní úkoly které je třeba plnit.

### (A42) Význam vrstev ISO/OSI

Nižší vrstvy: (zajišťují přenost, směřují na konkrétní uzel)
L1 - fyzická vrstva
L2 - linková vrstva
L3 - síťová vrstva
Adaptační vrstva: (end-to-end komunikace)
L4 - transportní vrstva
Vyšší vrstvy:
L5 - relační vrstva
L6 - prezentační vrstva
L7 - aplikační vrstva

### (A43) Úkoly fyzické vrstvy

Chceme přes přenosové médium poslat bity.

Přenosové médium:

- vedené
  - kroucené dvoulinky, coaxiální kabely -> elektrické signály
  - optická vlákna -> světlo
- nevedené
  - bezdrátové -> radiové elektromagnetické vlny

posíláme konkrátní symboly
Vrstva neví co posílá, nerozlišuje mezi tím.
V reále jsou přenosové cesty utlumené, zkreslené, rušené... a přenosový potenciál je tedy vždy limitován.
Vždy jsme omezeni vzdáleností (zvyšuje vliv negativních jevů)

Přenášený signál jako takový je vždy analogový. Až při přijetí je třeba jej interpretovat jako analogový nebo digitální.

Při zasílání dat řešíme i další věci:
kódování (tvrdíme že posíláme bity, ale posíláme symboly)
modulování - vysílání signálů pro jednotlivé bity jako zvýšení, snížení je nepraktické
-> reálně posíláme sinusoidu ale měníme její amplitudu, frekvenci, nebo fázový posun (zde jsou pak uložena data)
časování (bitový interval - dostatečně dlouhý s mezerami mezi nimi)
synchronizace - odesílatel i příjemce mají vlastní hodiny a je třeba se synchronizovat aby p5íjemce uměl data rozeznávat
šířka páska - rozdíl mezi minimální a maximální frekvencí k dispozici (vyšší šířka pásma -> vyšší modulační rychlost -> pošleme více symbolů -> více bitů)

### (A44) Úkoly linkové vrstvy

V lokální síti chceme konkrátním příjemcům zasílat bloky dat

Opakovače, Switche, Mosty - přenos bloků dat mezi jednotlivými konkrétními uzly
Pracujeme s představou že naše síť má přímé spojení s každým uzlem sítě
Reálně může být síť komplikovaná -> poslaný rámec může projít celou sítí a být potenciálně přijat každým uzlem

Vnitřní struktura - jak skládat a projovat nebo rozdělovat segmenty - kde dát vnitřní zařízení a jak fungují - aplikujeme logickou topologii (ne nutně odpovídající fyzické, jak vypadá datový tok?) - sběrnice, hvězda, kruh, mřížka, hyperkrychle

Blok dat typicky doručujeme jednomu konkrétnímu příjemci - vyžaduje adresu. - MAC adresa - musí být v rámci sítě unikátní, slouží k identifikaci konkrétních uzlů - slouží k nalezení konkrétních uzlů - typicky přijímáme rámce které nám nepatří a díky adrese poznáme, zda nám patří
-> Je třeba zajistit přidělování adres (mohou být celosvětově jednoznačné, ač to není třeba) - EUI-48 (MAC-48) nebo EUI-64 - FC:77:74:19:41:1E
-> původně vymyšleno pro Ethernet, ale využívá se i u dalších analogicky fungujících sítí (Wi-Fi, Bluetooth)

Filtrování a forwarding

- topologie může být komplikovaná, vyplatí se tedy řešit filtrování
- filtrování - zamezaní zbytečného předávání
- forwarding - předávání pouze směrem kde se vyskytuje zamýšlený příjemce

Transparentnost - aby druhá vrstva dokázala fungovat, potřebujeme umět předávat data a zároveň kontrolní signály a metadata
-> Je třeba rozeznat co jsou metadata a co užitečná data (escaping - přepínání režimu, framing - hlavičkování, stuffing - umělé vkládání dalších bytů)
Framing - posíláme bloky - je třeba vymyslet fungování bloků - zkonstuujeme blok, přidáme náklad, předáme fyzické vrstvě (bloky = ethernetové rámce) - máme tedy maximální velikost užitečného bloku, který mumíme přenét - příjemce přijímá proud jednotlivých symbolů a musí umět jednotlivé bloky rozeznat -netriviální problém - druhá vrstva úmyslně vkládá bity navíc aby označila začátky a konce bloků, pomáhá synchronizaci
-> první a druhá vrstva spolu musí blízce komunikovat a spolupracovat

Původní představa byla taková, že přenosová média budou exkluzivní, reálně jsou sdílená
-> Je třeba druhou vrstvu na toto připravit -> K jedné přenosové cestě je připojeno více uzlů - v jednu chvíli může vysílat pouze jede
-> třeba vyřešit přenosové metody (soutěže, předávání tokenu)
-> je třeba aby vysílal pouze jeden (toto není v OSI zohledněno a vedlo k rozdělení druhé vrstvy)
MAC (Media Access Control) - podvrstva řešící přístup k médiím
LLC (Logical Link Control) - podvrstva nahrazující 2

### (A45) Úkoly síťové vrstvy

Globální kontext, chceme doručovat bloky konečným zamýšleným příjemcům napříč vzájemně propojenými sítěmi. - routing & forwarding

hop-to-hop (z uzlu na uzel) - skrze celou soustavu sítí chceme doručit IP paket mezi odesílatelem a příjemcem
V routerech řešíme routing a forwarding

Odesílatel si musí být vědom existence soustavy a vzájemné propojenosti sítí (znalost musí být, stačí ale částečná) - není iluze přímého spojení
Začneme u odesílajícícho uzlu a paket skáče přes routery až k příjemci

L3/4 Switche

Adresy a adresování: - IPv4 213.46.172.38 - adresy musí být celosvětově jedinečné a přidělované systematicky (uzly v jedné síti musejí sdílet prefix) - je třeba adresy přiřazovat konkrétním uzlům v naší síti, ale také sítím jako celkům -> přidělujeme bloky adres - je třeba i poznat do jaké sítě IP adresa patří - reálně dochází k docházení IPv4 adres.

Poílání paketů:
přímé odesílání - IP patří do stejné sítě, předáme paket druhé vrstvě a doručíme v rámci lokální sítě
nepřímé odesílání - příjemce je jinde, pomocí směrovacích tabulek najdeme router a paket pošleme routeru který zajistí další posílání s směrování
lokální doručování - Představa: paket zapouzdříme a předáme nižší vrstvě kde se vytvoří příslušný rámec/buňka a ten odešleme - Realita: známe IP adresu, ale L2 používá MAC adresy

Směrovače (ROUTER)
ROUTING - spočítají a najdou optimální cestu skrz směrovače pro paket (hledání cesty ve váženém multigrafu) - je třeba poměrně dost informací - informace o tomto se ukládají do směrovacích tabulek na základ2 celých dochází k počítání nejkratších cest - různé přístupy
FORWARDING - posílání na základě tabulek (standardně vykonává router)

Při velkých sítích budou tabulky i jejich aktualizační informace obrovské
-> je třeba je dekomponovat do menších celků (směrovacích domén)
a uvnitř a napříč nimi se používají jiné hierarchické systémy směrování

Fragmentace - IP pakety mohou být větší než MTU využívané L2 - je třeba je fragmentovat a pak opět defragmentovat

### (A46) Úkoly transportní vrstvy

End-to-End komunikace, přizpůsobovací mezivrstva, komunikace konkrétních entit (běžících procesů uvnitř entit), vypořádává se s tm co nabízejí nižší vrstvy a zajišťuje to co vyžadují vyšší

Komunikace konkrétních entit uvnitř uzlů
Tato vrstva je implementována výhradně uvnitř (koncových zařízení)
Jednotlivé entity je třeba rozlišit : porty (25 pro poštu)
Adresy portů: - jedinečné - statické - fixní a víme je dopředu - abstraktní - platformově nezávislé - implicitní - nezávislé na aktuální situaci

Transportních spojení pravděpodobně probíhá v každou chvíli více (multiplexing)
multiplexing -> je třeba měnit více komunikací na jednu L3
demultiplexing -> rozebrat a rozdat konkrétním entitám

Rozhraní mezí 3 a 4 vrstvou je soket posílá a přijímá konkrétní data - dynamicky svazováno s konkrétními porty (port je adresa)

Přizpůsobovací : Navrhuje vyšším vrstvám varianty připojení které nižší vrstvy nepodporují - proudové přenosy, spojované přenosy, spolehlivé přenosy, QoS

FlowControl - řízení toku - je třeba předejít zahlcení příjemce
Congestion control - kontrola zahlcení sítě

### (A47) Úkoly relační vrstvy

Management relací, otevírání, navazování a organizace výměny dat, řešení dialogu.

Nabízíme mechanismy pro řízení a organizaci dialogů mezi komunikujícími stranami

- navazování a kontrola relací

Reálně se toto nedělá apokud to nějaká aplikace chce, pořeší si to sama

Ve vztahu k čtvrté vrstvě díky relacím dosahujeme: - nad více paralelními transportními spojeními vytvoříme jednu relaci v 5. vrstvě - bonding, bundling - opětovné navazování přerušovaných spojení k vytvoření nepřerušované relace - jedno transportní spojení, vytvoříme posloupnost navazujících relací - jedno transportní spojení ale více paralelních relací

Mělo řešit i: - synchronzaci komunikujících entit - simplex, hlaf-duplex, full-duplex - předcházení deadlockům - atomicitu a konzistenci (checkpointing - vytváření bodů obnovy) - dvoufázové protokoly ujišťování domluv - identifikace protistran - autentizace (ověření identity) - autorizace (co protistrana / uživatel může) - bezpečnost - šifrování dat

### (A48) Úkoly prezentační vrstvy

Automatická konverze a serializace strukturovaných dat

Chceme přenášet nepozměněná data - ze sémantického hlediska - uzly mohou být na různých systémech a lokalizacích
-> chceme najít serializaci dat do síťového formátu - konverze mezi hodnotami a různá kódování - textová kódování, big/little-endian, reprezentace čísel - i pro strukturované hodnoty, pole záznamy, tenzory (matice), struktury provázané pointery (ty na druhém uzlu určitě nebudou dávat smysl) - je třeba komplexní strukturu rozdělit na poslatelné části - Abstraktní syntaxe - popsání struktury dat
ASN.1 - Transformace dat do abstraktní syntaxe
BER
Reálně se toto řeš např u SQL (Protocol Buffers)

### (A49) Úkoly aplikační vrstvy

Posílání zpráv a užívání nebo nabízení user-oriented služeb

Původně se zde měly vložit celé aplikace - to by vyžadovalo serializaci, což je nereálné
-> nakonec zde jsou pouze komponenty aplikací které zajišťují komuniaci

Identifikace: IRI identifikátory (url adresy)
DNS (hierarchie doménových jmen, překlady na IP adresy)

protokoly implementující konkrétní přenosy (HTTP, ...)

### (A50) Architektura TCP/IP

Existující architektura
Ze světa počítačů - preferuje nespojovaný charakter komunikace, nespolehlivost, best effort principle
4 vrstvy

Vznikal pomaleji, vymyšlela se myšlenka, zkusila, implementovala, vznikalo zdola nahoru.

### (A51) Srovnání ISO/OSI a TCP/IP

(L1+L2) Vrstva síťového rozhraní (wifi, ethernet)
(L3) Síťová vrstva
(L4) Transportní vrstva
(L7 + části L5 a L6) Aplikační vrstva

## Techniky přenosu dat

Zpět na [Přehled](#přehled).

### (B01) Fyzická přenosová média

Přenáší jednotlivé bity (respektive symboly)
Vedené - kovové - kroucená dvojlinka, coax - optické
Nevedené
-radio, infrared

Přenášíme vždy elektromagnetické vlnění - elektrické signály, světlo, nebo radiové vlny

Vlastnosti fyzických médií

- útlum (zeslabení)
- zkreslení (posunutí, deformace)
- rušení (signál se prolíná s jinými)
  - dva současně vedené vodiče vyzařují
  - proto je dvoulinka kroucená
  - lze řešit i nějakým tlumením

Ideálně chceme přenášet signál dokonale, realisticky jej posíláme alespoŇ tak dobře, aby šel s dobrou mírou spolehlivosti rekonstrovat.
Přenosový potenciál je vždy omezený -> některé signály lze přenášet dobře,
-určité frekvence, nebo povahy frekvencí nelze přenést, nebo se přenášejí špatně (nebo to dokonce nejde vůbec)
Negativní efekt je navíc přímo úměrný vzdálenosti

### (B02) Analogové a digitální přenosy

Analogové- přenášíme veličinu (konkrétní napětí) a příjemce naměří nižší.

- přenášená hodnota je přímo informací kterou se snažíme získat (útlum naši informaci zkreslí)

Digitální přenos - přenášené hodnoty rozdělíme na určité intervaly a podle intervalu rozlišujeme diskrétní možnosti

reálně se vždy přenáší analogový signál

U analogových přenosů nelze dosáhnout dokonalosti (můžeme potlačovat negativní dopad, ale čím vyšší snaha tím vyšší cena) - navíc je třeba počítat i s negativními vlivy z okolí - každý úsek cesty zhoršuje efekt (někdy nejen sčítá, ale i násobí)

U digitálních přenosů může dojít k ideálním přenosům - efektivnější (vyžaduje menší šířku frekvencí) - nehrozí řetězení (každé zařízení naměří hodnotu a posílá signál znovu zesílený (regenerovaný))

### (B03) Tvary a vlastnosti křivek

Čtvercový -> nejčastěji posílaný
Trojúhelníkový
Sinusoida (má amplitudu, frekvenci a fázi)

z Fourierovy transformace vyplývá
lze být dekomponována do řady sinusoid.
Přenosové cesty mají omezenou šířku pásma (určité frekvence dobře, určité hůř a určité vůbec)
lze znázornit pomocí vanové křivky (prostřední se přenášejnejlépe, pak hůř a ke kraji se rychle zhoršuje kvalita)

Ostré změny jsou problematické - vyšší frekvence keré k tomuto jsou třeba se špatně přenášejí
-> je lepší vyvarovat se ostrým přechodům
-> kvalita přenosu záleží na kvalitě média

### (B04) Přenosy v základním pásmu

nemodulované
Přímo médiem přenásíme pulsy informací (vlastní data) - 0 dole, 1 nahoře
nižší frekvence, celou šířku přen. pásma používáme k přenosu jednoho signálu
snadno implementovatelné, vhodné pouze pro krátké vzdálenosti

Není vhodné pro vysoké frekvence

Unipolar (střídá vysokou a nízkou hladinu)
Bipolární NRZ (kladné napětí a záporné napětí) bez vracení k nule
Bipolární RZ (s vracením k nule)
Manchester - důležitá hodnota je na přechodu (pády a vzestupy)

Cílem je vymyslet konkrétní kódování

- polarita, návrat k nule?
- dvojfázová (změna uprostř€d intervalu)
- encoding - nahoře/dole nebo měnění

### (B05) Principy linkových kódů

konkrétní kódování

hlavní účel:
je možné řešit scrambling/překlad (blokové kodovaní)
na nižším levelu je třeba vymyslet vzory pro přenášení (rozdíly, přechody, detekce změn...)

Chceme garantovat synchronizaci, ideálně vyváženou DC komponentu a omezenou disparitu, ale zároveň jednoduchý (levný) hardware

chcem v signálu co nejvíce pravidelných změn
problémy: chceme vysokou přenosovou rychlost a vysokou míru spolehlivosti

Slíbili jsme že budeme umět přenášet bity, ale nad nimi nemáme kontrolu
Každé fyzické medium má specifick vlastnosti
-> kodování je na míru mediu

### (B06) Problém synchronizace

Odesílání každého bitu trvá nějak dlouho, je třeba synchronizovat časování odesílatele a příjemce aby vnímal příjemce správě

- aby příjemce vzorkoval se stejnou frekvencí

Můžeme mít vlastní sběrnici přenášející tikot -drahé
Začleníme jej přímo do přenášeného proudu - clock recovery
-> lepší vnímání hodinek, lepší šance na správný sampling

### (B07) Techniky synchronizace

Isochronní - hodinové signály jsou ve stejnou dobu jako data
přímý - přenášené signály mají v každém bitovém intervalu nějakou změnu (redundantní kodování)
nepřímo - není zajistěn neustálý tikot, ale z běhu lze poznat že k tikání docházelo - bit stuffing, blokové kodovaní - alespoň čas od času nějaká změna

lze i neisochronně, ale to se nepoužívá

### (B08) DC komponenta a disparita

DC - stejnosměrný proud

- průměrná amplituda přenášené vlny - zejména na delší vzdálenosti je třeba mít vybalancovanou (žádnou) DC amplitudu - Manchester - konstrantně vyvýžený symbol - balancování naskrz po sobě jdoucími symboly (hlídání disparity)
  -> cílem je omezená disparita
  -> hlídáním jde vyvážit DC komponenta (je to jen jedn z možností)
  Řešení:
  Redundandní kódování: MANCHESTER - během každého bitu je přechod navíc, 100% overhead, zajistí synchronizaci - směr uprostřed bitu definuje bit - clock signal se měří mezi bity - self clocking, DC balanced - nevhodné pro větší objemy dat a vzdálenosti - Ethernet, NFC
  Bitový stuffing: - pokud odesílatel detekuje příliš vysoké množství stejných symbolů (běh) uměle vloží opačný bit - přijímající bit zase odstraní - režije se limitně blíží nule - zajistí synchronizaci
  Scrambling: - bity např€d namícháme s pseudo-náhodnou sekvencí - zlepšuje disparitu, ale nic nelze zaručit - je třeba zajistit, že příjemce detekuje stejnou posloupnost náhodných čísel

### (B09) Blokové kódování

- vezme se skupina n bitů a přeloží se na k>n bitů před posláním
  - na základě pravidel, nebo přednastavoného slovníku
  - třeba 4->5 bitů
  - chceme co nejvíce změn v našich přenosech
  - pro jednu vstupní máme více výstupních sekvencí - vybíráme je podle toho kteý se nám hodí více k balancování
  - některé výstupní kódy jsou navíc - jde je použít třeba pro řídící signály, nebo detekci chyb
  - 4B5B
    - 4->5 bitů
    - self clocking, DC balanced jenom se scramble, disparita nevyvážená
    - 25% overhead
    - fast ethernet
  - 8B10B
    - 5+3b -> 6+4 b
    - garantuje synchronizaci, vyváženou DC komponentu i omezenou disparitu
    - běh nejvýše 5 stejnýh bitů zasebou
    - celkový rozdíl (disparita) +-2
    - 25% overhead
    - Gigabit Ethernet, HDMI, SATA, USB 3.0

### (B10) Přenosy v přeloženém pásmu

modulované
Přenášíme sinusovku (nosnou harmonickou vlnu)
Samotná vlna nenese informaci - ta je ve změně frekvence, amplitudy nebo fáze
nepoužíváme celé frekvenční pásmo
menší zkreslení, mnohem delší vzdálenost - lepší kvalita i rychlost přenosu

Přenášíme nosnou vlnu - sínusovku která sama nepřenáší užitečnou informaci.
Amplitudová (vyšší vlna), frekvence (rychlejší), phase (změna směru)

klíčování == digitální modulace

Změna fáze lze nejsnadněji detekovat.

Příklad - Quadraturní Amplitudová modulace

### (B11) Kvadraturní amplitudová modulace

- 16stavová(symbol -> 4 bity), 64-stavová(6 bitů)
- u WiFi6-1024 stavů
- 16st-> svě vlny posunuté o 90stupňů.
  - jedna se moduluje amplitudovým způsobem na 3 stavy
  - druhá fáově na 12 stavů
  - velkem tedy 36 stavů, záměrně používáme jen 16, dostatečně vzdálených aby nebylo snadné je zaměnit
  - každému stavu přiřadíme užitečnou hodnotu - Greyovy kódy - dva sousední stavy se řeší právě v jednom bitu (aby byla chyba co nejmenší)

### (B12) Zajištění transparence

Aby linková vrstva fungovala, musí přenášet užitečná data a zárovn vlastní užitečná metadata pro funkcinalitu druhé vrstvy.
Užitečná nemáme pod kontrolou, musíme je přenášet beze změny.
Ve skutečnosti se občas přidávají další symboly.

Řídící signály - je třeba umět je detekovat a interpretovat.

Transparence -> rozlišování užitečných dat od signálů

- oddělená přenosová cesta (dělo se u nejstarších modemů), drahé, komplikované
- přepínání inerpretací (escapint)
  - datový nebo příkazový režim
  - u novějších modemů i mimo sítě

### (B13) Principy a cíle framingu

Jak zajistit přenos a transparenci blokových dat.

Encapsulation - zapouzdření - konstruuje blok dat jako takový (hlavička, patička..)

Framing - už máme blok, vyznačujeme jeho hranice

- fyzická vrstva přenáší pouze bity, odesílatel nemá problém, příjemce potřebuje být schopen zjistit kde začínají a jak dlouhé jsou bloky

Ohraničení:

- Flag na začátku a na konci (konkrétní byte) (je třeba zajistit aby se flagy nevyskytly uprostřed)
- Označení začátku a uložení délky - je těžké znovu-objevit synchronizaci

Vrstvy by mezi sebou neměly být závislé

Označení začátku a implicitní konce

- s koncem rámce skončí nosná vlna

Coding violation - 4B5B využijeme nedatová slova k označení bloků

fyzická přenosová cesta, multiplexing, chceme více přenosů najednou - kapacitu je třeba rozdělit

- multiplex na základě dělení času - různé sloty pro různé přenosy
- jsou jednotkové délky a pak odpočítáváním slotů známe jak jsou dlouhé

### (B14) Techniky stuffingu

Záměrné přidávání umělých znaků/bitů/bytů k označení konkrétního místa v přenášených datech
na linkové vrstvě tím zajišťujeme označení začátku/konce bloku (je pak třeba hlídat prostředek)
lze vnímat tak, že linková a fyzická vrstva si mohou pomáhat

### (B15) Znakově orientované protokoly

Dnes už se nepoužívají
Přenášená data vnímáme na úrovni znaků
využijeme netisknutelné znaky.

Znaky - start header, start text, end text + znak data link escape (escapovací znak aktivuje ostatní)
DLE symboly v těle se zdvojují - pak je vidět že tam není žádná fce

nebo mů6€me před celou tabulku dát dva synchronizační znaky

SLIP - pro dvoubodová spojení bereme IP pakety jako takové a na konec a začátek dáme konec/začátek
(pokud je uprostřed, dáme k němu escape symbol a transponujeme jej, escape symbol taky oescapujeme a taky transponujeme)

### (B16) Bitově orientované protokoly

Na začátek a konec dáme křídlovou značku 0111111110 např. Je třeba zajišťovat aby se flag nevyskytl uprostřed
Když detekujeme 7 za sebou odeslných 1 uměle vložíme 0, čímž zabráníme chybnmu flagu

6-8 jedniček

### (B17) Bytově orientované protokoly

Kompromis, výhody obojího
Označujeme začátek nebo konec křídlovými značkami. Značka je jedne nebo dvy byty.
Escaping - escapovací znaky(byty) které využijeme k označení vnitřních výskytů značek.

Příklad Ethernet:
7bytů 0x55 - preambule (10101010) - little endian.
Start frame delimeter (10101011)
a pak frame.

## Síťová vrstva a směrování

Zpět na [Přehled](#přehled).

### (B18) Routing a forwarding

### (B19) Směrovací a forwardovací tabulky

### (B20) Obvyklé přístupy směrování

### (B21) Klasifikace směrovacích přístupů

### (B22) Adaptivní a neadaptivní směrování

### (B23) Statické (fixní) směrování

### (B24) Záplavové směrování

### (B25) Techniky řízené záplavy

### (B26) Centralizované směrování

### (B27) Distribuované izolované směrování

### (B28) Metoda zpětného učení

### (B29) Metoda zdrojového směrování

### (B30) Distribuované neizolované směrování

### (B31) Směrování distance-vector

### (B32) Problém count to infinity

### (B33) Vlastnosti protokolu RIP

### (B34) Směrování link-state

### (B35) Srovnání principů RIP a OSPF

### (B36) Velikost tabulek a aktualizací

### (B37) Směrovací domény

### (B38) Techniky směrování na L2

## Transportní vrstva a protokoly

Zpět na [Přehled](#přehled).

### (B39) End-to-end komunikace

### (B40) Transportní spojení

### (B41) Srovnání protokolů TCP a UDP

### (B42) Bytový stream TCP

### (B43) Navazování spojení

### (B44) Zajištění spolehlivosti

### (B45) Detekce poškozených bloků

### (B46) Kontrola parity

### (B47) Kontrolní součty

### (B48) Cyklické redundantní součty

### (B49) Potvrzovací strategie

### (B50) Jednotlivé potvrzování

### (B51) Kontinuální potvrzování

### (B52) Potvrzování s návratem

### (B53) Selektivní opakování

### (B54) Metoda posuvného okénka

### (B55) Problém řízení toku

### (B56) Předcházení zahlcení sítě

### (B57) Spolehlivost v TCP

### (B58) Možnosti zajištění QoS

### (B59) Principy řešení DiffServ

### (B60) Principy řešení IntServ

### (B61) Opatření client buffering

## Internetworking

Zpět na [Přehled](#přehled).

### (C01) Cíle internetworkingu

### (C02) Aktivní a pasivní síťové prvky

### (C03) Propojování napříč vrstvami

### (C04) Principy propojování na L1

### (C05) Funkce opakovačů

### (C06) Vlastnosti opakovačů

### (C07) Přístupová metoda CSMA/CD

### (C08) Přenosová kapacita segmentu

### (C09) Principy propojování na L2

### (C10) Filtrování a cílený forwarding

### (C11) Činnost linkového rozhraní

### (C12) Mechanismus Store&Forward

### (C13) Mechanismus Cut-Through

### (C14) Segmentace sítě

### (C15) Vyhrazená přenosová kapacita

### (C16) Propojovací zařízení na L2

### (C17) Vlastnosti zařízení na L2

### (C18) Principy propojování na L3

### (C19) Činnost síťového rozhraní

### (C20) Pravidla 80:20 a 20:80

### (C21) Místní L2 broadcast

### (C22) Místní L3 broadcast

### (C23) Cílený L3 broadcast

### (C24) Funkce směrovače

### (C25) Funkce L3 přepínače

### (C26) Rozdíly směrovačů a L3 přepínačů

### (C27) Využití L4 a L7 přepínačů

### (C28) Principy a účel sítí VLAN

### (C29) Koncepty VLAN sítí

### (C30) Logický model VLAN sítě

### (C31) Přístupové VLAN porty

### (C32) Trunkovací VLAN porty

### (C33) Konfigurace VLAN sítí

### (C34) Tagování 802.1q Dot1q

### (C35) Směrování mezi VLAN sítěmi

### (C36) Princip a typy firewallů

### (C37) Demilitarizovaná zóna

### (C38) Aplikační brány

### (C39) Realizace DMZ

### (C40) Paketové filtry a ACL

## Adresy a adresování

Zpět na [Přehled](#přehled).

### (D01) Principy adresování na L2

### (D02) Adresy EUI-48 a EUI-64

### (D03) Principy adresování na L3

### (D04) Tvar a zápis IPv4 adres

### (D05) Třídy a prostor IPv4 adres

### (D06) Speciální IPv4 adresy

### (D07) IPv4 multicastové adresy

### (D08) Realizace multicastu na L2

### (D09) Přidělování IPv4 adres sítím

### (D10) Řešení nedostatku IPv4 adres

### (D11) Mechanismus subnettingu

### (D12) Mechanismus supernettingu

### (D13) Mechanismus CIDR

### (D14) Hierarchie registrátorů

### (D15) Závislost IP adres na ISP

### (D16) Koncept privátních IP adres

### (D17) Princip překladu adres

### (D18) Způsob fungování NAT

### (D19) Charakter NAT/PAT vazeb

### (D20) Princip a vlastnosti PAT

### (D21) Způsob fungování PAT

### (D22) Varianty chování NAT/PAT

### (D23) Problémy NAT/PAT

### (D24) Cíle návrhu IPv6 adres

### (D25) Vztah IPv4 a IPv6 adres

### (D26) Tvar a zápis IPv6 adres

### (D27) Základní druhy IPv6 adres

### (D28) Dělení IPv6 unicast adres

### (D29) Globální IPv6 unicast adresy

### (D30) Principy adresování na L4

### (D31) Porty a jejich číslování

### (D32) Principy adresování na L7

### (D33) Obecná struktura URI

## Protokoly IPv4 a IPv6

Zpět na [Přehled](#přehled).

### (D34) Vlastnosti protokolu IPv4

### (D35) Struktura IPv4 datagramu

### (D36) Položky IPv4 hlavičky

### (D37) IPv4 Time to Live

### (D38) Nástroj TraceRoute

### (D39) IPv4 kontrolní součet

### (D40) IPv4 doplňky hlavičky

### (D41) Principy IPv4 fragmentace

### (D42) IPv4 varianty detekce MTU

### (D43) IPv4 Path MTU Discovery

### (D44) Proces IPv4 fragmentace

### (D45) IPv4 fragmentační hlavičky

### (D46) Proces IPv4 defragmentace

### (D47) Problémy IPv4 de/fragmentace

### (D48) Principy protokolu ICMPv4

### (D49) Struktura ICMPv4 zprávy

### (D50) Příklady ICMPv4 zprávy

### (D51) Principy protokolu ARP

### (D52) Struktura ARP zprávy

### (D53) Princip ARP cachování

### (D54) Zpracování ARP dotazu

### (D55) Reverzní ARP protokol

### (D56) Nevýhody RARP protokolu

### (D57) Koncept protokolu DHCP

### (D58) DHCP alokační strategie

### (D59) Chování DHCP klienta

### (D60) Rozdíly IPv6 oproti IPv4

### (D61) Struktura IPv6 paketu

### (D62) Položky IPv6 hlavičky

### (D63) Koncept IPv6 toků

### (D64) IPv6 rozšiřující hlavičky

### (D65) IPv6 fragmentační hlavička

### (D66) IPv6 Path MTU Discovery

### (D67) Formát ICMPv6 zprávy
