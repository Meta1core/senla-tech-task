# senla-tech-task

The data file is located in "src/main/resources/data_storage.txt"
___________________
File format: <br />
The first line stores atm values in the following order (ATM Id, ATM money balance, ATM name, ATM address) <br />
The next lines stores payment cards data in the following order (Card number, Card pin-code, Card money balance, amount of invalid login attemps, lock date in milliseconds) <br />
______
Card format - ####-####-####-#### <br />
Pin-code format - #### <br />
Data storage separator - " " <br />
____________
Currently added two test cards, but can be added more   <br />
#1 Card number - 1111-2222-3333-4444 Pin-code - 1234, amount of money - 500001    <br />
#2 Card number - 4444-3333-2222-1111 Pin-code - 4321, amount of money - 1 <br />
Amount of money in the ATM - 500000
