# StockExchange

U ovom repozitorijumu nalaze se frontend i backend aplikacije za jednostavni stock exchange. Potrebno je pokrenuti frontend na portu 4200 i backend na portu 9090. Takođe, potrebno je kreirati bazu u Postgresu sa nazivom Stocks. Šema baze i test podaci kreiraju se prilikom prvog pokretanja projekta. U util folderu nalazi se DataLoader u kojem se nalazi spisak korisnika za testiranje (svi koriste isti password -> 'sifra'). 

Svi exchange order-i unutar aplikacije odnose se na istu akciju radi jednostavnosti. Prilikom kreiranja order-a korisnik bira da li je market/limit tipa i da li je buy/sell. Kad se order kreira počinje order matching. Ukoliko je order matching makar delimično moguć, on se odmah izvršava i korisnik koji je napravio order dobija obaveštenje o tome putem HTTP Response. Korisnici sa čijim orderima se match-ovao dobijaju obaveštenje putem websocketa na koji se automatski povezuju prilikom uspešnog logovanja na sistem. Da bi korisnik pregledao postojeće ordere ili napravio novi, mora biti ulogovan. 

Ukoliko postoje neki problemi sa frontend-om ili u njegovom povezivanju sa backend-om, ispod su navedeni API pozivi:

Login:\
	POST\
	http://localhost:9090/users/login \
	Body:\
		{
    			"email":"john.doe@gmail.com",
    			"password":"sifra"
		}


Kreiranje order-a:\
	POST\
	http://localhost:9090/stockorders/create \
	 + Authorization header sa jwt \
	Body: \
		{
    			"amount":6,
    			"price":600,
    			"stockOrderMatchingType":0,
    			"stockOrderType":1
		} \
	stockOrderMatchingType -> Market = 0 , Limit = 1 \
	stockOrderType -> Buy = 0 , Sell = 1 \

Dobavljanje top 10 ordera: \
	GET \
	http://localhost:9090/stockorders/gettopbuying \
	 + Authorization header sa jwt \
	GET \
	http://localhost:9090/stockorders/gettopselling \
 	 + Authorization header sa jwt 
	
