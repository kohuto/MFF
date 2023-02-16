## Okruh A

### Základní pojmy a paradigmata

##### Přenos dat

- nezpracujeme a neukládáme, data se nemění
- parametry: vzdálenost, objem dat, rychlost, zpoždění
- přenosová cesta - prostě cesta sender-recipient, jakýkoli způsob
	- kanál - jednosměrná cesta
	- okruh - obousměrná cesta
	- simplexní mód - cesta umožňuje pouze v jednom směru
	- poloduplexní mód - můžeme přenášet oběma směry, ale ne neustále - většinou se to musí střídat
	- plný duplex - kdykoli můžeme přenášet oběma směry
- přenos:
	- unicast - 1 odesílatel a 1 zamýšlený příjemce
	- anycast - 1 odesílatel a jakýkoli jeden příjemce z dané skupiny
	- multicast - 1 odesílatel a více příjemců v multicastové skupině (dynamické/statické)
	- broadcast - jakýkoli dosažitelný příjemce


##### Síť

- soustava uzlů propojených síťovými prvky
- uzly - kdokoli, kdo chce komunikovat
- síťové prvky - aktivní (repeaters, switche, routery) a pasivní (kabely)
- cíl sítí - poskytují služby (televize, rozhlas, hovory, video, mail, zprávy, webové stránky)

##### Služby

- různé úrovně abstrakce, mohou být stavěny na sobě
- různé protokoly, technologie, požadavky a očekávání



#### Proudové a blokové přenosy

- **proudový přenos **- stálý proud dat bez nějakého logického rozdělení a struktury
- zachovává pořadí, dobré pro point-to-point (jeden příjemce), např. L1 - Ethernet, Wifi, L4 - TCP
- **blokový přenos **- data dělena do rozumně velkých jednotek - bloků (velikost fixní nebo volitelná), bloky zasílány jednotlivě
- bloky nemusí být seřazené po sobě, hodí se nějaké mezery - nemusí se zasílat najednou
- mohou být přidána metadata - adresa příjemce/odesílatele při nepřímé cestě, identifikace přenosu - použití stejné cesty více službami, číslo pořadí bloku
- L2 ethernet *frames*, ATM *cells*, L3 IPv4 *datagram*, IPv6 *packety*, L4 TCP *segmenty*, UDP *datagramy*, L7 HTTP *messages*
- **kombinace**
	- bloky přes proud *framing*
	- bloky přes bloky *encapsulace*
	- proud přes bloky

#### Přepojování okruhů

- nejdříve vytvořen **přenosový okruh** na vyžádání (fyzické spojení, na vyšších vrstvách virtuálně spojené), pak je vše přímo posláno
- založeno na **rezervované kapacitě** - povoluje *spolehlivé přenosy*
- iluze *přímého spojení* - nízká latence, zanechává pořadí
- podpora proudů i bloků
- vždy **spojované**
- většinou telekomunikační sítě PSTN (původně ruční, poté elektronické, teď digitální)

#### Přepojování paketů

- každý blok je zařazen a přeposlán samostatně
- každý interface má vstupní a výstupní buffer, tam uloží došlé bloky - přes ně to iteruje a zpracovává, dá je to do výstupního a pošle co nejdříve
- založeno na limitované **sdílené kapacitě** (přenosová kapacita cesty, výpočetní kapacita switchovacích vrcholů)
- vysoká, proměnlivá, nepředvídatelná latence, závisí i na konkrétní vytíženosti, sdílená kapacita nemusí být dostačující
- pouze bloky (nutná metadata - identifikace příjemce a odesílatele)
- většinou počítačové sítě - robustní a odolné vůči selháním a výpadkům, L2 ethernet switche, L3 IP routery
- **spojované** - virtuální okruhy, cesta je vytvořena pouze virtuálně, přiřazeno VCI, jednotlivé síťové prvky ví o cestě, každý blok přepojen dle VCIs přes stejnou cestu, L2 ATM
- **nespojované** - datagramové služby

#### Spojované přenosy

- obě strany vytvoří, udržují a ukončí vzájemné spojení
- pořadí dat zachováno
- přiřazení unikátního přenosového ID
- vytvoří - obě strany existují, mohou se najít a souhlasí s komunikací, mohou se dohodnout na parametrech, mohou vytvořit cestu a alokovat zdroje
- ukončí - zdroje a okruhy se uvolní
- operace se stavy - strany jsou vždy v nějakém stavu, změny jsou korektní a koordinované, nestandartní situace musí být detekovány
- např. telefonní hovor (vytočení čísla, tvorba spojení, hovor, ukončení a zavěšení)
- L2 ATM, L4 TCP, L7 HTTP, SMTP, POP3

#### Nespojované přenosy

- posílání jednotlivých zpráv **datagramů** nezávisle na sobě
- žádné spojení, příjemce nemusí existovat/chtít komunikovat, nic není vytvořeno ani ukončeno, žádné stavy
- pořadí dat nezachováno, mohou jít různými cestami
- musí existovat plná identifikace příjemce
- např. pošta
- L4 UDP, L3 IP, ICMP, L2 ethernet

#### Virtuální okruhy

- **spojované přepojování packetů** - virtuální okruhy, cesta je vytvořena pouze virtuálně
- přiřazeno VCI virtual circuit identifier, jednotlivé síťové prvky ví o cestě
- každý blok přepojen dle VCIs přes stejnou cestu, L2 ATM

#### Datagramová služba

- **nespojované přepojování packetů** - datagramové služby

#### Spolehlivé přenosy

- odesílatel se stará, aby došlo vše
- chyby detekovány a odstraněny
- detekce - parita, kontrolní součet, CRC - avšak je nemožné zkontrolovat všechny chyby a rozsah poškození
- oprava - samoopravné kódy, opakovaný přenos - pozitivní a negativní ack
- L4 TCP
- více zpráv (plus acks), není pravidelné doručování - zpoždění, lehce větší zprávy, nutná větší výpočetní a přenosová kapacita
- spolehlivost není nikdy absolutní

#### Nespolehlivé přenosy

- chyby nedetekovány a neodstraněny, přenos běží dále
- spolehlivost je vždy relativní, někdy ok, jindy fail
- např. multimedia - důležitá pravidelnost a nízká latence
- L4 UDP, L3 IP, L2 ethernet, ATM

#### Ne/garantované služby

- **garantované služby** - budou dostatečné zdroje pro všechny přenosy, musí být předem rezervované, realizováno přepojováním okruhů, exkluzivní kapacita pro dané strany
- musí být dostatečně velká, aby pojala maximální zátěž, nevyužitá kapacita je k ničemu, neefektivní a drahé (všechno je pro maximum zátěže)
- **negarantované služby **- dostatečné zdroje mohou chybět, realizováno přepojováním packetů, sdílená kapacita - vše uzpůsobeno průměrnému zatížení
- když nejsou dostupné zdroje - packety se musí zahodit, avšak jaké ...

#### Princip Best Effort

- všechny packety doručeny, dokud to je možné, avšak při zahazování packetů zahodíme jakýkoli
- L3 IP, L2 ethernet, ATM

#### Quality of Service

- cokoli jiného než best effort
- **relativní QoS **- prioritizace, lepší podmínky pro určitý druh dat
- **absolutní QoS** - rezervace, stejné podmínky nezávisle na situaci a zatížení, zdroje nutno rezervovat - možné odmítnutí, podobné přepojování okruhů 

#### Svět telekomunikačních sítí

- starý - komunikace je strategické, chytrá síť, dumb zařízení
- síťové prvky jsou pro jeden účel, jednodušší a centrální správa, drahé a neflexibilní
- zařízení jsou jednoduchá
- **přepojování okruhů, spojované, spolehlivé, garantované** QoS
- nedostatečné zdroje - exkluzivní rozdělení zdrojů, strategické - regulace, postupně odvobození
- uživatelé a majitelé většinou různí
- oddělené sítě a odlišné, ale snaha o integraci

#### Svět počítačových sítí

- dumb síť, chytrá zařízení
- síť minimalistická, cílem přenos dat bez pochopení, levnější, přímější a flexibilní
- **přepojování packetů, nespojované, nespolehlivé, negarantované** Best Effort
- zdroje nejsou tolik limitovány, technické faktory důležitější, svobodné od začátku, standardizace a koordinace, abychom zachovali kompatibilitu
- uživatelé a majitelé většinou stejní
- oddělené sítě a odlišné, ale snaha o integraci

#### Hospodaření se zdroji

- Moorův zákon - počet tranzistorů se v integrovaném obvodu za každé dva roky zdvojnásobí - historický trend, drží se již přes 50 let, tedy cena výpočetní síly se o polovinu zmenší
- Gilderův zákon - přenosová kapacita se ztrojnásobí každý rok
- Disk zákon - úložná kapacita se zdvojnásobí každý rok



### Taxonomie počítačových sítí

##### Distribuční sítě

- **broadcast** jeden všem dosažitelným (žádné routování ani přeposílání)
- televize, radio
- DVB, DAB

##### Sítě s přepojováním

- **unicast** jeden na jednoho (routování a přeposílání)
- přepojování okruhů - vždy spojované a garantované, blokové/proudové, ne/spolehlivé
- přepojování packetů - vždy blokové, spojované - virtuální okruhy (ne/spolehlivé a best effort/QoS), nespojované - datagramové služby (ne/spolehlivé a best effort/relativní QoS)

##### Typy sítí

- telekomunikační
- datové
- počítačové

##### Bezdrátové přístupové sítě

- nevyžadují výkopy, avšak vhodné frekvence (některé placené)
- možnosti pohybu:
	- mobilní - umožňuje i během pohybu
	- nomadické - nemožné během pohybu
	- fixní - nesmí se hýbat
- operační principy:
	- point to point
	- point to multipoint (mobilní, pevné bezdrátové, wifi)



#### Telekomunikační sítě

- pouze jeden cíl, broadcast (TV/radio) nebo switched (mobilní síť)
- chytrá síť, hloupá zařízení
- infrastruktura:
	- páteřní část/transportní - propojuje všechny hlavní komponenty celé infrastruktury, malé množství, velké vzdálenosti, optická vlákna
	- přístupová část - umožňuje připojení koncových uživatelů na páteřní část, Point of Presence je propojení páteře a přístupů, Customer Premises - místo, kde se vyskytují koncoví uživatelé

#### Pevná telefonní síť

- v ČR SPT Praha - SPT Telecom - Český Telecom - Telefonica O2 CR - O2 CR - **CETIN **(Česká telekomunikační infrastruktura)
- core 38k km optiky, access 20m km metaliky
- infrastruktura:
	- páteřní část - hierarchie telefonních ústředen (mezinárodní, tranzitní, lokální, předsunuté Remote Subscriber Units RSU)
	- přístupová část
		- POP - lokální ústředny/RSU s hlavním rozvaděčem, z něho spoustu lokálních smyček - kroucené dvoulinky do CP, na cestě mohou být síťové rozvaděče, zhruba do 5 km okolo RSU
		- CP - domy, kanceláře

#### Mobilní síť GSM

- páteřní část - Network Switching Subsystem NSS, Mobile Switching Center MSC - brána MSC do různých sítí
- přístupová část - Base Station Subsystem BSS
	- **GERAN** (GSM EDGE Radio Access Network) složený z Base Station Controller BSC a **Base Transceiver Station BTS** (komunikace koncových uživatelů se sítí, typicky několik antén, jedna BTS je jedna buňka - jedna oblast, může mít několik sektorů, každá různou frekvenci)

#### Přístupové sítě (pevné)

- pokrývají celé území, musí být u všech potenciálních zákazníků
- musí být na veřejných místech - výkopy, plánováno předem, formální povolení, drahé a komplikované
- budované předem, využití nejlepších budoucích technologií, přehnaně obří
- **poslední míle** - poslední část sítě, několik km přístupové sítě, z pohledu poskytovatele (zákazník má první míli)
- nová síť - drátová/bezdrátová, drahé, nebo využití stávající infrastruktury - překryvné sítě

#### Překryvné přístupové sítě

- nová síť vybudována na té staré - modifikace nebo rozšíření staré technologie, původní funkčnost je zachována
- použijeme jiné frekvence nebo zapouzdření dat
	- fixní telefonní sítě (kroucené dvojlinky v lokálních smyčkách)
	- elektrické sítě (drátování silové elektřiny)
	- síť kabelové televize (koaxiální kabely)

#### Technologie xDSL

- Digital Subscriber Line

- tradiční fixní telefonní síť Plain Old Telephone Service POTS (kovové kroucené dvojlinky, původně hlasové hovory a frekvence)
- užití: nehlasové frekvence nad 3,4 kHz, základem je frekvenční multiplex, digitalizace, limitní je vzdálenost
- technické řešení:
	- DSL modem - splitter - lokální smyčka - DSLAM
	- CP část - modem (převod do/z analogu) a splitter (slučuje/rozděluje frekvenční pásma)
	- POP část (lokální ústředna/RSU) - jediná jednotka DSLAM (DSL Access Multiplexer) zařídí přesun datových přenosů do oddělené sítě
- nejdříve ADSL (Asymmetric) na větší vzdálenosti, pak VDSL2 (Very High-Speed) na malé vzdálenosti

#### Technologie PLC 

- Power-Line Communication

- původní elektrická síť - střídavý proud 230 V a 50 Hz
- užití: využití vyšších frekvencí, frekvenční multiplex, různé standarty v různých zemích, radiové emise nestíněných drátů - šum, hlučné prostředí, útlum
	- long haul - nízká frekvence, správci monitorují a udržují
	- last mile - drahé, Broadband over Power Line (BPL) - střední napětí mezi transformátory, nižší napětí doma
	- last meter - doma, HomePlug (za domácím elektroměrem - domácí sítě, třeba 500 Mb/s)

#### Technologie DOCSIS

- Data over Cable service interface specificaiton EuroDOCSIS (40 Mb down, 10 Mb up, potom 10 Gb down, 6 Gb up)

- tradiční kabelová televizní síť Community Antenna TElevision CATV (koaxiální kabely, původně analogové a jednosměrné)
- přístupová síť Hybrid Fiber Coaxial (HFC)
	- kombinace optiky a koaxiálů
	- POP - CMTS Cable Modem Termination system zařízení v distribučním hubu (analogicky k DSLAM)
	- optické kabely do optických uzlů, převod na koaxiál a pak do CP (tam je Cable Modem CM)

#### Optické přístupové sítě FTTx

- Fiber to the x

- aktivní prvky - musí být zapojeny do elektřiny, vyšší přenosová kapacita a vzdálenost, dražší
- pasivní prvky - mohou být zakopány, menší kapacita a vzdálenost, ale dostatečné
- většinou jako passive optical networks PON
- čím blíže k zákazníkovi, tím lépe - avšak dražší, takže od POP kombinace s jinou technologií
- fiber to the home/building/curb/node (DOCSIS v HFC)

#### Privátní datové sítě

- přenos dat v digitální formě, hloupá síť, původ v telekomunikacích
- uživatel je vlastník
- uživatel si vše rozhoduje, je to ale drahé
- např. datová síť ministerstva vnitra (jistí česká pošta, integrovaný záchranný systém)

#### Veřejné datové sítě

- přenos dat v digitální formě, hloupá síť, původ v telekomunikacích
- využíváno zákazníky komerčně - kdo platí, má, vlastníci většinou nepoužívají - operátoři
- platba na základě objemu dat, počtu spojení, nutná dokumentace jak se spojit, adresovat, posílat data
- výhodnější pro menší objekty, flexibilní - jako služba, sdíleno uživateli - aspekt bezpečnosti
- vlastník vše rozhoduje, větší zisky

#### Virtuální privátní datové sítě

- přenos dat v digitální formě, hloupá síť, původ v telekomunikacích
- sdílená infrastruktura, iluze privátní sítě (jednotlivé sítě jsou od sebe oddělené a neviditelné)
- uživatelé jsou firmy
- levnější, nezávislost v rozhodování (základ vlastník, ostatní zákazník)

#### Sítě PAN, LAN, MAN, WAN

- PAN Personal Area - osobní prostor, 1 - 10 m
- LAN Local Area - domácnost, budova, firma, 10 m - 1 km
- MAN Metropolitan - univerzita, město, 1 km - 100 km
- WAN Wide Area - regiony, státy, planeta

#### Personal Area Networks

- propojení v osobním prostoru okolo jedné osoby
- stabilní - počítač, klávesnice, tiskárna, mobilní - mobil, tablet
- kabelové - USB, bezdrátové - wifi, bluetooth

#### Local Area Networks

- propojení blízkých počítačů a zařízení
- LAN širší - jakákoli blízká síť i s routery
- LAN užší - uzly jsou propojeny na L1 a L2 (mohou být repeatery a switche, ale ne routery)
- technologie - ethernet, wifi
- oproti WAN nižší latence, vyšší spolehlivost, systematičnost, limitace přístupnosti uzlů

#### Metropolitan Area Networks

- propojení jednotlivých LAN
- PASNET (Prague academic and scientific network) - rychlé mezi univerzitami (Akademie věd, UK, CVUT)
- MEPNET - privátní pražská síť
- technologie - gigabit ethernet, ATM, WiMAX
- vlastníkem skupina organizací, město, využívají i jiní lidé, přes veřejná místa

#### Wide Area Networks

- propojení MAN nebo LAN
- přenos přes velké vzdálenosti (přes veřejná místa a hranice států)
- vlastníkem velká organizace, poskytovatelé a telekomunikační operátoři
- CESNET - národní infrastruktura pro vědu, vývoj a vzdělání
- technologie L1 cesty - optika, L2 technologie - ATM, MPLS, Packet over SONET/SDH, L3 protokoly TCP/IP
- oproti LAN vyšší latence, menší spolehlivosti, nesystematické uspořádání, trvalá dostupnost

#### Architektura Internetu

- původně 1 páteřní síť (ARPANET - první WAN packet switching, vojenská od ARPY, NSFNET)
- více komerčních páteřních sítí, které spolu soutěžily, propojeny přes Network access points NAPs
- hierarchie internet service providers ISP - tier 1/2/3
- propojené sítě tieru 1 tvoří páteř
- propojené přes internet exchange points IXP (např. NIX.cz)

#### Peeringové a tranzitní přenosy

- **peering** - dohoda mezi dvěma ISP, přímá výměna dat mezi jejich sítěmi
- **tranzitní** - koncový uživatel nebo ISP platí dalšímu ISP, aby dával přenos na internet

Tier 1

- síť jde kamkoli bez peeringu nebo tranzitu, přímý přístup do jakékoli sítě (Deutsche Telekom, AT&T)

Tier 2

- síť, která peeruje zdarma s některými sítěmi, ale platí si tranzit nebo peer, aby dosáhla alespoň do nějaké části netu

Tier 3

- platí tranzit a peering, aby mohli na internet

#### Intranet, extranet a darknet

- intranet - služby a prostředky pro vnitřní uživatele (sdílené tiskárny, uložiště)
- extranet - služby a prostředky nabízené externím zákazníkům
- internet
- darknet - překryvová síť, anonymní a neveřejná



### Vrstvy a vrstvové modely

#### Principy vrstvových modelů

- sítě jsou komplexní - dekompozice na menší problémy
- hierarchie vrstev, vertikální rozdělení
- každá vrstva má jiný úkol (jiná úroveň abstrakce - bity vs. komplexní služby)
- nižší vrstvy nabízí služby, vyšší vrstvy využívají služby
- definováno veřejné rozhraní, vnitřní detaily jsou skryty (vrstvy jsou na sobě nezávislé, alternativní způsoby přístupu, flexibilita - každá vrstva se může implementovat sama)
- Kolik vrstev? Jaké úkoly? Jaké rozhraní?

#### Vertikální komunikace

- komunikace mezi různými vrstvami ve stejném uzlu nebo aktivním síťovém prvku
- odesílatel - data jsou připravena a předána nižší vrstvě
- příjemce - unpackuje přijatá data a předá je vyšší vrstvě
- jednotlivé vrstvy nemohou být přeskočeny (jen sousedící vrstvy mohou komunikovat)

#### Horizontální komunikace

- entity na stejné vrstvě mezi různými vrcholy nebo aktivními síťovými prvky
- nemůže se nikdy stát mezi vrstvami, protože odpovídající entity jsou vždy na stejné vrstvě
- více přenosů najednou, všechny zahrnuté entity dodržují stejný protokol
- asynchronní - bity/bloky zaslány a je nutno čekat na odpověď
- většinou virtuální charakter - vyšší vrstvy poskytují iluzi přímého horizontálního přenosu (v reálu jen na L1, jinak vertikální)

#### Principy síťových protokolů

- specifikace, jak spolu entity mají komunikovat, musí být dáno předem
- nutné: veřejné rozhraní (vertikální komunikace), komunikační pravidla (horizontální komunikace, povolené a očekávané akce entit v situacích, technicky stavové diagramy), formát dat (vnitřní struktura a složení komponent)
- vždy souvisí s jednou vrstvou, může existovat více protokolů na vrstvě - alternativní nebo komplementární, dokonce mohou být i konkurenční, ale musí se mezi nimi dát rozeznat
- Protocol data unit PDU - jednotka přenosu mezi entitami, různé názvy
	- hlavička (adresy), tělo (data - payload z vyšší vrstvy), někdy koncovka
- maximum transmission unit MTU - maximální velikost payload
- nutná specifikace metadat, velikost bloků, koexistence protokolů

#### Síťové modely a architektury

- **síťový model** - konceptní model popisující jak by měla síť pracovat, počet vrstev, úkoly k vyřešení a jejich rozdělení vrstvám, poskytované služby
- **síťová architektura** - konkrétní implementovaný síťový model, vše výše plus definice protokolů

#### Referenční model ISO/OSI

- Open systems interconnection model
- pochází ze světa komunikací (preference spojovaného a spolehlivého přenosu s QoS)
- 7 vrstev

#### Význam vrstev ISO/OSI

- nižší vrstvy
	- L1 fyzická (posílá jednotlivé bity přes fyzické médium)
	- L2 datová/linková (posílá bloky dat lokální sítí)
	- L3 síťová (směruje a přeposílá packety systémem sítí)
- adaptační vrstva
	- L4 transportní (end-to-end komunikace jednotlivých entit mezi uzly)
- vyšší vrstvy
	- L5 relační (spravuje relace a organizuje výměnu dat)
	- L6 prezentační (automatický převod a serializace dat)
	- L7 aplikační (posílání zpráv a uživatelské služby)

#### Úkoly fyzické vrstvy

- přenos jednotlivých bitů přes dané fyzické médium (nerozumí datům a obecně obsahu)
- média: kovová (kroucená dvojlinka, koaxiál), optická, bezdrátová
- v reálu vždy omezený přenos (rušení, ztráty)
- analogová kvalita přenášena elektrickými signály (kov), světlem (optika), elmag. vlnění (bezdtrátové)
- interpretace analogická nebo digitální
- úkoly: kódování, modulace, časování, synchronizace, šířka pásma

#### Úkoly linkové vrstvy

- posílá bloky dat mezi síťovými rozhraními jednotlivých vrcholů v lokální síti
- iluze přímé cesty (každý vrchol viditelný a dosažitelný), realita jiná - může být komplexní
- typicky vše posíláno všem
- pomocí aktivních prvků bridgů a switchů, interní mechanismy Store&Forward, Cut-Through
- logické uspořádání - logická struktura sítě, definuje jak budou data proudit (sběrnice, hvězda, kruh, ...)
- adresování fyzickými adresami (MAC) - identifikace zamýšleného příjemce (aby mohl být nalezen a aby poznal svá data), musí být unikátní v síti
- ethernet, wifi, bluetooth
- filtrování a přeposílání - mechanismy k nalezení a dosažení příjemce, jinak by se to muselo posílat všem, v bridges a switches
- jistota transparentnosti - kontrolní metadata musí být oddělena od payload (pomocí mezer, frames, ...)
- umožňuje blokový přenos (framing)
	- odesílatel prostě pošle PDU (třeba ethernet frame) do L1, MTU závisí na technologii
	- příjemce dostane proud bitů na L1, frames musí být správně poznány - dle začátku bloku a jeho délky
- spolupráce L1 a L2 - přidávají se extra bity k synchronizaci
- sdílené médium - více vrcholů sdílí přenosovou cestu, pouze jeden může přenášet, dána pravidla přístupu
- řeší to nižší MAC podvrstva (Media access control) a vyšší LLC (logical link control)

#### Úkoly síťové vrstvy

- hop-to-hop směřování a přeposílání paketů přes systém propojených sítí do cílového vrcholu (vědomí více sítí a jejich propojení)
- pakety doručovány jednotlivě přes aktivní prvky routery
- adresace:
	- každý vrchol globálně unikátní adresa, všechny vrcholy v síti stejný prefix
	- interní struktura - jednoduchá výměna uzlů v síti
	- přiřazení bloků adres sítím celkově, přiřazení individuálních adres uzlům v síti
	- např. IPv4 - ale jich je nedostatek - subnetting, supernetting, privátní adresy, NAT, IPv6 ...
- packety přímo dodané (IP adresa příjemce v naší síti - posláno lokálně přes L2 do cíle) nebo nepřímo dodané (paket poslán lokálně přes L2 do routeru, který přepošle)
- lokální L2 doručení - uzel musí být dosažitelný (příjemce při přímém, jinak router), encapsulace - paket je dán do L2 frame, poté zasláno (nutnost překladu IP na MAC)
- směřování - proces výběru optimální přenosové cesty (sekvence routerů, které vedou k příjemci), problém hledání nejkratší cesty - nutnost znát L3 strukturu pomocí routovacích tabulek
- strategie dynamické/statické, izolované/centralizované/distribuované
- přeposílání - zaslání packetů zadanou routovací cestou přes přeposílací tabulky (může ale nemusí být na routeru)
- routovací domény - není možné mít celou routovací tabulku - dekompozice do malých částí - autonomous systems AS
- fragmentace bloků - každá L2 technologie má vlastní MTU, ale packety mohou být větší - nutná fragmentace

#### Úkoly transportní vrstvy

- end-to-end komunikace jednotlivých entit mezi odesílatelem a příjemcem
- nižší vrstvy berou uzly jako jednotky (nerozeznávají služby), vyšší vrstvy implementovány v koncových vrcholech (v routeru je max L3)
- adresování:
	- entity musí být rozlišitelné, unikátní v uzlu, statické - víme dopředu, abstraktní - nezávislé na platformě, implicitní - nezávislé na situaci
	- porty v TCP/IP (nutná pravidla pro příchozí a odchozí směr)
- několik komunikací najednou, ale jen jedna cesta na L3 - multiplexing (sloučení přenosů odesílatelem) a demultiplexing (rozložení příjemcem)

- sockety - datová struktura umožňující aplikacím odesílat, přijímat data (na vyžádání, dynamicky spojené s jednotlivými porty)
- adaptační vrstva - adaptace vyšších vrstev na možnosti nižších vrstev (proud přes bloky, spojované přes nespojované, spolehlivé přes nespolehlivé, QoS přes best effort)
- další služby - kontrola proudu (aby rychlejší odesílatelé nepřehlušili pomalé), kontrola zácpy (aby síť nebyla zahlcena dopravou)

#### Úkoly relační vrstvy

- mechanismus otevření, zavření a udržení relací, organizace dialogu - výměny dat mezi entitami
- původně široká funkčnost, ale nyní se nevyužívá a není implementována
- udržení relací:
	- jedna L5 relace přes více L4 přenosových spojení (jedna přes více konkurenčních - vyšší přenosová kapacita, jedna přes více stejných - jistota pokračování při jedné chybě)
	- více L5 relací přes jedno L4 přenosové spojení (více stejných přes jednu - minimalizace počtu otevřených spojení, více konkurenčních přes jednu - multiplexing více oddělených relací najednou)
- synchronizace entit - iluze synchronní komunikace přes asynchronní L4 (jako při remote procedure call RPC), simplex/half-duplex/full-duplex, prevence zastavení
- tvorba checkpointů na vyžádání, obnovení dat po chybě
- autentifikace (ověření identity), autorizace (určení přístupových práv), šifrování

#### Úkoly prezentační vrstvy

- poskytuje mechanismy k automatické serializaci dat, jejich přenosu a překladů zajišťujících stejnou skladbu na různých platformách
- nutný překlad mezi aplikační a síťovou vrstvou kvůli různým OS, lokacím, HW
- ve skutečnosti většinou vynechána
- textové kódování, pořadí bytů (LE a BE), formáty dat - mantisa, exponenty pro desetinná čísla
- serializace struktur (jednoduchých - set, array, složitých - matice vyšších dimenzí zploští, pointery jsou jiné kvůli address space)
- serializace nějakým protokolem, abstraktní syntaxe (struktura dat popsána v nějakém jazyce ASN.1 Abstract Syntax notation one) a přenosová syntaxe (serializace dat do formátu - BER basic encoding rules)
- v reálu např. používání NoSQL databázemi (apache)

#### Úkoly aplikační vrstvy

- přístup ke komunikačnímu rozhraní, umožňuje aplikaci zasílat a přijímat zprávy, díky kterým se poskytují služby
- původně L7 celá standardizovaná aplikace, v reálu pouze komunikační základ, ne UI
- adresace:
	- identifikace pomocí IRI internationalized resource identifier
	- lokalizace DNS domain name system (hierarchický pro jména domén, překlad do IP adres)
- protokoly SMTP (simple mail transfer protocol), HTTP (hypertext transfer protocol)

#### Architektura TCP/IP

- internet protocol suite/stack
- pochází ze světa počítačů (preference nespojovaného a nespolehlivého přenosu s best effort)
- 4 vrstvy
	- síťové rozhraní (linková vrstva, L1 + L2)
	- síťová vrstva (internetová, L3)
	- transportní vrstva (L4)
	- aplikační vrstva (L7 a části z L5 a L6)

#### Srovnání ISO/OSI a TCP/IP





Oblast C

    Internetworking I
        (C01) Cíle internetworkingu
        (C02) Aktivní a pasivní síťové prvky
        (C03) Propojování napříč vrstvami
        (C04) Principy propojování na L1
        (C05) Funkce opakovačů
        (C06) Vlastnosti opakovačů
        (C07) Přístupová metoda CSMA/CD
        (C08) Přenosová kapacita segmentu
        (C09) Principy propojování na L2
        (C10) Filtrování a cílený forwarding
        (C11) Činnost linkového rozhraní
        (C12) Mechanismus Store&Forward
        (C13) Mechanismus Cut-Through
        (C14) Segmentace sítě
        (C15) Vyhrazená přenosová kapacita
        (C16) Propojovací zařízení na L2
        (C17) Vlastnosti zařízení na L2
        (C18) Principy propojování na L3
        (C19) Činnost síťového rozhraní
    
    Internetworking II
        (C20) Pravidla 80:20 a 20:80
        (C21) Místní L2 broadcast
        (C22) Místní L3 broadcast
        (C23) Cílený L3 broadcast
        (C24) Funkce směrovače
        (C25) Funkce L3 přepínače
        (C26) Rozdíly směrovačů a L3 přepínačů
        (C27) Využití L4 a L7 přepínačů
        (C28) Principy a účel sítí VLAN
        (C29) Koncepty VLAN sítí
        (C30) Logický model VLAN sítě
        (C31) Přístupové VLAN porty
        (C32) Trunkovací VLAN porty
        (C33) Konfigurace VLAN sítí
        (C34) Tagování 802.1q Dot1q
        (C35) Směrování mezi VLAN sítěmi
        (C36) Princip a typy firewallů
        (C37) Demilitarizovaná zóna
        (C38) Aplikační brány
        (C39) Realizace DMZ
        (C40) Paketové filtry a ACL

Oblast D

    Adresy a adresování
        (D01) Principy adresování na L2
        (D02) Adresy EUI-48 a EUI-64
        (D03) Principy adresování na L4
        (D04) Porty a jejich číslování
        (D05) Principy adresování na L7
        (D06) Obecná struktura URI
        (D07) Principy adresování na L3
        (D08) Tvar a zápis IPv4 adres
        (D09) Třídy a prostor IPv4 adres
        (D10) Speciální IPv4 adresy
        (D11) IPv4 multicastové adresy
        (D12) Realizace multicastu na L2
        (D13) Přidělování IPv4 adres sítím
        (D14) Řešení nedostatku IPv4 adres
        (D15) Mechanismus subnettingu
        (D16) Mechanismus supernettingu
        (D17) Mechanismus CIDR
        (D18) Hierarchie registrátorů
        (D19) Závislost IP adres na ISP
        (D20) Koncept privátních IP adres
        (D21) Princip překladu adres
        (D22) Způsob fungování NAT
        (D23) Charakter NAT/PAT vazeb
        (D24) Princip a vlastnosti PAT
        (D25) Způsob fungování PAT
        (D26) Varianty chování NAT/PAT
        (D27) Problémy NAT/PAT
        (D28) Cíle návrhu IPv6 adres
        (D29) Vztah IPv4 a IPv6 adres
        (D30) Tvar a zápis IPv6 adres
        (D31) Základní druhy IPv6 adres
        (D32) Dělení IPv6 unicast adres
        (D33) Globální IPv6 unicast adresy
    
    Protokoly IPv4 a IPv6
        (D34) Vlastnosti protokolu IPv4
        (D35) Struktura IPv4 datagramu
        (D36) Položky IPv4 hlavičky
        (D37) IPv4 Time to Live
        (D38) Nástroj TraceRoute
        (D39) IPv4 kontrolní součet
        (D40) IPv4 doplňky hlavičky
        (D41) Principy IPv4 fragmentace
        (D42) IPv4 varianty detekce MTU
        (D43) IPv4 Path MTU Discovery
        (D44) Proces IPv4 fragmentace
        (D45) IPv4 fragmentační hlavičky
        (D46) Proces IPv4 defragmentace
        (D47) Problémy IPv4 de/fragmentace
        (D48) Principy protokolu ICMPv4
        (D49) Struktura ICMPv4 zprávy
        (D50) Principy protokolu ARP
        (D51) Struktura ARP zprávy
        (D52) Princip ARP cachování
        (D53) Zpracování ARP dotazu
        (D54) Reverzní ARP protokol
        (D55) Nevýhody RARP protokolu
        (D56) Koncept protokolu DHCP
        (D57) Strategie DHCP alokace
        (D58) Chování DHCP klienta
        (D59) Rozdíly IPv6 oproti IPv4
        (D60) Struktura IPv6 paketu
        (D61) Položky IPv6 hlavičky
        (D62) IPv6 rozšiřující hlavičky
        (D63) Formát ICMPv6 zprávy
        (D64) IPv6 fragmentační hlavička
        (D65) IPv6 Path MTU Discovery
        (D66) Identifikace IPv6 toků
        (D67) Využití IPv6 toků