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

Vlastnosti:

- pouze jeden účel - broadcast (TV, radio), přepínané (telefony)
- Chytrá síť, hloupé koncové uzly
  - síť je drahá, mohutná, neflexibilní
- přepojování okruhů, spojované, spolehlivé a garantované přenosy (QoS)
- Zdroje jsou limitované (není jich dostatek pro každého) - prodej exkluzivní rezervace zdrojů - prodej spolehlivé služby
- Majiteli telekomunikačních sítí nejsou jejich koneční uživatelé.

#### Počítačové sítě

Vlastnosti:

- síť hloupá, koncová zařízení chytrá (levnější)
  - síť je levná, jednodchá, flexibilní
- přepojování paketů, nespojované, nespolehlivé a negarantované přenosy (best effort)
- hlavní cíl je přenos dat (bez nutnosti chápat jejich význam)
- nedostatek zdrojů není hlavní limitující faktor, nejdůlěžitější jsou technické faktory
- vlastník sítě je většinou uživatel
- nutnost řešit problém s kompatibilitou a sjednocením.

### (A09) Hospodaření se zdroji

- Moore's law - každé dva roky se zdvojnásobí výkon (počet tranzistorů na mm) (postupně se zpomaluje, původně to bylo co rok).
  - Z moorova zákona vyplývá, že každé dva roky máme srovnatelnou výpočetní sílu za poloviční cenu
- Gilder's law - přenosová kapacita cest se každý rok ztrojnásobuje
- Disk law - uložná kapacita se každý rok přibližně zdvojnásobí

## Taxonomie počítačových sítí

Zpět na [Přehled](#přehled).

### (A10) Distribuční sítě a sítě s přepojováním

#### Distribuční sítě

přenosy 1:N (1 vysílá, N přijímá), neřešíme směrování (data patří všem), př. televize a radia

technologie:

- DVB (digital video broadcasting) - př. DVB-T2
- DAB (digital audio broadcasting)

#### Sítě s přepojováním

přenosy 1:1 (řešíme routování a přeposílání)
možné varianty:

- přepojování okruhů
  - vždy spojované a garantované přenosy
  - volitelně: blokové/proudové, spolehlivé/nespolehlivé
- přepojování paketů
  - vždy blokový přenos
  - volitelně: virtuální okruhy (spolehlivé/nespolehlivé, Best effort/QoS) nebo datagramová služba (spolehlivé/nespolehlivé, Best effort/relativní QoS)

### (A11) Pevná a mobilní telefonní síť

#### Pevná telefonní síť

- páteřní část - hierarchie telefonních ústředen (mezinárodní, tranzitní, lokální, předsunuté Remote Subscriber Units RSU)
- přístupová část
  - POP - lokální ústředny/RSU s hlavním rozvaděčem
  - z POP kroucené dvoulinky dlouhé max 5 km do všech CP
  - CP - domy, kanceláře

#### Mobilní telefonní síť (GSM)

- Páteřní část - Network switching subsystem (NSS) - provádí spojení mezi účastníky jiných sítí
- přístupová část - základem je systém základnových stanic (BSC). Tyto stanice řídí několik BTS stanic (v řádu desítek). BTS stanice propojuje koncového uživatele (telefon) se zbytkem sítě. V případě pohybu uživatele řídí BSC předávání hovoru mezi BTS. Uzemí, které je pokryto signálem mobilní sítě je rozděleno na malé oblasti (tzv. buňky - cells), Každou buňku obsluhuje jedna BTS. BTS může mít více antén (každá s vlastní frekvencí, každá frekvence pro jeden sektor, BTS tedy může spravovat i více sektorů, aniž by se sektory vzájemně rušily)
  ![Mobile telephone network](./images/mobile_telephone_network.png)

### (A12) Páteřní a přístupové sítě

#### Páteřní (transportní) sítě

Propojuje na velké vzdálenosti hlavní komponenty celé infrastruktury. Tvořena malým počtem uzlů, většinou propojených optickými vlákny

#### Přístupové sítě

umožňuje připojení koncových uživatelů do páteřní sítě

- point of presence (POP) - místo propojení páteřní a přístupové sítě
- customer premises (CP) - místo, kde se mohou vyskytovat potenciální zákazníci

vlastnosti:

- pokrývají celé území (musí vést ke všem potenciálním zákazníkům, když ji buduji)
- vede skrz veřejná místa (musím rozkopat ulici, abych mohl položit kabel), tudíž drahé a musím plánovat dlouho dopředu
  - při budování používám aktuálně nejmodernější technologie
  - vždy naddimenzovávám (když by mi stačilo 10 kabelů, tak položím 100 kabelů)

"poslední míle" (z pohledu operátor "první míle") - propojení mezi koncovým uživatelem a koncovým bodem telefonní sítě

### (A13) Překryvné přístupové sítě

Využiji existující infrastrukturu, kterou modifikuji a vybuduji na ní vlastní infrastrukturu. Původní funkčnost je zachovaná.

použijeme jiné frekvence nebo zapouzdření dat

- fixní telefonní sítě (kroucené dvojlinky v lokálních smyčkách)
- elektrické sítě (drátování silové elektřiny)
- síť kabelové televize (koaxiální kabely)

### (A14) Technologie xDSL

Digital Subscriber Line - využití struktury původních pevných telefonních sítí POTS. POTS používali omezenou frekvenci (pouze pro přenos hlasu po kroucené dvoulince). Způsob překrytí - data budeme přenášet po stejné dvoulince ale pro přenos dat použijeme vyšší frekvenci (frekvenční multiplex). Největší problém je přenos na velkou vzdálenost

Technické řešení:

- CP část:
  - DSL modem - k modemu jsou připojení uživatelé (počítače) - mění digitální data na analogový signál
  - splitter - odděluje/spojuje nižší frekvence (hlas) od vyšších (data) - místo, kde se spojí připojený telefon (stará síť) a DSL modem (s novými počítači)

![CPpartxDsl](./images/CPxDSLpart.jpg)

- POP část
  - místní smyčka (přenáší hlas i data)
  - DSLAM (DSL Access Multiplexer) - zařízení v POP - datový přenos je rozdělen do více páteřních sítí

![xDslschema](./images/xdslschema.gif)

Čím vyšší frekvence, tím náročnější přenos - s větší frekvencí se snižuje přenosová vzdálenost -> vznik předsunutých ústředen (zkrácení vzdálenosti). Podle frekvence pak rozlišujeme použité technologie př. ADSL (Asymetric Digital Subscriber Line), VDSL2 (Very High-Speed Digital Subscriber Line)

### (A15) Technologie PLC

Power-Line Communication - překrytí původní elektrické sítě (napětí 230V a frekvence 50Hz). Data přenášíme na vyšší frekvenci (frekvenční multiplex)

problémy:

- různé standardy v různých zemích (110V, 120V, 60Hz atd.)
- na sloupech vyskového napětí nejsou kabely stíněné a fungují jako antény
- rušení vyzařovanými signály prostředí
- rušení i v domácnosti (vypnutí/zapnutí spotřebiče...)

Kdy tedy (ne)lze použít?

- PLC lze využít na velké vzdálenosti → nízká frekvence → nízké přenosové rychlosti. Použití pro pro monitorování údržbu sítě
- Last mile - drahé a nepoužívané - Broadband over Powerline - Střední napětí desítky kV mezi transformátory - Na nízkých 230V jiný přístup
- Last Meter - Homeplug - vnitřní rozvody za domácím elektroměrem lze využít k částečnému vybudování domácí sítě (až 500Mb/s)

### (A16) Technologie DOCSIS

kabelová televize - televize připojena pomocí koaxiálního kabelu pro analogový jednosměrný přenos → chceme digitální a obousměrné

EuroDOCSIS (Data Over Cable Service Interface Specification)

struktura přístupové sítě - Hybrid Fiber-Coaxial systém (HFC):

- kombinace optických vláken a koaxiálních kabelů
- CMTS - analogicky jako DSLAM
- mezi CMTS a CP leží optický uzel
  - z optického uzlu směrem k CMTS vede optické vlákno
  - z optického uzlu směrem k CP vede koaxiální kabel
- u CP je Cable Modem (CM), který uvede vše do provozu

### (A17) Technologie FTTx

obecné označení pro oblast sítě, která využívá optická vlákna na poslední míli připojení. `x` udává, jak blízko k užvateli jdu:

- FTTH - home
- FTTB - building
- FTTC - curb (obrubník)
- FFTN - node - využívá DOCSIS v HFC

obecně dělíme na:

- Aktivní - v rámci infrastruktury využívají aktivní síťové prvky (rozbočování, zesilování...) zapojené do elektřiny. Vyšší přenosová kapacita, větší vzdálenost
- Pasivní - zakopané pasivní elementy - na kratší vzdálenost

### (A18) Datové sítě

hloupá síť, chytrá koncová zařízení - jediný účel sítě je přenést data.

Vlastnosti:

- možné přepojování okruhů i paketů
- používá Best Effort i QoS
- podle vztahu vlastník+uživatel:
  - privátní
  - veřejná
  - virtuální privátní

#### Soukromé datové sítě

= uživatel je vlastník (vybudoval ji sám pro sebe)

výhody:

- majitel rozhoduje o všech technologiích, protokolech, adresách, ...

nevýhody:

- drahé - typicky buduje stát (u nás vybudovalo ministerstvo obrany, využívají ho datové schránky, IZS...)

#### Veřejné datové sítě

Telekomunikační operátor vybuduje veřejnou datovou síť a nabízí ji zákazníkům za poplatek (cena podle množství přenesených dat) - vlastník není zamýšleným uživatelem
K připojení je třeba kvalitní a podrobná dokumentace jak se připojit

výhody:

- výhodné pro malé subjekty
- flexibilní - využívá službu, není to investice

nevýhody (pro zákazníka):

- sdílena všemi uživateli (bezpečnost)
- o všem rozhoduje vlastník

#### Virtuální privátní datové sítě

Kombinace předchozích - sdílená veřejná infrastruktura, navozujeme iluzi samostatných sítí oddělených od sebe (uživatelé se navzájem nevidí)

výhody:

- levnější
- nezávislost - nejdůležitější určí majitel (technologie, protokoly, ...), o zbytku rozhoduje uživatel (oprávění, ...)

### (A19) Sítě PAN, LAN, MAN, WAN

Hloupá síť, chytrá koncová zařízení.

#### Personal Area Networks

1-10m - propojení zařízení v pracovním prstoru jednoho člověka (propojení monitoru, klávesnice, počítače, sluchátek tiskárny, ...)

#### Local Area Networks

10m-1km - propojení zařízení v rámci domácnosti, firmy, školy

- v širším pojetí - jakákoliv síť menších rozměrů (zahrnujeme i routery - tedy soustavu sítí)
- v užším pojetí . propojení pouze zařízení na L1 a L2 - repeatery, switche, ...

oproti WAN nižší latence, vyšší spolehlivost, systematická topologie (sběrnice, hvězda, ...)

#### Metropolitan Area Networks

1km-100km - propojení jednotlivých LAN (může se připojit i koncový uživatel, ale není to k tomu určené)

příklady:

- PASNET (Prague Academic and Scientific Network)
- MEPNET (Metropolitan Prague Network)

vlastník většinou právnická osoba nebo město. Rozléhají se přes veřejné prostory (oproti LAN)

#### Wide Area Networks

100km a více - propojení jednotlivých LAN a MAN, přenos dat na velké vzdálenosti. Překračují i hranice států. Vlastnící jsou většinou velké firmy, poskytlovatelé připojení nebo operátoři.
př. CESNET (akademická výzkumná infrastruktura)

- čím delší vzdálenost, tím vyšší latence, nižší spolehlivost, nesymetrická topologie, permanentní dostupnost

### (A20) Architektura Internetu, peering a tranzit

Dnes máme vrstevnatou architekturu (T1/2/3 poskytovatelé a access pointy)

Páteř - tvořena sítěmi všech Tier 1 poskytovatelů internetu, které jsou propojené pomocí IXP (internet exchange points) př. NIX.cz

přenosy:

- Peeringový - dohoda mezi dvěma ISP, přímá výměna dat mezi jejich sítěmi
- Tranzit - koncový uživatel nebo ISP platí dalšímu ISP, aby zprostředkoval přenos do zbytku internetu

provideři:

- Tier 1 - operátor vlastnící infrastrukturu s přímým přístup do jakékoliv sítě na světě. Nikomu neplatí za tranzit/peering (př. Deutsche Telekom)
- Tier 2 - přístup zadarmo do některých sítí. Za tranzit/peering do nějaké částí internetu platí (státy, regiony)
- Tier 3 - nabízí služby koncovým uživatelům, za veškerý tranzit/peering platí

### (A21) Intranet, extranet a darknet

Klasifikuje jakým způsobem jsou využívány služby a prostředky

- Intranet - služby pro vnitřní uživatele - sdílené tiskárny, dokumenty a programy v rámci firmy
- Extranet - nabízení služeb venkovním uživatelům - updaty, support, helpdesk, marketing
- Darknet - překryvná síť nad internetem, anonymizovaný neveřejný přístup

## Vrstvy a vrstvové modely

Zpět na [Přehled](#přehled).

### (A22) Principy vrstvových modelů

sítě příliš komplexní - snaha dekompozice problému do vrstev. Základní principy:

- Nižší vrstva nabízí službu té vyšší, vyšší využívají služeb nižších
- Čím vyšší vrstva tím vyšší míra abstrakce (fyzická - přenos bitů, aplikační - řešení komplexníxh služeb)
- definováno veřejné rozhraní
- vnitřní detaily skryty - vrstvy jsou na sobě nezávislé, možnost alternativních řešení, flexibilita (nová implementace je bezproblémová)

### (A23) Vertikální a horizontální komunikace

#### Horizontální komunikace

Komunikace na jedné vrstvě nebo mezi síťovými prvky (tzn. komunikace mezi různými prvky nebo aktivními síťovými prvky na stejné vrstvě) - př. L1 opačné konce drátů, L2 protější síťová rozhraní, L3 uzly, L7 běžící aplikace

pozorování:

- V dané vrstvě zpravidla probíhá více komunikací najednou
- entity dodržují pravidla definovnaná protokoly
- Asynchronní charakter - po odeslání je třeba řekat na reakci
- Virtuální charakter - pouze L1 skutečně něco posílá (zbytek je vlastně vertikální komunikace)

#### Veritkální komunikace

Komunikace mezi vrstvanu ve stejném uzlu nebo aktivním síťovém prvku. Při zpracování dat nelze přeskočit žádnou vrstvu. Princip z pohledu:

1. odesílatele - připraví data k odeslání a předá je nižší vrstvě
2. příjemce - obdrží data → data rozbalí a předá vyšší vrstvě

### (A24) Principy síťových protokolů

Předem domluvená pravidla, nezávislá na konkrétní implementaci, podle kterých spolu entity komunikují

V protokolech musí být definováno:

- veřejné rozhraní - pro vertikální komunikaci
- komunikační pravidla - pro horizontální komunikaci - lze popsat stavovým diagramem (povolené a očekávané akce entit v různých situacích)
- formát dat (PDU - protocol data unit) - vniřní struktura PDU (header, body, footer), MTU (maximalní velikost PDU)

pozorování:

- protokol vždy souvisí pouze s jednou vrstvou
- Na jedné vrstvě často existuje více protokolů - buď se jedna o alternativy UDP/TCP, nebo řeší odlišné úkoly SMTP/HTTP

### (A25) Síťové modely a architektury

Model - teoretický model popisující, jak by měla síť fungovat (počet vrstev a jejich účel, poskytované služby - (ne)spojované, (ne)spolehlivé). př. OSI model

Architektura - konkrétní implemetace síťového modelu + definice protokolů. př. TCP/IP architektura

### (A26) Referenční model ISO/OSI

Sedmi vrstevný model preferující spojované, spolehlivé přenosy, podpora QoS.

Vrstvy:

- Nižší vrstvy: (zajišťují přenos, směřují na konkrétní uzel)
  - L1 (fyzická vrstva), L2 (linková vrstva), L3 (síťová vrstva)
- Adaptační vrstva: (end-to-end komunikace)
  - L4 (transportní vrstva)
- Vyšší vrstvy:
  - L5 (relační vrstva), L6 (prezentační vrstva), L7 (aplikační vrstva)

L4-L7 jsou implementované pouze v koncových uzlech (př. v routeru je nejvyšší vrstva L3)

### (A27) Úkoly fyzické vrstvy

Hlavním cílem je přenést bity přes přenosové médium. Nerozumí významu dat.

Přenášený signál je vždy analogový (kov - elektrické signály, optika - světlo, bezdrát - vlnění) → po přijetí interpretujeme jako analogový/digitální.

Aspekty k řešení: signál je limitován (útlum, rušení...), kódování, modulování (reálně posíláme sinusoidu - měníme amplitudu, frekvenci, fázový posun), časování (bitový interval), synchronizace (odesílatel/příjemce mají různé hodiny), šířka páska (rozdíl mezi minimální a maximální frekvencí)

### (A28) Úkoly linkové vrstvy

Hlavním cílem je přenášet bloky (framing) dat mezi síťovými rozhraními a jednotlivými uzly v rámci lokální sítě.

- odesílatel vytvoří PDU a předá je L1 (MTU závisí na konkrétní technologii)
- příjemce obdrží na L1 proud bitů - musí rozpoznat jednotlivé rámce (frames) → mohou být např. vloženy extra bity označující začátky a konce bloků.

Data posíláme (směrujeme) pomocí aktivních síťových prvků (bridges, switches) a interních mechanismů (Store&Forward, Cut-Through). Vytváříme iluzi přímé cesty (každý vrchol sítě je viditelný a dosažitelný). Vnitřní struktura sítě udává, jak data proudí (nemusí odpovídat topologii L1) - př. sběrnice, hvězda, kruh, mřížka, hyperkrychle.

Adresování - používáme fyzické adresy (MAC). Musí být unikátní v rámci sítě (globálně nemusí). Slouží k identifikiaci příjemce (aby byl nalezen a aby poznal svá data - typicky totiž přijímáme i rámce, které nám nepatří). Nutno zajistit přidělování adres (nemusí být celosvětově unikátní) - EUI-48 (MAC-48) nebo EUI-64

filtrování a forwarding - mechanismus v bridges a switches k nalezení příjemce. Kdyby nebyl, museli bychom data posílat všemi směry.

- filtrování - zamezení zbytečného předávání
- forwarding - předávání pouze směrem, kde je příjemce

Transparentnost - musíme umět předávat data a zároveň kontrolní signály a metadata a umět je od sebe rozeznávat. Techniky:

- escaping - přepínání režimu
- framing - hlavičkování
- stuffing - umělé vkládání dalších bytů

Původní představa - přenosová média budou exkluzivní. Realita - více uzlů sdílí jedno přenosové médium. V jednu chvíli může vysílat pouze jeden (OSI s tím nepočítal) → rozdělení na dvě vrstvy

- MAC (Media Access Control) - podvrstva řešící přístup k médiím
- LLC (Logical Link Control) - podvrstva nahrazující 2

### (A29) Úkoly síťové vrstvy

Hlavním cílem je hop-to-hop (z uzlu na uzel) přenos a směrování paketů přes systém navzájem propojených sítí k příjemci (vědomí více sítí a jejich propojení - chybí iluze přímého propojení). Pakety posíláme (směrujeme) přes routery.

Adresování - uzly musí mít globálně jednoznačnou adresu (IPv4, IPv6). Adresy v rámci jedné sítě mají stejný prefix. Adresy přiřazujeme také sítím jako celku (potřeba poznat, do jaké sítě adresa patří). Nedostatek IPv4 se řeší pomocí např. subnetting, supernettingg, CIDR, privatná adresy a NAT, IPv6.

Posílání paketů:

- přímé odesílání - IP adresa příjemce patří do stejné sítě → předáme paket L2 a doručíme v rámci lokální sítě
- nepřímé odesílání - příjemce je v jiné síti → předám L2 a doručím routeru, který zařídí další směrování
- lokální doručování (princip stejný u přímého/nepřímého) - paket zapouzdříme → předám L2, kde se vytvoří rámec → rámec se odešle (potřeba udělat překlad IP → MAC)

Routing - hledání optimální cesty skrz routery (_hledání nejkratší cesty v ohodnoceném multigrafu_). K výpočtu je potřeba znát trochu topologii sítě (uloženo v routovacích tabulkách - nelze mít jednu tabulku v případě velkých sítí → dekompozice na části př. Autonomous system). Používají se různé strategie výpočtu (dynamické/statické, izolované/centralizované...)

Forwarding - posílání paketů po spočítané trase (pomocí forwarding tables)

Fragmentace - IP pakety mohou být větší než MTU využívané konrétní L2 → je třeba je fragmentovat

### (A30) Úkoly transportní vrstvy

Hlavním cílem je End-to-End komunikace mezi koncovými zařízeními (mezi běžícími procesy uvnitř).

Adresování

, přizpůsobovací mezivrstva, komunikace konkrétních entit (běžících procesů uvnitř entit), vypořádává se s tm co nabízejí nižší vrstvy a zajišťuje to co vyžadují vyšší

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

### (A31) Úkoly relační vrstvy

Hlavním cílem je management relací (otevírání/uzavírání/udržování spojení, organizace výměny dat). Dnes už se většinou neimplementuje.

udržování relací

- Jedna L5 relace nad několika L4 přenosy:
  - Více soubežných přenosů - dosažení vyšší přenosové kapacity (bonding)
  - více navazujicích přenosů - nutno zajistit pokračování při selhání jednoho přenosu (bundling?)
- Více L5 relací nad jedním L4 přenosem
  - více navazujících relací - minimalizace počtu L4 spojení
  - více souběžných relací - multiplexing (zapouzdření do jedné) relací

Co by dále měla řešit L5:

- synchronzaci komunikujících entit - iluze synchronní komunikace (L4 je asynchronní), simplex/half-duplex/full-duplex, předcházení deadlockům
- vytváření checkpointů na požádání - obnovení dat po chybě
- dvoufázové protokoly ujišťování domluv
- bezpečenost - autentizace (ověření identity), autorizace (co protistrana / uživatel může), šifrování dat

### (A32) Úkoly prezentační vrstvy

Hlavním cílem je automatická serializaci, která umožní přenos a konverzi dat a která zajistí stejnou skladbu na různých platformách. (_to neznamená, že data přijdou nezměněná, je důležitý správný překlad_). V praxi se opět neimplementuje.

Typicky: různé kódování, endianita, číselné formáty. S

Serializace:

- jednouduché strukutry (pole, sets apod.) - přímočaré
- složitější strukutry
  - 2D, 3D,... matice - přenosová cesta je vždy 1D
  - objekty s pointery - odesílatel/příjemce mají vždy svůj adresový prostor
  - komplexní struktura se rozdělí na poslatelné části - Abstraktní syntaxe: popsání struktury dat, př. ASN.1 → přenosová syntaxe: serializace dat do formátu, př. BER

### (A33) Úkoly aplikační vrstvy

Hlavním cílem je zajistit přístup ke komunikačnímu rozhraní a umoožnit aplikaci zasílat a přijímat zprávy, díky kterým se poskytují služby

Původně měla L7 obsahovat celé aplikace → nereálné → realita: obsahuje pouze komunikační základ, ne UI

adresování - Identifikace pomocí IRI (př. URL), lokalizace pomocí DNS

protokoly implementující konkrétní přenosy (HTTP, SMTP, ...)

### (A50) Architektura TCP/IP

Existující architektura
Ze světa počítačů - preferuje nespojovaný charakter komunikace, nespolehlivost, best effort principle
4 vrstvy

Vznikal pomaleji, vymyšlela se myšlenka, zkusila, implementovala, vznikalo zdola nahoru.

Srovnání ISO/OSI a TCP/IP

(L1+L2) Vrstva síťového rozhraní (wifi, ethernet)
(L3) Síťová vrstva
(L4) Transportní vrstva
(L7 + části L5 a L6) Aplikační vrstva

## Techniky přenosu dat

Zpět na [Přehled](#přehled).

### (B01) Fyzická přenosová média

- Vedené
  - kovové - kroucená dvojlinka, koaxiální kabely
  - optické - optické kabely
- Nevedené
  - bezdrátové - radio, infračervené atd.

Přenášíme vždy elektromagnetické vlnění s určitou měřitelnou vlastností - elektrické signály (napětí, proud), světlo (intenzita), nebo radiové vlny (frekvence, fáze)

(nechtěnné) Vlastnosti fyzických médií:

- útlum (zeslabení signálu)
- zkreslení (posunutí, deformace)
- rušení (signál se prolíná s jinými) - př. dva paralelní dráty se chovají jako anténa
- určité frekvence nelze vůbec přenést
- čím větší vzdálenost, tím větší negativní vlivy

![signal transmission problems](./images/signaltransmition.jpg)

Neexistuje dokonalý přenos → signál přenášíme tak, aby šel zpětně rekonstruovat

### (B02) Analogové a digitální přenosy

Vždy se přenáší analogový signál, liší se pouze to, jak doručený signál interpretujeme.

#### Analogové

Zajímá nás přímo naměřená hodnota. Doručená hodnota bude vždy zkreslená.

![analog transmission](./images/analogtransmission.jpg)

#### Digitální

Možné naměřené hodnoty jsou rozděleny do intervalů → podle intervalů určíme výsledek. Je optimální, daleko efektivnější. Vyžaduje menší šířku pásma. V každém uzlu je signál změřen a obnoven.

![signal transmission problems](./images/digitaltransmission.jpg)

### (B03) Tvary a vlastnosti křivek

- Čtvercový, Trojúhelníkový, Sinusoida

![shape of waves](./images/shapeswaves.jpg)
Libovolná křivka může být interpretovaná jako součet sinusoid (Fourierova transformace)

Přenosové cesty mají omezenou šířku pásma → spolehlivě lze přenášet pouze některé frekvence (charakterizuje to vanová křivka - prostřední se přenášejí dobře, krajní špatně)

Ostré změny jsou problematické - vyšší frekvence jsou oříznuty

![deformation of waves](./images/deformationwaves.jpg)

### (B04) Přenosy v základním pásmu (baseband)

- Nemodulovaný
- frekvence blízko nuly
- frekvence změny signálu = frekvence změny dat
- využívá čtvercový tvar křivky
- jeden datový signál zabírá celou šířku pásma (tzn. pokud využívám pro svůj přenos médium, nikdo jiný toto médium nemůže používat)
- pouze na krátké vzdálenosti
- typické pro wired media

typické vlastnosti (ukázáno na linkových kódech níže):

- polarita - kolik úrovní rozeznáváme
  - unipolární - rozeznáváme low/hig → dva intervaly
  - bipolární - negative/positive/zero → máme tři intervaly
- NRZ (non return to zero), RZ (return to zero) → po vyslání hodnoty se (ne)vrátím zpátky na nulu (Bipolar RZ/NRZ)
- bifázový - alespoň jeden přenos za bit
- kódování
  - buď měřím stabilní hladinu (unipolar/bipolar RZ/bipolar NRZ)
  - nebo směr změny signálu (manchester)

### (B05) Principy a příklady linkových kódů

informace reprezentována různými výškami signálu nebo hranami. Signál vysílán po určitou dobu

Cíle:

- zajistit dostatečně pravidelné změny v přenášených datech (důvody viz dále) → problém: vstupní data nemáme pod kontrolou

Příklady:

- unipolární (1V = -1, 0 V = -0 → po celý interval)
- bipolární RZ
  - 0 → polovina intervalu v -1V, pak polovina intervalu v 0V
  - 1 → polovinu intervalu v 1V, pak polovina intervalu v 0V
- bipolární NRZ (stejné jako uni, ale 1V = 1, -1V = 0)
- manchester
  - 0 → v polovině intervalu je klesající hrana
  - 1 → v polovině intervalu je rostoucí hrana

![link codes](./images/linkcodes.jpg)

### (B06) Problém synchronizace, DC komponenty a disparity

#### Problém synchronizace

bitový interval (Bit period) = časový interval na poslaní jednoho bitu.

Potřeba synchronizovat čas odesílatele a příjemce, aby byly signály správně reprezentovány:

![synchronization problem](./images/lossbits.jpg)

#### DC komponenta a disparita

_DC → stejnosměrný proud_

DC komponenta = průměrná amplituda přenášené vlny

zejména na delší vzdálenosti chceme vybalancovanou (žádnou) DC amplitudu. Přístupy:

- konstantně vyvážený kód - každý symbol je vybalancovaný sám o sobě
- párovaný disparitní kód- balancování naskrz po sobě jdoucími symboly (hlídání disparity)

průběžná disparita = rozdíl počtu 0 a 1. Ideál je vyvážená disparita

### (B07) Techniky zajištění synchronizace

_clock recovery - proces extrahování času_

- oddělený hodinový signál - v praxi se nepoužívá
- self-clocking - hodinový signál je začleněn do dat (musíme přenášet dostatečné množství dat)
  - izochronní self-clocking - hodinový signál je poslán ve stejný čas jako data
    - direct recovery
      - přenášené signály mají v každém bitovém intervalu nějakou změnu - změna udává tiky hodin
      - techniky: redundant coding
    - indirect recovery
      - není zajistěn neustálý tikot, synchronizace chvíli vydrží, musíme se vyhnout dlouhým stejným sekvencím
      - techniky: bit stuffing, block coding
  - neizochornní self-clocking - hodinový signál je poslán v jiný čas než data, nepoužívá se

### (B08) Redundantní kódování, bit stuffing, scrambling

#### Rendundantní kódování

Každý bitový interval obsahuje alespoň jednu změnu (0 → 1 nebo 1 → 0). Zajištěna synchronizace, ale 100% overhead.

Příklady:

- Manchester
  - směr změny uprostřed bitvého interalu určuje datový bit → hodinový signál je uprostřed bitového intervalu
  - self-clocking, DC vyvážený, nevhodné pro velké objemy dat a velké vzdálenosti
  - využití: ethernet (10 MB/s), NFC
- Bipolar RZ

#### Bitový stuffing

V případě dlouhé sekvence stejných symbolů vloží odesílatel opačný bit (příjemce bit odstraní). Zajištěna synchronizace, limitně 0% overhead.

#### Scrambling

Bity první namícháme s pseudo-náhodnou sekvenci (příjemce musí být schopen vygenerovat stejnou sekvenci pro zpětnou rekonstrukci). Zlepšuje disparitu a synchronizaci.

### (B09) Blokové kódování

Před posláním vezme se skupina n bitů a přeloží se na základě daného mapování na $k>n$ bitů. Př. 0001 → 01001

Funkce:

- výsledná sekvence má hodně změn (0/1)
- pro jednu vstupní máme více výstupních sekvencí (lze vybrat vhdonou pro vybalancování)
- některé výstupní kódy jsou navíc (používají se jako řídící signály nebo detekce chyb)

Příklady:

- 4B5B
  - 4b → 5b
  - self clocking, DC balanced jenom se scramble, disparita nevyvážená
  - 25% overhead
  - použití: fast ethernet
- 8b/10b
  - 5+3b → 6+4b
  - garantuje synchronizaci, self clocking, DC vyvážený, omezená disparita
    - běh nejvýše 5 stejnýh bitů za sebou
    - maximální disparita $\pm$ 2
  - 25% overhead
  - použití: Gigabit Ethernet (1GB/s), HDMI, SATA, USB 3.0

### (B10) Přenosy v přeloženém pásmu (passband)

[článek](https://resources.system-analysis.cadence.com/blog/msa2021-types-of-digital-modulation)

modulované - Přenášíme sinusovku (nosnou harmonickou vlnu) → samotná vlna nenese informaci, informace je reprezentovaná **změnami** ve vlnovém parametru

![analog modulation](./images/analogmodulation.jpg)

dva typy modulace - analogová a digitální. Jediný rozdíl je v tom, že u digitální modulace rozeznáváme omezené (diskrétní) množství stavů (česky - buď reprezentujeme danou změnu jako 0 nebo jako 1). Př. (vztaženo k obrázku nahoře):

- amplituda
  - 1 → vyšší amplituda
  - 0 → nižší amplituda
- frekvence
  - 1 → vyšší ferkvence
  - 0 → nižší frekvence
- phase
  - 1 → vyšší
  - 0 → nižší

klíčování = digitální modulace. Techniky:

- Amplitude-shift keying (ASK) - odlišné amplitudy
- Frequency-shift keying (FSK) - odlišné frekvence
- Phase-shit keying (PSK) - odlišné phases

Pozorování:

- menší zkreslení → přenos na větší vzdálenost → vyšší využití (bezdrátová média a optika)
- vyšší frekvence
- typické pro bezdrátové připojení a optické kabely

### (B11) Kvadraturní amplitudová modulace (QAM)

informace reprezentována změnami v parametrech vlny (analogová a digitální modulace)

alternativy:

- 16-QAM (16 stavů → 4b symboly)
- 64-QAM (64 stavů → 6b symboly) - Wifi 2/3/4, DVB-T
- 256-QAM (256 stavů → 8b symboly) - WiFi 5, DVB-T2
- 1024-QAM (1024 stavů → 10b symboly) - WiFi 6

#### 16-QAM

- vysíláme dvě vlny posunuté o $\frac{\pi}{2}$
- u první vlny amplitudová modulace (3 možné stavy → na obrázku níže reprezentované jako kružnice)
- u druhé fázová modulace (12 možných stavů → na obrázku níže reprezentované jako úsečky)
- celkem 36 možných stavů → používáme pouze 16 z nich (ty, které lze od sebe snadno rozlišit)
- namapujeme stavy na hodnoty (Greyovy kódy) - dva sousední stavy se řeší právě v jednom bitu (případná chyba je malá)

![16-qam](./images/16qam.jpg)

### (B12) Zajištění transparence

Aby linková vrstva fungovala, musí přenášet užitečná data a kontrolní singály a rozeznávat je od sebe

- Užitečná (data pro vyšší vrstvy) - nelze je modifikovat, respektive můžeme, ale příjemce je musí umět zpětně zrekonstruovat
- Řídící signály - je třeba umět je detekovat a interpretovat.

Transparence = rozlišování užitečných dat od signálů

Strategie pro proudové přenosy:

- oddělená přenosová cesta - nepoužívá se
- escaping - dva módy data/signály. Je potřeba mít přepínací mechanismus mezi módy

strategie pro blokové přenosy:

- framing

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
