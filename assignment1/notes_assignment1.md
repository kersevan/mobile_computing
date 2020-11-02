- a cafeteria's order android app includinf loyalty campaigns
- 2 android aplikaciji: terminal, customer
- 1 http strežnik: server

## REGISTRACIJA
customer - server  
potrebni podatki:  
    - name  
    - payment info  
    - NIF (fiskalna številka)  
    - [nickname/password]?  
    - 16 bytov certifikat/UUID

- android library RSA z dolžino 512 bitov, ustvari public in private key  
- android keystore za shranjevanje ključev 
- ključe ustvari android in se pošlje na server; server nato ustvari UUID?

## MESSAGE
- SHA-256
- pošlje se message + signature
- prejemnik nad sporočilom izračuna SHA-256 in to primerja s signatureom